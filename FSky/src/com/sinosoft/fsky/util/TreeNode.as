package com.sinosoft.fsky.util
{
	import mx.collections.ArrayCollection;
	
	[Bindable]
	
	public class TreeNode
	{
		public function TreeNode()
		{
		}
		
		private var _id:String;
		private var _nodeName:String;
		private var _children:ArrayCollection;
		private var _parentNode:TreeNode;
		private var _description:String;
		
		public function get code():String
		{
			return _id;
		}
		
		public function set code(value:String):void
		{
			this._id = value;
		}
		
		public function get text():String
		{
			return this._nodeName;
		}
		
		public function set text(value:String):void
		{
			this._nodeName = value;
		}
				
		public function get children():ArrayCollection
		{
			return this._children;
		}
		
		public function set children(value:ArrayCollection):void
		{
			this._children = value;
		}
		
		public function get parentNode():TreeNode
		{
			return this._parentNode ;	
		}
		
		public function set parentNode(value:TreeNode):void
		{
			this._parentNode = value;
		}
		
		public function get description():String
		{
			return this._description;
		}
		
		public function set description(value:String):void
		{
			this._description = value;
		}
		public function addChild(value:TreeNode):void
		{
			if(this._children == null)
			{
				this._children = new ArrayCollection();
			}
			value.parentNode = this;
			this._children.addItem(value);
		}
	}
}