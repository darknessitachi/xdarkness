package com.sinosoft.fsky.remoting
{
	import flash.utils.setTimeout;
	
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	public class SyncResponder extends AbstractResponder
	{
		private var _delay:Number;
		public function SyncResponder(delay:Number=1)
		{
			super();
			if(delay<1) throw new Error("invalid number for delay");
			_delay=delay;
		}
		
		public function invokeResult(data:Object):void{			
			setTimeout(super.notifyResultListeners,1,data);
		}
		
		public function invokeFault(info:Object):void{			
			setTimeout(super.notifyFaultListeners,1,info);
		}
	}
}