package com.sinosoft.fsky.component
{
	import spark.components.Button;
	
	[Style(name="imgIcon", type="Class", inherit="no")]
	public class MyButton extends Button
	{
		public function MyButton()
		{
			super();
		}
		
		private var _imgIcon:Class;
	}
}