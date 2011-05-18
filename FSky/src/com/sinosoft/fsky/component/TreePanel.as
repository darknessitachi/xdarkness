package com.sinosoft.fsky.component
{
	import mx.controls.Tree;
	
	import spark.components.Group;
	import spark.components.SkinnableContainer;
	
	
	public class TreePanel extends SkinnableContainer
	{
		[Embed(source="assets/images/treeItem_bg.png", scaleGridTop="2", scaleGridLeft="12", scaleGridRight="183", scaleGridBottom="25")]
		[Bindable]
		public var treeItemBG:Class;
		
		[SkinPart(required="false")]
		public var backgroundGroup:Group;
		
		[SkinPart(required="false")]
		public var treeItemGroup:Group;
		
		public function TreePanel()
		{
			super();
		}
	}
}