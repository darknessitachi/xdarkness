using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using System.IO;
using Sky.ImData;

namespace Sky.WebIm
{
    public partial class upload : System.Web.UI.Page
    {
        private string picPath = "";
        private string picServer = "/upload";
        protected string itemID = "";

        protected void Page_Load(object sender, EventArgs e)
        {

            if (Request.QueryString["id"] != null)
            {
                itemID = Request.QueryString["id"];
            }

            if (IsPostBack)
            {
                //��Ҫ���ô˴�
                picPath = Server.MapPath("~/upload/head");
                string headImg = doUpload();
                ChangeHeadImg(headImg);
            }
        }

        /// <summary>
        /// �����û�ͷ��
        /// </summary>
        /// <param name="headImg"></param>
        public void ChangeHeadImg(string headImg)
        {
            string id = this.userId.Value;
            if (headImg == null || headImg == "")
            {
                return;
            }

            Users users = new Users();
            users.ChangeHeadImg(headImg, id);
        }

        /// <summary>
        /// �ϴ��ļ�
        /// </summary>
        protected string doUpload()
        {
            try
            {
                HttpPostedFile file = fileImg.PostedFile;
                string strNewPath = GetSaveFilePath() + GetExtension(file.FileName);
                int size = 1024;
                try
                {
                    size = int.Parse(imgSize.Value);
                }
                catch { }
                if (file.ContentLength / 1024 > size)
                {
                    WriteJs("parent.LayoutSettings.uploadFail('�ϴ�ͷ����С��" + size/1024 + "M��');");
                    return "";
                }
                file.SaveAs(picPath + strNewPath);

                File.Delete(picPath + "\\" + currentImg.Value);

                string urlPath = picServer + strNewPath;
                urlPath = urlPath.Replace("\\", "/");
                WriteJs("parent.LayoutSettings.uploadsuccess('" + strNewPath.Replace("\\", "") + "','" + itemID + "'); ");

                return strNewPath.Replace("\\","") ;
            }
            catch
            {
                WriteJs("parent.LayoutSettings.uploaderror();");
                return "";
            }
        }

        /// <summary>
        /// ��ȡ�ļ���׺
        /// </summary>
        /// <param name="fileName"></param>
        /// <returns></returns>
        private string GetExtension(string fileName)
        {
            try
            {
                int startPos = fileName.LastIndexOf(".");
                return fileName.Substring(startPos, fileName.Length - startPos);
            }
            catch
            {
                WriteJs("parent.LayoutSettings.uploaderror('" + itemID + "');");
                return string.Empty;
            }
        }

        /// <summary>
        /// ��ȡ������ļ�·��
        /// </summary>
        /// <returns></returns>
        private string GetSaveFilePath()
        {
            try
            {
               
                DateTime dateTime = DateTime.Now;
                string yearStr = dateTime.Year.ToString();
                string monthStr = dateTime.Month.ToString();
                string dayStr = dateTime.Day.ToString();
                string hourStr = dateTime.Hour.ToString();
                string minuteStr = dateTime.Minute.ToString();

                /**
                 * �½�һ����������ɵ��ļ���
                 * 
                string dir = dateTime.ToString(@"\\yyyyMMdd");
                if (!Directory.Exists(picPath + dir))
                {
                    Directory.CreateDirectory(picPath + dir);
                }*/

                return dateTime.ToString("\\\\yyyyMMddHHmmss");

                return "\\\\user" + userId.Value;
            }
            catch
            {
                WriteJs("parent.LayoutSettings.uploaderror();");
                return string.Empty;
            }
        }

        protected void WriteJs(string jsContent)
        {
            this.Page.ClientScript.RegisterStartupScript(this.GetType(),"writejs", "<script type='text/javascript'>" + jsContent + "</script>");
        }
    }
}
