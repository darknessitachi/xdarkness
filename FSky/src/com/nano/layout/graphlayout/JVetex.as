package com.nano.layout.graphlayout{
	import flash.display.Sprite;
	/**
	 * 顶点
	 */ 
	public class JVetex{
		public var x:Number=0;				//x轴位置
		public var dx:Number=0;				//x轴偏移量
		public var y:Number=0;				//y轴位置
		public var dy:Number=0;				//y轴位置偏移量
		public var degree:Number=0;			//顶点的度
		public var fixed:Boolean=false;		//是否固定此节点，如果此属性为true，布局管理器将不会移动此节点
		public var ui:Sprite;				//此节点关联的显示对象
		/**
		 * 排斥力因子
		 */ 
		public var repulsionFactor:Number=0.75;
		/**
		 *	默认排斥力
		 */ 
		public var defaultRepulsion:Number=50;
		
		/**
		 * 构造函数
		 * 必须提供一个显示对象作为参数
		 */ 	
		public function JVetex(ui:Sprite){
			this.ui=ui;
		}
		
		/**
		 * 与UI对象进行位置同步
		 */ 
		public function refresh():void{
			this.x=this.ui.x;
			this.y=this.ui.y;
		}
		
		/**
		 * 提交更改到显示对象
		 */ 
		public function commit():void{
			this.ui.x=this.x;
			this.ui.y=this.y;
		}
		
		/**
		 * 获取顶点排斥力
		 * 顶点的排斥力与顶点的度相关
		 * 顶点的度越大，顶点的斥力越强
		 * @return
		 */
		public function getRepulsion():Number{
			var result:Number=defaultRepulsion;
			if(this.degree>1){
				result=(this.ui.width+this.ui.height)*Math.log(this.degree)*repulsionFactor;
			}
			return result;
		}
	}
}