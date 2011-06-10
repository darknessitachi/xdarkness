package com.abigdreamer.java.net.schedule;

public abstract class GeneralTask extends AbstractTask {
	protected boolean isRunning = false;
	protected String cronExpression;

	public String getType() {
		return "General";
	}

	public abstract void execute();

	public boolean isRunning() {
		return this.isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public String getCronExpression() {
		return this.cronExpression;
	}
}