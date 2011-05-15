package com.sinosoft.fsky.util
{
	public class XGenerator
	{
		
		/**
		 * 生成唯一的标识字符串符号，利用本地的最详细的日期时间以及随机数，
		 * 在一定程度上，避免了出现相同字符串的可能性
		 * 可以传进一个唯一id，也可以不传，都会自行产生
		 * 连接3次随机，那么相同概率是1000*1000*1000可能性非常小，更别说其他的日期了
		 * @return str:唯一标识
		 */
		public static function generateId():String
		{
			//生成随机数的最大值
			var MAX_NUMBER:Number = 1000;
			
			return "xsky_" + new Date().getTime() + String(Math.floor(Math.random() * MAX_NUMBER));
		}
	}
}