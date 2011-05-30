package com.nano.layout{
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	
	import mx.collections.ArrayCollection;
	/**
	 * 空布局管理器
	 * 什么事情都不做
	 * @version 1.0 2010-06-04
	 * @author  章小飞 zhangxf5@asiainfo-linkage.com
	 * @since   v1.3
	 */
	public class JEmptyLayout implements JLayoutManager {
		/**
		 * 布局管理器所属的容器
		 */
		protected var owner:DisplayObjectContainer;
		/**
		 * 添加到容器中的成员
		 */
		protected var members:ArrayCollection=new ArrayCollection();
		/**
		 * 子组件在容器中的边距
		 */
		public var topPadding:Number=0;
		public var rightPadding:Number=0;
		public var bottomPadding:Number=0;
		public var leftPadding:Number=0;
		
//		public static const VALIGN:Object={top:'top',middle:'middle',bottom:'bottom'};
//		public static const HALIGN:Object={left:'left',center:'center',right:'right'};
//		
//		public var halign:String;
//		public var valign:String;
		
		/**
		 * 构造方法
		 */
		public function JEmptyLayout(){
		}
		
		/**
		 * 对容器进行布局
		 * 默认的实现只是把子对象全部添加到容器中，而不对子组件的位置和尺寸提供任何操作
		 * 实际的子类必须覆盖此方法以提供特定的实现
		 * @param	owner
		 */
		public function layoutContainer():void {
			if(!this.owner){
				return;
			}
			for(var i:int=0;i<this.members.length;i++){
				this.owner.addChild(this.members.getItemAt(i) as DisplayObject);
			}
		}
		
		/**
		 * 向布局中添加一个组件
		 * @param	comp
		 * @param	constrains
		 */
		public function addComp(comp:DisplayObject,constrains:*=null):void{
			this.members.addItem(comp);
		}
		
		/**
		 * 在指定的位置插入一个组件
		 * @param	comp
		 * @param	index
		 * @param	constrains
		 */
		public function addCompAt(comp:DisplayObject,index:int,constrains:*=null):void {
			this.members.addItemAt(comp, index);
		}
		
		/**
		 * 从布局中删除一个组件
		 * @param	comp
		 * @param	constrains
		 */
		public function removeComp(comp:DisplayObject,constrains:*=null):void{
			this.owner.removeChild(comp);
			this.members.removeItemAt(this.members.getItemIndex(comp));
		}
		
		/**
		 * 设置布局管理器所属的容器
		 * @param	owner
		 */
		public function setOwner(owner:DisplayObjectContainer):void{
			this.owner=owner;
		}
		
		public function getMembers():ArrayCollection{
			return this.members;
		}
	}
}