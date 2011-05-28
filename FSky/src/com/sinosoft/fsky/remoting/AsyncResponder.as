package com.sinosoft.fsky.remoting
{
	import mx.rpc.AsyncToken;
	import mx.rpc.AsyncResponder;
	
	public class AsyncResponder extends AbstractResponder
	{
		private var token:AsyncToken;
		
		public function AsyncResponder(token:AsyncToken)
		{
			super();
			this.token=token;
			token.addResponder(new mx.rpc.AsyncResponder(resultHandler,faultHandler));
		}
		
		private function resultHandler(data:Object, token:Object = null):void{
			super.notifyResultListeners(data);
		}
		
		private function faultHandler(info:Object, token:Object = null):void{
			super.notifyFaultListeners(info);
		}
	}
}
