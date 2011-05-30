package com.nano.flcomp.chart {
	import com.nano.flcomp.JFlComponent;
	
	/**
	 * LED数码显示管
	 * @version	1.0 2010-05-19
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */
	public class JNumberShow extends JChart {
		public function JNumberShow(xPos:int=0,yPos:int=0){
			super(xPos,yPos);
			this.setCompUI(new NumberShow());
		}
	}
}