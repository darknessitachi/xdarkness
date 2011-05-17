package com.sinosoft.fsky.util
{
	import flash.events.Event;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	
	import mx.controls.Alert;
	
	public class XMLLoader
	{
		private static var _instance:XMLLoader;
		
		/**
		 * 获取对象实例
		 */
		public static function getInstance():XMLLoader {
			if(_instance == null) {
				_instance = new XMLLoader();
			}
			return _instance;
		}
		
		private var _urlHandler:Map=new Map();
		
		public function XMLLoader()
		{
		}
		
		public function send(url:String, onComplete:Function):void
		{
			var loader:XURLLoader=new XURLLoader();
			this._urlHandler.put(loader.xid, onComplete);
			
			var request:URLRequest=new URLRequest(url);
			loader.load(request);
			loader.addEventListener(Event.COMPLETE, onLoadComplete);
			
		}
		
		private function onLoadComplete(event:Event):void
		{
			var loader:XURLLoader=event.target as XURLLoader;
			if (loader != null)
			{
				var xml:XML=new XML(loader.data);
				this._urlHandler.get(loader.xid)(xml);
			}
			else
			{
				Alert.show("请求的XML资源不存在！", "提示");
			}
		}
	}
}