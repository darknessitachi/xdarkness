package com.abigdreamer.java.net.schedule;

public abstract class AbstractTask {
	public abstract long getID();

	public abstract String getType();

	public abstract String getName();

	public abstract String getCronExpression();
}
