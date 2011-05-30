/**
 * The Gauge component is from 
 * http://www.betterthanflex.com/app/gaugeexplorer/Main.html
 * 			//弧度 = radian, 角度 = angle.
			//垂直向上,角度为0, 向左为负,向右为正.
			//角度/180 = 弧度/PI
 * SmithFox(http://www.smithfox.com) fix some bugs, add value lable, and enhance neele center
 */
package com.nano.plugins
{
	import flash.display.Bitmap;
	import flash.display.DisplayObject;
	import flash.display.StageQuality;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.events.TimerEvent;
	import flash.filters.BevelFilter;
	import flash.filters.DropShadowFilter;
	import flash.geom.Matrix;
	import flash.geom.Point;
	import flash.text.*;
	import flash.ui.Mouse;
	import flash.utils.Timer;
	import mx.effects.easing.Bounce;
	import mx.effects.Rotate;
	
	import mx.controls.Alert;
	import mx.controls.Image;
	import mx.core.FlexGlobals;
	import mx.core.IFlexDisplayObject;
	import mx.core.UIComponent;
	import mx.core.UITextField;
	import mx.styles.CSSStyleDeclaration;
	import mx.styles.IStyleManager2;
	import mx.styles.StyleManager;
	
	
	[Style(name="backgroundAlpha", type="Number", inherit="no")]
	
	[Style(name="backgroundColor", type="uint", format="Color", inherit="no")]
	
	[Style(name="borderColor", type="uint", format="Color", inherit="no")]
	
	[Style(name="borderStyle", type="String", enumeration="solid,none", inherit="no")]
	
	[Style(name="borderThickness", type="Number", format="Length", inherit="no")]
	
	[Style(name="tickColor", type="uint", format="Color", inherit="no")]
	
	[Style(name="minorTickLength", type="Number", format="Length", inherit="no")]
	
	[Style(name="minorTickThickness", type="Number", format="Length", inherit="no")]
	
	[Style(name="majorTickLength", type="Number", format="Length", inherit="no")]
	
	[Style(name="majorTickThickness", type="Number", format="Length", inherit="no")]
	
	[Style(name="tickInset", type="Number", format="Length", inherit="no")]
	
	[Style(name="scaleFontColor", type="uint", format="Color", inherit="no")]
	
	[Style(name="scaleFontFamily", type="String", inherit="yes")]
	
	[Style(name="scaleFontSize", type="Number", format="Length", inherit="yes")]
	
	[Style(name="scaleFontStyle", type="String", enumeration="normal,italic", inherit="yes")]
	
	[Style(name="scaleFontWeight", type="String", enumeration="normal,bold", inherit="yes")]
	
	[Style(name="labelFontColor", type="uint", format="Color", inherit="no")]
	
	[Style(name="labelFontFamily", type="String", inherit="yes")]
	
	[Style(name="labelFontSize", type="Number", format="Length", inherit="yes")]
	
	[Style(name="labelFontStyle", type="String", enumeration="normal,italic", inherit="yes")]
	
	[Style(name="labelFontWeight", type="String", enumeration="normal,bold", inherit="yes")]
	
	[Style(name="valueFontColor", type="uint", format="Color", inherit="no")]
	
	[Style(name="valueFontFamily", type="String", inherit="yes")]
	
	[Style(name="valueFontSize", type="Number", format="Length", inherit="yes")]
	
	[Style(name="valueFontStyle", type="String", enumeration="normal,italic", inherit="yes")]
	
	[Style(name="valueFontWeight", type="String", enumeration="normal,bold", inherit="yes")]
	
	public class Gauge extends UIComponent
	{
		private var _minimum:Number = 0;
		private var _maximum:Number = 10;
		private var _value:Number = 5;
		
		private var _angleFrom:Number = -121;
		private var _angleTo:Number = 121;
		private var _radianFrom:Number = -121 * 0.017453292;
		private var _radianTo:Number = 121 * 0.017453292;
		
		private var _radius:Number = 100;
		private var _centerx:Number = 100;
		private var _centery:Number = 100;
		private const NEEDLE_SCALE:Number = 0.85;
		private var _needleOriginX:Number = 3;
		private var _needleOriginY:Number = 150;
		
		private var _label:TextField;
		private var _valueLabel:TextField;
		
		private var _showHalfTicks:Boolean = true;
		private var _showQuarterTicks:Boolean = true;
		
		private var _showOuterLine:Boolean = false;
		private var _showInnerLine:Boolean = false;
		
		private var graduations:Array;
		private var labels:Array = new Array();
		private var _scaleInside:Boolean = true;
		private var _scaleGap:Number = 10;
		
		[Embed(source="gauge_needle.png")]
		private var _needleClass:Class;
		private var _needle:Image;
		
		//设置默认样式, 你可以在mxml中改写
		private static var classConstructed:Boolean = classConstruct();
		
		private static function classConstruct():Boolean {
			if (!FlexGlobals.topLevelApplication.styleManager.getStyleDeclaration("com.smithfox.components.Gauge"))
			{
				var myStyles:CSSStyleDeclaration = new CSSStyleDeclaration();
				myStyles.defaultFactory = function():void
				{
					this.backgroundColor=0xEEEEEE;
					this.borderStyle = "none";
					this.tickColor = 0x888888;
					this.majorTickLength=10;
					this.minorTickLength=5;
					this.majorTickThickness=2;
					this.minorTickThickness=1;
					this.tickInset=0;
					this.borderThickness=3;
					
					this.scaleFontColor=0x888888;
					this.scaleFontFamily="Arial";
					this.scaleFontSize=12;
					this.scaleFontWeight="bold"; 
					
					this.labelFontColor=0x888888;
					this.labelFontFamily="Arial";
					this.labelFontSize=18;
					this.labelFontWeight="bold";
					
					this.valueFontColor=0x888888;
					this.valueFontFamily="Arial";
					this.valueFontSize=12;
					this.valueFontWeight="bold";

				}
				FlexGlobals.topLevelApplication.styleManager.setStyleDeclaration("com.smithfox.components.Gauge", myStyles, true);
				
			}
			return true;
		}
		
		override protected function measure():void 
		{
			super.measure();
			this.measuredMinHeight = this.measuredHeight = this._radius * 2;
			this.measuredMinWidth  = this.measuredWidth  = this._radius * 2;
		}
		
		public function Gauge()
		{
			super();   
		}
		
		override protected function createChildren():void 
		{
			super.createChildren();
			
			r = new Rotate();
			r.easingFunction = Bounce.easeOut;
			
			graduations = GaugeUtil.optimalScale(_minimum,_maximum,5,false);
			generateLabels();
			
			_label= new TextField();
			_label.autoSize = TextFieldAutoSize.LEFT;            
			
			_valueLabel= new TextField();
			_valueLabel.autoSize = TextFieldAutoSize.CENTER;
			
			var bitmap:Bitmap = new _needleClass();
			bitmap.smoothing = true;
			
			_needle = new Image();
			_needle.width = bitmap.width;
			_needle.height = this._radius / NEEDLE_SCALE;
			
			this._needleOriginX = this._needle.width / 2;
			this._needleOriginY = this._needle.height * NEEDLE_SCALE;
			
			_needle.x = _centerx - _needleOriginX;
			_needle.y = _centery - _needleOriginY;
			_needle.source = bitmap;
			
			this.addChild(_label);
			this.addChild(_valueLabel);
			this.addChild(_needle);

			value = minimum;
		}
		
		private function generateLabels():void
		{
			if(labels != null)
			{
				for(var j:int = 0;j < labels.length;j++)
				{
					if(this.contains(labels[j]))
					{
						this.removeChild(labels[j]);
					}
				}
				//delete all labels
				labels.splice(0,labels.length);
			}
			for(var i:int = 0;i < graduations.length; i++)
			{
				var tf:TextField = new TextField();
				tf.antiAliasType = AntiAliasType.ADVANCED;
				tf.autoSize = TextFieldAutoSize.LEFT;
				tf.text = graduations[i];    
				tf.selectable = false; 
				labels.push(tf);
				this.addChildAt(tf,0);
			}
		}

		private function _setValue(val:Number):void {
			_value = val;
		}

		private var newCenter:Point = new Point();
		
		private var r:Rotate;
		private function positionNeedle():void
		{
			
			var angle:Number = calculatePointerAngle();

			if ( r.isPlaying ) {
				r.stop();
			}

			var duration:int = 500;

			r.originX = this._needleOriginX;
			r.originY = this._needleOriginY;
			r.duration = duration;
			r.angleFrom = _needle.rotation;
			//r.angleFrom = r.angleTo;
			r.angleTo = angle;
			r.target = _needle;
			r.play();
		}
		
		private function calculatePointerAngle():Number {
			//rotate appropriate angle
		    var delta:Number;
			var ratio:Number;
			var angle:Number;

			delta=maximum-minimum;

			ratio=_value/delta;
			//Check to see if we exceed boundary conditions
			if (_value > this.maximum) ratio = 1;
			if (_value < this.minimum) ratio = 0;
			
			angle = ((this._angleTo - this._angleFrom) * ratio) + this._angleFrom;

			return angle;
		}

		private function positionScaleLabels():void
		{
			var sweep:Number = radianTo - radianFrom;
			var gradRadian:Number = sweep / (graduations.length - 1); 
			
			var matl:Number =  getStyle("majorTickLength");
			var ti:Number = getStyle("tickInset");
			var labelRadius:Number = _radius - ti - (scaleInside?(matl + _scaleGap):-scaleGap);
			for(var i:int = 0;i<labels.length;i++)
			{
				var radian:Number = radianFrom + (i * gradRadian);
				labels[i].x = Math.sin(radian) * labelRadius + _centerx - (labels[i].width/2) - ((scaleInside?1:-1) * (Math.sin(radian) * (labels[i].width/2))); 
				labels[i].y = -Math.cos(radian) * labelRadius + _centery - labels[i].height/2 + ((scaleInside?1:-1) * (Math.cos(radian) * (labels[i].height/2)));
			}
			
			_label.x = _centerx - _label.width/2;
			_label.y = _centery - _label.height;
			
			_valueLabel.x = _centerx - _valueLabel.width/2;
			_valueLabel.y = _centery + 20;
		}
		
		private function setTextStyles():void
		{
			var tf:TextFormat =  new TextFormat(getStyle("scaleFontFamily"),getStyle("scaleFontSize"),getStyle("scaleFontColor"),(getStyle("scaleFontWeight") == "bold"),(getStyle("scaleFontStyle") == "italic"));
			for(var i:int = 0;i<labels.length;i++)
			{
				TextField(labels[i]).setTextFormat(tf);
			}
			var ltf:TextFormat =  new TextFormat(getStyle("labelFontFamily"),getStyle("labelFontSize"),getStyle("labelFontColor"),(getStyle("labelFontWeight") == "bold"),(getStyle("labelFontStyle") == "italic"));
			_label.setTextFormat(ltf);
			
			var vltf:TextFormat =  new TextFormat(getStyle("valueFontFamily"),getStyle("valueFontSize"),getStyle("valueFontColor"),(getStyle("valueFontWeight") == "bold"),(getStyle("valueFontStyle") == "italic"));
			_valueLabel.setTextFormat(vltf);
			_valueLabel.defaultTextFormat = vltf;
		}
		
		protected override function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void
		{
			if (r.target != null && r.isPlaying) return;
			
			graphics.clear();
//do not draw background
/*
			graphics.lineStyle(0);
			
			graphics.beginFill(getStyle("backgroundColor"),getStyle("backgroundAlpha"));
			
			graphics.drawCircle(_centerx,_centery,_radius);
			
			graphics.endFill();
*/			
			graphics.lineStyle(getStyle("majorTickThickness"),getStyle("tickColor"));
			if(showOuterLine)
				GaugeUtil.circCurveTo(graphics,radianFrom,radianTo,_centerx,_centery,_radius - getStyle('tickInset'));
			if(showInnerLine)
				GaugeUtil.circCurveTo(graphics,radianFrom,radianTo,_centerx,_centery,_radius - getStyle('tickInset') - getStyle('majorTickLength'));
			
			graphics.lineStyle(1,0,1);
			generateTicks();
			
			if(getStyle("borderStyle") == "solid")
				graphics.lineStyle(getStyle("borderThickness"),getStyle("borderColor"));
			graphics.drawCircle(_centerx,_centery,_radius);
			
			graphics.lineStyle(0);
			graphics.beginFill(0xFF0000);
			graphics.drawCircle(_centerx,_centery, 6);
			graphics.endFill();
			
			setTextStyles();
			callLater(positionScaleLabels);
		}
		
		private function generateTicks():void
		{
			var sweep:Number = radianTo - radianFrom;
			var gradRadian:Number = sweep / (graduations.length - 1);
			var matl:Number =  getStyle("majorTickLength");
			var mitl:Number =  getStyle("minorTickLength");
			var matt:Number = getStyle("majorTickThickness");
			var mitt:Number = getStyle("minorTickThickness");
			var tc:uint = getStyle("tickColor");
			var ti:Number = getStyle("tickInset");
			for(var i:int = 0;i < graduations.length; i++)
			{
				graphics.lineStyle(matt,tc,1);
				graphics.moveTo(Math.sin(radianFrom + (i * gradRadian)) * (_radius - ti - matl) + _centerx,-Math.cos(radianFrom + (i * gradRadian)) * (_radius - ti - matl) + _centery);
				graphics.lineTo(Math.sin(radianFrom + (i * gradRadian)) * (_radius - ti) + _centerx,-Math.cos(radianFrom + (i * gradRadian)) * (_radius - ti) + _centery);
				
				graphics.lineStyle(mitt,tc,1);
				if(showHalfTicks && i != (graduations.length - 1))
				{    
					graphics.moveTo(Math.sin(radianFrom + (i * gradRadian) + (gradRadian/2)) * (_radius - ti - matl) + _centerx,-Math.cos(radianFrom + (i * gradRadian) + (gradRadian/2)) * (_radius - ti - matl) + _centery);
					graphics.lineTo(Math.sin(radianFrom + (i * gradRadian) + (gradRadian/2)) * (_radius - ti - (matl - mitl)) + _centerx,-Math.cos(radianFrom + (i * gradRadian) + (gradRadian/2)) * (_radius - ti - (matl - mitl)) + _centery);    
				}
				if(showQuarterTicks && i != (graduations.length - 1))
				{
					graphics.moveTo(Math.sin(radianFrom + (i * gradRadian) + (gradRadian/4)) * (_radius - ti - matl) + _centerx,-Math.cos(radianFrom + (i * gradRadian) + (gradRadian/4)) * (_radius - ti - matl) + _centery);
					graphics.lineTo(Math.sin(radianFrom + (i * gradRadian) + (gradRadian/4)) * (_radius - ti - (matl - mitl)) + _centerx,-Math.cos(radianFrom + (i * gradRadian) + (gradRadian/4)) * (_radius - ti - (matl - mitl)) + _centery);
					
					graphics.moveTo(Math.sin(radianFrom + (i * gradRadian) + (3*gradRadian/4)) * (_radius - ti - matl) + _centerx,-Math.cos(radianFrom + (i * gradRadian) + (3*gradRadian/4)) * (_radius - ti - matl) + _centery);
					graphics.lineTo(Math.sin(radianFrom + (i * gradRadian) + (3*gradRadian/4)) * (_radius - ti - (matl - mitl)) + _centerx,-Math.cos(radianFrom + (i * gradRadian) + (3*gradRadian/4)) * (_radius - ti - (matl - mitl)) + _centery);
				}
			}
		}
		
		
		[Bindable]
		public function set value(val:Number):void
		{
			if(val > maximum)
				_setValue(maximum);
			else if(val < minimum)
				_setValue(minimum);
			else
				_setValue(val);
			
			_valueLabel.text = _value.toFixed(0);
			
			positionNeedle();
		}

		public function get value():Number{
			return _value;
		}
		
		[Bindable]
		public function set minimum(value:Number):void
		{
			_minimum = value;
			graduations = GaugeUtil.optimalScale(_minimum,_maximum,5,false);
			generateLabels();
			value = _value; //need to reset value in case the value is outside the new range
			invalidateDisplayList();
		}
		
		public function get minimum():Number
		{
			return _minimum;
		}
		
		[Bindable]
		public function set maximum(value:Number):void
		{
			_maximum = value;
			graduations = GaugeUtil.optimalScale(_minimum,_maximum,5,false);
			generateLabels();
			value = _value; //need to reset value in case the value is outside the new range
			invalidateDisplayList();
		}
		
		public function get maximum():Number{
			return _maximum;
		}
		
		public override function set width(value:Number):void
		{
			_radius = Math.min(value/2,this.height/2);
			_centerx = value/2;
			//positionScaleLabels();
			//positionNeedle();
			super.width = value;
		}
		
		public override function set height(value:Number):void
		{
			_radius = Math.min(value/2,this.width/2);
			_centery = value/2;
			//positionScaleLabels();
			//positionNeedle();
			super.height = value;
		}
		
		[Bindable]
		public function set angleFrom(value:Number):void{
			_angleFrom = value;
			_radianFrom = (_angleFrom / 180) * Math.PI;
			positionNeedle();
			invalidateDisplayList();
		}
		
		public function get angleFrom():Number {
			return _angleFrom;
		}
		
		public function get radianFrom():Number {
			return _radianFrom;
		}
		
		[Bindable]
		public function set angleTo(value:Number):void{
			_angleTo = value;
			_radianTo = (_angleTo / 180) * Math.PI;
			positionNeedle();
			invalidateDisplayList();
		}
		
		public function get angleTo():Number{
			return _angleTo;
		}
		
		public function get radianTo():Number {
			return _radianTo;
		}
		
		[Bindable]
		public function set label(value:String):void
		{
			if(_label != null)
				_label.text = value;
			invalidateDisplayList();
		}
		
		public function get label():String
		{
			if(_label != null)
				return _label.text;
			else
				return "";
		}
		
		[Bindable]
		public function set scaleGap(value:Number):void
		{
			_scaleGap = value;
			//positionScaleLabels();
		}
		
		public function get scaleGap():Number
		{
			return _scaleGap;
		}
		
		[Bindable]
		public function set scaleInside(value:Boolean):void
		{
			_scaleInside = value;
			//positionScaleLabels();
		}
		
		public function get scaleInside():Boolean
		{
			return _scaleInside;
		}
		
		[Bindable]
		public function get showInnerLine():Boolean
		{
			return _showInnerLine;
		}
		
		public function set showInnerLine(value:Boolean):void
		{
			_showInnerLine = value;
			invalidateDisplayList();
		}
		[Bindable]
		public function get showOuterLine():Boolean
		{
			return _showOuterLine;
		}
		
		public function set showOuterLine(value:Boolean):void
		{
			_showOuterLine = value;
			invalidateDisplayList();
		}
		
		[Bindable]
		public function get showHalfTicks():Boolean
		{
			return _showHalfTicks;
		}
		
		public function set showHalfTicks(value:Boolean):void
		{
			_showHalfTicks = value;
			invalidateDisplayList();
		}
		
		[Bindable]
		public function get showQuarterTicks():Boolean {
			return _showQuarterTicks;
		}
		
		public function set showQuarterTicks(value:Boolean):void {
			_showQuarterTicks = value;
			invalidateDisplayList();
		}

	}
}
