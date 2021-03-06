using System;
using System.IO;
using System.Web;
using System.Text;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.HtmlControls;

using Sky.ImBusiness;

namespace Sky.WebIm
{
    public partial class savemessage : Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {}

        protected void btnWrite_Click(object sender, EventArgs e)
        {
            string writeContent = txtWriteContent.Text;

            writeContent = writeContent.Replace(
                "    ", "\r\n").Replace(
                    string.Format("{0}  ", txtCnSender.Text),
                    string.Format("\r\n\r\n{0}  ", txtCnSender.Text)
                    ).Replace(
                        string.Format("{0}  ", txtCnReceiver.Text),
                        string.Format("\r\n\r\n{0}  ", txtCnReceiver.Text)
                        );

            writeContent = writeContent.TrimStart(new char[] { '\r', '\n' });

            string messageName = DateTime.Now.ToString("yyyyMMddHHmmss");

            Response.BinaryWrite(Encoding.UTF8.GetBytes(writeContent));

            Response.HeaderEncoding = Encoding.Default;
            Response.AddHeader("Content-Disposition", "attachment;filename=" + messageName + ".txt");

            Response.End();
        }
    }
}