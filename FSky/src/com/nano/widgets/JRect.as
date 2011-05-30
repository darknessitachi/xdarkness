package com.nano.widgets {
	import flash.geom.Matrix;

	/**
	 * 实线矩形
	 * @version	1.1
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	2009-09-22
	 */
	public class JRect extends JFilledShape{
		/**
		 * 构造函数
		 * @param	x
		 * @param	y
		 */
		public function JRect(x:Number = 0, y:Number = 0){
			this.x = x;
			this.y = y;
		}
		
		/**
		 * 根据指定的类型绘制填充的形状
		 */
		override protected function fillShape():void{
			switch(this.fillType){
				case JFilledShape.FILL_TYPE.PLAIN:		//普通的填充
					this.graphics.beginFill(this.fillColor, this.fillTrans);
					break;
				case JFilledShape.FILL_TYPE.GRADIENT:	//渐变填充
					var matrix:Matrix=new Matrix();
					matrix.createGradientBox(this.endX,this.endY,90 * Math.PI / 180,0,0);
					this.gradientFillVars.matrix=matrix;
					this.graphics.beginGradientFill(this.gradientFillVars.type, this.gradientFillVars.colors, this.gradientFillVars.alphas, this.gradientFillVars.ratios, this.gradientFillVars.matrix,this.gradientFillVars.spreadMethod, this.gradientFillVars.interpolationMethod, this.gradientFillVars.focalPointRatio);
					break;
				case JFilledShape.FILL_TYPE.BITMAP:		//位图填充
					
					break;
				default:
					break;
			}
		}
		
		/**
		 * 绘制组件
		 */
		override public function drawWidget():void {
			this.graphics.clear();
			this.graphics.lineStyle(this.strokeW, this.strokeColor, this.strokeTrans);
			if(this.useFill){
				fillShape();
			}
			this.graphics.drawRect(0, 0, this.endX, this.endY);
			this.graphics.endFill();
			
			super.drawWidget();
		}
	}
}