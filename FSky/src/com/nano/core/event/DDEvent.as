package com.nano.core.event{
	import flash.events.Event;
	/**
	 * 拖拽事件
	 * @copy	南京联创
	 * @version	1.3 2010-05-18
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */	
	public class DDEvent extends Event {
		/**
		 * 开始拖拽
		 */
		public static const START_DRAG:String = "StartDrag";
		/**
		 * 拖拽中
		 */
		public static const ON_DRAGGING:String = "OnDragging";
		/**
		 * 结束拖拽
		 */
		public static const END_DRAG:String="EndDrag";
		/**
		 * 进入落下区域
		 */
		public static const DROP_ENTER:String = "DropEnter";
		/**
		 * 位于落下区域上方
		 */
		public static const DROP_OVER:String = "DropOver";
		/**
		 * 离开落下区域
		 */
		public static const DROP_OUT:String = "DropOut";
		/**
		 * 拖拽源在目标区域放下
		 */
		public static const DROP:String = "Drop";
		/**
		 * 拖拽源源拖拽结束
		 */
		public static const SRC_DRAG_END:String = "SrcDragEnd";
		/**
		 * 合法的落下区域
		 */
		public static const VALID_DROP_TARGET:String = "validDropTarget";
		/**
		 * 非法的落下区域
		 */
		public static const INVALID_DROP_TARGET:String = "invalidDropTarget";
		/**
		 * 容器内部成员拖拽中
		 */
		public static const MEMBER_DRAGING:String="memberDraging";
		/**
		 * 事件目标对象，由于原生的Event.target属性为只读，使用此属性来标记当前的目标对象
		 */
		public var targetObj:Object = null;
		public var stageX:Number=0;
		public var stageY:Number = 0;
		/**
		 * x轴位置与上一次位置之间的偏移量
		 */
		public var deltaX:Number=0;
		/**
		 * y轴位置与上一次位置之间的偏移量
		 */
		public var deltaY:Number=0;
		/**
		 * 从MOUSE_DOWN到MOUSE_UP之间x轴总共移动的位移量
		 */
		public var deltaXDistance:Number = 0;
		/**
		 * 从MOUSE_DOWN到MOUSE_UP之间y轴总共移动的位移量
		 */
		public var deltaYDistance:Number = 0;
		/**
		 * 对象开始被拖拽的位置参数，格式为:{origX:0,origY:0,item:obj}，对象在收到DDEvent后，
		 * 可以通过此参数获得开始被拖拽的位置信息
		 */
		public var origPosition:*;
		
		/**
		 * 构造方法
		 * @param	type
		 */
		public function DDEvent(type:String){
			super(type);
		}
		
		/**
		 * 克隆事件对象
		 * @return
		 */
		override public function clone():Event{
			var newOne:DDEvent = new DDEvent(type);
			newOne.stageX = this.stageX;
			newOne.stageY = this.stageY;
			newOne.deltaX=this.deltaX;
			newOne.deltaY = this.deltaY;
			newOne.deltaXDistance = this.deltaXDistance;
			newOne.deltaYDistance = this.deltaYDistance;
			newOne.targetObj=this.targetObj;
			return newOne;
		}
		
		/**
		 * toString
		 * @return
		 */
		public override function toString():String{
			return formatToString("DDEvent","type","bubbles","cancelable");
		}
	}
}