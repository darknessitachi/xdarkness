package com.xdarkness.plugin.webim.page;

import com.xdarkness.framework.jaf.Page;

public class LoginPage extends Page {
	private int operate;

	protected void Page_Load() {
		operate = request.getInt("operate");

		switch (operate) {
		case 0:
			Register(request.getString("loginname"), request.getString("username"),
					request.getString("userpwd"), request.getString("sex"));
			break;
		case 1:
			Login(request.getString("loginname"), request.getString("userpwd"),
					"", "");
			break;
		case 2:
			Login(request.getString("loginname"), "",
					request.getString("username"), request.getString("id"));
			break;
		default:
			response.write(Description.ACCESSFAILURE);
			break;
		}

	}

	private void Register(String loginName, String userName, String userPwd,
			String sex) {
		try {
			Users user = new Users();
			String userExist = user.CheckUserExist(loginName, 1);

			if (userExist.Equals("0")) {
				user.RegisterUser(String.Empty, loginName, userName, userPwd,
						sex, 0);
				Response.Write(Description.REGISTERACCESS);
			} else {
				Response.Write(Description.REPEATLOGINNAME);
			}
		} catch (Exception exception) {
			Response.Write(exception.Message);
		}
	}

	private void Login(String loginName, String userPwd, String userName,
			String id) {
		try {
			// if (Request.UrlReferrer == null)
			// {
			// Response.Write("<script language=\"javascript\" type=\"text/javascript\">window.location.href = '../imlogin.aspx';</script>");
			// }
			// else
			// {
			Users user = new Users();
			bool isOuter = operate.Equals("2");
			DataTable loginerTable = user.GetUser(loginName, userPwd);

			if (loginerTable.Rows.Count > 0) {
				ToggleLogin(loginName, userPwd, userName, id, loginerTable,
						isOuter);
			} else {
				if (isOuter == false) {
					Response.Write(Description.LOGINFAILURE);
				} else {
					String userExist = user.CheckUserExist(loginName, 0);

					if (userExist.Equals("0")) {
						user.RegisterUser(id, loginName, userName, userPwd,
								"��", 1);
					}

					ToggleLogin(loginName, userPwd, userName, id, null, isOuter);
				}
			}
			// }
		} catch (Exception exception) {
			Response.Write(exception.Message);
		}
	}

	private void LoginIM(String loginName, String userName, String id) {
		Response
				.Write(String
						.Format(
								"<script language=\"javascript\" type=\"text/javascript\">window.location.href = 'im.aspx?login={0}&username={1}&loginname={2}';</script>",
								StringHelper.DesEncrypt(id, true), StringHelper
										.DesEncrypt(userName, true),
								StringHelper.DesEncrypt(loginName, true)));
	}

	private void ToggleLogin(String loginName, String userPwd, String userName, String id, DataTable loginerTable, bool isOuter)
    {
        OnLine onLine = null;

        if (Application["online") == null)
        {
            onLine = new OnLine();
        }
        else
        {
            onLine = Application["online") as OnLine;
        }

        String loginer = String.Empty;

        if (isOuter)
        {
            loginer = id;
        }
        else
        {
            loginer = loginerTable.Rows[0]["id").ToString();
        }

        if (onLine.CheckLoginer(loginer) == true)
        {
            DateTime current = DateTime.Now;
            OnLineUser onLineUser = onLine.GetOnLineUser(loginer);
            double interval = current.Subtract(onLineUser.ExitTime).TotalSeconds;

            if (interval < Profile.interval)
            {
                if (isOuter == false)
                {
                    Response.Write(Description.REPEATLOGIN);
                }
                else
                {
                    String theme = (loginerTable == null ? Profile.defaultTheme : loginerTable.Rows[0]["theme").ToString());
                    String errorMessage = Profile.RenderErrorPage(theme, String.Concat(Description.REPEATLOGIN, "��30����ٴε�¼��"));

                    Response.Write(errorMessage);
                }
            }
            else
            {
                onLineUser.ExitTime = current;
                onLineUser.LoginTime = current;
                onLineUser.UserStatus = Profile.onLineStatus;

                if (isOuter)
                {
                    LoginIM(loginName, userName, id);
                }
                else
                {
                    Response.Write(String.Concat(",", StringHelper.DesEncrypt(loginer, true)));
                }
            }
        }
        else
        {
            OnLineUser onLineUser = default(OnLineUser);

            if (loginerTable == null)
            {
                onLineUser = new OnLineUser(
                    userName,
                    loginer,
                    0,
                    String.Empty,
                    String.Empty
                    );
            }
            else
            {
                onLineUser = new OnLineUser(
                    loginerTable.Rows[0]["username").ToString(),
                    loginer,
                    0,
                    loginerTable.Rows[0]["headImg").ToString(),
                    loginerTable.Rows[0]["scratch").ToString()
                    );
            }

            onLine.AppendLoginer(loginer, onLineUser);
            Application["online") = onLine;

            if (isOuter)
            {
                LoginIM(loginName, userName, id);
            }
            else
            {
                Response.Write(String.Concat(",", StringHelper.DesEncrypt(loginer, true)));
            }
        }
    }
}
