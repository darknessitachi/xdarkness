package com.xdarkness.framework.jaf;


public interface IPage {

	void setRequest(JafRequest dc);

	JafRequest getRequest();

	Cookies getCookie();

	void setCookie(Cookies cookie);

	JafResponse getResponse();

	void setResponse(JafResponse response);

}