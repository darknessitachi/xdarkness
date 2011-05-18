package com.sinosoft.fsky.component
{
	import spark.components.Button;
	
	[Style(name="xicon", type="String", inherit="no")]
	public class XButton extends Button
	{
		public function XButton()
		{
			super();
			setStyle("skinClass", com.sinosoft.fsky.component.XButtonSkin);
		}
	}
}