package com.nano.flexcomp
{
	import flash.events.TimerEvent;
	import flash.utils.Timer;
	
	import spark.components.Label;
	
	public class TwinkleLabel extends Label
	{
		private var twinkleTimer:Timer = new Timer(500, 0);
		private var _twinkle:Boolean = true;
		
		public function TwinkleLabel()
		{
			super();
			twinkleTimer.addEventListener(TimerEvent.TIMER, twinkleHandle);
		}
		
		
		public function set twinkle(value:Boolean):void {
			if (twinkle && !twinkleTimer.running)
				twinkleTimer.start();
			if (!twinkle && twinkleTimer.running)
				twinkleTimer.stop();
		}
		
		public function get twinkle():Boolean {
			return twinkleTimer.running;
		}
		
		public function startTwinkle():void {
			this.twinkleTimer.start();
		}
		
		public function stopTwinkle():void {
			this.twinkleTimer.stop();
		}
		
		private function twinkleHandle(evt:TimerEvent):void {
			this.visible = !this.visible;
		}
		
	}
}