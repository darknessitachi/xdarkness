package com.nano.core {
	/**
	 * 为实现观察者模式而设计，提供注册、触发高层语义事件的通用接口。
	 * 实现此接口的所有子类都必须提供一个名为"events"的HashMap，以进行事件的注册、反注册、触发以及查询。
	 * 该接口提供了一个默认实现_observable.as，需要使用此默认实现的子类可以include此as文件。
	 * 注意：此类是为高层自定义事件实现的观察者模式，对底层事件机制的封装由SysEventMgr提供。
	 * 虽然这两套事件机制暴露出相同的函数接口，但是内部的运作完全不同。
	 * @version	1.3 2010-05-18
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */
	public interface Observable {
		/**
		 * 为对象添加支持的事件名称
		 * @param	obj		一个String或者一个String数组，内容为事件名称(不可重复)
		 */
		function addEvents(obj:*):void;

		/**
		 * 触发指定的事件
		 * @param	...args
		 */
		function fireEvent(...args):Boolean;

		/**
		 * 为指定的事件添加监听器
		 * @param	evtName			需要监听的事件名称
		 * @param	handler			事件处理啊函数
		 * @param	scope			方法的作用域，如果不传递此参数，作用域为函数自身
		 */
		function addListener(evtName:String,handler:Function,scope:*=null):void;

		/**
		 * 删除指定事件的指定监听器
		 * @param	evtName
		 * @param	handler
		 */
		function removeListener(evtName:String,handler:Function):void;
		
		/**
		 * 清除对象上的所有监听器
		 */
		function purgeListeners():void;
		
		/**
		 * 挂起对象上指定事件名的监听器
		 */
		function suspendEvent(evtName:String):void;
		
		/**
		 * 恢复对象上指定事件名的监听器
		 */
		function resumeEvent(evtName:String):void;
		
		/**
		 * 根据作用域删除对象上的事件监听器
		 */
		function removeListenerByScope(scope:*):Boolean;
		
		/**
		 * 根据事件名删除对象上的事件监听器
		 */
		function purgeEvent(evtName:String):void;
	}
}