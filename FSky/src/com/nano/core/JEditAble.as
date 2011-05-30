
package com.nano.core{
	import com.nano.core.event.SelectionEvent;
	/**
	 * 所有可编辑的对象都必须实现此接口
	 * @version	1.1
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	2009-09-22
	 */
	public interface JEditAble {
		function updatePosition(tx:Number,ty:Number):void;		//按照偏移量更新对象位置
		function setPosition(newX:Number,newY:Number):void;		//将对象设置到新的位置
		function onSelect(e:SelectionEvent):void;				//选中事件处理
		function onDeSelect(e:SelectionEvent):void;				//删除编辑矩形框
		function canEdit():Boolean;								//获取对象是否处于可编辑状态
	}
}