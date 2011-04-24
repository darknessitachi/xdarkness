package com.xdarkness.platform;

public class MenuType {
	String type;

	private MenuType(String type) {
		this.type = type;
	}

	public String toString() {
		return this.type;
	}

	public static final MenuType FirstLevel = new MenuType("1");
	public static final MenuType SecondLevel = new MenuType("2");
}
