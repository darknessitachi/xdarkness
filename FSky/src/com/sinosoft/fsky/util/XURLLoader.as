package com.sinosoft.fsky.util
{
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	
	public class XURLLoader extends URLLoader
	{
		private var _xid:String;
		
		public function set xid(v:String):void {
			this._xid = v;
		}
		
		public function get xid():String {
			return this._xid;
		}
		
		public function XURLLoader(request:URLRequest=null)
		{
			super(request);
			
			this._xid = XGenerator.generateId();
		}
	}
}