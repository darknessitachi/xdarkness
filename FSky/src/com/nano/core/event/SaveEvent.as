package com.nano.core.event
{
	import flash.events.Event;
	
	public class SaveEvent extends Event
	{
		public var update_package:Object;
		public var update_tasks:XMLList;
		
		public function SaveEvent(type:String, update_package:Object, update_tasks:XMLList)
		{
			super(type);
			this.update_package = update_package;
			this.update_tasks = update_tasks;
		}
		
		override public function clone():Event
		{
			return new SaveEvent(type,update_package,update_tasks);
		}
		
	}
}