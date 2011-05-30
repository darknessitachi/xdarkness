package com.nano.widgets{
	

	/**
	 * 绘图页面
	 * @version	1.3 2010-05-18
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */
	public class JPage extends JRect {
		/**
		 * 背景网格大小
		 */
		public var GRID_SIZE:int = 50;
		public var multiSelect:Boolean=true;
		/**
		 * 构造方法
		 * @param	x	x轴坐标
		 * @param	y	y轴坐标
		 */
		public function JPage(x:Number=0,y:Number=0){
			super(x,y);
			this.fillType=JFilledShape.FILL_TYPE.PLAIN;
			//this.gradientFillVars.colors=[0xffffff,0xcccccc];
			//this.gradientFillVars.ratios=[0,255];
			//this.gradientFillVars.alphas=[0.8,0.5];
			this.strokeStyle(1,0x000000,0.8);
			this.fillStyle(0xeeeeee,0.8);
		}
		
		/**
		 * 创建背景网格线
		 */
		public function makeGrid():void{
			this.graphics.clear();
			this.drawWidget();
			this.graphics.lineStyle(1,0x000000,0.2);
			var i:int=0;
			for(;i<=this.endX;i+=GRID_SIZE){
				this.graphics.moveTo(i,0);
				this.graphics.lineTo(i,this.endY);
			}
			i=0;
			for(;i<=this.endY;i+=GRID_SIZE){
				this.graphics.moveTo(0,i);
				this.graphics.lineTo(this.endX,i);
			}
		}
		
		/**
		 * 清除背景网格线
		 */
		public function clearGrid():void{
			this.graphics.clear();
			this.drawWidget();
		}
		
		/**
		 * 设置页面的缩放比例	
		 * @param	scaleX
		 * @param	scaleY
		 * @param	offsetX
		 * @param	offsetY
		 */
		public function setZoom(sx:Number,sy:Number,offsetX:Number=undefined,offsetY:Number=undefined):void {
			var _mx:Number=this.mouseX
			var _my:Number = this.mouseY
			this.scaleX+=sx;
			this.scaleY+=sy;
			if(offsetX){
				this.x+=offsetX*(-sx);
			}else{
				this.x+=_mx*(-sx);
			}
			
			if(offsetY){
				this.y+=offsetY*(-sy);
			}else{
				this.y+=_my*(-sy);
			}
		}
	}
}