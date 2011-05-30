package com.nano.util{
	import com.nano.plugins.ColorGradient;
	/**
	 * 随机颜色生成器---尚未实现
	 */
	public class ColorUtil{
		private var colors:Array=[];
		
		public function ColorUtil(){
			this.colors=ColorGradient.generateTransitionalColor(0xff0000);
		}
		
		public function random():Number{
			return 0;
		}
	}
}