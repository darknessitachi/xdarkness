package com.nano.flexcomp{
	import spark.components.Group;
	import spark.components.Label;
	/**
	 * 第三方组件：模拟FieldSet
	 */ 
	public class JFieldSet extends Group{
		public function JFieldSet(){
			super();
		}
		
		private var vgtitle:Label;		// 标题控件
		private var _title:String;		// 设置标题变量
		private var _pad:int;			// 设置自定义容器中添加的子控件与边框的距离，上边距*2
		private var _pos:int;			// 设置标题相对容器的 x 坐标
		
		
		override protected function createChildren():void {
			super.createChildren();
			
			if (!vgtitle) {
				vgtitle = new Label();
				vgtitle.text = title;
				
				addElement(vgtitle);
			}
		}
		
		override protected function measure():void {
			super.measure();
			
			pad = pad <= 0 ? 10 : pad;
			// 增加容器的大小
			measuredWidth += pad * 2;
			measuredHeight += pad;
			
			// 设置子控件距离边框的距离
//			paddingLeft = pad;
//			paddingTop = pad * 2;
//			paddingRight = pad;
//			paddingBottom = pad;
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
			// 清除自定义边框重画
			graphics.clear();
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			
			// 移动标题至目标位置
			pos = pos <= 0 ? 18 : pos;
			vgtitle.move(pos, 0);
			
			//根据标题位置画出边框位置
			graphics.lineStyle(1, 0x000000, 1.0);
			graphics.moveTo(vgtitle.x - 2, vgtitle.height/2);
			graphics.lineTo(0, vgtitle.height/2);
			graphics.lineTo(0, unscaledHeight);
			graphics.lineTo(unscaledWidth, unscaledHeight);
			graphics.lineTo(unscaledWidth, vgtitle.height/2);
			graphics.lineTo(vgtitle.x + vgtitle.width + 2, vgtitle.height/2);
			
		}
		
		public function get title():String {
			return _title;
		}
		
		public function set title(value:String):void {
			_title = value;
		}
		
		public function get pad():int {
			return _pad;
		}
		
		public function set pad(value:int):void {
			_pad = value;
		}
		
		public function get pos():int {
			return _pos;
		}
		
		public function set pos(value:int):void {
			_pos = value;
		}
	}
}