package com.sinosoft.fsky.component
{
	import mx.controls.Tree;
	
	import spark.components.Group;
	import spark.components.SkinnableContainer;

	
	public class TreePanel extends SkinnableContainer
	{
		[Embed(source="assets/images/tableBg_right.gif")]//, scaleGridTop="2", scaleGridLeft="2", scaleGridRight="169", scaleGridBottom="25")]
		[Bindable]
		public var background:Class;
		
		[Embed(source="assets/images/treeItem_bg.gif")]//, scaleGridTop="2", scaleGridLeft="2", scaleGridRight="169", scaleGridBottom="25")]
		[Bindable]
		public var borderBG:Class;
		
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