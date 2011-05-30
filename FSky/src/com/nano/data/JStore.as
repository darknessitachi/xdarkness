package com.nano.data {
	import com.nano.core.NanoSystem;
	import com.nano.core.Observable;
	import com.linkage.cmp.util.CollectionUtil;
	
	import mx.collections.ArrayCollection;

	/**
	 * 客户端数据缓存
	 * 使用该类可以实现前台数据岛
	 * 在向JStore中插入、删除或者更新数据时，JStore会触发相应的事件
	 * 基于这些事件可以实现数据的同步等功能
	 * <pre>
	 * 		用法示例：
	 * 		var store:JStore=new JStore();
	 *		var func1:Function=function(...args):void{
	 *			trace("添加了一条记录...");
	 *		}
	 *		var func2:Function=function(...args):void{
	 *			trace("更新了一条记录...");
	 *		}
 	 *		var func3:Function=function(...args):void{
	 *			trace("删除了一条记录...");
	 *		}
	 *		var func4:Function=function(...args):void{
	 *			trace("缓存清空...");
	 *		}
	 *		var host:JHost=new JHost(100,100);
	 *		store.addListener("addcomplete",func1);
	 *		store.addListener("update",func2);
	 *		store.addListener("remove",func3);
	 *		store.addListener("clear",func4);
	 *		store.add(host);
	 *		store.update(host);
	 *		store.remove(host);
	 *	
	 *		for(var i:Number=0;i<100;i++){
	 *			host=new JHost();
	 *			trace("host id"+host.id);
	 *			store.add(host);
	 *		}
	 *		trace("记录条数>"+store.getCount());
	 *		store.clear();
	 *		trace("记录条数>"+store.getCount());
	 *	
	 *		var obj:Object={id:NanoSystem.id(),name:'zhangxf',age:27};
	 *		store.add(obj);
	 *		trace(store.getRange(0,1));
	 * </pre>
	 * @version	1.3 2010-05-18
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */
	public class JStore implements Observable {
		include "../core/__observable.as";
		/**
		 * 动态加载数据URL
		 */
		public var url:String;
		/**
		 * 数据集合
		 */
		private var dataArr:ArrayCollection = new ArrayCollection();
		/**
		 * 数据集Hash表
		 */
		private var dataMap:JHashMap = new JHashMap();
		/**
		 * 最后一次提交以来修改过的记录
		 */
		private var modified:Array=[];
		
		/**
		 * 构造方法
		 */
		public function JStore(){
			this.addEvents(["addcomplete", "b4remove","remove", "update","clear","b4load","load"]);
		}
		
		/**
		 * 向缓存中添加一条记录
		 * 添加到Store中的对象必须含有一个全局唯一的id
		 * @param	obj
		 */
		public function add(obj:Object):void {
			if (!this.dataArr.contains(obj)){
				this.dataArr.addItem(obj);
				this.dataMap.put(obj.id, obj);
				this.fireEvent("addcomplete",obj);
			}
		}
		
		/**
		 * 将一个Array、ArrayCollection或者JHashMap添加到Store中
		 * @param	arr
		 */
		public function addAll(arr:*):void{
			this.suspendEvent("addcomplete");
			CollectionUtil.each(arr,function(...args):void{
				var item:*=args[0];
				if(!item.id){
					if(item.obj_Id){
						item.id=item.obj_Id;
					}else{
						item.id=NanoSystem.id();
					}
				}
				add(item);
			});
			this.resumeEvent("addcomplete");
			this.fireEvent("addcomplete");
		}
		
		/**
		 * 从缓存中删除一条记录
		 * @param	obj			对象的id或者引用
		 */
		public function remove(obj:Object):void {
			this.fireEvent("b4remove",obj);
			if (obj is String){
				var objTemp:Object = this.dataMap.get(obj);
				this.dataArr.removeItemAt(this.dataArr.getItemIndex(objTemp));
				this.dataMap.remove(obj);
			} else if (obj is Object){
				this.dataMap.remove(obj.id);
				this.dataArr.removeItemAt(this.dataArr.getItemIndex(obj));
			}
			this.fireEvent("remove",obj);
		}
		
		/**
		 * 更新对象
		 * @param	obj
		 */
		public function update(obj:Object):void {
			var objTemp:Object = this.dataMap.get(obj.id);
			if (objTemp){
				for(var p:* in obj){
					objTemp[p]=obj[p];
				}
				this.fireEvent("update",objTemp);
			}
		}
		
		/**
		 * 根据id查找对象
		 * @param	id
		 * @return
		 */
		public function findById(id:String):Object {
			return this.dataMap.get(id);
		}
		
		/**
		 * 根据指定的属性名和属性值查找对象
		 * 返回一个结果数组
		 * @param	n
		 * @param	v
		 * @return
		 */
		public function findByProperty(n:String,v:String):Array{
			var result:Array=[];
			this.dataMap.eachValue(function(item:*):void{
				if(item[n]==v){
					result.push(item);
				}	
			});
			return result;
		}
		
		/**
		 * 根据指定的属性名和属性值过滤出符合条件的对象集合
		 * @param	n
		 * @param	v
		 * @return
		 */
		public function filter(n:String,v:String):Array{
			var arr:Array=[];
			var result:*=this.findByProperty(n,v);
			if(result)arr.push(result);
			return result;
		}
		
		/**
		 * 获取缓存中的记录条数
		 * @return
		 */
		public function getCount():Number {
			return this.dataArr.length;
		}
		
		/**
		 * 根据起始和结束位置获取缓存中的记录
		 * @param	startIndex
		 * @param	endIndex
		 * @return
		 */
		public function getRange(startIndex:Number,endIndex:Number):Array {
			return this.dataArr.source.slice(startIndex,endIndex);
		}
		
		/**
		 * 获取所有记录
		 * @return
		 */
		public function getAll():Array {
			return this.getRange(0,this.dataArr.length);
		}
		
		/**
		 * 清空缓存
		 */
		public function clear():void {
			this.dataArr.removeAll();
			this.dataMap.clear();
			this.fireEvent("clear");
		}
		
		/**
		 * 加载数据
		 */
		public function load():void {
			//尚未实现
		}
		
		/**
		 * 在store中的每个对象上调用指定方法
		 */
		public function eachItem(fn:Function):void{
			CollectionUtil.each(this.dataArr,fn);
		}
		
		/**
		 * 根据字段排序
		 */ 
		public function sortOn(filedName:Object,options:Object=null):void{
			this.dataArr.source.sortOn(filedName,options);
			this.dataArr.refresh();
		}
	}
}