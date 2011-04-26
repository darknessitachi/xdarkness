using System;
using System.IO;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.HtmlControls;
using System.Collections.Generic;

using Sky.ImBusiness;

namespace Sky.WebIm
{
    public partial class emotion : Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                LoadEmotionIcos();
            }
        }

        private void LoadEmotionIcos()
        {
            int averageCell = 6;
            HtmlTableRow emotionRow = null;
            List<string> emotionIcos = Emotion.GetEmotionIcos();

            for (int i = 0; i < emotionIcos.Count; i++)
            {
                if (i % averageCell == 0)
                {
                    emotionRow = new HtmlTableRow();
                    tableEmotion.Rows.Add(emotionRow);
                }

                HtmlTableCell emotionCell = new HtmlTableCell();
                emotionCell.InnerHtml = string.Format(
                    "<img alt=\"{2}\" src=\"../{0}/{1}\" emotion=\"{1}\" onmousedown=\"Emotion.setContentEmotion(this);\" style=\"cursor: pointer;\" />",
                    Emotion.EmotionIcosPath,
                    Path.GetFileName(emotionIcos[i]),
                    Path.GetFileNameWithoutExtension(emotionIcos[i])
                    );

                emotionRow.Cells.Add(emotionCell);

            }
        }
    }
}