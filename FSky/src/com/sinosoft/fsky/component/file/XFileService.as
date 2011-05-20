package com.sinosoft.fsky.component.file
{
	import com.sinosoft.fsky.remoting.BaseService;
	import com.sinosoft.fsky.remoting.IInvokeResponder;
	
	public class XFileService extends BaseService
	{
		public function XFileService()
		{
			super("xfileFlexService");
		}
		
		private static var _instance:XFileService;
		public static function getInstance():XFileService {
			if(_instance == null) {
				_instance = new XFileService();
			}
			
			return _instance;
		}
		
		public function getXFile(xid:String):IInvokeResponder {
			return super.invoke("getXFile", xid);
		}
	}
}