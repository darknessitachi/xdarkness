package com.nano.core.event{
	import flash.events.Event;

	/**
	 * 形变事件
	 * @version	1.3 2010-05-18
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */
	public class TransformEvent extends Event {
		public static const MOVED:String = "moved";//对象的位置发生了变化
		public static const SCALED:String = "scaled";//对象发生了缩放形变
		public static const SKEWED:String = "skewed";//对象发生了斜切形变
		public static const ROTATED:String = "rotated";//对象发生了旋转形变
		public function TransformEvent(type:String) {
			super(type);
		}
		
		public override function clone():Event{
			var newOne:TransformEvent=new TransformEvent(type);
			return newOne;
		}
		
		public override function toString():String{
			return formatToString("TransformEvent","type","bubbles","cancelable");
		}
	}

}