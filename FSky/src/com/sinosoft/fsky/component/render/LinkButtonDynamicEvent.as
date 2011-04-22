package com.sinosoft.fsky.component.render
{
	import mx.events.DynamicEvent;

	public class LinkButtonDynamicEvent extends DynamicEvent
	{
		public var rowObject:Object;
		
		public static const LINK_BUTTON_CLICK_EVENT:String = "DataGridLinkButtonClickEvent";
		
		public function LinkButtonDynamicEvent(type:String, object:Object)
		{

			super(type, true);

			this.rowObject=object;
		}
	}
}
