package com.nano.util {
	import flash.events.TimerEvent;
	import flash.utils.Timer;
	import flash.utils.setTimeout;

	/**
	 * 函数工具类，
	 * 该类提供操纵Function的一组工具方法，
	 * 该类不能实例化
	 * @version	1.0
	 * @author 章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  2010-04-21
	 */
	public class FuncUtil {
		//添加一个sequence工具，按次序执行一组函数
		
		public function FuncUtil() {
			throw new Error("尝试实例化函数工具类。");
		}

		/**
		 * 定时执行器
		 * @param	func		需要定时执行的方法
		 * @param	interval	定时间隔
		 * @param	autoStart	定时器是否立即启动
		 * @param	count		重复调用的次数
		 * @param	scope		定时执行的方法的作用域
		 * @param	args		传递给定时执行方法的参数
		 * @return	定时器
		 */
		public static function timerExcutor(func:Function, interval:Number, autoStart:Boolean = true, count:int = 0, scope:Object = null, args:Array = null):Timer {
			var timer:Timer = null;
			if (count > 0){
				timer = new Timer(interval, count);
			} else {
				timer = new Timer(interval);
			}
			if (args){
				args.push(timer);
			} else {
				args = [timer];
			}
			var func:Function = FuncUtil.createDelegate(func, scope, args);
			timer.addEventListener(TimerEvent.TIMER, func);
			if (autoStart){
				timer.start();
			}
			return timer;
		}
		
		/**
		 * 定时任务
		 * timerExcutor的别名
		 */
		public static var timerTask:Function=timerExcutor;
		
		/**
		 * 为指定的函数创建回调函数
		 * 在需要进行函数回调时，如果需要向回调函数传递参数
		 * 使用该方法
		 * 回调函数的作用域为本函数，如果需要为回调函数指定
		 * 特殊的作用域，请使用craeteDelegate()方法
		 * @param	func
		 * @param	...args
		 * @return
		 */
		public static function createCallBack(func:Function, ... args):Function {
			var method:Function = func;
			return function(){
				return func.apply(method, args);
			};
		}

		/**
		 * 为指定的函数创建代理
		 * 同时可以改变函数的作用域
		 * 以及参数数量
		 * @param	func
		 * @param	scope
		 * @param	args
		 * @param	appendArgs
		 * @return
		 */
		public static function createDelegate(func:Function, scope:Object, args:Array, appendArgs:Object = false):Function {
			var method:Function = func;
			return function(){
				var callArgs:Array = args || arguments;
				if (appendArgs is Boolean && appendArgs == true){
					callArgs = new Array(arguments);
					(callArgs as Array).concat(args);
				} else if (appendArgs is Number){
					callArgs = new Array(arguments);
					var applyArgs:Array = [appendArgs, 0].concat(args);
					(callArgs as Array).splice(applyArgs);
				}
				return method.apply(scope || method, callArgs);
			}
		}

		/**
		 * 延迟指定的毫秒之后执行制定的函数
		 * 同时可以改变函数的作用域
		 * 以及参数个数
		 * @param	func
		 * @param	millis
		 * @param	args
		 * @param	scope
		 * @param	appendArgs
		 * @return
		 */
		public static function delay(func:Function,millis:Number,args:Array = null,scope:Object = null,appendArgs:Object = false):Number {
			scope = scope || func;
			var fn:Function = FuncUtil.createDelegate(func,scope,args,appendArgs);
			if (millis){
				return setTimeout(fn, millis);
			}
			fn();
			return 0;
		}

		/**
		 * 为指定的函数创建拦截器
		 * 可以指定拦截器的作用域以及参数
		 * @param	orig
		 * @param	fn
		 * @param	scope
		 * @return
		 */
		public static function createInterceptor(orig:Function, fn:Function, scope:Object = null):Function {
			if (!fn is Function){
				return orig;
			}
			return function(){
				if (!fn.apply(scope || fn, arguments)){
					return;
				}
				orig.apply(orig, arguments);
			};
		}
	}
}