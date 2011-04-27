 package com.zving.workflow;
 
 import com.zving.framework.User;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.pub.NoUtil;
 import com.zving.schema.ZWStepSchema;
 import com.zving.schema.ZWStepSet;
 import com.zving.workflow.methods.ConditionMethod;
 import com.zving.workflow.methods.ConditionScript;
 import com.zving.workflow.methods.MethodScript;
 import com.zving.workflow.methods.NodeMethod;
 import java.util.Date;
 
 public class WorkflowAction
 {
   public static final int RESTART_ACTIONID = -5;
   public static final int START_ACTIONID = -2;
   public static final int TEMPORARYSAVE_ACTIONID = -1;
   public static final int APPLY_ACTIONID = -3;
   public static final int SCRIPT_ACTIONID = -4;
   public static final int FORCEEND_ACTIONID = -6;
   private int id;
   private String name;
   private Mapx data;
   private Workflow.Node node;
   private Context context;
 
   protected WorkflowAction(int id, String name, Mapx data)
   {
     this.id = id;
     this.name = name;
     this.data = data;
   }
 
   public WorkflowAction(Workflow.Node node) {
     if (!node.getType().equals("ActionNode")) {
       throw new RuntimeException("WorkflowAction必须是动作节点!");
     }
     this.id = node.getID();
     this.name = node.getName();
     this.data = node.getData();
     this.node = node;
   }
 
   public int getID() {
     return this.id;
   }
 
   public String getName() {
     return this.name;
   }
 
   public Mapx getData() {
     return this.data;
   }
 
   public void execute(Transaction tran, long instanceID, String memo)
     throws Exception
   {
     execute(tran, WorkflowUtil.findInstance(instanceID), memo);
   }
 
   public void execute(Transaction tran, long instanceID, String selectedUser, String memo) throws Exception {
     execute(tran, WorkflowUtil.findInstance(instanceID), selectedUser, memo);
   }
 
   public void execute(Context context, String selectedUser, String memo) throws Exception {
     execute(context.getTransaction(), context.getInstance(), context.getStep(), selectedUser, memo);
   }
 
   public void execute(Transaction tran, WorkflowInstance instance, String memo)
     throws Exception
   {
     execute(tran, instance, null, memo);
   }
 
   public void execute(Transaction tran, WorkflowInstance instance, String selectedUser, String memo) throws Exception {
     if (this.id == -5) {
       restartInstance(tran, instance, memo);
       return;
     }
     ZWStepSchema step = WorkflowUtil.findCurrentStep(instance.getID());
     execute(tran, instance, step, selectedUser, memo);
   }
 
   public void execute(Transaction tran, WorkflowInstance instance, ZWStepSchema step, String selectedUser, String memo) throws Exception
   {
     if (this.id == -5) {
       restartInstance(tran, instance, memo);
       return;
     }
     this.context = new Context(tran, instance, step);
     step.setMemo(memo);
     if (this.id == -1) {
       WorkflowUtil.findAdapter().onTemporarySave(this.context);
       this.context.save();
     } else {
       step.setState("Finish");
       step.setFinishTime(new Date());
       step.setOwner(User.getUserName());
       Workflow wf = WorkflowUtil.findWorkflow(this.context.getStep().getWorkflowID());
       executeMethod(this.context, wf.findNode(step.getNodeID()), "Post");
       if (!this.context.getInstance().getState().equals("Completed")) {
         executeMethod(this.context, wf.findNode(this.id), "Post");
       }
       WorkflowUtil.findAdapter().onActionExecute(this.context, this);
       this.context.save();
       if (!this.context.getInstance().getState().equals("Completed"))
         tryCreateNextStep(selectedUser);
     }
   }
 
   public void restartInstance(Transaction tran, WorkflowInstance instance, String memo)
   {
     instance.setState("Activated");
     tran.add(instance, 2);
     Workflow flow = WorkflowUtil.findWorkflow(instance.getWorkflowID());
 
     WorkflowStep newStep = new WorkflowStep();
     newStep.setID(NoUtil.getMaxID("WorkflowStepID"));
     newStep.setWorkflowID(instance.getWorkflowID());
     newStep.setNodeID(flow.getStartNode().getID());
     newStep.setActionID(-5);
     newStep.setPreviousStepID(flow.getEndNode().getID());
     newStep.setInstanceID(instance.getID());
     newStep.setDataVersionID("0");
     newStep.setOwner(User.getUserName());
     newStep.setStartTime(new Date());
     newStep.setMemo("重新开始流程");
     newStep.setOperators(User.getUserName());
     newStep.setState("Underway");
     newStep.setAddTime(new Date());
     newStep.setAddUser(User.getUserName());
     tran.add(newStep, 1);
 
     Context context = new Context(tran, instance, newStep);
     WorkflowUtil.findAdapter().onStepCreate(context);
     context.save();
   }
 
   private void tryCreateNextStep(String selectedUser)
     throws Exception
   {
     if (this.node == null) {
       throw new WorkflowException("不正确的WorkflowAction对象，node未置值");
     }
     WorkflowTransition[] wts = this.node.getTransitions();
     for (int i = 0; i < wts.length; ++i) {
       WorkflowTransition wt = wts[i];
 
       if (checkConditions(wt)) {
         if (!checkOtherLine(wt)) {
           this.context.getStep().setState("Wait");
           this.context.save();
           return;
         }
 
         if ("1".equals(this.node.getWorkflow().findNode(this.context.getStep().getNodeID()).getData().get("SignJointly"))) {
           QueryBuilder qb = new QueryBuilder("update ZWStep set State=? where InstanceID=? and State=?");
           qb.add("Finish");
           qb.add(this.context.getStep().getInstanceID());
           qb.add("Wait");
         }
 
         ZWStepSchema newStep = new WorkflowStep();
         newStep.setID(NoUtil.getMaxID("WorkflowStepID"));
         newStep.setWorkflowID(this.context.getInstance().getWorkflowID());
 
         newStep.setAllowOrgan(wt.getTargetNode().getData().getString("Organ"));
         newStep.setAllowRole(wt.getTargetNode().getData().getString("Role"));
         newStep.setAllowUser(wt.getTargetNode().getData().getString("User"));
 
         newStep.setNodeID(wt.getTargetNode().getID());
         newStep.setActionID(getID());
         newStep.setInstanceID(this.context.getInstance().getID());
 
         newStep.setPreviousStepID(this.context.getStep().getID());
         newStep.setStartTime(new Date());
         newStep.setOperators(null);
         newStep.setState("Unread");
         newStep.setAddTime(new Date());
         newStep.setAddUser(User.getUserName());
 
         if ("1".equals(wt.getTargetNode().getData().get("SignJointly")))
         {
           String[] users = WorkflowUtil.getPrivUsers(newStep);
           if ((users == null) || (users.length == 0)) {
             throw new WorkflowException("没有可以执行下一步骤的用户，请与系统管理员联系！InstanceID=" + newStep.getInstanceID());
           }
           for (int j = 0; j < users.length; ++j)
             if (j == 0) {
               newStep.setAllowUser(users[j]);
               newStep.setAllowOrgan(null);
               newStep.setAllowRole(null);
             }
             else {
               ZWStepSchema step = (ZWStepSchema)newStep.clone();
               step.setAllowUser(users[j]);
               step.setAllowOrgan(null);
               step.setAllowRole(null);
               step.setID(NoUtil.getMaxID("WorkflowStepID"));
               this.context.getTransaction().add(step, 1);
             }
         }
         else if (StringUtil.isNotEmpty(selectedUser)) {
           if (!StringUtil.checkID(selectedUser)) {
             throw new RuntimeException("可能的SQL注入：" + selectedUser);
           }
           newStep.setAllowUser(selectedUser);
           newStep.setAllowOrgan(null);
           newStep.setAllowRole(null);
         }
 
         if (wt.getTargetNode().getType().equals("StartNode")) {
           newStep.setAllowUser(this.context.getInstance().getAddUser());
           newStep.setState("Underway");
           newStep.setOwner(this.context.getInstance().getAddUser());
         }
 
         this.context = new Context(this.context.getTransaction(), this.context.getInstance(), newStep);
 
         if (!"EndNode".equalsIgnoreCase(wt.getTargetNode().getType())) {
           if ("1".equals(this.context.getWorkflow().getData().get("NotifyNextStep"))) {
             String[] users = WorkflowUtil.getPrivUsers(newStep);
             if ((users == null) || (users.length == 0)) {
               throw new WorkflowException("没有可以执行下一步骤的用户，请与系统管理员联系！InstanceID=" + newStep.getInstanceID());
             }
             WorkflowUtil.findAdapter().notifyNextStep(this.context, users);
           }
           else if (!WorkflowUtil.hasPrivUser(newStep)) {
             throw new WorkflowException("没有可以执行下一步骤的用户，请与系统管理员联系！InstanceID=" + newStep.getInstanceID());
           }
 
         }
 
         WorkflowUtil.findAdapter().onStepCreate(this.context);
         executeMethod(this.context, 
           WorkflowUtil.findWorkflow(newStep.getWorkflowID()).findNode(newStep.getNodeID()), "Pre");
 
         if (!this.context.getInstance().getState().equals("Completed"))
         {
           if ("EndNode".equalsIgnoreCase(wt.getTargetNode().getType()))
           {
             newStep.setState("Finish");
             newStep.setFinishTime(new Date());
             newStep.setOwner("SYSTEM");
 
             this.context.getInstance().setState("Completed");
             this.context.getTransaction().add(this.context.getInstance(), 2);
           }
           this.context.getTransaction().add(newStep, 1);
         }
         this.context.save();
       }
     }
   }
 
   public boolean checkConditions(WorkflowTransition wt)
     throws Exception
   {
     Object[] keyArray = wt.getData().keyArray();
     boolean flag = true;
     for (int i = 0; i < keyArray.length; ++i) {
       String key = (String)keyArray[i];
       String value = wt.getData().getString(key);
       if (("Script".equalsIgnoreCase(key)) && (StringUtil.isNotEmpty(value))) {
         ConditionScript cs = new ConditionScript();
         cs.setScript(value);
         flag = cs.validate(this.context);
       } else if (("Method".equalsIgnoreCase(key)) && (StringUtil.isNotEmpty(value))) {
         Object o = Class.forName(value).newInstance();
         ConditionMethod cm = null;
         if (o instanceof ConditionMethod)
           cm = (ConditionMethod)o;
         else {
           throw new WorkflowException(value + "没有实现ConditionMethod抽象类");
         }
         flag = cm.validate(this.context);
       }
     }
     return flag;
   }
 
   private boolean checkOtherLine(WorkflowTransition wt)
   {
     QueryBuilder qb = new QueryBuilder("where InstanceID=? and ID<>? and (State=? or State=?)", this.context
       .getInstance().getID(), this.context.getStep().getID());
     qb.add("Underway");
     qb.add("Unread");
     ZWStepSet set = new ZWStepSchema().query(qb);
     for (int i = 0; i < set.size(); ++i) {
       int nodeID = set.get(i).getNodeID();
       if (isLinked(nodeID, wt.getTargetNode().getID(), 1)) {
         return false;
       }
     }
     return true;
   }
 
   private boolean isLinked(int src, int target, int level)
   {
     if (level > 50) {
       return false;
     }
     Workflow wf = WorkflowUtil.findWorkflow(this.context.getInstance().getWorkflowID());
     WorkflowTransition[] wts = wf.findNode(src).getTransitions();
     for (int j = 0; j < wts.length; ++j) {
       if (wts[j].getFromNode().getID() == wts[j].getTargetNode().getID()) {
         continue;
       }
       if (wts[j].getTargetNode().getID() == target) {
         return true;
       }
       if (isLinked(wts[j].getTargetNode().getID(), target, level + 1)) {
         return true;
       }
     }
 
     return false;
   }
 
   public static void executeMethod(Context context, Workflow.Node node, String type)
     throws Exception
   {
     String method = node.getData().getString(type + "Action");
     String script = node.getData().getString(type + "Script");
     if (StringUtil.isNotEmpty(method)) {
       Object o = Class.forName(method).newInstance();
       NodeMethod nm = null;
       if (o instanceof NodeMethod)
         nm = (NodeMethod)o;
       else {
         throw new WorkflowException(method + "没有实现NodeMethod抽象类");
       }
       nm.execute(context);
     }
     if (StringUtil.isNotEmpty(script)) {
       MethodScript sa = new MethodScript(script);
       sa.execute(context);
     }
   }
 
   public static WorkflowAction getTemporarySaveAction()
   {
     return new WorkflowAction(-1, "暂存", new Mapx());
   }
 
   public static WorkflowAction getRestartAction()
   {
     return new WorkflowAction(-5, "重新处理", new Mapx());
   }
 
   public static WorkflowAction getApplyAction(int nodeID)
   {
     Mapx map = new Mapx();
     map.put("NodeID", nodeID);
     return new WorkflowAction(-3, "申请处理", map);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.workflow.WorkflowAction
 * JD-Core Version:    0.5.4
 */