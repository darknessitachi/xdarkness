using System;
using System.Web;
using System.Text;
using System.Web.UI;
using System.Collections.Generic;

using Sky.ImData;
using Sky.ImBusiness;

namespace Sky.WebIm
{
    public partial class refreshuser : Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            switch (Request.Params["flag"])
            {
                case "0":
                    RefreshUser();
                    break;
                case "1":
                    RefreshUserList();
                    break;
                case "2":
                    RefreshUserSetting();
                    break;
            }

            Response.End();
        }

        public void RefreshUser()
        {
            if (Application["online"] != null)
            {
                try
                {
                    OnLineUser onLineUser =
                        (Application["online"] as OnLine).GetOnLineUser(Request.Params["owner"]);

                    int editedStatus = int.Parse(Request.Params["status"]);
                    
                    //在线时间统计
                    if (editedStatus == Profile.onLineStatus)
                    {
                        if (onLineUser.UserStatus == Profile.offLineStatus)
                        {
                            onLineUser.LoginTime = DateTime.Now;
                        }
                    }

                    onLineUser.UserStatus = editedStatus;

                    if (onLineUser.UserStatus == Profile.offLineStatus)
                    {
                        Users user = new Users();
                        user.UpdateOnLineTime(Request.Params["owner"], onLineUser.LoginTime);
                    }

                    Response.Write("1");
                }
                catch
                {
                    Response.Write("0");
                }
            }
            else
            {
                Response.Write("0");
            }
        }

        private void RefreshUserList()
        {
            if (Application["online"] != null)
            {
                Dictionary<string, OnLineUser> onLineList
                   = (Application["online"] as OnLine).OnLineList;

                string owner = Request.Params["owner"];

                StringBuilder userTreeBuilder = new StringBuilder();
                userTreeBuilder.Append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                userTreeBuilder.Append("<result>");

                foreach (KeyValuePair<string, OnLineUser> onLine in onLineList)
                {
                    DateTime current = DateTime.Now;

                    if (!onLine.Key.Equals(owner))
                    {
                        double interval = current.Subtract(onLine.Value.ExitTime).TotalSeconds;

                        if (interval > Profile.interval)
                        {
                            onLine.Value.UserStatus = Profile.offLineStatus;
                        }
                    }
                    else
                    {
                        onLine.Value.ExitTime = current;
                    }

                    userTreeBuilder.AppendFormat(
                        "    <user userid=\"{0}\">",
                        onLine.Value.LoginName
                        );

                    userTreeBuilder.AppendFormat(
                        "    <status>{0}</status>",
                        onLine.Value.UserStatus
                        );

                    userTreeBuilder.AppendFormat(
                        "    <headimg>{0}</headimg>",
                        onLine.Value.HeadImg
                        );

                    userTreeBuilder.AppendFormat(
                        "    <scratch>{0}</scratch>",
                        onLine.Value.Scratch
                        );

                    userTreeBuilder.AppendFormat(
                        "    <username>{0}</username>",
                        onLine.Value.UserName
                        );

                    userTreeBuilder.Append("</user>");
                }

                userTreeBuilder.Append("</result>");
                Response.Write(userTreeBuilder.ToString());
            }
            else
            {
                Response.Write("0");
            }
        }

        private void RefreshUserSetting()
        {
            if (Application["online"] != null)
            {
                OnLineUser onLineUser =
                    (Application["online"] as OnLine).GetOnLineUser(Request.Params["owner"]);

                onLineUser.HeadImg = Request.Params["headimg"];
                onLineUser.Scratch = Request.Params["scratch"];
                onLineUser.UserName = Request.Params["username"];
              
                Response.Write("1");
            }
            else
            {
                Response.Write("0");
            }
        }
    }
}