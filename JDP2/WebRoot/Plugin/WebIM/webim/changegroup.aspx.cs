using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.HtmlControls;

using Sky.ImData;

namespace Sky.WebIm
{
    public partial class changegroup : Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            string method = Request.Params["method"];

            if (method == null)
            {
                return;
            }

            if ("getUserGroups".Equals(method.Trim()))
            {
                GenerateUserGroups();
            }
            else if ("changeUserGroup".Equals(method.Trim()))
            {
                ChangeUserGroup();
            }
            else if ("changePassword".Equals(method.Trim()))
            {
                PasswordChange();
            }
            else if ("changeName".Equals(method.Trim()))
            {
                ChangeGroupName();
            }
            else if ("deleteGroup".Equals(method.Trim()))
            {
                DeleteGroup();
            }
            else if ("addGroup".Equals(method.Trim()))
            {
                AddGroup();
            }
        }

        /// <summary>
        /// 添加分组
        /// </summary>
        public void AddGroup()
        {
            string groups = Request.Params["groups"];
            string userId = Request.Params["userId"];

            groups = groups.Replace("，",",");
            string[] groupNames = groups.Split(',');

            UserGroup userGroup = new UserGroup();
            string groupIds = "";
            string changedGroupNames = "";

            for(int i=0;i<groupNames.Length;i++){

                string groupName = groupNames[i];
                string tempGroupName = groupName;
                string strGroupId = null;

                if (groupName == "陌生人")
                {
                    //strGroupId = userGroup.GetStrangerGroupId();
                    WriteMessage("不允许创建名称为‘陌生人’的组！");
                    return;
                }
                else if (groupName == "自己")
                {
                    WriteMessage("不允许创建名称为‘自己’的组！");
                    return;
                }
                else
                {
                    strGroupId = userGroup.ExistGroupByName(tempGroupName, userId);
                    while (strGroupId != null && strGroupId != "")
                    { // 如果存在该组，将该组最后一个数字加1，没有数字，则设置为1
                        tempGroupName += "(1)";
                        strGroupId = userGroup.ExistGroupByName(tempGroupName, userId);
                    }
                }
                
                // 创建该组
                string createGroupId = userGroup.CreateGroup(tempGroupName, userId);
                if (createGroupId == "")
                {
                    WriteMessage("创建该组失败，请稍候重试！");
                    return;
                }

                changedGroupNames += i + ":" + tempGroupName + ",";
                groupIds += createGroupId + ",";
               
            }

            WriteMessage(groupIds.TrimEnd(',') + "→" + changedGroupNames.TrimEnd(','));
        }

        /// <summary>
        /// 更新组名称
        /// </summary>
        public void ChangeGroupName()
        {
            string groupName = Request.Params["groupName"];
            string groupId = Request.Params["groupId"];
            string userId = Request.Params["userId"];
            UserGroup userGroup = new UserGroup();

            if (groupName == "陌生人"
                || groupName == "自己"
                || userGroup.ExistGroupByName(groupName, userId) != null)
            {
                WriteMessage("0");
                return;
            }

            if (userGroup.ChangeGroupName(groupId, groupName))
            {// 更新组名称成功
                WriteMessage("更新成功！");
            }
            else
            {
                WriteMessage("0");
            }
        }

        /// <summary>
        /// 删除组
        /// </summary>
        public void DeleteGroup()
        {
            string groupId = Request.Params["groupId"];
            string userId = Request.Params["userId"];

            GroupRelation groupRelation = new GroupRelation();
            if (groupRelation.ExistGroupUser(groupId, userId))
            {// 该用户组下还存在用户
                groupRelation.MoveToStrangerGroup(groupId);
            }

            UserGroup userGroup = new UserGroup();
            if (userGroup.DeleteGroup(groupId))
            {// 删除组成功
                WriteMessage("删除成功！");
            }
            else
            {
                WriteMessage("0");
            }
        }

        /// <summary>
        /// 生成组下拉列表div
        /// </summary>
        public void GenerateUserGroups()
        {
            string userId = Request.Params["userId"];
            UserGroup userGroup = new UserGroup();
            DataTable groupList = userGroup.GetUserGroup(userId.Trim());

            StringBuilder groupTreeBuilder = new StringBuilder("<div class=\"itemList\" id=\"divUserGroups\" style=\"padding:1px;width:110px;\">");
            
            for (int i = 0; i < groupList.Rows.Count; i++)
            {
                DataRow group = groupList.Rows[i];
                if (i == 0)
                {
                    groupTreeBuilder.AppendFormat("<a href=\"#\" id='aDefaultUserGroup' groupId='{1}' onmousedown=\"Effect.setUserGroup('{0}','{1}'); return false;\" style=\"font-size: 12px;\">{0}</a>"
                        , group["groupName"], group["id"]);
                }
                else
                {
                    groupTreeBuilder.AppendFormat("<a href=\"#\" groupId='{1}' onmousedown=\"Effect.setUserGroup('{0}','{1}'); return false;\" style=\"font-size: 12px;\">{0}</a>"
                    , group["groupName"], group["id"]);
                }
            }
            
            groupTreeBuilder.Append("</div>");

            WriteMessage(groupTreeBuilder.ToString());
        }

        /// <summary>
        /// 移动好友到所选组
        /// </summary>
        public void ChangeUserGroup()
        {
            string userId = Request.Params["userId"];
            string relationshiper = Request.Params["relationshiper"];
            string groupName = Request.Params["groupName"];

            UserGroup userGroup = new UserGroup();

            string strGroupId;
            if (groupName == "陌生人")
            {
                strGroupId = userGroup.GetStrangerGroupId();
            }
            else if (groupName == "自己")
            {
                WriteMessage("不允许创建名称为‘自己’的组！");
                return;
            }
            else
            {
                strGroupId = userGroup.ExistGroupByName(groupName, userId);
            }

            if (strGroupId == null || strGroupId == "") // 如果不存在该组，须先创建该组
            {
                string createGroupId = userGroup.CreateGroup(groupName, userId);
                if (createGroupId == "")
                {
                    WriteMessage("创建该组失败，请稍候重试！");
                    return;
                }

                strGroupId = createGroupId;
            }
            
            GroupRelation groupRelation = new GroupRelation();
            bool existGroupRelation = groupRelation.ExistGroupRelation(userId, relationshiper, strGroupId);
            if (existGroupRelation == true)// 如果该好友已经在该组中，则直接返回
            {
                return;
            }

            // 该好友不在该组中,则移动该好友到该组中
            bool flag = groupRelation.UpdateGroupRelation(userId, relationshiper, strGroupId);
            if (flag == true)
            {
                WriteMessage("移动成功！");
            }
            else
            {
                WriteMessage("移动失败，请稍候重试！");
            }
            
        }

        /// <summary>
        /// 修改密码
        /// </summary>
        public void PasswordChange()
        {
            string userId = Request.Params["userId"];// 用户Id
            string passwordBefore = Request.Params["passwordBefore"];// 原始密码
            string passwordNew = Request.Params["passwordNew"];// 新密码

            Users users = new Users();
            if (users.CheckUserExist(userId, passwordBefore))// 如果用户密码正确
            {
                if (users.ChangePassword(userId, passwordNew))// 如果更新用户密码成功
                {
                    WriteMessage("更新密码成功！");
                }
                else
                {
                    WriteMessage("更新密码失败！");
                }
            }
            else
            {
                WriteMessage("1");
            }
        }

        /// <summary>
        /// 辅助函数，输出消息
        /// </summary>
        /// <param name="message">消息</param>
        public void WriteMessage(string message)
        {
            Response.Clear();
            Response.Write(message);
            Response.End();
        }
    }
}
