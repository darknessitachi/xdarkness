package com.nano.util{
	import flash.events.TimerEvent;
	import flash.utils.Timer;
	
	public class TaskRunner
	{
		public function TaskRunner(){
			
		}
		/**
		 * <blockquote>
		 * config.func:Function,需运行的程序<br>
		 * config.interval:Number,定时间隔时间,以毫秒为单位，默认为3秒<br>
		 * config.autoStart:Boolean,是否立即启动，默认为立即启动<br>
		 * config.count:int,重复执行次数，默认为持续执行<br>
		 * config.scope:Object,定时执行的方法的作用域<br>
		 * config.args:Array,参数<br>
		 * </blockquote>
		 */ 
		public static function intervalRun(config:Object):Timer{
			var cfg:Object = config;
			
			if(cfg.func!=null){
				f = cfg.func;
			}else{
				throw new Error("参数不能为空。");
			}
			
			var f:Function =cfg.func || null;
			var interval:Number = cfg.interval || 3000;
			var autoStart:Boolean = true;
			var count:int = cfg.count || 0;
			var args:Array = cfg.args || null;
			var scope:Object = cfg.scope|| null;
			if(cfg.autoStart != null){
				autoStart = cfg.autoStart;
			}
			if(f == null){
				throw new Error("func参数不能为空。");
			}
			
			var timer:Timer = null;
			if (count > 0){
				timer = new Timer(interval, count);
			} else {
				timer = new Timer(interval);
			}
			
			var func:Function = FuncUtil.createDelegate(f, scope, args);
			timer.addEventListener(TimerEvent.TIMER, func);
			if (autoStart){
				timer.start();
			}
			return timer;
		}
		
		//别名
		public static var run:Function=intervalRun;
	}
}