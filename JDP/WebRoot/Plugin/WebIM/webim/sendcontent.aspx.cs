using System;
using System.Web;
using System.Web.UI;
using System.Text.RegularExpressions;

using Sky.ImBusiness;

namespace Sky.WebIm
{
    public partial class sendcontent : Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            try
            {
                if (Application["online"] != null)
                {
                    string receiver = Request.Params["receiver"];
                    OnLine onLine = Application["online"] as OnLine;

                    if (onLine.CheckLoginer(receiver))
                    {
                        if (onLine.OnLineList[receiver].UserStatus == Profile.offLineStatus)
                        {
                            Response.Write("1");
                        }
                        else
                        {
                            IM im = new IM();

                            im.Custom = Request.Params["flag"];
                            im.Sender = Request.Params["sender"];
                            im.SendDate = DateTime.Now.ToString();
                            im.SendDate = DateTime.Now.ToString();
                            im.IsOuter = Request.Params["isouter"];
                            //im.Content = Request.Params["content"];
                            im.Receiver = Request.Params["receiver"];
                            im.CNSender = Request.Params["cnsender"];
                            im.LoginName = Request.Params["loginname"];
                            im.ContentStyle = Request.Params["contentstyle"];
                            im.Identy = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff");
                            im.Content = Regex.Replace(
                                Request.Params["content"],
                                "<A href\\s*=\\s*(?:\"(?<1>[^\"]*)\"|(?<1>\\S+))>",
                                string.Empty
                                ).Replace("</A>", string.Empty);

                            im.Content = Regex.Replace(im.Content, @"\bon\w*=.*?( |>)|alt=.*?( |>)|title=.*?( |>)", string.Empty);

                            Assistant.AppendContent(im.Identy, im);
                            
                            string content = string.Format(
                                "<font color=\"{4}\">{0}&nbsp;&nbsp;{1}{5}</font><br />&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"{2}\">{3}</span><br />",
                                im.CNSender,
                                im.SendDate,
                                im.ContentStyle,
                                im.Content,
                                Profile.senderHeadColor,
                                (im.Custom.Equals("0") ? string.Empty : string.Format("&nbsp;[{0}]", Profile.userStatusList[1]))
                                );

                            string result = Assistant.SaveCurrentMessage(im.Sender, im.Receiver, content);

                            if (result.Length > 0)
                            {
                                Response.Write(result);
                            }
                            else
                            {
                                Response.Write(content);
                            }
                        }
                    }
                    else
                    {
                        Response.Write("1");
                    }
                }
                else
                {
                    Response.Write("0");
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