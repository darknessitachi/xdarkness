package com.nano.layout {
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	
	import mx.collections.ArrayCollection;
	

	/**
	 * VBox
	 * 单行垂直布局
	 * @version 1.0
	 * @author  章小飞 zhangxf5@asiainfo-linkage.com
	 * @since   v1.3
	 */
	public class JVBoxLayout extends JEmptyLayout {
		private var halign:Number;
		private var vgap:Number;
		public static const HALIGN:*={LEFT:0,CENTER:1,RIGHT:2};
		
		/**
		 * 构造方法
		 * @param	halign	水平方向的间隔
		 * @param	vgap	垂直方向的间隔
		 */
		public function JVBoxLayout(halign:Number=1,vgap:Number=0){
			this.halign=halign;
			this.vgap=vgap;
		}
		
		/**
		 * 对所属的容器进行布局操作
		 */
		override public function layoutContainer():void{
			super.layoutContainer();
			var temp:*=0;
			var ytemp:Number=0;
			for(var i:int=0;i<this.members.length;i++){
				var item:DisplayObject=this.members.getItemAt(i) as DisplayObject;
				if(i>0){
					var previous:DisplayObject=this.members.getItemAt(i-1) as DisplayObject;
					ytemp+=previous.height;
					ytemp+=this.vgap;
					item.y=ytemp;
				}
				this.owner.addChild(item);
				item.x=this.getAlignX(item,this.halign);
			}
		}
				
		/**
		 * 根据水平方向的对齐方式获取组件的x轴坐标
		 * @param	obj
		 * @param	flag
		 * @return
		 */
		private function getAlignX(obj:DisplayObject,flag:Number=0):Number{
			var result:Number=0;
			if(flag==HALIGN.CENTER){
				result=(this.owner.width-obj.width)/2;
			}else if(flag==HALIGN.RIGHT){
				result=this.owner.width-obj.width;	
			}
			return result;
		}
	}
}