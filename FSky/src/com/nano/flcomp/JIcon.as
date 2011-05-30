package com.nano.flcomp {
	import com.nano.core.NanoSystem;
	import com.nano.dd.JDragSource;
	
	import flash.display.Bitmap;
	import flash.display.Sprite;
	import flash.text.TextFormat;

	/**
	 * 图标式组件
	 * 上面一个图标，可以是任意可视对象：图片、flash、new Sprite()等。
	 * 下面一个文本标签，表示组件名称
	 */
	public class JIcon extends JFlComponent implements JDragSource{
		
		protected var borderWidth:Number=1;
		//protected var borderColor:Number=0x86d7fd;
		protected var borderColor:Number=0xffffff;
		protected var borderTrans:Number=0.8;
		//protected var fillColor:Number=0x83b8fa;
		protected var fillColor:Number=0xffffff;
		protected var fillTrans:Number=0.6;
		
		public function JIcon(config:*=null){
			if(!config){
				config={
					id:NanoSystem.id(),
					x:0,
					y:0,
					fl:new DefaultServiceTypeIcon(),
					name:'',
					tf:null
				}
			}
			super(config.x,config.y);
			
			//图标
			if(config.fl is Class){
				var bg:Bitmap=new config.fl() as Bitmap;
				this.content=bg;
			}else if(config.fl is Sprite){
				this.content=config.fl;
			}else{
				this.content=new DefaultServiceTypeIcon();
			}
			this.setCompUI(this.content);
			
			//文字
			this.addNameArea();
			if(config.tf is TextFormat){
				this.nameArea.setTextFormat(config.tf);
			}
			this.setModuleName(config.name||"");
			
			this.doLayout();
			
			if(config.id){
				this.id=config.id;
			}
			
			//注册事件监听器
			if(config&&config.listeners){
				for(var p:* in config.listeners){
					var listener:*=config.listeners[p];
					if(listener is Function){
						this.addListener(p+"",listener);
					}else if(listener is Object){
						var fn:Function=listener.fn as Function;
						this.addListener(p+"",fn);
					}
				}
			}
			this.setDataObj({});
		}
		
		/**
		 * 绘制选中状态矩形框
		 */
		override protected function _drawSelRect():void{
			this.graphics.clear();
			this.graphics.lineStyle(this.borderWidth,this.borderColor,this.borderTrans);
			this.graphics.beginFill(this.fillColor,this.fillTrans);
//			this.graphics.drawRoundRect(0,0,this.width/this.scaleX,this.height/this.scaleY,6,4);
			this.graphics.drawRect(0,0,this.width/this.scaleX,this.height/this.scaleY);
			this.graphics.endFill();
		}
	}
}