package com.sinosoft.fsky.component.tree
{
	import flash.events.MouseEvent;
	import flash.geom.Rectangle;
	import flash.xml.*;
	
	import mx.collections.*;
	import mx.controls.CheckBox;
	import mx.controls.Image;
	import mx.controls.Tree;
	import mx.controls.listClasses.*;
	import mx.controls.treeClasses.*;
	import mx.events.FlexEvent;
	import mx.events.ListEvent;
	import mx.styles.StyleManager;

	/**
	 * 三状态复选框树控件
	 */
	public class XTreeItemRenderer extends TreeItemRenderer
	{
		public static const DEAD_COLOR:int = 7701126;
		public static const LABEL_COLOR:Array = new Array(0,38468,12736512,585512,12714109,20961);
		
		protected var myImage:Image;
		private var imageWidth:Number=16;
		private var imageHeight:Number=16;
		private static var defaultLeafImg:String="assets/images/leaf.gif";
		private static var defaultFolderImg:String="assets/images/folder.gif";
		private static var defaultFolderOpenImg:String="assets/images/folder-open.gif";
		
		public function XTreeItemRenderer()
		{
			super();
		}

		
		override protected function createChildren():void
		{
			super.createChildren();
			
			myImage=new Image();
			myImage.source=defaultLeafImg;
			myImage.width=imageWidth;
			myImage.height=imageHeight;
			myImage.setStyle("verticalAlign", "middle");
			addChild(myImage);
		}
		
		//通过覆盖data方法来动态设置tree的节点图标
		override public function set data(value:Object):void
		{
			super.data=value;
			
			var imageSource:String=value.@icon.toString();
			
			if (imageSource != "")
			{
				myImage.source=imageSource;
			}
			else
			{
				myImage.source=defaultLeafImg;
			}
			
		}

		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void
		{
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			
			addDynamicIcon();
		}
		
		/**
		 * 动态添加图标:隐藏原有图标，并设置它的坐标
		 */
		private function addDynamicIcon():void {
			if (super.data != null)
			{
				var status:String=data.@labelColor.toString();
				if (status == "0")
				{
					//StyleManager.getStyleManager(null).getStyleDeclaration('mx.core.IUITextField').setStyle('color',"#FF0000");
					this.label.setColor(StyleManager.getColorName("#FF0000"));
				}
				else
				{
					this.label.setColor(StyleManager.getColorName("#000000"));
				}
				
				if (super.icon != null)
				{
					myImage.x=super.icon.x;
					myImage.y=2;
					super.icon.visible=false;
				}
				else
				{
					myImage.x=super.label.x;
					myImage.y=2;
					super.label.x=myImage.x + myImage.width + 17;
				}
				
				/**
				 * 如果有子节点，图标为folder[-open]，否则为leaf
				 * TODO:暂不实现，因为点击节点时有闪烁感，原因未知
				 */
				/*var myListData:TreeListData=TreeListData(this.listData);
				trace("" +  myListData.label + ">====>" + myListData.hasChildren + "" + (data.@icon) + 
					",hasChildren:" + (myListData.hasChildren && (data.@icon == ""))
				+ ",isOpen:" + myListData.open);
				if(myListData.hasChildren) {
					if(data.@icon.toString() == "") {
						if(myListData.open) {
							if(myImage.source != XTreeItemRenderer.defaultFolderOpenImg)
								myImage.source = XTreeItemRenderer.defaultFolderOpenImg;
						} else {
							if(myImage.source != XTreeItemRenderer.defaultFolderImg)
								myImage.source = XTreeItemRenderer.defaultFolderImg;
						}
					}
				}*/
			}
			
			
			var mylistData:TreeListData=listData as TreeListData;
			
			label.x+=1;
			
			if(mylistData.item.@dead==1)
			{
				label.setColor(DEAD_COLOR);
			}
			else if(mylistData.item.@level != null)
			{
				label.setColor(LABEL_COLOR[mylistData.item.@level]);
			}
			else
			{
				label.setColor(getStyle("color"));
			}
		}

	} //end class
} //end package