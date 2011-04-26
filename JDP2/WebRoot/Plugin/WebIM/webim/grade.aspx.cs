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
using System.Xml;
using Sky.ImData;

namespace Sky.WebIm.webim
{
    public partial class grade : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            string method = Request.Params["method"];
            if (method == null)
            {
                return;
            }

            if ("getGrade".Equals(method.Trim()))
            {
                GetGrade();
            }
        }

        private void GetGrade()
        {
            string userName = Request.Params["userName"];
            Users users = new Users();
            float time = 0;
            try{
                time = float.Parse(users.GetOnLineTime(userName.Trim()).Rows[0]["onlinetime"].ToString());
            }catch{}

            WriteMessage(GetGradeInfo(time));
        }

        private string GetGradeInfo(float intTime)
        {
            XmlDocument document = null;
            
            // 将分钟转换成小时
            intTime = intTime / 60 / 8;

            int dayTime = int.Parse(intTime.ToString().Split('.')[0]) + 1;

            try
            {
                document = new XmlDocument();
                document.Load(HttpContext.Current.Server.MapPath("../profile/grade.xml"));

                XmlNodeList gradeList = document.SelectNodes("//grades/grade");

                XmlNode gradeNode = gradeList.Item(0);
                float firstLevelTime = float.Parse(gradeNode.Attributes["time"].Value);
                if (dayTime < firstLevelTime)
                {
                    return gradeNode.Attributes["img"].Value + "," + (firstLevelTime - dayTime) + "," + gradeNode.Attributes["text"].Value + "," + dayTime;
                }

                for (int i = 1; i < gradeList.Count; i++)
                {
                    gradeNode = gradeList.Item(i-1);
                    float timeBegin = float.Parse(gradeNode.Attributes["time"].Value);

                    gradeNode = gradeList.Item(i);
                    float timeEnd = float.Parse(gradeNode.Attributes["time"].Value);

                    if (dayTime >= timeBegin && dayTime < timeEnd)
                    {
                        return gradeNode.Attributes["img"].Value + "," + (timeEnd - dayTime) + "," + gradeNode.Attributes["text"].Value + "," + dayTime; 
                    }
                }

                return gradeList.Item(gradeList.Count - 1).Attributes["img"].Value +
                       ",0," + gradeList.Item(gradeList.Count - 1).Attributes["text"].Value +
                       "," + dayTime;
            }
            finally
            {
                document = null;
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
