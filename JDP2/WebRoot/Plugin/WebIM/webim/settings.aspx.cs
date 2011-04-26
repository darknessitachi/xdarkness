using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.HtmlControls;
using System.Collections.Specialized;
using System.IO;
using System.Text;

using Sky.ImData;

namespace Sky.WebIm
{
    public partial class settings : Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            string method = Request.Params["method"];
            if (method == null || method == "")
            {
                return;
            }

            if ("changeSettings".Equals(method.Trim()))
            {
                SaveSettings();
            }
            else if ("getSettings".Equals(method.Trim()))
            {
                GetSettings();
            }
            else if ("getImgs".Equals(method.Trim()))
            {
                GetAllImgs();
            }
            else if ("getThemes".Equals(method.Trim()))
            {
                GetAllThemes();
            }
        }

        private void SaveSettings()
        {
            string revertMessage = Request.Params["revertMessage"];
            string scratch = Request.Params["scratch"];
            string theme = Request.Params["theme"];
            string id = Request.Params["userId"];
            string headImg = Request.Params["headImg"];
            string userName = Request.Params["userName"];
            
            Users user = new Users();
            bool success = false;
            if (userName != null && userName != "")
            {
                success = user.ChangeSettings(revertMessage, scratch, theme, id, GetImg(headImg), userName);
            }
            else
            {
                success = user.ChangeSettings(revertMessage, scratch, theme, id, GetImg(headImg));
            }
            if (success)
            {
                WriteMessage("�������óɹ���");
            }
            else
            {
                WriteMessage("��������ʧ�ܣ�");
            }
        }

        private string GetImg(string path)
        {
            string[] tempPath = path.Split('/');
            return tempPath[tempPath.Length-1];
        }

        private void GetSettings()
        {
            string id = Request.Params["userId"];

            Users users = new Users();
            DataTable dt = users.GetUserById(id);
            if (dt == null || dt.Rows.Count<1)
            {
                WriteMessage("0");
            }

            DataRow dr = dt.Rows[0];

            string message =  dr["scratch"].ToString() + "," +
                              dr["revertMessage"].ToString() + "," +
                              dr["headImg"].ToString() + "," +
                              dr["theme"].ToString() + "," +
                              dr["username"].ToString();

            WriteMessage(message);
        }

        /// <summary>
        /// ���������������Ϣ
        /// </summary>
        /// <param name="message">��Ϣ</param>
        private void WriteMessage(string message)
        {
            Response.Clear();
            Response.Write(message);
            Response.End();
        }

        /// <summary>
        /// ��ȡ����Ĭ��ͷ��
        /// </summary>
        private void GetAllImgs()
        {
            StringBuilder sbImgs = new StringBuilder();
            StringCollection imgs = GetAllFiles(Server.MapPath("~/include/images/head"));
            foreach (string img in imgs)
            {
                sbImgs.AppendFormat("<img src='../include/images/head/{0}' onclick=\"LayoutSettings.setCurrentImg(this);\" style='padding: 2 2 2 2;width:32px;height:32px;cursor:pointer;' align='absmiddle' />", img);
            }

            WriteMessage(sbImgs.ToString());
        }

        /// <summary>
        /// ��ȡ��������
        /// </summary>
        private void GetAllThemes()
        {
            StringBuilder sbDirs = new StringBuilder();
            StringCollection themes = GetAllDirs(Server.MapPath("~/include/themes"));
            foreach (string theme in themes)
            {
                sbDirs.Append(theme + ",");
            }

            WriteMessage(sbDirs.ToString().TrimEnd(','));
        }

        /// <summary>
        /// ���� rootdirĿ¼�µ������ļ�
        /// </summary>
        /// <param name="rootdir">Ŀ¼����</param>
        /// <returns>��Ŀ¼�µ������ļ�</returns>
        public StringCollection GetAllFiles(string rootdir)
        {
            StringCollection result = new StringCollection();
            GetAllFiles(rootdir, result);
            return result;
        }

        /// <summary>
        /// ���� rootdirĿ¼�µ������ļ���
        /// </summary>
        /// <param name="rootdir">Ŀ¼����</param>
        /// <returns>��Ŀ¼�µ������ļ�</returns>
        public StringCollection GetAllDirs(string rootdir)
        {
            StringCollection result = new StringCollection();
            GetAllDirs(rootdir, result);
            return result;
        }

        /// <summary>
        /// ��Ϊ�����ļ��������Ӻ���
        /// </summary>
        /// <param name="parentDir">Ŀ¼����</param>
        /// <param name="result">��Ŀ¼�µ������ļ�</param>
        public void GetAllFiles(string parentDir, StringCollection result)
        {
            //��ȡĿ¼parentDir�µ����е����ļ���
            //string[] dir = Directory.GetDirectories(parentDir);
            //for (int i = 0; i < dir.Length; i++)
            //    GetAllFiles(dir[i], result);

            //��ȡĿ¼parentDir�µ����е��ļ��������˵õ����е��ı��ļ�
            string[] file = Directory.GetFiles(parentDir);
            string fileExt = ".gif.jpg";
            for (int i = 0; i < file.Length; i++)
            {
                FileInfo fi = new FileInfo(file[i]);
                if (fileExt.LastIndexOf(fi.Extension.ToLower()) != -1)
                {
                    result.Add(file[i].Substring(file[i].LastIndexOf("\\") + 1));
                }
            }
        }

        /// <summary>
        /// ��Ϊ����Ŀ¼�������Ӻ���
        /// </summary>
        /// <param name="parentDir">Ŀ¼����</param>
        /// <param name="result">��Ŀ¼�µ������ļ�</param>
        public void GetAllDirs(string parentDir, StringCollection result)
        {
            //��ȡĿ¼parentDir�µ����е����ļ���
            //string[] dir = Directory.GetDirectories(parentDir);
            //for (int i = 0; i < dir.Length; i++)
            //    GetAllFiles(dir[i], result);

            //��ȡĿ¼parentDir�µ����е��ļ��������˵õ����е��ı��ļ�
            string[] dirs = Directory.GetDirectories(parentDir);
            for (int i = 0; i < dirs.Length; i++)
            {
                result.Add(dirs[i].Substring(dirs[i].LastIndexOf("\\") + 1));
            }
        } 
    }
}
