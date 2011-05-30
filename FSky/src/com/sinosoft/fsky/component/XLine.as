package com.sinosoft.fsky.component
{
	import flash.display.Sprite;
	import flash.geom.Point;
	
	import mx.core.UIComponent;

	public class XLine extends Sprite
	{
		public static var _instance:XLine;
		
		public function XLine()
		{
		}
		
		
		public function connectComponent(uFrom:UIComponent, uTo:UIComponent):Function {
			_instance.graphics.moveTo(uFrom.x+uFrom.width/2,uFrom.y+uFrom.height/2);
			_instance.graphics.lineTo(uTo.x+uTo.width/2,uTo.y+uTo.height/2);
			
			return connectComponent;
		}
		
		public function connectPoint(pFrom:Point, pTo:Point):Function {
			_instance.graphics.moveTo(pFrom.x, pFrom.y);
			_instance.graphics.lineTo(pTo.x, pTo.y);
			
			return connectPoint;
		}
		
		public function wrapper():UIComponent {
			var comp:UIComponent = new UIComponent();
			comp.addChild(_instance);
			
			_instance = null;
			return comp;
		}
		
		public function clear():void {
			_instance.graphics.clear();
		}
		
		public static function getLine(color:uint):XLine {
			
			if(_instance == null) {
				_instance = new XLine;
				_instance.graphics.lineStyle(2,color,1);
			}
			
			return _instance;
		}
		
		public function connect(xp:Number, yp:Number, col:uint=0):Function{
			_instance.graphics.moveTo(xp, yp);
			var line:Function = function(xp:Number, yp:Number):Function{
				_instance.graphics.lineTo(xp, yp);
				return line;
			}
			return line;
		}

	}
}