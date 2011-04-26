package com.xdarkness.framework.js.doc;

import java.util.ArrayList;
import java.util.List;

public class DocClass {
	DocClass(String[] classInfo){
		this.Name = classInfo[0];
		this.Comment = classInfo[1];
	}
	public String Name;
	public String DefinedInFileName;
	public String Comment;
	public List<DocMethod> methodList = new ArrayList<DocMethod>();
	public List<DocProperty> propertyList = new ArrayList<DocProperty>();
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDefinedInFileName() {
		return DefinedInFileName;
	}
	public void setDefinedInFileName(String definedInFileName) {
		DefinedInFileName = definedInFileName;
	}
	public String getComment() {
		return Comment;
	}
	public void setComment(String comment) {
		Comment = comment;
	}
	public List<DocMethod> getMethodList() {
		return methodList;
	}
	public void setMethodList(List<DocMethod> methodList) {
		this.methodList = methodList;
	}
	public List<DocProperty> getPropertyList() {
		return propertyList;
	}
	public void setPropertyList(List<DocProperty> propertyList) {
		this.propertyList = propertyList;
	}
}
