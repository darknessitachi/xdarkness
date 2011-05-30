package com.nano.util.transform {
	import com.nano.util.transform.control.ScaleControl;
	
	import flash.display.DisplayObject;
	import flash.display.Sprite;
	import flash.geom.Matrix;
	/**
	 * 变形工具
	 * <b>注意：该类尚未设计完成，请不要基于该包定义的类进行开发</b>
	 * @version 1.0
	 * @author  章小飞 zhangxf5@asiainfo-linkage.com
	 * @since   v1.3 2010-05-28
	 */
	public class JTransformTool extends Sprite{
		public var maintainControlForm:Boolean = true;
		public var moveUnderObjects:Boolean = true;
		
		public static const TOOL_SIZE:Number=20;
		
		private var _target:DisplayObject;
		private var toolSprites:Sprite=new Sprite();
		/**
		 * 缩放控制手柄
		 */ 
		private var scaleControls:Sprite=new Sprite();
		
		/**
		 * 构造方法
		 */ 
		public function JTransformTool(){
			this.createTools();
		}
		
		/**
		 * 设置新的变形对象
		 */ 
		public function set target(tar:DisplayObject):void{
			if(!tar){
				return;
			}
			this._target=tar;
			
			this.updateMatrix();
			this.updateToolsVisible();
		}
		
		/**
		 * 创建控制手柄
		 */ 
		private function createTools():void{
			var tlScale:ScaleControl=new ScaleControl();
			this.scaleControls.addChild(tlScale);
			
			this.toolSprites.addChild(this.scaleControls);
			this.updateToolsVisible();
		}
		
		/**
		 * 更新矩阵
		 */ 
		private function updateMatrix():void{
			var _matrix:Matrix=this._target.transform.matrix;
			this.transform.matrix=_matrix.clone();
			//trace(_matrix);
		}
		
		/**
		 * 更新手柄工具是否可见
		 */
		private function updateToolsVisible():void{
			var isChild:Boolean=this.contains(this.toolSprites);
			if(this._target){
				if(!isChild){
					this.addChild(this.toolSprites);
				}
			}else if(isChild){
				this.removeChild(this.toolSprites);
			}
		}
	}
}