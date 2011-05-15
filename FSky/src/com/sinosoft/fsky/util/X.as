package com.sinosoft.fsky.util
{
	public class X
	{
		/**
		 * 获取随机颜色
		 */
		public static function getRandomColor():uint {
			return Math.round(Math.random() * 0xffffff);
		}
	}
}