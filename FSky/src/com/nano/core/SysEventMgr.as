package com.nano.core{
	import com.nano.data.JHashMap;
	
	import flash.display.InteractiveObject;
	import flash.events.Event;
	import flash.events.EventDispatcher;
	
	import mx.collections.ArrayCollection;

	/**
	 * 消息管理器，
	 * 该类被设计作为注册事件的一种快捷方式，负责底层原生事件的绑定、触发、删除等操作。
	 * 该类不能处理高层语义的事件，高层语义事件一般通过观察者模式Observable实现。
	 * 该类应该为全局单例，一般情况下不需要实例化此类，而只是调用其静态工具方法。
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	2010-05-18
	 */
	public class SysEventMgr{
		public function SysEventMgr(){
			throw new Error("不能实例化事件管理工具类SysEventMgr");
		}
		
		/**
		 * 将事件发送到指定的一个对象或者一个集合中的所有对象
		 * 支持的集合类型包括ArrayCollection、HashMap
		 * @param	e
		 * @param	destination
		 */
		public static function sendMsg(e:Event,destination:*):void {
			if (destination is Array|| destination is ArrayCollection) {
				for each(var item:* in destination) {
					(item as InteractiveObject).dispatchEvent(e);
				}
				return;
			}else if(destination is JHashMap){
				(destination as JHashMap).eachValue(function(temp:*):void{
					(temp as InteractiveObject).dispatchEvent(e);
				});
				return;
			}
			(destination as InteractiveObject).dispatchEvent(e);
		}
		
		/**
		 * 给指定对象添加一个事件监听器
		 */ 
		public static function on(obj:EventDispatcher,evtName:String,func:Function):void{
			obj.addEventListener(evtName,func);
		}
		
		/**
		 * 给指定对象添加事件监听器
		 * 一次添加多个监听器
		 */ 
		public static function addListeners(obj:EventDispatcher,listeners:Object):void{
			if(!obj||!listeners){
				return;
			}
			for(var p:* in listeners){
				var listener:*=listeners[p];
				if(listener is Function){
					on(obj,p+"",listener);
				}else if(listener is Object){
					var fn:Function=listener.fn as Function;
					on(obj,p+"",fn);
				}
			}
		}
		
		/**
		 * 从指定对象上删除一个事件监听器
		 */ 
		public static function un(obj:EventDispatcher,evtName:String,func:Function):void{
			obj.removeEventListener(evtName,func);
		}
		
		/**
		 * 事件转发或者接力
		 * 目标对象des将会把源对象src上指定类型的事件转发出去。
		 * 该机制对于由多个子组件复合成的组件非常重要。
		 * @param	src
		 * @param	des
		 * @param	evtName
		 */
		public static function relay(src:EventDispatcher,des:EventDispatcher,evtName:String):void{
			src.addEventListener(evtName,function(e:Event):void{
				des.dispatchEvent(e);
			});
		}
		
		/**
		 * 单次执行的监听函数
		 * 监听函数执行一次之后就把自己删除掉。
		 * @param	obj
		 * @param	evtName
		 * @param	func
		 */
		public static function sigle(obj:EventDispatcher,evtName:String,func:Function):void{
			var fn:Function=function(e:Event):void{
				func.call(func,e);
				SysEventMgr.un(obj,evtName,fn);
			}
			SysEventMgr.on(obj,evtName,fn);
		}
	}
}