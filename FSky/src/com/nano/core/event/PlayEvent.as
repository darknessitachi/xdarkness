package com.nano.core.event
{
	import flash.events.Event;
	
	public class PlayEvent extends Event{
		public static const PLAYING:String="playing";	//正在播放中
		public static const STOPPED:String="stopped";	//已停止
		public var flag:Number=0;						//标志位
		
		public function PlayEvent(type:String,bubble:Boolean=false,cancelable:Boolean=false){
			super(type,bubble,cancelable);
		}
		
		public override function clone():Event{
			return new PlayEvent(type);
		}
		
		public override function toString():String{
			return formatToString("PlayEvent","type","bubbles","cancelable");
		}
	}
}