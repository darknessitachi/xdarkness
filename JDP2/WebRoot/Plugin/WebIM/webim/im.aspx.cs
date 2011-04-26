using System;
using System.Web;
using System.Data;
using System.Text;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.HtmlControls;
using System.Collections.Generic;

using Sky.ImData;
using Sky.ImUtility;
using Sky.ImBusiness;

namespace Sky.WebIm
{
    public partial class im : Page
    {
        private string theme = Profile.defaultTheme;
        private StringBuilder userTreeBuilder = null;

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                if (Request.UrlReferrer != null)
                {
                    BindUser();
                }
                else
                {
                    Response.Write(
                        Profile.RenderErrorPage(theme, Description.UNAUTHOR)
                        );

                    Response.End();
                }
            }
        }

        private void CreateGroupTop(string groupId, string groupName, bool attach, bool custom)
        {
            string liEvent = string.Empty;

            if (attach)
            {
                if (!custom)
                {
                    liEvent = string.Format(
                        " oncontextmenu=\"event.returnValue = false;LayoutGroup.groupManager('{1}', '{2}');\"",
                        theme,
                        groupId,
                        groupName
                        );
                }
                else
                {
                    liEvent = string.Format(
                        " oncontextmenu=\"event.returnValue = false;LayoutGroup.addGroupManager('{1}', '{2}');\"",
                        theme,
                        groupId,
                        groupName
                        );
                }
            }

            userTreeBuilder.AppendFormat(
                "<li class=\"groupTop\" id=\"li{0}GroupTop\" groupid=\"{0}\" onclick=\"LayoutIm.extendGroupList('{1}');\"{2}>",
                groupId,
                theme,
                liEvent
                );

            userTreeBuilder.AppendFormat(
                "<img id=\"img{0}GroupArrow\" groupid=\"{0}\" src=\"../include/themes/{1}/window/arrow_up.png\" alt=\"\" onclick=\"parentElement.click();\"/>&nbsp;&nbsp;{2}<span{3}>(0/0)</span></li>",
                groupId,
                theme,
                groupName,
                liEvent
                );
        }

        private void CreateBuddyList(string userId, string userName, string scratch, string headImage, int userStatus, bool attach)
        {
            string liEvent = "\"";

            if (attach)
            {
                liEvent = string.Format(
                    "LayoutGroup.createContextmenu('{0}', '{1}', 'li{0}blItem');\" ondblclick=\"LayoutIm.toggleChatPanel('{0}', '{1}', '{2}');\"",
                    userId,
                    userName,
                    theme
                    );
            }

            userTreeBuilder.AppendFormat(
                "<li id=\"li{0}blEmpty\" style=\"list-style-type: none; padding-top: 0px; margin-top: -9px;\"></li>",
                userId
                );

            userTreeBuilder.AppendFormat(
                "<li class=\"buddy\" userid=\"{0}\" id=\"li{0}blItem\" isrefresh=\"0\" style=\"list-style-type: none; cursor: pointer;\" {1} {2}",
                userId,
                "onmouseout=\"LayoutIm.setSelectedUserOutColor();\"",
                "onmouseover=\"LayoutIm.setSelectedUserOverColor();\""
                );            

            userTreeBuilder.AppendFormat(
                "oncontextmenu=\"event.returnValue = false;{0} userstatus=\"{1}\"{2}>&nbsp;&nbsp;",
                liEvent,
                userStatus,
                (scratch.Length > 0 ? string.Format(" title = \"{0}\"", scratch) : string.Empty)
                );

            userTreeBuilder.AppendFormat(
                "<img id=\"img{0}HeadImg\" alt=\"\" src=\"{5}{6}\" onmouseover=\"LayoutPersonInfo.createInfoPanel();\" onmouseout=\"LayoutPersonInfo.removeInfoPanel();\" style=\"border: none; width: 32px; height: 32px;\" />&nbsp;" +
                "<div style=\"padding: 0px; margin: -35px 0px 0px 44px;\"><img id=\"img{0}blImg\" alt=\"\" src=\"{2}{3}\" style=\"border: none; width: 8px; height: 8px;\"/>&nbsp;{1}</div>" +
                "<div id=\"div{0}scratch\" style=\"padding: 0px; margin: 5px 0px 0px 44px; color: #808080;\">{4}</div></li>",
                userId,
                userName,
                Profile.userStatusIconPath,
                Profile.userStatusIconList[userStatus],
                (scratch.Length > 0 ? StringHelper.GetSubString(scratch, 10) : "&nbsp;&nbsp;"),
                (headImage.Length < Profile.userHeadImageCount ? Profile.userDefaultHeadImagePath : Profile.userHeadImagePath),
                (headImage.Length < 0 ? Profile.userHeadImage : headImage)
                );
        }

        private void BindUser()
        {
            if (Application["online"] == null)
            {
                bl_top.InnerHtml = "IM";
            }
            else
            {
                hidSender.Value = StringHelper.DesDecrypt(Request.Params["login"]);

                Users users = new Users();
                DataTable userTree = users.GetUserTree();

                UserGroup userGroup = new UserGroup();
                DataTable groupList = userGroup.GetUserGroup(hidSender.Value);

                DataRow[] userRow = userTree.Select(
                    string.Format("id = '{0}'", hidSender.Value)
                    );

                if (userRow.Length == 0)
                {
                    hidCnSender.Attributes["outter"] = "1";
                    changepassword.Attributes["outter"] = "1";
                    hidCnSender.Value = StringHelper.DesDecrypt(Request.Params["userName"]);
                    hidCnSender.Attributes["lname"] = StringHelper.DesDecrypt(Request.Params["loginName"]);
                }
                else
                {
                    if (userRow[0]["theme"].ToString().Length > 0)
                    {
                        theme = userRow[0]["theme"].ToString();
                    }

                    hidCnSender.Value = userRow[0]["username"].ToString();
                    hidrevertMessage.Value = userRow[0]["revertMessage"].ToString();
                    hidCnSender.Attributes["lname"] = userRow[0]["loginname"].ToString();
                    hidCnSender.Attributes["outter"] = userRow[0]["isoutter"].ToString();
                    changepassword.Attributes["outter"] = userRow[0]["isoutter"].ToString();
                }

                GroupRelation groupRelation = new GroupRelation();
                DataTable relationList = groupRelation.GetGroupRelation(hidSender.Value);

                userTreeBuilder = new StringBuilder();
                OnLineUser onLineUser = new OnLineUser();

                foreach (DataRow group in groupList.Rows)
                {
                    bool flag = false;

                    if (group["groupname"].ToString().Equals(Profile.unknowGroup))
                    {
                        flag = true;
                        hidSender.Attributes["unknow"] = group["id"].ToString();
                    }

                    CreateGroupTop(group["id"].ToString(), group["groupname"].ToString(), true, flag);

                    DataRow[] relationshiperList = relationList.Select(
                        string.Format("usergroup = '{0}'", group["id"])
                        );

                    userTreeBuilder.AppendFormat(
                           "<ul class=\"group\" id=\"ul{0}Group\" groupid=\"{0}\" style=\"display : none;\">",
                           group["id"]
                           );


                    foreach (DataRow relationshiper in relationshiperList)
                    {
                        DataRow[] user = userTree.Select(
                            string.Format("id = '{0}'", relationshiper["relationshiper"]),
                            "username"
                            );

                        if (user.Length == 1)
                        {
                            int userStatus = onLineUser.GetUserStatus(user[0]["id"].ToString(), Application["online"]);

                            CreateBuddyList(
                                user[0]["id"].ToString(),
                                user[0]["username"].ToString(),
                                user[0]["scratch"].ToString(),
                                user[0]["headImg"].ToString(),
                                userStatus,
                                true
                                );
                        }
                    }

                    userTreeBuilder.Append("</ul>");
                }

                //创建预定义自己分组
                CreateGroupTop("owner", Profile.ownerGroup, true, true);
                userTreeBuilder.Append("<ul class=\"group\" id=\"ulownerGroup\" groupid=\"owner\" style=\"display : none;\">");

                int status = onLineUser.GetUserStatus(hidSender.Value, Application["online"]);
                statusSettings.Attributes["userstatus"] = status.ToString();

                CreateBuddyList(
                                hidSender.Value,
                                hidCnSender.Value,
                                (userRow.Length > 0 ? userRow[0]["scratch"].ToString() : string.Empty),
                                (userRow.Length > 0 ? userRow[0]["headImg"].ToString() : string.Empty),
                                status,
                                false
                                );

                userTreeBuilder.Append("</ul>");
                buddylist.InnerHtml = userTreeBuilder.ToString();
            }

            if (hidCnSender.Attributes["outter"].Equals("1"))
            {
                imgLogout.Attributes["style"] += "display: none;";
                changepassword.Attributes["style"] = "display:none";
            }

            hidSender.Attributes["theme"] = theme;
            bl_top.InnerHtml = string.Format("IM {0}", hidCnSender.Value);
            hidSender.Attributes["current"] = DateTime.Now.ToString("yyyy-MM-dd");
            imgLogout.Src = string.Format("../include/themes/{0}/window/signoff.png", theme);
            joinroom.Src = string.Format("../include/themes/{0}/window/joinroom.png", theme);
            addbuddy.Src = string.Format("../include/themes/{0}/window/addbuddy.png", theme);
            changepassword.Src = string.Format("../include/themes/{0}/window/changepassword.png", theme);
        }

        protected override void  Render(HtmlTextWriter writer)
        {
            Profile.RenderHtmlHead(writer, theme);
            base.Render(writer);            
        }
    }
}