package com.nano.core.event
{
	
	import flash.events.Event;
	
	public class CommandEvent extends Event {
		public static const EXEC_COMMAND:String= "execCommand";
		public static const EXIT_SERVER:String = "exitServer";
		
		public var command:String = null;
		
		
		public function CommandEvent(type:String, 
									 bubbles:Boolean = false,
									 cancelable:Boolean = false) {
			super(type, bubbles, cancelable);
		}
		
		public static function getExitCommandEvent():CommandEvent {
			var evt:CommandEvent = new CommandEvent(EXIT_SERVER);
			return evt;
		}
		
		public static function getExecCommandEvent(cmd:String):CommandEvent {
			var evt:CommandEvent = new CommandEvent(EXEC_COMMAND);
			evt.command = cmd;
			return evt;
		}
//		public function CommandEvent(type:String, bubbles:Boolean=false, 
//									 cancelable:Boolean=false) {
//			
//			super(type, bubbles, cancelable);
//		}
		
	}
}