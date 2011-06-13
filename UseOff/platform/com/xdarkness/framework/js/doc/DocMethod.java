package com.xdarkness.framework.js.doc;

import java.util.ArrayList;
import java.util.List;

public class DocMethod {
	
	DocMethod(String[] methodInfo) {
		this.Name = methodInfo[0];
		this.Comment = methodInfo[1];
	}
	public String Name;
	public String Comment;
	public String Type = "";
	public List<DocProperty> Properties = new ArrayList<DocProperty>();
	public DocProperty Return = new DocProperty();
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getComment() {
		return Comment;
	}
	public void setComment(String comment) {
		Comment = comment;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public List<DocProperty> getProperties() {
		return Properties;
	}
	public void setProperties(List<DocProperty> properties) {
		Properties = properties;
	}
	public DocProperty getReturn() {
		if(Return==null){
			Return = new DocProperty();
			Return.Type = "void";
		}
		return Return;
	}
	public void setReturn(DocProperty return1) {
		Return = return1;
	}
}
