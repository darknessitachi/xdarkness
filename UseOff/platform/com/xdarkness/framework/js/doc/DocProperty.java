package com.xdarkness.framework.js.doc;

public class DocProperty {
	DocProperty() {

	}

	DocProperty(String[] propertyInfo) {

		if (propertyInfo.length == 2) {
			Type = propertyInfo[0].replace("{", "").replace("}", "");
			Comment = propertyInfo[1];
		} else {

			Type = propertyInfo[0].replace("{", "").replace("}", "");
			Name = propertyInfo[1];
			Comment = propertyInfo[2];
		}
	}

	public String Name = "";
	public String Type = "void";
	public String Comment = "";

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getComment() {
		return Comment;
	}

	public void setComment(String comment) {
		Comment = comment;
	}
}
