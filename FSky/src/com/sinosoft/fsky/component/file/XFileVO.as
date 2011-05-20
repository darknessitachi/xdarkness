package com.sinosoft.fsky.component.file
{
	[Bindable]
	[RemoteClass(alias="com.sinosoft.carbon.file.XFile")]
	public class XFileVO
	{
		public var id:Number;
		public var fileName:String;
		public var fileSize:Number;
		public var version:String;
	}
}