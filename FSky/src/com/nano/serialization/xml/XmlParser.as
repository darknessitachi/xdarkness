package com.nano.serialization.xml{
	import flash.utils.describeType;

	/**
	 * 静态工具类，将xml对象或者字符串转换成AS3对象。
	 * 注意：参数必须是标准单根结构的XML对象或者字符串，如果传递的参数不是合法的XML格式，将会抛出解析异常。
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	2011-01-18
	 */ 
	public class XmlParser{
		/**
		 * 私有：解析过程中内部使用的堆栈
		 */ 
		private static var stack:Array=[];
		
		public function XmlParser(){
			throw new Error("不能实例化工具类XmlParser");
		}
		
		public static function parse(obj:*):*{
			var result:Object=null;
			try{
				if(!obj){
					return result;
				}
				if(!(obj is XML)){
					obj=new XML(obj.toString());
				}
				//清空堆栈
				stack=[];
				doParse(obj);
				result=stack[0];
			}catch(e:Error){
				throw new Error("Xml2Obj无法解析所提供的XML数据");
			}
			return result;
		}
		
		/**
		 * 递归解析，前序遍历
		 */ 
		private static function doParse(root:XML):void{
			var obj:Object={name:root.name().toString(),children:[]};
			stack.push(obj);
			
			//解析直接属性
			var attrLen:int=root.attributes().length();
			for(var j:int=0;j<attrLen;j++){
				var attr:XML=root.attributes()[j];
				obj[attr.name().toString()||"_temp"]=attr.toString();
			}
			
			//解析子标签
			var len:int=root.children().length();
			if(len){
				for(var i:int=0;i<len;i++){
					var sub:XML=root.children()[i];
					var subLen:int=sub.children().length();
					if(subLen){
						doParse(sub);
						var temp:Object=stack.pop();
						var temp2:Object=stack[stack.length-1];
						temp2.children.push(temp);
					}else{
						var top:Object=stack[stack.length-1];
						delete top.children;
//						top[top.name]=sub.toString();
//						delete top.name;
						top.value=sub.toString();
					}
				}
			}
		}
		
		public static function obj2Xml(obj:Object):XML{
			var result:XML=null;
			try{
				result=flash.utils.describeType(obj);
				//delete result.metadata;
				//delete result.method;
				trace(result);
			}catch(e:Error){
				throw new Error("解析对象出错");
			}
			return result;
		}
	}
}