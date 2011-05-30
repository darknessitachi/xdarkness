
package com.nano.widgets.link {
	

	/**
	 * 简单的单条无向连接线
	 * @version	1.2
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	2009-10-05
	 */
	public class JSimpleLink extends JLink{
		/**
		 * 构造方法
		 * @param	x1
		 * @param	y1
		 * @param	strokeW
		 * @param	strokeColor
		 * @param	strokeTrans
		 */
		public function JSimpleLink(x1:Number=0,y1:Number=0,strokeW:Number=2,strokeColor:Number=0x000000,strokeTrans:Number=0.8) {
			super(x1, y1, strokeW, strokeColor, strokeTrans);
		}
		
		/**
		 * 方法覆盖：绘制组件
		 * 为了增加鼠标点击的感应范围，先在在线的外层绘制一层完全透明的线
		 */
		override public function drawWidget():void {
			this.graphics.clear();
			
			this.graphics.lineStyle(this.strokeW+10,0xff0000,0);
			this.graphics.moveTo(0,0);
			this.graphics.lineTo(this.endX, this.endY);
			
			this.graphics.lineStyle(this.strokeW, this.strokeColor, this.strokeTrans);
			this.graphics.moveTo(0,0);
			this.graphics.lineTo(this.endX, this.endY);
		}
	}
}