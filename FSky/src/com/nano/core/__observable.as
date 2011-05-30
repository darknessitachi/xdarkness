/**
 * 实现Observable接口的类都必须[include]此文件，以提供高层语义事件支持
 * @version	1.3 2010-05-18
 * @author	章小飞 zhangxf5@asiainfo-linkage.com
 * @since  	V1.0
 */
import com.nano.data.JHashMap;

import mx.collections.ArrayCollection;

/**
 * 组件所支持的事件表
 */
protected var events:JHashMap = new JHashMap();
/**
 * 当前被挂起的事件数组
 */
protected var suspendEvts:ArrayCollection =new ArrayCollection();

/**
 * 添加一个支持的事件
 * @param	obj
 */
public function addEvents(obj:*):void{
	if(obj is String){
		this.events.put(String(obj).toLowerCase(),new ArrayCollection());
	}else if(obj is Array){
		for each(var p:* in obj){
			this.events.put(String(p),new ArrayCollection());
		}
	}
}

/**
 * 从对象上删除一个事件类型
 * 在删除事件之前将会清除所有此事件的监听器
 * @param	evtName
 */
public function removeEvent(evtName:String):void{
	if(!this.events.get(evtName.toLowerCase())){
		return;
	}
	var arr:ArrayCollection=ArrayCollection(this.events.get(evtName.toLowerCase()));
	arr.removeAll();
	this.events.remove(evtName);
}

/**
 * 触发指定名称的事件
 * @param	evtName
 * @see		Observable
 */
public function fireEvent(...args):Boolean{
	if(this.suspendEvts.contains(args[0])){
		trace("事件>"+args[0]+"已经被挂起");
		return false;
	}
	if(this.events.containsKey(args[0])){
		var arr:ArrayCollection=this.events.get(args[0]) as ArrayCollection;
		for each(var item:* in arr){
			if(args.length>1){
				item.handler.apply(item.scope,args.slice(1));
			}else{
				item.handler.apply(item.scope);
			}
		}
	}
	return true;
}

/**
 * 添加一个事件监听器
 * @param	evtName
 * @param	h
 * @param	s
 * @see		Observable
 */
public function addListener(evtName:String,h:Function,s:*=null):void{
	if(!this.events.containsKey(evtName)){
		this.addEvents(evtName);
	}
	var arr:ArrayCollection=ArrayCollection(this.events.get(evtName.toLowerCase()));
	var obj:*={scope:s==null?h:s,handler:h};
	arr.addItem(obj);
}

/**
 * 删除一个事件监听器
 * @param	evtName
 * @param	handler
 * @see		Observable
 */
public function removeListener(evtName:String,handler:Function):void{
	if(this.events.containsKey(evtName)){
		var arr:ArrayCollection=ArrayCollection(this.events.get(evtName));
		for each(var item:* in arr){
			if(item.handler==handler){
				arr.removeItemAt(arr.getItemIndex(item));
			}
		}
	}
}

/**
 * 清除指定事件名上的所有监听器
 * @see		Observable
 */
public function purgeEvent(evtName:String):void{
	if(this.events.containsKey(evtName)){
		var arr:ArrayCollection=ArrayCollection(this.events.get(evtName));
		arr.removeAll();
	}
}

/**
 * 删除对象上的所有事件监听器
 * @see		Observable
 */
public function purgeListeners():void{
	this.events.eachValue(function(item:*):void{
		(item as ArrayCollection).removeAll();
	});
}
		
/**
 * 删除指定作用域上的监听器
 * @param	scope
 * @return
 */
public function removeListenerByScope(scope:*):Boolean {
	var isFind:Boolean = false;
	this.events.eachValue(function(item:*):void{
		var arr:ArrayCollection = item as ArrayCollection;
		for each(var obj:* in arr) {
			if (obj.scope == scope) {
				isFind = true;
				arr.removeItemAt(arr.getItemIndex(obj));
			}
		}
	});
	return isFind;
}

/**
 * 挂起指定名称的事件
 * @param	evtName
 */
public function suspendEvent(evtName:String):void{
	if(!this.suspendEvts.contains(evtName)){
		this.suspendEvts.addItem(evtName);
	}
}

/**
 * 恢复指定名称的事件
 * @param	evtName
 */
public function resumeEvent(evtName:String):void{
	if(this.suspendEvts.contains(evtName)){
		var index:int=this.suspendEvts.getItemIndex(evtName);
		this.suspendEvts.removeItemAt(index);
	}
}

