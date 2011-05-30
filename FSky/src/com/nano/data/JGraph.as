package com.nano.data{
	import com.linkage.cmp.util.CollectionUtil;

	/**
	 * 图
	 * @version	1.3 2010-08-18
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */
	public class JGraph{
		/**
		 * 边：二维数组
		 */ 
		public var edges:Array=[];
		/**
		 * 标志位，说明图中是否存在“回路”
		 */
		public var ringExits:Boolean=false;
		
		/**
		 * 构造方法
		 * @param	nums	顶点个数
		 */
		public function JGraph(nums:int=0){
			for(var i:int=0;i<nums;i++){
				var arr:Array=new Array(nums);
				CollectionUtil.fillArray(arr,0);
				edges.push(arr);
			}	
		}
		
		/**
		 * 检测两个顶点之间是否有边
		 * @param	i
		 * @param	j
		 * @return
		 */
		public function hasEdge(i:int,j:int):Number {
			return edges[i][j];
		}
		
		/**
		 * 设置两个顶点之间的边
		 * @param	i
		 * @param	j
		 * @param	weight	权重，默认为1
		 * @return
		 */
		public function setEdge(i:int,j:int,weight:Number=1):void {
			if(i>this.size()-1||j>this.size()-1){
				throw new Error("超出数组边界");
			}
			edges[i][j] = weight;
		}
		
		/**
		 * 获取指定顶点的邻居
		 * @param	vertex
		 * @return
		 */
		public function neighbors(vertex:int):Array {
			var result:Array = [];
			for (var i:int = 0; i < this.size();i++ ) {
				if (this.hasEdge(vertex,i)) {
					result.push(i);
				}
			}
			return result;
		}
		
		/**
		 * 返回图中顶点的个数
		 * @return
		 */
		public function size():int {
			return this.edges.length;
		}
		
		/**
		 * 深度优先遍历
		 * @param	int source
		 * @return
		 */
		public function depthFirstTraverse(source:int):Array {
			var result:Array = [];
			var visited:Array = [];
			_depthFirstTraverse(source, result, visited);
			return result;
		}
		
		/**
		 * 深度优先遍历实现
		 * @param	vertex  	当前访问的顶点
		 * @param	result		结果
		 * @param	visited		访问的节点数组
		 */
		protected function _depthFirstTraverse(vertex:int,result:Array,visited:Array):void {
			visited[vertex] = true;
			result.push(vertex);
			var nbs:Array=this.neighbors(vertex);
			for(var i:int=0;i<nbs.length;i++){
				var temp:int=nbs[i];
				if (!visited[temp]) {
					_depthFirstTraverse(temp,result,visited);//递归
				}
			}
		}
		
		/**
		 * 广度优先遍历
		 * @param	int source
		 * @return
		 */
		public function breadthFirstTraverse(source:int):Array{
			var result:Array = [];
			var visited:Array = [];
			var queue:JQueue=new JQueue();
			visited[source]=true;
			queue.offer(source);
			while(!queue.isEmpty()){
				var vertex:int=queue.poll() as int;
				result.push(vertex);
				var nbs:Array=this.neighbors(vertex);
				for(var i:int=0;i<nbs.length;i++){
					var temp:int=nbs[i];
					if (!visited[temp]) {
						visited[temp]=true;
						queue.offer(temp);
					}
				}
			}
			return result;
		}
		
		
		/**
		 * 环路检测，从指定源点开始，按照深度优先算法，检测是否存在到自己的环路
		 * 如果检测到回路，则返回第一个找到的回路
		 * @param	source
		 * @return
		 */
		public function ringTest(source:int):Array{
			var result:Array = [];
			var visited:Array = [];
			_ringTest(source,source, result, visited);
			return result;
		}
		
		protected function _ringTest(source:int,vertex:int,result:Array,visited:Array):void{
			visited[vertex] = true;
			result.push(vertex);
			var nbs:Array=this.neighbors(vertex);
			for(var i:int=0;i<nbs.length;i++){
				var temp:int=nbs[i];
				if (!visited[temp]) {
					_ringTest(source,temp,result,visited);//递归
				}else if(temp==source){					  //如果出现了指向源点的边，说明出现了“回路”，立即返回
					this.ringExits=true;
					return;
				}
			}
			return;
		}
		
		public function toString():String{
			var result:String="";
			for(var i:int=0;i<this.edges.length;i++){
				var arr:Array=this.edges[i];
				for(var j:int=0;j<arr.length;j++){
					result+=arr[j];
					if(j==(arr.length-1)){
						result+="\n";
					}else{
						result+=" ";
					}
				}
			}
			return result;
		}
	}
}