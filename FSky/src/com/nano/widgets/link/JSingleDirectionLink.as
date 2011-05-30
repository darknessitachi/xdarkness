package com.nano.widgets.link {
	import com.nano.widgets.JArrowLine;
	
	import flash.geom.Point;

	/**
	 * 单向箭头连接线
	 * @version	2.0
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	2010-05-18
	 */
	public class JSingleDirectionLink extends JDirectionLink {
		public function JSingleDirectionLink(){
			this.arrowType = JArrowLine.ARROW_TYPE.END_ARROW;
		}
	}
}