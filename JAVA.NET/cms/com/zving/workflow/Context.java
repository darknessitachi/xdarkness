 package com.zving.workflow;
 
 import com.zving.framework.User;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.Mapx;
 import com.zving.platform.pub.NoUtil;
 import com.zving.platform.pub.PlatformCache;
 import com.zving.schema.ZWStepSchema;
 import java.util.Date;
 
 public class Context
 {
   private WorkflowInstance instance;
   private ZWStepSchema step;
   private Mapx variables;
   private Workflow flow;
   private Transaction tran;
   private boolean variableFlag = false;
 
   public Context(Transaction tran, WorkflowStep step) throws WorkflowException {
     this(tran, step);
   }
 
   public Context(Transaction tran, ZWStepSchema step) throws WorkflowException {
     this.tran = tran;
     this.flow = WorkflowUtil.findWorkflow(step.getWorkflowID());
     this.instance = WorkflowUtil.findInstance(step.getInstanceID());
     this.step = step;
     this.variables = WorkflowUtil.getDataVariables(this.instance.getDataID(), step.getDataVersionID());
   }
 
   public WorkflowInstance getInstance() {
     return this.instance;
   }
 
   public ZWStepSchema getStep() {
     return this.step;
   }
 
   public Mapx getVariables() {
     return this.variables;
   }
 
   public Workflow getWorkflow() {
     return this.flow;
   }
 
   public Transaction getTransaction() {
     return this.tran;
   }
 
   public Context(Transaction tran, WorkflowInstance instance, WorkflowStep step) {
     this(tran, instance, step);
   }
 
   public Context(Transaction tran, WorkflowInstance instance, ZWStepSchema step) {
     this.tran = tran;
     this.flow = WorkflowUtil.findWorkflow(step.getWorkflowID());
     this.instance = instance;
     this.step = step;
     if (step.getID() != 1L)
       this.variables = WorkflowUtil.getDataVariables(instance.getDataID(), step.getDataVersionID());
   }
 
   protected Context(long workflowID)
   {
     this.flow = WorkflowUtil.findWorkflow(workflowID);
   }
 
   public String getOwner()
   {
     return User.getUserName();
   }
 
   public String getOwnerOrgan()
   {
     return User.getBranchInnerCode();
   }
 
   public void setStepOrgan(String organNames)
   {
     this.step.setAllowOrgan(organNames);
   }
 
   public void setStepRole(String roleNames)
   {
     this.step.setAllowRole(roleNames);
   }
 
   public void setStepUser(String userNames)
   {
     this.step.setAllowUser(userNames);
   }
 
   public Object getValue(String fieldName)
   {
     return this.variables.get(fieldName);
   }
 
   public void setValue(String fieldName, String value)
   {
     this.variableFlag = true;
     this.variables.put(fieldName, value);
   }
 
   public void setValue(String fieldName, long value)
   {
     this.variableFlag = true;
     this.variables.put(fieldName, value);
   }
 
   public void setValue(String fieldName, int value)
   {
     this.variableFlag = true;
     this.variables.put(fieldName, value);
   }
 
   public void save()
   {
     this.tran.add(this.step, 2);
     if (this.variableFlag)
       WorkflowUtil.saveDataVariables(this);
   }
 
   public boolean isOwnRole(String roleName)
   {
     String roles = "," + PlatformCache.getUserRole(User.getUserName()) + ",";
     roleName = "," + roleName + ",";
     return roles.indexOf(roleName) >= 0;
   }
 
   public String getFlowName()
   {
     return this.instance.getName();
   }
 
   public String getWorkflowName()
   {
     return WorkflowUtil.findWorkflow(this.instance.getWorkflowID()).getName();
   }
 
   public String getStepName()
   {
     return WorkflowUtil.getStepName(this.flow.getID(), this.step.getNodeID());
   }
 
   public void gotoStep(String stepName) throws Exception {
     Workflow.Node[] nodes = this.flow.getNodes();
     for (int i = 0; i < nodes.length; ++i) {
       Workflow.Node node = nodes[i];
       if ((!node.getType().equals("ActionNode")) && (node.getName().equalsIgnoreCase(stepName))) {
         WorkflowStep newStep = new WorkflowStep();
         newStep.setID(NoUtil.getMaxID("WorkflowStepID"));
         newStep.setWorkflowID(this.flow.getID());
 
         newStep.setAllowOrgan(node.getData().getString("Organ"));
         newStep.setAllowRole(node.getData().getString("Role"));
         newStep.setAllowUser(node.getData().getString("User"));
 
         newStep.setNodeID(node.getID());
         newStep.setActionID(-4);
         newStep.setInstanceID(this.instance.getID());
 
         newStep.setPreviousStepID(this.step.getID());
         newStep.setStartTime(new Date());
         newStep.setOperators(null);
         newStep.setState("Unread");
         newStep.setAddTime(new Date());
         newStep.setAddUser(User.getUserName());
 
         if (node.getType().equals("StartNode")) {
           newStep.setAllowUser(this.instance.getAddUser());
           newStep.setState("Underway");
           newStep.setOwner(this.instance.getAddUser());
         }
 
         Context context = new Context(this.tran, this.instance, newStep);
 
         WorkflowUtil.findAdapter().onStepCreate(context);
         WorkflowAction.executeMethod(this, this.flow.findNode(newStep.getNodeID()), "Pre");
         context.save();
 
         if ("EndNode".equalsIgnoreCase(node.getType()))
         {
           newStep.setState("Finish");
           newStep.setFinishTime(new Date());
           newStep.setOwner("SYSTEM");
 
           this.instance.setState("Completed");
           this.tran.add(context.getInstance(), 2);
         }
         this.tran.add(newStep, 1);
       }
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.workflow.Context
 * JD-Core Version:    0.5.4
 */