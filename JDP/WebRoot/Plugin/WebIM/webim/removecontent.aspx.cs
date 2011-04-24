using System;
using System.Web;
using System.Text;
using System.Web.UI;

using Sky.ImBusiness;

namespace Sky.WebIm
{
    public partial class removecontent : Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            try
            {
                string message = string.Empty;
                StringBuilder resultBuilder = new StringBuilder("");
                string[] result = Assistant.GetContent(Request.Params["receiver"]);

                if (result != null)
                {
                    resultBuilder.Append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                    resultBuilder.Append("<result>");
                    resultBuilder.AppendFormat("    <sender>{0}</sender>", result[0]);
                    resultBuilder.AppendFormat("    <isouter>{0}</isouter>", result[2]);
                    resultBuilder.AppendFormat("    <username>{0}</username>", result[3]);
                    resultBuilder.AppendFormat("    <loginname>{0}</loginname>", result[4]);
                    resultBuilder.AppendFormat("    <content><![CDATA[{0}]]></content>", result[1]);
                    resultBuilder.Append("</result>");

                    message = Assistant.SaveCurrentMessage(Request.Params["receiver"], result[0], result[1]);
                }

                if (message.Length > 0)
                {
                    Response.Write(message);
                }
                else
                {
                    Response.Write(resultBuilder.ToString());
                }
            }
            catch
            {
                Response.Write("0");
            }

            Response.End();
        }
    }
}