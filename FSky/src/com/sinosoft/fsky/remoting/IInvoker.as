package com.sinosoft.fsky.remoting
{
	import flash.events.IEventDispatcher;
	
	public interface IInvoker extends IEventDispatcher
	{
		//使用args指定的参数调用名为method的方法
		function invoke(method:String,args:Array):IInvokeResponder;
	}
}