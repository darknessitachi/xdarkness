using System;
using System.Web.UI;
using System.Web.Caching;

using Sky.ImData;
using Sky.ImBusiness;

namespace Sky.WebIm
{
    public partial class removeuser : Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (Application["online"] != null)
            {
                try
                {
                    string logOut = Request.Params["logout"];

                    OnLine onLine = Application["online"] as OnLine;
                    OnLineUser onLineUser = onLine.GetOnLineUser(logOut);

                    onLine.RemoveLoginer(logOut);

                    Users user = new Users();
                    user.UpdateOnLineTime(logOut, onLineUser.LoginTime);

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

            Response.End();
        }
    }
}