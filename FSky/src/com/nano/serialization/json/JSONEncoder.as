package com.nano.serialization.json {
	import flash.utils.describeType;
	/**
	 * JSON编码器，将一个AS3对象解析成JSON字符串，该类由官方的JSONEncoder改进而来
	 * @version	1.0
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	2010-03-24
	 */
	public class JSONEncoder {
		private var jsonString:String;
		/**
		 * 构造方法
		 * @param	o
		 */
		public function JSONEncoder(o:Object){
			this.jsonString = convertToString(o);
		}
		
		/**
		 * 获取格式化好的字符串
		 * @return
		 */
		public function getString():String {
			return this.jsonString;
		}
		
		/**
		 * 将对象格式化成JSON
		 * @param	o
		 * @return
		 */
		private function convertToString(o:*):String {
			if (o is String) {
				return this.escapeString(o as String);
			}else if (o is Number) {
				return isFinite(o as Number)?'"'+o.toString()+'"':"null";
			}else if (o is Boolean) {
				return o?'"'+true+'"':'"'+false+'"';
			}else if (o is Array) {
				return this.arr2Str(o as Array);
			}else if (o is Date) {
				return this.date2Str(o as Date);
			}else if (o is Object&& o!=null) {
				return this.obj2Str(o);
			}
			return '"'+null+'"';
		}
		
		/**
		 * 将一个日期对象格式化成字符串
		 * @param	d
		 * @return
		 */
		private function date2Str(d:Date):String {
			var str:String = d.getFullYear() + "-" + d.getMonth() + "-" + d.getDay() + " " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
			return this.escapeString(str);
		}
		
		/**
		 * 将一个数组格式化成JSON
		 * @param	arr
		 * @return
		 */
		private function arr2Str(arr:Array):String {
			var s:String="";
			for(var i:Number=0;i<arr.length;i++){
				if(s.length>0){
					s+=",";
				}
				s+=this.convertToString(arr[i]);
			}
			return "["+s+"]";
		}
		
		/**
		 * 将一个对象格式化成JSON
		 * @param	obj
		 * @return
		 */
		private function obj2Str(obj:*):String {
			var s:String="";
			var classInfo:XML=describeType(obj);
			if(classInfo.@name=="Object" || classInfo.@name=="mx.utils::ObjectProxy"||classInfo.@name=="object"){	//本身是一个动态obj
				var value:Object;
				for(var key:String in obj){
					value=obj[key];
					if(value is Function){
						continue;
					}
					if(s.length>0){
						s+=",";
					}
					s+=this.escapeString(key)+":"+this.convertToString(value);
				}
			}else{							//自定义类
				var v:XMLList=classInfo..variable+classInfo..accessor;
				for each(var temp:XML in v){
					if(temp.@type=="Number"||temp.@type=="int"||temp.@type=="String"||temp.@type=="Boolean"){
						if(s.length>0){
							s+=",";
						}
						s+=this.escapeString(temp.@name)+":"+this.convertToString(obj[temp.@name]);
					}
				}
			}
			return "{"+s+"}";
		}
		
		/**
		 * 过滤特殊字符
		 * @param	str
		 * @return
		 */
		private function escapeString( str:String ):String {
			var s:String = "";
			var ch:String;
			var len:Number = str.length;
			for ( var i:int = 0; i < len; i++ ) {
				ch = str.charAt( i );
				switch ( ch ) {
					case '"':
						s += "\\\"";
						break;
					case '\\':
						s += "\\\\";
						break;
					case '\b':
						s += "\\b";
						break;
					case '\f':
						s += "\\f";
						break;
					case '\n':
						s += "\\n";
						break;
					case '\r':
						s += "\\r";
						break;
					case '\t':
						s += "\\t";
						break;
					default:
						if ( ch < ' ' ) {
							var hexCode:String = ch.charCodeAt( 0 ).toString( 16 );
							var zeroPad:String = hexCode.length == 2 ? "00" : "000";
							s += "\\u" + zeroPad + hexCode;
						} else {
							s += ch;
						}
				}
			}			
			return "\"" + s + "\"";
		}
	}
}