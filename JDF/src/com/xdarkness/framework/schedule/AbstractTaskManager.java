package com.xdarkness.framework.schedule;

import com.xdarkness.framework.util.Mapx;

public abstract class AbstractTaskManager {
	public abstract Mapx getUsableTasks();

	public abstract Mapx getConfigEnableTasks();

	public abstract String getTaskCronExpression(long paramLong);

	public abstract void execute(long paramLong);

	public abstract boolean isRunning(long paramLong);

	public abstract String getCode();

	public abstract String getName();
}
