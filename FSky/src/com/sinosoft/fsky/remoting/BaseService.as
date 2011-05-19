package com.sinosoft.fsky.remoting
{
	public class BaseService
	{
		private var _dest:String;
		private var _invoker:IInvoker;
		public function BaseService(dest:String)
		{
			this._dest=dest;
		}
		
		protected function get destination():String{
			return _dest;
		}
		protected function get invoker():IInvoker{
			if(!_invoker){
				_invoker=new RemoteObjectInvoker(destination);
			}
			return _invoker;
		}
		
		protected function set invoker(value:IInvoker):void{
			this._invoker=value;
		}
		
		protected function invoke(method:String,...args):IInvokeResponder{
			return invoker.invoke(method,args);
		}
	}
}