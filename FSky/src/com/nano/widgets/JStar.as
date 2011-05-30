package com.nano.widgets {
	import com.nano.math.geom.JLineUtil;
	import flash.geom.Point;
	/**
	 * 五角星
	 * @version 1.0 2010-05-18
	 * @author  章小飞
	 * @since   V1.0
	 */
	public class JStar extends JFilledShape {
		/**
		 * 外接圆半径
		 */
		private var radius:Number;
		
		/**
		 * 构造方法
		 * @param	radius		外接圆半径
		 */
		public function JStar(radius:Number=10){
			this.radius = radius;
			this.drawWidget();
		}
		
		/**
		 * 设置外接圆半径
		 * @param	radius
		 */
		public function setRadius(radius:Number):void{
			this.radius=radius;
			this.drawWidget();
		}
				
		/**
		 * 五角星边长与外接圆半径之间的换算公式：l=R*tan36或者(3-根号5)/2*R
		 * <p>
		 * 为提升运算效率，默认使用近似值0.381计算内接正五边形半径，如果需要更精确的实现，请覆盖此方法
		 * </p>
		 */
		override protected function drawWidget():void {
			/**
			 * 计算五角星内接正五边形边长
			 * 为了提升绘图速度，此处可以使用估计值，内接正五边形的半径是大概是外接圆的0.35
			 */ 
			var l:Number = 0.381*this.radius;
			//开始绘制
			this.graphics.clear();
			this.graphics.lineStyle(this.strokeW,this.strokeColor,this.strokeTrans);
			this.graphics.beginFill(this.fillColor,this.fillTrans);
			//10点法绘制
			var _temp:Number=Math.PI/5;
			var p:Point=null;
			for(var i:int=0;i<11;i++){
				var angle:Number=_temp*i;
				if(i%2==0){
					p=Point.polar(this.radius,angle);
				}else{
					p=Point.polar(l,angle);
				}
				p.offset(this.radius,this.radius);
				if(i==0){
					this.graphics.moveTo(p.x,p.y);
				}else{
					this.graphics.lineTo(p.x,p.y);
				}
			}
			this.graphics.endFill();
			
			super.drawWidget();
		}
	}
}