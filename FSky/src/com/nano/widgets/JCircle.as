package com.nano.widgets{
	import com.nano.core.JComponent;
	
	/**
	 * 圆或椭圆
	 * <pre>
	 * 		var circle:JCircle=new JCircle();
	 * 		this.addChild(circle);
	 * 		circle.strokeStyle(3,0xff0000,0.8);
	 * 		circle.setBeginPoint(0,0);
	 * 		circle.setEndPoint(100,100);
	 * </pre>
	 * @version	1.1
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	2009-09-22
	 */
	public class JCircle extends JFilledShape{
		/**
		 * 构造方法
		 * @param	x
		 * @param	y
		 */
		public function JCircle(x:Number=0,y:Number=0){
			this.x=x;
			this.y=y;
		}
		
		/**
		 * 更新对象
		 */
		override public function drawWidget():void {
			this.graphics.clear();
			this.graphics.lineStyle(this.strokeW, this.strokeColor, this.strokeTrans);
			this.graphics.beginFill(this.fillColor,this.fillTrans);
			this.graphics.drawEllipse(0,0,this.endX,this.endY);
			this.graphics.endFill();
			
			super.drawWidget();
		}
	}
}