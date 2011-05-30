package com.nano.serialization.json {
	/**
	 * JSON解析和生成工具，
	 * 该类从官方的扩展JSON包改进而来
     * @version	1.0
	 * @author 章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  2010-04-21
	 */
	public class JSON {
		public function JSON() {
			throw new Error("尝试实例化JSON工具类");
		}
		
		/**
		 * 将一个AS对象解析成JSON字符串
		 * @param	o		可以是任意AS3对象
		 * @return
		 */
		public static function encode(o:Object):String {
			return new JSONEncoder(o).getString();
		}

		/**
		 * 将一个JSON字符串解析成AS3对象
		 * @param	s
		 * @param	strict
		 * @return
		 */
		public static function decode( s:String, strict:Boolean = true ):*{	
			return new JSONDecoder( s, strict ).getValue();	
		}
	}
}