 package com.zving.workflow;
 
 import com.zving.framework.utility.Mapx;
 import com.zving.schema.ZWWorkflowSchema;
 import java.io.StringReader;
 import java.util.List;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.Element;
 import org.dom4j.io.SAXReader;
 
 public class Workflow extends ZWWorkflowSchema
 {
   private static final long serialVersionUID = 1L;
   public static final String STARTNODE = "StartNode";
   public static final String ENDNODE = "EndNode";
   public static final String COMMONNODE = "Node";
   public static final String ACTIONNODE = "ActionNode";
   private Node[] nodes;
   private Mapx data = new Mapx();
 
   protected synchronized void init() {
     try {
       SAXReader sax = new SAXReader();
       Document doc = null;
       StringReader reader = new StringReader(getConfigXML());
       doc = sax.read(reader);
       List list = doc.getRootElement().elements("config");
       for (int i = 0; i < list.size(); ++i) {
         Element ele = (Element)list.get(i);
         this.data.put(ele.attributeValue("name"), ele.attributeValue("value"));
       }
       list = doc.getRootElement().elements("node");
       this.nodes = new Node[list.size()];
       for (int i = 0; i < list.size(); ++i) {
         Element ele = (Element)list.get(i);
         Node node = new Node(this, ele);
         this.nodes[i] = node;
       }
 
       for (int i = 0; i < this.nodes.length; ++i) {
         WorkflowTransition[] wts = this.nodes[i].getTransitions();
         for (int j = 0; j < wts.length; ++j)
           wts[j].init();
       }
     }
     catch (DocumentException e1) {
       e1.printStackTrace();
     }
   }
 
   public Mapx getData()
   {
     return this.data;
   }
 
   public Node[] getNodes() {
     return this.nodes;
   }
 
   public Node findNode(int id) {
     if (this.nodes == null) {
       init();
     }
     for (int i = 0; i < this.nodes.length; ++i) {
       if (this.nodes[i].getID() == id) {
         return this.nodes[i];
       }
     }
     return null;
   }
 
   public Node getStartNode() {
     for (int i = 0; i < this.nodes.length; ++i) {
       if (this.nodes[i].getType().equals("StartNode")) {
         return this.nodes[i];
       }
     }
     return null;
   }
 
   public Node getEndNode() {
     for (int i = 0; i < this.nodes.length; ++i) {
       if (this.nodes[i].getType().equals("EndNode")) {
         return this.nodes[i];
       }
     }
     return null;
   }
 
   public WorkflowTransition findTransition(int id) {
     if (this.nodes == null) {
       init();
     }
     for (int i = 0; i < this.nodes.length; ++i) {
       WorkflowTransition[] wts = this.nodes[i].getTransitions();
       for (int j = 0; j < wts.length; ++j) {
         if (wts[j].getID() == id) {
           return wts[j];
         }
       }
     }
     return null;
   }
 
   public static Workflow convert(ZWWorkflowSchema schema) {
     Workflow wf = new Workflow();
     for (int i = 0; i < wf.getColumnCount(); ++i) {
       wf.setV(i, schema.getV(i));
     }
     wf.init();
     return wf;
   }
 
   public class Node
   {
     private int id;
     private String name;
     private String type;
     private Workflow wf;
     private Mapx map;
     private WorkflowTransition[] transitions;
 
     public Node(Workflow wf, Element ele)
     {
       this.wf = wf;
       String strid = ele.attributeValue("id");
       this.id = Integer.parseInt(strid.substring(strid.lastIndexOf("e") + 1));
       this.type = strid.substring(0, strid.lastIndexOf("e") + 1);
       this.name = ele.attributeValue("name");
       List list = ele.elements("data");
       this.map = new Mapx();
       for (int i = 0; i < list.size(); ++i) {
         Element data = (Element)list.get(i);
         String k = data.attributeValue("type");
         String v = data.getText();
         this.map.put(k, v);
       }
       list = ele.elements("line");
       this.transitions = new WorkflowTransition[list.size()];
       for (int i = 0; i < list.size(); ++i) {
         Element lineEle = (Element)list.get(i);
         this.transitions[i] = new WorkflowTransition(this, lineEle);
       }
     }
 
     public int getID() {
       return this.id;
     }
 
     public String getName() {
       return this.name;
     }
 
     public String getType() {
       return this.type;
     }
 
     public Workflow getWorkflow() {
       return this.wf;
     }
 
     public WorkflowTransition[] getTransitions()
     {
       return this.transitions;
     }
 
     public Mapx getData() {
       return this.map;
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.workflow.Workflow
 * JD-Core Version:    0.5.4
 */