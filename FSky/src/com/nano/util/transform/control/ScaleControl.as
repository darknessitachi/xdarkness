package com.nano.util.transform.control {
	import com.nano.util.transform.JTransformTool;
	
	/**
	 * 缩放控制手柄
	 * 
	 */
	public class ScaleControl extends TransformToolControl {
		public function ScaleControl(){
			
		}
		
		override protected function draw():void{
			var tempP:Number=JTransformTool.TOOL_SIZE/2;
			this.graphics.clear();
			this.graphics.lineStyle(2,0x000000,1);
			this.graphics.beginFill(0x0000ff,1);
			this.graphics.drawRect(-tempP,-tempP,JTransformTool.TOOL_SIZE,JTransformTool.TOOL_SIZE);
			this.graphics.endFill();
		}
	}
}