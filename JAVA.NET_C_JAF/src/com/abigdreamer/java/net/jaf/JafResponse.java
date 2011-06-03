package com.abigdreamer.java.net.jaf;

import com.abigdreamer.java.net.orm.data.DataCollection;

/**
 * 
 * @author Darkness Create on 2010-5-29 上午10:45:34
 * @version 1.0
 * @since JDF1.0
 */
public class JafResponse extends DataCollection {

	public JafResponse() {
		Status = 1;
		Message = "";
	}

	public String getMessage() {
		return Message;
	}

	public void setError(String message) {
		setMessage(message);
		setStatus(0);
	}

	public void setMessage(String message) {
		Message = message;
		put("_SKY_MESSAGE", Message);
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
		put("_SKY_STATUS", Status);
	}

	public void setLogInfo(int status, String message) {
		Status = status;
		put("_SKY_STATUS", Status);
		Message = message;
		put("_SKY_MESSAGE", Message);
	}

	public String toXML() {
		put("_SKY_STATUS", Status);
		return super.toXML();
	}

	private static final long serialVersionUID = 1L;
	public int Status;
	public String Message;
}
