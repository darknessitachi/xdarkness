package com.xdarkness.workflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xdarkness.cms.pub.PubFun;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZWInstanceSchema;
import com.xdarkness.schema.ZWInstanceSet;
import com.xdarkness.schema.ZWStepSchema;
import com.xdarkness.schema.ZWStepSet;
import com.xdarkness.schema.ZWWorkflowSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mapx;

public class WorkflowUtil {
	private static Mapx WorkflowMap = new Mapx();
	private static Object mutex = new Object();
	private static WorkflowAdapter adapter;

	public static Workflow findWorkflow(long workflowID) {
		long key = new Long(workflowID);
		if (WorkflowMap.containsKey(key)) {
			return (Workflow) WorkflowMap.get(key);
		}
		Workflow wf = new Workflow();
		wf.setID(workflowID);
		if (!wf.fill()) {
			return null;
		}
		wf.init();
		synchronized (mutex) {
			WorkflowMap.put(key, wf);
		}
		return wf;
	}

	public static Context createInstance(Transaction tran, long workflowID,
			String name, String dataID, String dataVersionID) throws Exception {
		WorkflowInstance instance = new WorkflowInstance();
		instance.setWorkflowID(workflowID);
		instance.setAddTime(new Date());
		instance.setAddUser(User.getUserName());
		instance.setDataID(dataID);
		instance.setName(name);
		instance.setState("Activated");
		instance.setID(NoUtil.getMaxID("WorkflowInstanceID"));
		tran.add(instance, OperateType.INSERT);

		ZWStepSchema newStep = new ZWStepSchema();
		newStep.setID(NoUtil.getMaxID("WorkflowStepID"));
		newStep.setWorkflowID(instance.getWorkflowID());
		newStep.setNodeID(findWorkflow(instance.getWorkflowID()).getStartNode()
				.getID());
		newStep.setActionID(-2);
		newStep.setPreviousStepID(-1L);
		newStep.setInstanceID(instance.getID());
		newStep.setDataVersionID(dataVersionID);
		newStep.setOwner(User.getUserName());
		newStep.setStartTime(new Date());
		newStep.setMemo("");
		newStep.setOperators(User.getUserName());
		newStep.setState("Underway");
		newStep.setAddTime(new Date());
		newStep.setAddUser(User.getUserName());
		tran.add(newStep, OperateType.INSERT);

		Context context = new Context(tran, instance, newStep);
		findAdapter().onStepCreate(context);

		WorkflowAction.executeMethod(context, findWorkflow(
				instance.getWorkflowID()).getStartNode(), "Post");
		return context;
	}

	public static WorkflowInstance findInstance(long workflowID, String dataID)
			throws WorkflowException {
		ZWInstanceSchema wi = new ZWInstanceSchema();
		wi.setWorkflowID(workflowID);
		wi.setDataID(dataID);
		ZWInstanceSet set = wi.query();
		if (set.size() == 0) {
			throw new WorkflowException("未能找到指定的工作流实例：WorkflowID=" + workflowID
					+ ",DataID=" + dataID);
		}
		if (set.size() > 1) {
			throw new WorkflowException("找到的工作流实例个数大于一：WorkflowID="
					+ workflowID + ",DataID=" + dataID);
		}
		wi = set.get(0);
		WorkflowInstance wfi = new WorkflowInstance();
		for (int i = 0; i < wfi.getColumnCount(); i++) {
			wfi.setV(i, wi.getV(i));
		}
		return wfi;
	}

	public static WorkflowInstance findInstance(long instanceID)
			throws WorkflowException {
		ZWInstanceSchema wi = new ZWInstanceSchema();
		wi.setID(instanceID);
		if (!wi.fill()) {
			throw new WorkflowException("未能找到指定的工作流实例：ID=" + instanceID);
		}
		WorkflowInstance wfi = new WorkflowInstance();
		for (int i = 0; i < wfi.getColumnCount(); i++) {
			wfi.setV(i, wi.getV(i));
		}
		return wfi;
	}

	public static WorkflowStep findStep(long stepID) throws WorkflowException {
		ZWStepSchema step = new ZWStepSchema();
		step.setID(stepID);
		if (!step.fill()) {
			throw new WorkflowException("未能找到指定的工作流步骤：ID=" + stepID);
		}
		WorkflowStep wfs = new WorkflowStep();
		for (int i = 0; i < wfs.getColumnCount(); i++) {
			wfs.setV(i, step.getV(i));
		}
		return wfs;
	}

	public static ZWStepSchema findCurrentStep(long instanceID)
			throws WorkflowException {
		QueryBuilder qb = new QueryBuilder(
				"where InstanceID=? and (State=? or State=?)", instanceID,
				"Underway");
		qb.add("Unread");
		ZWStepSet set = new WorkflowStep().query(qb);
		for (int i = 0; i < set.size(); i++) {
			ZWStepSchema step = set.get(i);
			if ((step.getState().equals("Underway"))
					&& (step.getOwner().equals(User.getUserName()))) {
				return step;
			}
		}
		for (int i = 0; i < set.size(); i++) {
			ZWStepSchema step = set.get(i);
			if ((step.getState().equals("Unread"))
					&& (hasPriv(step.getAllowOrgan(), step.getAllowRole(), step
							.getAllowUser()))
					&& ("1".equals(findWorkflow(step.getWorkflowID()).getData()
							.get("NotNeedApply")))) {
				return step;
			}
		}

		throw new WorkflowException("未能找到当前步骤:InstanceID=" + instanceID
				+ ",User=" + User.getUserName());
	}

	public static WorkflowAction[] findInitActions(long workflowID)
			throws Exception {
		Workflow wf = findWorkflow(workflowID);
		Workflow.Node[] nodes = wf.getNodes();
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i].getType().equals("StartNode")) {
				ArrayList list = new ArrayList(4);

				list.add(WorkflowAction.getTemporarySaveAction());
				Context ctx = new Context(workflowID);
				WorkflowTransition[] tss = nodes[i].getTransitions();
				for (int j = 0; j < tss.length; j++) {
					if (tss[j].validate(ctx)) {
						list.add(new WorkflowAction(tss[j].getTargetNode()));
					}
				}
				WorkflowAction[] actions = new WorkflowAction[list.size()];
				for (int j = 0; j < list.size(); j++) {
					actions[j] = ((WorkflowAction) list.get(j));
				}
				return actions;
			}
		}
		return null;
	}

	public static WorkflowAction[] findAvaiableActions(long instanceID)
			throws Exception {
		WorkflowInstance instance = findInstance(instanceID);
		return findAvaiableActions(instance);
	}

	public static WorkflowAction[] findAvaiableActions(WorkflowInstance instance)
			throws Exception {
		if (instance.getState().equals("Completed")) {
			return new WorkflowAction[0];
		}
		ZWStepSchema step = new ZWStepSchema();
		QueryBuilder qb = new QueryBuilder(
				"where InstanceID=? and (State=? or State=?)");
		qb.add(instance.getID());
		qb.add("Underway");
		qb.add("Unread");
		ZWStepSet set = step.query(qb);
		ArrayList list = new ArrayList(4);
		Workflow flow = findWorkflow(instance.getWorkflowID());

		boolean underwayFlag = false;
		for (int i = 0; i < set.size(); i++) {
			step = set.get(i);
			Transaction tran = new Transaction();
			Context ctx = new Context(tran, step);
			if ((step.getOwner() != null)
					&& (step.getOwner().equals(User.getUserName()))) {
				Workflow.Node node = flow.findNode(step.getNodeID());

				list.add(WorkflowAction.getTemporarySaveAction());

				WorkflowTransition[] tss = node.getTransitions();
				for (int j = 0; j < tss.length; j++) {
					if (tss[j].validate(ctx)) {
						list.add(new WorkflowAction(tss[j].getTargetNode()));
					}
				}
				underwayFlag = true;
				break;
			}
		}
		if (!underwayFlag) {
			for (int i = 0; i < set.size(); i++) {
				step = set.get(i);
				if (!step.getState().equals("Unread"))
					continue;
				if (hasPriv(step.getAllowOrgan(), step.getAllowRole(), step
						.getAllowUser())) {
					if (!"1".equals(flow.getData().get("NotNeedApply"))) {
						list.add(WorkflowAction
								.getApplyAction(step.getNodeID()));
					} else {
						Transaction tran = new Transaction();
						Context ctx = new Context(tran, step);
						Workflow.Node node = flow.findNode(step.getNodeID());

						list.add(WorkflowAction.getTemporarySaveAction());

						WorkflowTransition[] tss = node.getTransitions();
						for (int j = 0; j < tss.length; j++) {
							if (tss[j].validate(ctx)) {
								list.add(new WorkflowAction(tss[j]
										.getTargetNode()));
							}
						}
						underwayFlag = true;
					}
				}
			}

		}

		for (int i = list.size() - 1; i >= 0; i--) {
			WorkflowAction a1 = (WorkflowAction) list.get(i);
			for (int j = i - 1; j >= 0; j--) {
				WorkflowAction a2 = (WorkflowAction) list.get(j);
				if (a1.getID() == a2.getID()) {
					list.remove(i);
				}
			}
		}
		WorkflowAction[] actions = new WorkflowAction[list.size()];
		for (int i = 0; i < list.size(); i++) {
			actions[i] = ((WorkflowAction) list.get(i));
		}
		return actions;
	}

	public static ZWStepSchema findUnreadStep(long instanceID, int nodeID)
			throws WorkflowException {
		ZWStepSchema step = new ZWStepSchema();
		step.setInstanceID(instanceID);
		step.setNodeID(nodeID);
		step.setState("Unread");
		ZWStepSet set = step.query();
		if (set.size() > 0) {
			step = set.get(0);
			return step;
		}
		throw new WorkflowException("找不到可以申请的步骤!InstanceID=" + instanceID
				+ ",NodeID=" + nodeID);
	}

	public static void applyStep(long instanceID, int nodeID) throws Exception {
		ZWStepSchema step = findUnreadStep(instanceID, nodeID);
		if (!hasPriv(step.getAllowOrgan(), step.getAllowRole(), step
				.getAllowUser())) {
			throw new WorkflowException("用户没有申请流程步骤" + step.getNodeID()
					+ "的权限，WorkflowID=" + step.getWorkflowID());
		}
		QueryBuilder qb = new QueryBuilder(
				"update ZWStep set State=?,Owner=? where State=? and InstanceID=? and NodeID=? and ID=?");
		qb.add("Underway");
		qb.add(User.getUserName());
		qb.add("Unread");
		qb.add(instanceID);
		qb.add(nodeID);
		qb.add(step.getID());
		if (qb.executeNoQuery() == 0)
			throw new WorkflowException("申请失败，可能己被别的用户申请!");
	}

	public static void forceEnd(long instanceID, int nodeID) throws Exception {
		Transaction tran = new Transaction();
		forceEnd(tran, instanceID, nodeID);
		if (!tran.commit())
			throw new WorkflowException(tran.getExceptionMessage());
	}

	public static void forceEnd(Transaction tran, long instanceID, int nodeID)
			throws Exception {
		if (!"admin".equals(User.getUserName())) {
			throw new WorkflowException("当前用户没有强制结束流程的权限!");
		}

		WorkflowInstance instance = findInstance(instanceID);
		Workflow flow = findWorkflow(instance.getWorkflowID());
		if (flow == null) {
			LogUtil.warn("强制结束流程时发现ID为" + instance.getWorkflowID() + "的流程不存在!");
		} else {
			ZWStepSchema newStep = new ZWStepSchema();
			newStep.setID(NoUtil.getMaxID("WorkflowStepID"));
			newStep.setWorkflowID(instance.getWorkflowID());
			newStep.setNodeID(flow.getEndNode().getID());
			newStep.setActionID(-6);
			newStep.setPreviousStepID(0L);
			newStep.setInstanceID(instance.getID());
			newStep.setDataVersionID("0");
			newStep.setOwner(User.getUserName());
			newStep.setStartTime(new Date());
			newStep.setMemo("强制结束流程");
			newStep.setOperators(User.getUserName());
			newStep.setState("Finish");
			newStep.setAddTime(new Date());
			newStep.setAddUser(User.getUserName());

			tran.add(newStep, OperateType.INSERT);
			Context context = new Context(tran, instance, newStep);
			findAdapter().onStepCreate(context);
			WorkflowAction.executeMethod(context, findWorkflow(
					instance.getWorkflowID()).getEndNode(), "Pre");
		}

		instance.setState("Completed");
		tran.add(instance, OperateType.UPDATE);
		tran.add(new QueryBuilder(
				"update ZWStep set State=? where InstanceID=?", "Finish",
				instanceID));
	}

	private static boolean hasPriv(String[] branchInnerCodes,
			String[] roleCodes, String[] userNames) {
		if (userNames != null) {
			for (int i = 0; i < userNames.length; i++) {
				if (userNames[i].equals(User.getUserName())) {
					return true;
				}
			}
		}
		boolean flag = false;
		if (branchInnerCodes != null) {
			String innerCode = User.getBranchInnerCode();
			if (XString.isNotEmpty(innerCode))
				for (int i = 0; i < branchInnerCodes.length; i++)
					if (innerCode.equals(branchInnerCodes[i])) {
						flag = true;
						break;
					}
		} else {
			flag = true;
		}
		if ((flag) && (roleCodes != null)) {
			List list = PubFun.getRoleCodesByUserName(User.getUserName());
			for (int i = 0; i < roleCodes.length; i++) {
				if (list.contains(roleCodes[i])) {
					return true;
				}
			}
		}

		return false;
	}

	private static boolean hasPriv(String branchInnerCodes, String roleCodes,
			String userNames) {
		String[] p1 = (String[]) null;
		String[] p2 = (String[]) null;
		String[] p3 = (String[]) null;
		if (XString.isNotEmpty(branchInnerCodes)) {
			p1 = XString.splitEx(branchInnerCodes, ",");
		}
		if (XString.isNotEmpty(roleCodes)) {
			p2 = XString.splitEx(roleCodes, ",");
		}
		if (XString.isNotEmpty(userNames)) {
			p3 = XString.splitEx(userNames, ",");
		}
		return hasPriv(p1, p2, p3);
	}

	public static WorkflowAction findAction(long workflowID, int actionID) {
		Workflow wf = findWorkflow(workflowID);
		if (actionID == -1) {
			return WorkflowAction.getTemporarySaveAction();
		}
		if (actionID == -5) {
			return WorkflowAction.getRestartAction();
		}
		if (actionID == -2) {
			return new WorkflowAction(actionID, "开始流转", new Mapx());
		}
		if (actionID == -4) {
			return new WorkflowAction(actionID, "脚本跳转", new Mapx());
		}
		if (actionID == -6) {
			return new WorkflowAction(actionID, "强制结束", new Mapx());
		}
		Workflow.Node node = wf.findNode(actionID);
		if (node.getType().equals("ActionNode")) {
			return new WorkflowAction(node);
		}
		return null;
	}

	public static WorkflowAdapter findAdapter() {
		if (adapter == null) {
			synchronized (mutex) {
				if (adapter == null) {
					String className = Config.getValue("App.WorkflowAdapter");
					if (XString.isEmpty(className))
						throw new RuntimeException("未定义工作流适配器!");
					try {
						Class c = Class.forName(className);
						if (!WorkflowAdapter.class.isAssignableFrom(c)) {
							throw new RuntimeException("类" + className
									+ "未继承WorkflowAdapter!");
						}
						adapter = (WorkflowAdapter) c.newInstance();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return adapter;
	}

	public static Mapx getDataVariables(String dataID, String dataVersionID) {
		return findAdapter().getVariables(dataID, dataVersionID);
	}

	public static boolean saveDataVariables(Context context) {
		return findAdapter().saveVariables(context);
	}

	public static String getStepName(long workflowID, int nodeID) {
		Workflow flow = findWorkflow(workflowID);
		if (flow == null) {
			return "未找到工作流";
		}
		Workflow.Node node = flow.findNode(nodeID);
		if (node == null) {
			return "未找到步骤";
		}
		return node.getName();
	}

	public static String getActionNodeName(long workflowID, int actionID) {
		return findAction(workflowID, actionID).getName();
	}

	public static void deleteInstance(Transaction tran, long instanceID) {
		ZWInstanceSet iset = new ZWInstanceSchema().query(new QueryBuilder(
				"where ID=?", instanceID));
		ZWStepSet sset = new ZWStepSchema().query(new QueryBuilder(
				"where InstanceID=?", instanceID));
		tran.add(iset, OperateType.DELETE_AND_BACKUP);
		tran.add(sset, OperateType.DELETE_AND_BACKUP);
	}

	public static void updateCache(ZWWorkflowSchema schema) {
		synchronized (mutex) {
			WorkflowMap.put(new Long(schema.getID()), Workflow.convert(schema));
		}
	}

	public static void deleteCache(ZWWorkflowSchema schema) {
		synchronized (mutex) {
			WorkflowMap.remove(new Long(schema.getID()));
		}
	}

	private static boolean checkPrivString(String str) {
		if ((str.indexOf('\'') > 0) || (str.indexOf('"') > 0)) {
			return false;
		}

		return (str.indexOf('(') <= 0) && (str.indexOf(')') <= 0);
	}

	public static boolean hasPrivUser(ZWStepSchema step)
			throws WorkflowException {
		if (XString.isNotEmpty(step.getAllowUser())) {
			return true;
		}
		QueryBuilder qb = new QueryBuilder(
				"select count(1) from ZDUser where 1=1");
		if (XString.isNotEmpty(step.getAllowOrgan())) {
			if (!checkPrivString(step.getAllowOrgan())) {
				throw new WorkflowException("AllowOrgan中含有非法字符,StepID="
						+ step.getID());
			}
			qb.append(" and BranchInnerCode in ('"
					+ step.getAllowOrgan().replaceAll(",", "','") + "')");
		}
		if (XString.isNotEmpty(step.getAllowRole())) {
			if (!checkPrivString(step.getAllowRole())) {
				throw new WorkflowException("AllowRole中含有非法字符,StepID="
						+ step.getID());
			}
			qb
					.append(" and exists (select 1 from ZDUserRole where UserName=ZDUser.UserName and RoleCode in ('"
							+ step.getAllowRole().replaceAll(",", "','")
							+ "'))");
		}
		if ((XString.isNotEmpty(step.getAllowOrgan()))
				|| (XString.isNotEmpty(step.getAllowRole()))) {
			return qb.executeInt() > 0;
		}
		return false;
	}

	public static String[] getPrivUsers(ZWStepSchema step)
			throws WorkflowException {
		Mapx map = new Mapx();
		if ((XString.isNotEmpty(step.getAllowUser()))
				&& (checkPrivString(step.getAllowUser()))) {
			String[] p1 = step.getAllowUser().split(",");
			for (int i = 0; i < p1.length; i += 2) {
				map.put(p1[i], "1");
			}
		}

		QueryBuilder qb = new QueryBuilder(
				"select username,1 from ZDUser where 1=1");
		if (XString.isNotEmpty(step.getAllowOrgan())) {
			if (!checkPrivString(step.getAllowOrgan())) {
				throw new WorkflowException("AllowOrgan中含有非法字符,StepID="
						+ step.getID());
			}
			qb.append(" and BranchInnerCode in ('"
					+ step.getAllowOrgan().replaceAll(",", "','") + "')");
		}
		if (XString.isNotEmpty(step.getAllowRole())) {
			if (!checkPrivString(step.getAllowRole())) {
				throw new WorkflowException("AllowRole中含有非法字符,StepID="
						+ step.getID());
			}
			qb
					.append(" and exists (select 1 from ZDUserRole where UserName=ZDUser.UserName and RoleCode in ('"
							+ step.getAllowRole().replaceAll(",", "','")
							+ "'))");
		}
		if ((XString.isNotEmpty(step.getAllowOrgan()))
				|| (XString.isNotEmpty(step.getAllowRole()))) {
			map.putAll(qb.executeDataTable().toMapx(0, 1));
		}
		String[] users = new String[map.size()];
		Object[] ks = map.keyArray();
		for (int i = 0; i < map.size(); i++) {
			users[i] = ks[i].toString();
		}
		return users;
	}

	public static boolean isStartStep(long instanceID) {
		QueryBuilder qb = new QueryBuilder(
				"where InstanceID=? and (State=? or State=?)", instanceID);
		qb.add("Underway");
		qb.add("Unread");
		ZWStepSet set = new ZWStepSchema().query(qb);
		for (int i = 0; i < set.size(); i++) {
			if (findWorkflow(set.get(i).getWorkflowID()).getStartNode().getID() == set
					.get(i).getNodeID()) {
				return true;
			}
		}
		return false;
	}
}

/*
 * com.xdarkness.workflow.WorkflowUtil JD-Core Version: 0.6.0
 */