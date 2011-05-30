package com.nano.layout{
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	
	import mx.collections.ArrayCollection;
	
	/**
	 * 布局管理器顶层接口
	 * 注意：JLayoutManager被设计用来为JFlComponent提供布局操作。
	 * JLayoutManager是完全自定义的布局管理器，与Flex系统的布局操作没有关系。
	 * 所有JLayoutManager的子类必须实现接口规定的方法。
	 * @version	1.0
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com(QQ253445528)
	 * @since  	2010-03-06
	 */
	public interface JLayoutManager {
		/**
		 * 强制进行布局操作
		 * @param	owner
		 */
		function layoutContainer():void;
		/**
		 * 像布局中添加一个组件
		 * @param	comp
		 * @param	constrains
		 */
		function addComp(comp:DisplayObject, constrains:*=null):void;
		/**
		 * 从布局中删除一个组件
		 * @param	comp
		 * @param	constrains
		 */
		function removeComp(comp:DisplayObject,constrains:*=null):void;
		
		function setOwner(owner:DisplayObjectContainer):void;
		
		function getMembers():ArrayCollection;
	}
}