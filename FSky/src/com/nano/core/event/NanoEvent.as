package com.nano.core.event{
	import com.nano.serialization.json.JSON;
	
	import flash.events.Event;

	/**
	 * NanoSystem一般化的事件
	 * 所有需要的参数都可以设置到evtParam对象上，以便收到事件的函数做出处理。
	 */ 
	public class NanoEvent extends Event{
		public var evtParam:Object={};
		
		public function NanoEvent(type:String="nano_evt",bubble:Boolean=false,cancelable:Boolean=false){
			super(type,bubble,cancelable);
		}
		
		/**
		 * 克隆事件对象
		 * @return
		 */
		override public function clone():Event{
			var newOne:NanoEvent = new NanoEvent(type);
			newOne.evtParam=this.evtParam;
			return newOne;
		}
		
		override public  function toString():String{
			return formatToString("NanoEvent","type","bubbles","cancelable");
		}
	}
}