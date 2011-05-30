package com.nano.data {
	import adobe.utils.CustomActions;
	
	import com.nano.serialization.json.JSON;
	import com.nano.util.CollectionUtil;
	
	import flash.utils.Dictionary;

	/**
	 * HashMap，模拟HashMap，该类与Java版的HashMap不完全相同，该HashMap的key不允许为null或者空字符串
	 * @version	1.3 2010-05-18
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */
	public class JHashMap {
		/**
		 * 对象个数
		 */
		private var length:int;
		/**
		 * 内部包装的对象
		 */
		private var content:Dictionary;

		/**
		 * 构造方法
		 */
		public function JHashMap(){
			length = 0;
			this.content = new Dictionary();
		}

		/**
		 * 添加一个键值对
		 * @param	key
		 * @param	value
		 * @return
		 */
		public function put(key:*, value:*):* {
			if (!key){
				throw new Error("键不能为空");
				return undefined;
			}
			if (!value){
				return this.remove(key);
			}
			if (!this.containsKey(key)){
				this.length++;
			}
			var oldValue:* = this.get(key);
			this.content[key] = value;
			return oldValue;
		}

		/**
		 * 根据key获取value
		 * @param	key
		 * @return
		 */
		public function get(key:*):* {
			var value:* = this.content[key];
			if (value !== undefined){
				return value;
			}
			return null;
		}

		/**
		 * 获取Hash表长度
		 * @return
		 */
		public function size():int {
			return this.length;
		}

		/**
		 * 删除一个键值对
		 * @param	key
		 * @return
		 */
		public function remove(key:*):* {
			var exists:Boolean = this.containsKey(key);
			if (!exists){
				return null;
			}
			var temp:* = this.get(key);
			delete this.content[key];
			this.length--;
			return temp;
		}

		/**
		 * 测试是否包含指定的键
		 * @param	key
		 * @return
		 */
		public function containsKey(key:*):Boolean {
			if (content[key] != undefined){
				return true;
			}
			return false;
		}

		/**
		 * 测试是否包含指定的值
		 * @param	value
		 * @return
		 */
		public function containsValue(value:*):Boolean {
			for each (var i:*in this.content){
				if (i == value){
					return true;
				}
			}
			return false;
		}

		/**
		 * 清除所有键值对
		 */
		public function clear():void {
			this.length = 0;
			this.content = new Dictionary();
		}

		/**
		 * 在每个key上调用指定的方法
		 * @param	fn
		 */
		public function eachKey(fn:Function):void {
			for (var i:*in this.content){
				fn(i);
			}
		}

		/**
		 * 在每个value上调用指定的方法
		 * @param	fn
		 */
		public function eachValue(fn:Function,single:Boolean=false):void{
			for each (var i:*in this.content){
				fn(i);
			}
		}

		/**
		 * 获得所有值的数组
		 * @return
		 */
		public function values():Array {
			var arr:Array = new Array();
			for each (var item:*in this.content){
				arr.push(item);
			}
			return arr;
		}

		/**
		 * 获得所有键的数组
		 * @return
		 */
		public function keys():Array {
			var arr:Array = new Array();
			for (var item:*in this.content){
				arr.push(item);
			}
			return arr;
		}
		
		public function toJsonString():String{
			var arr:Array=[];
			for(var p:* in content){
				var _temp:*={};
				_temp.key=p;
				_temp.value=content[p];
				arr.push(_temp);
			}
			return JSON.encode(arr);
		}
	}
}