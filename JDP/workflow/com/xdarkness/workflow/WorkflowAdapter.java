package com.xdarkness.workflow;

import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mapx;

public abstract class WorkflowAdapter {
	public void onTemporarySave(Context context) {
	}

	public void onStepCancel(Context context) {
	}

	public void onStepCreate(Context context) {
	}

	public void onActionExecute(Context context, WorkflowAction action) {
	}

	public void notifyNextStep(Context context, String[] users) {
		String className = Config.getValue("App.WorkflowAdapter");
		LogUtil.warn("ID为" + context.getWorkflow().getID()
				+ "的流程配置了'通知下一步处理人'选项，但" + className + "未实现notifyNextStep()方法");
	}

	public abstract void onWorkflowDelete(Transaction paramTransaction,
			long paramLong);

	public abstract Mapx getVariables(String paramString1, String paramString2);

	public abstract boolean saveVariables(Context paramContext);
}
