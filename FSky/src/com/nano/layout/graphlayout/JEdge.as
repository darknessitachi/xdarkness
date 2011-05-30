package com.nano.layout.graphlayout{
	/**
	 * 边
	 */ 
	public class JEdge{
		public var fromNode:JVetex;
		public var toNode:JVetex;
		
		public function JEdge(fromNode:JVetex=null,toNode:JVetex=null){
			this.fromNode=fromNode;
			this.toNode=toNode;
		}
		
		/**
		 * 判断两个顶点之间是否存在一条“边”
		 * @param	n1
		 * @param	n2
		 * @return
		 */
		public function existEdge(n1:JVetex,n2:JVetex):Boolean{
			if(this.fromNode==n1&&this.toNode==n2){
				return true;
			}
			if(this.fromNode==n2&&this.toNode==n1){
				return true;
			}
			return false;
		}
		
		/**
		 * 获取边的长度
		 * @return
		 */
		public function getLength():Number{
			var result:Number=(this.fromNode.ui.width+this.fromNode.ui.height+this.toNode.ui.width+this.toNode.ui.height)/4;
			if(result>0){
				return result;
			}
			return 50;
		}
	}
}