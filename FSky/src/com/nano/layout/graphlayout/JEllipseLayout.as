package com.nano.layout.graphlayout{
	import com.greensock.TweenLite;
	
	import flash.display.Sprite;
	/**
	 * 椭圆形布局
	 */ 
	public class JEllipseLayout extends JDiagramLayout{
		private var _a:Number=0;
		private var _b:Number=0;
		
		public function JEllipseLayout(startX:Number=0,startY:Number=0,layoutWidth:Number=0,layoutHeight:Number=0){
			super(startX,startY,layoutWidth,layoutHeight);
			this._a=this.layoutWidth/2;
			this._b=this.layoutHeight/2;
		}
		
		override public function doLayout(config:*=null):void{
			var _nums:int=this.nodes.length;
			var _step:Number=2*Math.PI/_nums;
			for(var i:int=0;i<_nums;i++){
				var radian:Number = i*_step+this.layoutRotation;
				var _x:Number = _a * Math.sin(radian)+this.startX;
				var _y:Number = _b * Math.cos(radian)+this.startY;
				var _node:Sprite=this.nodes[i];
				TweenLite.to(_node,1,{x:_x,y:_y,onUpdate:function():void{
					if(this.eachFunc){
						for(var j:int=0;j<nodes.length;j++){
							var _node2:Sprite=nodes[j];
							eachFunc.call(_node2);
						}
					}
				}});
			}
		}
	}
}