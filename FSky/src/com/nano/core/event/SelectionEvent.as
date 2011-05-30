package com.nano.core.event
{
	import flash.events.Event;
	/**
	 * 对象被选中或反选事件
	 * @version	1.3 2010-05-18
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */ 
	public class SelectionEvent extends Event{
		public static const SELECT:String="select";
		public static const DESELECT:String = "deselect";
		public static const REMOVE:String="remove";
		
		public var x:int = 0;
		public var y:int = 0;
		public var targetObj:Object = null;
		
		public function SelectionEvent(type:String,bubble:Boolean=false,cancelable:Boolean=false){
			super(type,bubble,cancelable);
		}
		
		public override function clone():Event{
			return new SelectionEvent(type);
		}
		
		public override function toString():String{
			return formatToString("SelectionEvent","type","bubbles","cancelable");
		}
	}
}