package com.sinosoft.fsky.remoting
{

	public class AbstractResponder implements IInvokeResponder
	{
		private var resultListeners:Array;
		private var faultListeners:Array;

		public function AbstractResponder()
		{
		}

		public function addResultListener(handler:Function, ... params):IInvokeResponder
		{
			if (!resultListeners)
			{
				resultListeners=new Array();
			}
			resultListeners.push(new Listener(handler, params));
			return this;
		}

		public function addFaultListener(handler:Function, ... params):IInvokeResponder
		{
			if (!faultListeners)
			{
				faultListeners=new Array();
			}
			faultListeners.push(new Listener(handler, params));
			return this;
		}

		protected function notifyResultListeners(result:Object, first:int=0):void
		{
			if (resultListeners)
			{
				for (var i:int=first; i < resultListeners.length; i++)
				{
					var listener:Listener=resultListeners[i];
					var func:Function=listener.handler;
					var params:Array=listener.arguments;
					params.unshift(result);
					func.apply(null, params);
				}
			}
		}

		protected function notifyFaultListeners(fault:Object, first:int=0):void
		{
			if (faultListeners)
			{
				for (var i:int=first; i < faultListeners.length; i++)
				{
					var listener:Listener=faultListeners[i];
					var func:Function=listener.handler;
					var params:Array=listener.arguments;
					params.unshift(fault);
					func.apply(null, params);
				}
			}
		}

		protected function clear():void
		{
			resultListeners=[];
			faultListeners=[];
		}
	}
}

class Listener
{
	private var _handler:Function;
	private var _args:Array;

	public function Listener(handler:Function, args:Array)
	{
		this._handler=handler;
		this._args=args ? args : [];
	}

	public function get handler():Function
	{
		return _handler;
	}

	public function get arguments():Array
	{
		return _args.concat();
	}
}


