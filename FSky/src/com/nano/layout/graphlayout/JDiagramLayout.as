package com.nano.layout.graphlayout{
	import com.nano.data.JHashMap;
	import com.linkage.cmp.util.CollectionUtil;
	
	import flash.display.Sprite;
	
	import mx.collections.ArrayCollection;

	/**
	 * 布局管理器基类
	 * 所有“自由图形式”自由布局都必须继承该类
	 * 并提供doLayout()方法的具体实现
	 */
	public class JDiagramLayout{
		public var nodes:Array=[];
		public var startX:Number=0;
		public var startY:Number=0;
		public var layoutWidth:Number=0;
		public var layoutHeight:Number=0;
		public var eachFunc:Function;//布局过程中需要在每个对象上调用的函数引用
		
		/**
		 * 布局整体旋转的角度
		 */ 
		public var layoutRotation:Number=0;
		
		/**
		 * 构造方法
		 * @param	startX
		 * @param	startY
		 * @param	layoutWidth
		 * @param	layoutHeight
		 */
		public function JDiagramLayout(startX:Number=0,startY:Number=0,layoutWidth:Number=0,layoutHeight:Number=0){
			this.startX=startX;
			this.startY=startY;
			this.layoutWidth=layoutWidth;
			this.layoutHeight=layoutHeight;
		}
		
		/**
		 * 子类必须提供此方法的具体实现
		 */
		public function doLayout(config:*=null):void{}
		
		/**
		 * 向布局管理器中添加一个节点
		 * @param	node
		 */
		public function addToLayout(config:*):void{
			if(config is Sprite){
				this.nodes.push(config);
			}else if(config is Array||config is ArrayCollection||config is JHashMap){
				CollectionUtil.each(config,function(item:*):void{
					nodes.push(item);
				});
			}
		}
		
		public function clearLayout():void{
			this.nodes.length=0;
		}
	}
}