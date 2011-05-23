package com.sinosoft.fsky.util
{
	import flash.events.Event;
	import flash.events.IOErrorEvent;
	import flash.events.ProgressEvent;
	import flash.text.TextFormatAlign;
	
	import flashx.textLayout.formats.TextAlign;
	
	import mx.controls.Image;
	import mx.controls.ProgressBar;
	import mx.controls.ProgressBarLabelPlacement;
	import mx.controls.ProgressBarMode;
	import mx.effects.Effect;
	
	import spark.components.Label;
	import spark.components.VGroup;
	import spark.effects.Fade;
	
	/**
	 * @description 图片加载器，带有加载进度显示和图片显示效果
	 * @create_date 2011-4-26
	 * @modify_date 2011-4-26
	 */
	public class ImageLoader extends Image
	{
	
		/**
		 * 加载进度条
		 */
		private var progressBar:ProgressBar;
		
		private var _showAnimation:Effect;
		[Bindable]
		public function get showAnimation():Effect{
			return _showAnimation;
		}
		public function set showAnimation(effect:Effect):void{
			_showAnimation = effect;
			_showAnimation.target = this;
		}
		
		public function ImageLoader()
		{
			super();
			this.addEventListener(Event.COMPLETE, loadCompleteHandler);
			this.addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
			
			var fadeShow:Fade = new Fade();
			fadeShow.alphaFrom = 0;
			fadeShow.alphaTo = 1;
			fadeShow.duration = 500;
			this.showAnimation = fadeShow;
		}
		
		public function ioErrorHandler(event:IOErrorEvent):void{
			trace(event.text);
			throw event;
		}
		
		public function loadCompleteHandler(event:Event):void{
			progressBar.visible = false;
			showAnimation.play();
		}
		
		override protected function createChildren():void{
			if ( progressBar == null ){
				progressBar = new ProgressBar();
				progressBar.includeInLayout = progressBar.visible;
				progressBar.mode = ProgressBarMode.EVENT;
				progressBar.label = "%3%%";
				progressBar.setStyle("textAlign", TextFormatAlign.CENTER);
				progressBar.labelPlacement = ProgressBarLabelPlacement.BOTTOM;
				progressBar.source = this;
			}
			
			this.addChild(progressBar);
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			
			progressBar.width = unscaledWidth;
			progressBar.y = (this.unscaledHeight / 2 - progressBar.height / 2);
			progressBar.x = (this.unscaledWidth / 2 - progressBar.width / 2);
			progressBar.setStyle("labelWidth", progressBar.width);
		}
		
		
	}
}