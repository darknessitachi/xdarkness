package com.sinosoft.fsky.component.tree
{
	import com.sinosoft.fsky.util.IconUtility;
	
	import mx.controls.Tree;
	import mx.core.ClassFactory;
	import mx.events.ListEvent;

	public class XTree extends Tree
	{

		public function XTree()
		{
			super();
			this.labelField = "@label";
			
			this.percentHeight = 100;
			this.percentWidth = 100;
			
			this.showRoot = false;
		}

		override protected function createChildren():void
		{
			var myFactory:ClassFactory=new ClassFactory(XTreeItemRenderer);
			this.itemRenderer=myFactory;
			super.createChildren();
		}
		
//		override public function itemToIcon(item:Object):Class
//		{
//			try
//			{
//				super.itemIcons(item);
//			} 
//			catch(error:Error) 
//			{
//				icon = String(item[iconField]);
//			}
//		}
		

	}
}