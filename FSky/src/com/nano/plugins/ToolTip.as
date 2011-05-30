﻿/**
 
 The MIT License
 
 Copyright (c) 2008 Duncan Reid ( http://www.hy-brid.com )
 
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 
 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
 
 **/
/**
 * 注意：从第三方组件改编而来
 */
package com.nano.plugins{
	import com.greensock.TweenMax;
	import com.greensock.events.TweenEvent;
	import com.nano.core.NanoSystem;
	import com.nano.core.Observable;
	import com.nano.util.FuncUtil;
	
	import flash.display.*;
	import flash.events.*;
	import flash.filters.*;
	import flash.geom.*;
	import flash.text.*;
	import flash.utils.Timer;
	
	/**
	 * Public Setters:
	 
	 *		tipWidth 				Number				Set the width of the tooltip
	 *		titleFormat				TextFormat			Format for the title of the tooltip
	 * 		stylesheet				StyleSheet			StyleSheet object
	 *		contentFormat			TextFormat			Format for the bodycopy of the tooltip
	 *		titleEmbed				Boolean				Allow font embed for the title
	 *		contentEmbed			Boolean				Allow font embed for the content
	 *		align					String				left, right, center
	 *		delay					Number				Time in milliseconds to delay the display of the tooltip
	 *		hook					Boolean				Displays a hook on the bottom of the tooltip
	 *		hookSize				Number				Size of the hook
	 *		cornerRadius			Number				Corner radius of the tooltip, same for all 4 sides
	 *		colors					Array				Array of 2 color values ( [0xXXXXXX, 0xXXXXXX] ); 
	 *		autoSize				Boolean				Will autosize the fields and size of the tip with no wrapping or multi-line capabilities, 
	 													 helpful with 1 word items like "Play" or "Pause"
	 * 		border					Number				Color Value: 0xFFFFFF
	 *		borderSize				Number				Size Of Border
	 *		buffer					Number				text buffer
	 * 		bgAlpha					Number				0 - 1, transparency setting for the background of the ToolTip
	 *
	 * Example:
	 
	 		var tf:TextFormat = new TextFormat();
			tf.bold = true;
			tf.size = 12;
			tf.color = 0xff0000;
			
			var tt:ToolTip = new ToolTip();
			tt.hook = true;
			tt.hookSize = 20;
			tt.cornerRadius = 20;
			tt.align = "center";
			tt.titleFormat = tf;
			tt.show( DisplayObject, "Title Of This ToolTip", "Some Copy that would go below the ToolTip Title" );
	 *
	 *
	 * @author Duncan Reid, www.hy-brid.com
	 * @date October 17, 2008
	 * @version 1.2
	 */
	public class ToolTip extends Sprite {
		public var id:String=NanoSystem.id();
		private var _stage:Stage;
		private var _parentObject:DisplayObjectContainer;
		private var _tf:TextField;  // title field
		private var _cf:TextField;  //content field
		private var _contentContainer:Sprite = new Sprite(); // container to hold both textfields
		private var _tween:TweenMax;
		
		//formats
		private var _titleFormat:TextFormat;
		private var _contentFormat:TextFormat;
		
		//stylesheet
		private var _stylesheet:StyleSheet;
		
		/* check for stylesheet override */
		private var _styleOverride:Boolean = false;
		
		/* check for format override */
		private var _titleOverride:Boolean = false;
		private var _contentOverride:Boolean = false;
		
		// font embedding
		private var _titleEmbed:Boolean = false;
		private var _contentEmbed:Boolean = false;
		
		//defaults
		private var _defaultWidth:Number = 120;
		private var _defaultHeight:Number;
		private var _buffer:Number = 5;
		private var _align:String = "center"
		private var _cornerRadius:Number = 12;
		private var _bgColors:Array = [0xFFFFFF, 0x9C9C9C];
		private var _autoSize:Boolean =true;
		private var _hookEnabled:Boolean = false;
		private var _delay:Number = 0;  //millilseconds
		private var _hookSize:Number = 10;
		private var _border:Number;
		private var _borderSize:Number = 1;
		private var _bgAlpha:Number = 1;  // transparency setting for the background of the tooltip
		private var _offSet:Number;
		private var _hookOffSet:Number;
		private var _timer:Timer;
		
		public function ToolTip():void {
			this.mouseEnabled = true;
			this.buttonMode = false;
			this.mouseChildren =false;
			_timer = new Timer(this._delay, 1);
            _timer.addEventListener("timer", timerHandler);
		}
		
		public function setContent( title:String, content:String = null ):void {
			this.graphics.clear();
			this.addCopy( title, content );
			this.setOffset();
			this.drawBG();
		}
		
		/**
		 * 在指定的目标对象上显示一个tip，可以接受的参数有：
		 * target:需要显示到哪个对象上
		 * followMouse:tip是否跟随鼠标移动
		 * autoRemove&&removeTime:是否在指定的毫秒数之后自动清除自己
		 * animate:是否启用闪烁效果
		 * inerval:闪烁频率，毫秒数
		 * @param	config
		 */
		public function show(config:Object=null):void{
			if(!config){
				return;
			}
			
			this._stage = config.target.stage;
			this._parentObject = config.target as DisplayObjectContainer;
			
			var onStage:Boolean = this.addedToStage( this._contentContainer );
			if( ! onStage ){
				this.addChild( this._contentContainer );
			}
			this.addCopy(config.title||"",config.content||"");
			this.setOffset();
			this.drawBG();
			this.bgGlow();
			
			this.x=this._parentObject.x+this._offSet+this._parentObject.width/2;
			this.y=this._parentObject.y-this.height-10;
			this.alpha = 0;
			if(this._parentObject.parent){
				this._parentObject.parent.addChild(this);
			}
			if(this._parentObject is Observable){
				(this._parentObject as Observable).addListener("moving",followTarget);
			}
			//tip直接添加到目标对象上
//			this.x=this._offSet+this._parentObject.width/2;
//			this.y=-(this.height+10);
//			this.alpha = 0;
//			this._parentObject.addChild(this);
			
			//是否跟随鼠标移动
			if(config.followMouse){
				this.follow( true );
			}
			
			//定时自动清除
			if(config.autoRemove&&config.removeTime){
				var me:ToolTip=this;
				FuncUtil.delay(function(...args):void{
					if(me.parent){
						me.parent.removeChild(me);
					}
				},config.removeTime);
			}
			
			//是否使用闪烁效果
			if(config.animate&&config.interval){
				var me2:ToolTip=this;
				FuncUtil.timerTask(function(...args):void{
					if(me2.visible){
						me2.visible=false;
					}else{
						me2.visible=true;
					}
				},config.interval);
			}
			
			//淡出
			_timer.start();
		}
		
		private function followTarget(...args):void{
			this.x=this._parentObject.x+this._offSet+this._parentObject.width/2;
			this.y=this._parentObject.y-this.height-10;
		}
		
//		public static function alarm(config:Object=null):void{
//			
//		}
		
		public function hide():void {
			this.animate( false );
		}
		
		private function timerHandler( event:TimerEvent ):void {
			this.animate(true);
		}

		private function onMouseOut( event:MouseEvent ):void {
			event.currentTarget.removeEventListener(event.type, arguments.callee);
			this.hide();
		}
		
		private function follow( value:Boolean ):void {
			if( value ){
				addEventListener( Event.ENTER_FRAME, this.eof );
			}else{
				removeEventListener( Event.ENTER_FRAME, this.eof );
			}
		}
		
		private function eof( event:Event ):void {
			this.position();
		}
		
		private function position():void {
			var speed:Number = 3;
			var parentCoords:Point = new Point( _parentObject.mouseX, _parentObject.mouseY );
			var globalPoint:Point = _parentObject.localToGlobal(parentCoords);
			var xp:Number = globalPoint.x + this._offSet;
			var yp:Number = globalPoint.y - this.height - 10;
			
			var overhangRight:Number = this._defaultWidth + xp;
			if( overhangRight > stage.stageWidth ){
				xp =  stage.stageWidth -  this._defaultWidth;
			}
			if( xp < 0 ) {
				xp = 0;
			}
			if( (yp) < 0 ){
				yp = 0;
			}
			this.x += ( xp - this.x ) / speed;
			this.y += ( yp - this.y ) / speed;
		}

		private function addCopy( title:String, content:String = null ):void {
			if( this._tf == null ){
				this._tf = this.createField( this._titleEmbed ); 
			}
			// if using a stylesheet for title field
			if( this._styleOverride ){
				this._tf.styleSheet = this._stylesheet;
			}
			this._tf.htmlText = title;
			
			// if not using a stylesheet
			if( ! this._styleOverride ){
				// if format has not been set, set default
				if( ! this._titleOverride ){
					this.initTitleFormat();
				}
				this._tf.setTextFormat( this._titleFormat );
			}
			if( this._autoSize ){
				this._defaultWidth = this._tf.textWidth + 4 + ( _buffer * 2 );
			}else{
				this._tf.width = this._defaultWidth - ( _buffer * 2 );
			}
			
			
				
			this._tf.x = this._tf.y = this._buffer;
			this.textGlow( this._tf );
			this._contentContainer.addChild( this._tf );
			
			//if using content
			if( content != null ){
				
				if( this._cf == null ){
					this._cf = this.createField( this._contentEmbed );
				}
				
				// if using a stylesheet for title field
				if( this._styleOverride ){
					this._cf.styleSheet = this._stylesheet;
				}
			
				this._cf.htmlText = content;
				
				// if not using a stylesheet
				if( ! this._styleOverride ){
					// if format has not been set, set default
					if( ! this._contentOverride ){
						this.initContentFormat();
					}
					this._cf.setTextFormat( this._contentFormat );
				}
			
				var bounds:Rectangle = this.getBounds( this );
				this._cf.x = this._buffer;
				this._cf.y = this._tf.y +  this._tf.textHeight;
				this.textGlow( this._cf );
				
				if( this._autoSize ){
					var cfWidth:Number = this._cf.textWidth + 4 + ( _buffer * 2 )
					this._defaultWidth = cfWidth > this._defaultWidth ? cfWidth : this._defaultWidth;
				}else{
					this._cf.width = this._defaultWidth - ( _buffer * 2 );
				}
				this._contentContainer.addChild( this._cf );	
			}
		}
		
		private function createField( embed:Boolean ):TextField {
			var tf:TextField = new TextField();
			tf.embedFonts = embed;
			tf.gridFitType = "pixel";
			tf.autoSize = TextFieldAutoSize.LEFT;
			tf.selectable = false;
			if( ! this._autoSize ){
				tf.multiline = true;
				tf.wordWrap = true;
			}
			return tf;
		}
		
		private function drawBG():void {
			this.graphics.clear();
			var bounds:Rectangle = this.getBounds( this );
			var h:Number = isNaN( this._defaultHeight ) ? bounds.height + ( this._buffer * 2 ) : this._defaultHeight;
			var fillType:String = GradientType.LINEAR;
		   	var alphas:Array = [ this._bgAlpha, this._bgAlpha];
		   	var ratios:Array = [0x00, 0xFF];
		   	var matr:Matrix = new Matrix();
			var radians:Number = 90 * Math.PI / 180;
		  	matr.createGradientBox(this._defaultWidth, h, radians, 0, 0);
		  	var spreadMethod:String = SpreadMethod.PAD;
			if( ! isNaN( this._border )){
				this.graphics.lineStyle( _borderSize, _border, 1 );
			}
		  	this.graphics.beginGradientFill(fillType, this._bgColors, alphas, ratios, matr, spreadMethod); 
			if( this._hookEnabled ){
				var xp:Number = 0; var yp:Number = 0; var w:Number = this._defaultWidth; 
				this.graphics.moveTo ( xp + this._cornerRadius, yp );
				this.graphics.lineTo ( xp + w - this._cornerRadius, yp );
				this.graphics.curveTo ( xp + w, yp, xp + w, yp + this._cornerRadius );
				this.graphics.lineTo ( xp + w, yp + h - this._cornerRadius );
				this.graphics.curveTo ( xp + w, yp + h, xp + w - this._cornerRadius, yp + h );
				
				//hook
				this.graphics.lineTo ( xp + this._hookOffSet + this._hookSize, yp + h );
				this.graphics.lineTo ( xp + this._hookOffSet , yp + h + this._hookSize );
				this.graphics.lineTo ( xp + this._hookOffSet - this._hookSize, yp + h );
				this.graphics.lineTo ( xp + this._cornerRadius, yp + h );
				
				this.graphics.curveTo ( xp, yp + h, xp, yp + h - this._cornerRadius );
				this.graphics.lineTo ( xp, yp + this._cornerRadius );
				this.graphics.curveTo ( xp, yp, xp + this._cornerRadius, yp );
				this.graphics.endFill();
			}else{
				this.graphics.drawRoundRect( 0, 0, this._defaultWidth, h, this._cornerRadius );
			}
		}
		
		private function animate( show:Boolean ):void {
			var end:int = show == true ? 1 : 0;
			// added : DR : 04.29.2010
			if(_tween){
				_tween.kill();
			}
//			if( _tween != null && _tween. ) {
//				_tween.stop();
//			}
			
			// end add
			//更改此处
			_tween=TweenMax.to(this,1,{alpha:1});
//		    _tween = new Tween( this, "alpha", Strong.easeOut, this.alpha, end, .5, true );
			
//			if( ! show ){
//				_tween.addEventListener( TweenEvent.MOTION_FINISH, onComplete );
//				_timer.reset();
//			}
			if(!show){
				_tween.addEventListener(TweenEvent.COMPLETE,this.onComplete);
				_timer.reset();
			}
		}
		
		private function onComplete(event:Event=null):void {
			_tween.removeEventListener(TweenEvent.COMPLETE,this.onComplete);
//			event.currentTarget.removeEventListener(event.type, arguments.callee);
			this.cleanUp();
		}
		
		public function set buffer( value:Number ):void {
			this._buffer = value;
		}
		
		public function get buffer():Number {
			return this._buffer;
		}
		
		public function set bgAlpha( value:Number ):void {
			this._bgAlpha = value;
		}
		
		public function get bgAlpha():Number {
			return this._bgAlpha;
		}
		
		public function set tipWidth( value:Number ):void {
			this._defaultWidth = value;
		}
		
		public function set titleFormat( tf:TextFormat ):void {
			this._titleFormat = tf;
			if( this._titleFormat.font == null ){
				this._titleFormat.font = "_sans";
			}
			this._titleOverride = true;
		}
		
		public function set contentFormat( tf:TextFormat ):void {
			this._contentFormat = tf;
			if( this._contentFormat.font == null ){
				this._contentFormat.font = "_sans";
			}
			this._contentOverride = true;
		}
		
		public function set stylesheet( ts:StyleSheet ):void {
			this._stylesheet = ts;
			this._styleOverride = true;
		}
		
		public function set align( value:String ):void {
			var a:String = value.toLowerCase();
			var values:String = "right left center";
			if( values.indexOf( value ) == -1 ){
				throw new Error( this + " : Invalid Align Property, options are: 'right', 'left' & 'center'" );
			}else{
				this._align = a;
			}
		}
		
		public function set delay( value:Number ):void {
			this._delay = value;
			this._timer.delay = value;
		}
		
		public function set hook( value:Boolean ):void {
			this._hookEnabled = value;
		}
		
		public function set hookSize( value:Number ):void {
			this._hookSize = value;
		}
		
		public function set cornerRadius( value:Number ):void {
			this._cornerRadius = value;
		}
		
		public function set colors( colArray:Array ):void {
			this._bgColors = colArray;
		}
		
		public function set autoSize( value:Boolean ):void {
			this._autoSize = value;
		}
		
		public function set border( value:Number ):void {
			this._border = value;
		}
		
		public function set borderSize( value:Number ):void {
			this._borderSize = value;
		}
		
		public function set tipHeight( value:Number ):void {
			this._defaultHeight = value;
		}

		public function set titleEmbed( value:Boolean ):void {
			this._titleEmbed = value;
		}
		
		public function set contentEmbed( value:Boolean ):void {
			this._contentEmbed = value;
		}
		
		private function textGlow( field:TextField ):void {
			var color:Number = 0x000000;
            var alpha:Number = 0.35;
            var blurX:Number = 2;
            var blurY:Number = 2;
            var strength:Number = 1;
            var inner:Boolean = false;
            var knockout:Boolean = false;
            var quality:Number = BitmapFilterQuality.HIGH;

           var filter:GlowFilter = new GlowFilter(color,
                                  alpha,
                                  blurX,
                                  blurY,
                                  strength,
                                  quality,
                                  inner,
                                  knockout);
            var myFilters:Array = new Array();
            myFilters.push(filter);
        	field.filters = myFilters;
		}
		
		private function bgGlow():void {
			var color:Number = 0x000000;
            var alpha:Number = 0.20;
            var blurX:Number = 5;
            var blurY:Number = 5;
            var strength:Number = 1;
            var inner:Boolean = false;
            var knockout:Boolean = false;
            var quality:Number = BitmapFilterQuality.HIGH;

           var filter:GlowFilter = new GlowFilter(color,
                                  alpha,
                                  blurX,
                                  blurY,
                                  strength,
                                  quality,
                                  inner,
                                  knockout);
            var myFilters:Array = new Array();
            myFilters.push(filter);
            filters = myFilters;
		}
		
		private function initTitleFormat():void {
			_titleFormat = new TextFormat();
			_titleFormat.bold = false;
			_titleFormat.font=FontType.DEVICE;
			_titleFormat.size = 12;
			_titleFormat.color = 0xeeeeee;
		}
		
		private function initContentFormat():void {
			_contentFormat = new TextFormat();
			_contentFormat.bold = false;
			_contentFormat.font=FontType.DEVICE;
			_contentFormat.size = 8;
			_contentFormat.color = 0xeeeeee;
		}
		
		private function addedToStage( displayObject:DisplayObject ):Boolean {
			var hasStage:Stage = displayObject.stage;
			return hasStage == null ? false : true;
		}
		
		private function setOffset():void {
			switch( this._align ){
				case "left":
					this._offSet = - _defaultWidth +  ( _buffer * 3 ) + this._hookSize; 
					this._hookOffSet = this._defaultWidth - ( _buffer * 3 ) - this._hookSize; 
				break;
				
				case "right":
					this._offSet = 0 - ( _buffer * 3 ) - this._hookSize;
					this._hookOffSet =  _buffer * 3 + this._hookSize;
				break;
				
				case "center":
					this._offSet = - ( _defaultWidth / 2 );
					this._hookOffSet =  ( _defaultWidth / 2 );
				break;
				
				default:
					this._offSet = - ( _defaultWidth / 2 );
					this._hookOffSet =  ( _defaultWidth / 2 );;
				break;
			}
		}
		
		private function cleanUp():void {
			this._parentObject.removeEventListener( MouseEvent.MOUSE_OUT, this.onMouseOut );
			//this._parentObject.removeEventListener( MouseEvent.MOUSE_MOVE, this.onMouseMovement );
			this.follow( false );
			if(this._tf){
				this._tf.filters = [];
				if(this._contentContainer.contains(_tf)){
					this._contentContainer.removeChild(this._tf );
				}
			}
			if( this._cf){
				this._cf.filters = []
				if(this._contentContainer.contains(_cf)){
					this._contentContainer.removeChild(this._cf );
				}
			}
			this.filters = [];
			this._tf = null;
			this.graphics.clear();
			
			if(this.contains(this._contentContainer)){
				this.removeChild(this._contentContainer );
			}
			if(this.parent&&this.parent.contains(this)){
				parent.removeChild( this );
			}
		}
	}
}
