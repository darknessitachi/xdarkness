 package com.zving.workflow;
 
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.workflow.methods.ConditionMethod;
 import com.zving.workflow.methods.ConditionScript;
 import java.util.List;
 import org.dom4j.Element;
 
 public class WorkflowTransition
 {
   private Workflow.Node node;
   private int id;
   private String name;
   private Mapx map;
   private Workflow.Node targetNode;
   private String target;
 
   public WorkflowTransition(Workflow.Node node, Element ele)
   {
     this.node = node;
     String strid = ele.attributeValue("id");
     this.id = Integer.parseInt(strid.substring(strid.lastIndexOf("e") + 1));
     this.name = ele.attributeValue("name");
     this.target = ele.attributeValue("target").substring(ele.attributeValue("target").lastIndexOf("e") + 1);
     List list = ele.elements("data");
     this.map = new Mapx();
     for (int i = 0; i < list.size(); ++i) {
       Element data = (Element)list.get(i);
       String k = data.attributeValue("type");
       String v = data.getText();
       this.map.put(k, v);
     }
   }
 
   protected void init() {
     int targetID = Integer.parseInt(this.target);
     this.targetNode = this.node.getWorkflow().findNode(targetID);
   }
 
   public int getID() {
     return this.id;
   }
 
   public String getName() {
     return this.name;
   }
 
   public Mapx getData() {
     return this.map;
   }
 
   public Workflow.Node getTargetNode() {
     return this.targetNode;
   }
 
   public Workflow.Node getFromNode() {
     return this.node;
   }
 
   public boolean validate(Context context)
     throws Exception
   {
     Object[] keyArray = this.map.keyArray();
     boolean flag = true;
     for (int i = 0; i < keyArray.length; ++i) {
       String key = (String)keyArray[i];
       String value = this.map.getString(key);
       if (("Script".equalsIgnoreCase(key)) && (StringUtil.isNotEmpty(value))) {
         ConditionScript cs = new ConditionScript();
         cs.setScript(value);
         flag = cs.validate(context);
       } else if (("Method".equalsIgnoreCase(key)) && (StringUtil.isNotEmpty(value))) {
         Object o = Class.forName(value).newInstance();
         ConditionMethod cm = null;
         if (o instanceof ConditionMethod)
           cm = (ConditionMethod)o;
         else {
           throw new WorkflowException(value + "没有实现ConditionMethod抽象类");
         }
         flag = cm.validate(context);
       }
     }
     return flag;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.workflow.WorkflowTransition
 * JD-Core Version:    0.5.4
 */