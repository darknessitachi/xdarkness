package com.nano.flexcomp{
	import flash.display.GradientType;
	import flash.display.Graphics;
	import flash.geom.Matrix;
	
	import mx.containers.VBox;
	import mx.styles.CSSStyleDeclaration;
	import mx.styles.StyleManager;
	import mx.utils.StringUtil;
	
	[Style(name="backgroundGradientColors", type="Array", arrayType="uint", format="Color", inherit="no")]
	[Style(name="backgroundGradientAlphas", type="Array", arrayType="Number", inherit="no")]
	
	
	/**
	 * 第三方组件：背景可渐变的VBox
	 * New component that overrides the normal skin for a VBox.
	 * Added new backgroundColors style that allows the definition of gradient fill.
	 */
	public class GradientVBox extends VBox{
		private var backgroundGradientColorsChanged : Boolean = true;
		private var backgroundGradientColorsData : Array;
		
		private var backgroundGradientAlphasChanged : Boolean = true;
		private var backgroundGradientAlphasData : Array;
		
		private static var classConstructed:Boolean = classConstruct();
		
		private const DELIM:String = ",";
		
		// - Constructor
		public function GradientVBox()
		{
			
		}
		
		
		/**
		 * Initiate style properties
		 */     
		private static function classConstruct():Boolean
		{
			
			if (!StyleManager.getStyleDeclaration("GradientVBox"))
			{
				var newStyleDeclaration:CSSStyleDeclaration = new CSSStyleDeclaration();
				
				newStyleDeclaration.setStyle("backgroundGradientColors", [0xFFFFFF, 0xFFFFFF]);
				newStyleDeclaration.setStyle("backgroundGradientAlphas", [1, 1]);
				
				StyleManager.setStyleDeclaration("GradientVBox", newStyleDeclaration, true);
			}
			
			return true;
			
		} // function
		
		
		
		/**
		 * Checks for a change in the style property defined in this component.
		 */  
		override public function styleChanged(styleProp:String):void {
			
			super.styleChanged(styleProp);
			
			// Check to see if style changed.
			if (styleProp=="backgroundGradientColors")
			{
				backgroundGradientColorsChanged = true;
				invalidateDisplayList();
			} // if
			
			if (styleProp=="backgroundGradientAlphas")
			{
				backgroundGradientAlphasChanged = true;
				invalidateDisplayList();
			} // if
			
		} // function
		
		
		
		/**
		 * Default size 100 x 100
		 */  
		override protected function measure():void
		{
			
			super.measure();
			
			measuredWidth = measuredMinWidth = 100;
			measuredHeight = measuredMinHeight = 100;
			
		} // function
		
		
		/**
		 * Override the default updateDisplayList function call.
		 * First you must call its base implementation - then you can clear the graphics and draw the new skin
		 * Use the built in drawRoundRect function. It is part of UIComponent - a copy of programaticSkin function.
		 */  
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void
		{
			
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			
			if ( (backgroundGradientColorsChanged == true) || (backgroundGradientAlphasChanged == true) )
			{
				
				graphics.clear();
				
				var colorStr:String = StringUtil.trimArrayElements( getStyle("backgroundGradientColors"), DELIM );
				backgroundGradientColorsData = colorStr.split(DELIM);
				getColorHexs( backgroundGradientColorsData );
				
				if ( backgroundGradientColorsData.length == 0 )
					backgroundGradientColorsData = [0xFFFFFF,0x000000];
				
				var alphaStr:String = StringUtil.trimArrayElements( getStyle("backgroundGradientAlphas"), DELIM );
				backgroundGradientAlphasData = alphaStr.split(DELIM);;
				
				if ( backgroundGradientAlphasData.length == 0 )
					backgroundGradientAlphasData = [1,1];
				
				/**
				 
				 private var n:Number;
				 n = myButton.getStyle("color"); //// Returns 16711680
				 var colorStr:String = "0x" + n.toString(16).toUpperCase();
				 
				 */
				var borderColor:Number = this.getStyle("borderColor");
				
				var borderThickness:Number = this.getStyle("borderThickness");
				
				var cornerRad:Number = this.getStyle("cornerRadius");
				
				var matr:Matrix = new Matrix();
				matr.createGradientBox(unscaledWidth, unscaledHeight, Math.PI/2);
				
				// Could also use
				//matr = verticalGradientMatrix(x, y, unscaledWidth, unscaledHeight);
				
				var ratios:Array = null;
				
				var g:Graphics = graphics;
				g.lineStyle(borderThickness,borderColor);
				
				/**
				 * drawRoundRect(
				 * x:Number, y:Number, width:Number, height:Number,
				 * cornerRadius:Object = null,
				 * color:Object = null, alpha:Object = null,
				 * gradientMatrix:Matrix = null, gradientType:String = "linear", gradientRatios:Array = null,
				 * hole:Object = null):void
				 */
				drawRoundRect(
					0,0, unscaledWidth, unscaledHeight,
					cornerRad,
					backgroundGradientColorsData, backgroundGradientAlphasData,
					matr, GradientType.LINEAR, ratios );
				
				// g.endFill(); Already called by drawRoundRect function
				
				backgroundGradientColorsChanged = false;
				backgroundGradientAlphasChanged = false;
				
			} // if
			
		} // function 
		
		
		/**
		 *
		 * Takes an integer color and converts it to a string color as normally used by styles.
		 *
		 * @param color
		 * @return
		 *
		 */  
		private function intToHex(color:int = 0):String
		{
			var mask:String = "000000";
			var str:String = mask + color.toString(16).toUpperCase();
			return "#" + str.substr(str.length - 6);
		}
		
		
		/**
		 * Takes an array of colors stings as normally given to styles and converts them to
		 * an array of hexadicimal integers as normally used by the drawing API.
		 *
		 * @param colors - string
		 *
		 */  
		public function getColorHexs(colors:Array):void
		{
			if (!colors)
				return;
			
			var n:int = colors.length;
			for (var i:int = 0; i < n; i++)
			{
				if ( (colors[i] != null) && isNaN(colors[i]) )
				{
					colors[i] = Number( String(colors[i]).replace("#", "0x") );
				} // if
			} // for
			
		} // function
		
	} // class
} // package