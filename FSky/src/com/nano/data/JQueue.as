package com.nano.data{
	import flash.utils.Dictionary;
	
	/**
	 * 队列
	 * @version	1.0 2010-08-20
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */ 
	public class JQueue{
		private var content:Dictionary=new Dictionary();
		private var maxSize:int=int.MAX_VALUE;
		private var front:int=0;		//记录队列头部索引位置
		private var rear:int=0;			//记录队列尾部索引位置
		
		public function JQueue(maxSize:int=int.MAX_VALUE){
			this.maxSize=maxSize;
		}
		
		/**
		 * 从头部取出一个元素
		 */ 
		public function poll():Object{
			if(this.isEmpty()){
				return null;
			}
			var result:Object=this.content[front];
			this.content[front]=null;
			this.front++;
			return result;
		}
		
		/**
		 * 在队尾插入一个元素
		 */ 
		public function offer(obj:Object):void{
			if(this.isFull()){
				throw new Error("队列已满");
			}
			this.content[rear]=obj;
			this.rear++;
		}
		
		public function size():int{
			return (this.rear-this.front);
		}
		
		public function isFull():Boolean{
			return this.size()==this.maxSize;
		}
		
		public function isEmpty():Boolean{
			return this.size()==0;
		}
	}
}