package com.xdarkness.framework.jaf.controls;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.xdarkness.framework.Config;
import com.xdarkness.framework.util.XString;

public class UploaderTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String action;
	private String barcolor;
	private String width;
	private String height;
	private String uploadtype;
	private String allowtype;
	private String onComplete;
	private String fileCount;
	private String fileMaxSize;

	public void setPageContext(PageContext pc) {
		super.setPageContext(pc);
		this.id = null;
		this.action = null;
		this.barcolor = null;
		this.width = null;
		this.height = null;
		this.uploadtype = null;
		this.allowtype = null;
		this.onComplete = null;
		this.fileCount = null;
		this.fileMaxSize = null;
	}

	public int doAfterBody() throws JspException {
		String content = getBodyContent().getString();
		try {
			getPreviousOut().print(getHtml(content));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 6;
	}

	public int doEndTag() throws JspException {
		if ((this.bodyContent == null)
				|| (XString.isEmpty(this.bodyContent.getString()))) {
			try {
				this.pageContext.getOut().print(getHtml(""));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 6;
	}

	public String getHtml(String content) {
		String FlashVars = "";
		String srcSWF = "ZUploader.swf";
		if (XString.isEmpty(this.id)) {
			this.id = "_SKY_NOID_";
		}
		if (XString.isEmpty(this.name)) {
			this.name = this.id;
		}

		if (XString.isNotEmpty(this.action)) {
			FlashVars = FlashVars + "actionURL=" + this.action;
		}

		if (XString.isNotEmpty(this.uploadtype)) {
			String types = "";
			if (this.uploadtype.equals("Image")) {
				types = Config.getValue("AllowImageExt");
			} else if (this.uploadtype.equals("Video")) {
				srcSWF = "ZUploader_Video.swf";
				types = Config.getValue("AllowVideoExt");
			} else if (this.uploadtype.equals("Audio")) {
				types = Config.getValue("AllowAudioExt");
			} else if (this.uploadtype.equals("Attach")) {
				types = Config.getValue("AllowAttachExt");
			}
			if (XString.isNotEmpty(types))
				this.allowtype = types;
		} else {
			this.uploadtype = "";
		}

		if (XString.isNotEmpty(this.allowtype)) {
			FlashVars = FlashVars + "&fileType=" + this.allowtype;
		}
		if (XString.isNotEmpty(this.barcolor)) {
			FlashVars = FlashVars + "&barColor=" + this.barcolor;
		}
		if (XString.isNotEmpty(this.onComplete)) {
			FlashVars = FlashVars + "&onComplete=" + this.onComplete;
		}
		if ((XString.isNotEmpty(this.fileCount)) && (this.fileCount != "0")) {
			FlashVars = FlashVars + "&fileCount=" + this.fileCount;
		}
		if ((XString.isNotEmpty(this.fileMaxSize))
				&& (this.fileMaxSize != "0")) {
			FlashVars = FlashVars + "&fileMaxSize=" + this.fileMaxSize;
		}
		if (XString.isEmpty(this.width)) {
			this.width = "250";
		}
		if ((this.uploadtype.equals("Video"))
				&& (Integer.parseInt(this.width) < 400)) {
			this.width = "450";
		}
		if (XString.isEmpty(this.height)) {
			this.height = "25";
		}

		StringBuffer sb = new StringBuffer();
		sb
				.append("<object id="
						+ this.id
						+ " classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' width='"
						+ this.width + "' height='" + this.height + "'>\n");
		sb.append("<param name='movie' value='" + Config.getContextPath()
				+ "Tools/" + srcSWF + "'>\n");
		sb.append("<param name='quality' value='high'>\n");
		sb.append("<param name='wmode' value='transparent'>\n");
		sb.append("<param name='FlashVars' value='" + FlashVars + "'>\n");
		sb.append("<embed name='" + this.name + "' src='"
				+ Config.getContextPath() + "Tools/" + srcSWF + "' FlashVars='"
				+ FlashVars + "' quality='high' wmode='transparent' width='"
				+ this.width + "' height='" + this.height + "'></embed>\n");
		sb.append("</object>\n");
		sb.append("<script type='text/javascript'>\n");
		sb
				.append("function getUploader(movieName) {return document[movieName];}\n");
		sb.append("function getZUploaderTaskStatus(taskID){\n");
		sb.append("var dc = new DataCollection();\n");
		sb.append("dc.add('taskID',taskID);\n");
		sb
				.append("Server.sendRequest('com.xdarkness.framework.controls.UploadStatus.getTaskStatus',dc,function(response){\n");
		sb
				.append("var arr = [response.get('FileIDs'),response.get('Types'),response.get('Paths'),response.get('Status')];\n");
		sb.append("getUploader('" + this.id + "').setStatusStr(arr);\n");
		sb.append("})}");
		if (!this.uploadtype.equals("Video")) {
			sb.append("function doResize(fileLength,cCount,cType){\n");
			sb.append("if(fileLength>1&&fileLength<=6&&cType=='add'){\n");
			sb.append("if(fileLength==2){cCount=cCount+1;}\n");
			sb.append("if(getUploader('" + this.id
					+ "').height>187){return;}\n");
			sb.append("getUploader('" + this.id
					+ "').height=new Number(getUploader('" + this.id
					+ "').height)+(27*cCount);\n");
			sb.append("}else if(fileLength<6&&cType=='del'){\n");
			sb.append("if(fileLength==1){cCount=cCount+1;}\n");
			sb.append("getUploader('" + this.id
					+ "').height=new Number(getUploader('" + this.id
					+ "').height)-(27*cCount);}}\n");
		} else {
			sb.append("function doResize(){\n");
			sb.append("getUploader('" + this.id + "').height=60;}\n");
		}
		sb.append("</script>\n");
		return sb.toString();
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String width() {
		return this.width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return this.height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getUploadtype() {
		return this.uploadtype;
	}

	public void setUploadtype(String uploadtype) {
		this.uploadtype = uploadtype;
	}

	public String getAllowtype() {
		return this.allowtype;
	}

	public void setAllowtype(String allowtype) {
		this.allowtype = allowtype;
	}

	public String getBarcolor() {
		return this.barcolor;
	}

	public void setBarcolor(String barcolor) {
		this.barcolor = barcolor;
	}

	public String getonComplete() {
		return this.onComplete;
	}

	public void setonComplete(String onComplete) {
		this.onComplete = onComplete;
	}

	public String getfileCount() {
		return this.fileCount;
	}

	public void setfileCount(String fileCount) {
		this.fileCount = fileCount;
	}

	public String getFileMaxSize() {
		return this.fileMaxSize;
	}

	public void setFileMaxSize(String fileMaxSize) {
		this.fileMaxSize = fileMaxSize;
	}
}

/*
 * com.xdarkness.framework.controls.UploaderTag JD-Core Version: 0.6.0
 */