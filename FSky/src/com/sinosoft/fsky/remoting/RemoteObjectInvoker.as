package com.sinosoft.fsky.remoting
{
	
	import flash.events.EventDispatcher;
	
	import mx.rpc.AbstractOperation;
	import mx.rpc.AsyncToken;
	import mx.rpc.remoting.mxml.RemoteObject;

	public class RemoteObjectInvoker extends EventDispatcher implements IInvoker
	{
		private var ro:RemoteObject;
		
		public function RemoteObjectInvoker(dest:String)
		{
			ro=RemoteObjectManager.getRemoteObject(dest);
		}

		public function invoke(method:String,args:Array):IInvokeResponder
		{
			var oper:AbstractOperation=ro.getOperation(method);
			var token:AsyncToken=oper.send.apply(oper,args);
			
			return new AsyncResponder(token);
		}
	}
}