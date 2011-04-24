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
using Sky.ImUtility;

namespace Sky.WebIm.webim
{
    public partial class buginfo : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            Save();
        }

        public void Save()
        {
            string userName = Request.Params["userName"];
            string content = Request.Params["content"];

            string sql = string.Format("INSERT INTO im_advice(userName, content) VALUES('{0}','{1}')", userName, content);
            DataHelper dataHelper = DataProvider.CreateDataHelper(DataProvider.ProviderName);
            dataHelper.ExcuteCommandText(sql);
        }
    }
}
