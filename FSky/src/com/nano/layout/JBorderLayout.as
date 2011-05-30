package com.nano.layout {
	import com.nano.math.geom.JRectUtil;
	
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	

	/**
	 * Border布局
	 * @version  1.0
	 * @author   章小飞 zhangxf5@asiainfo-linkage.com
	 * @since    v1.0
	 */
	public class JBorderLayout extends JEmptyLayout{
		public static const CENTER:int=0;
		public static const WEST:int=1;
		public static const EAST:int=2;
		public static const NORTH:int=3;
		public static const SOUTH:int=4;
		private var _center:DisplayObject;
		private var _west:DisplayObject;
		private var _east:DisplayObject;
		private var _north:DisplayObject;
		private var _south:DisplayObject;
		private var hgap:Number=0;
		private var vgap:Number=0;
		
		public function JBorderLayout(hgap:Number=0,vgap:Number=0){
			this.hgap=hgap;
			this.vgap=vgap;
		}
		
		override public function layoutContainer():void{
			var tempV:*=null;
			if(!this._center){
				throw new Error("JBorderLayout必须有位于CENTER的元素。");
			}else{
				this.owner.addChild(this._center);
			}
			if(this._north){
				this.owner.addChild(this._north);
				this._center.y=JRectUtil.getBoundPoint(this._north).b.y+this.vgap;
			}
			if(this._south){
				this.owner.addChild(this._south);
				this._south.y=JRectUtil.getBoundPoint(this._center).b.y+this.vgap;
			}
			if(this._west){
				this.owner.addChild(this._west);
				this._west.y=this._center.y;
				tempV=JRectUtil.getBoundPoint(this._west);
				this._center.x=tempV.r.x+this.hgap;
				if(this._south&&this._south.y<tempV.b.y){
					this._south.y=tempV.b.y+this.vgap;
				}
			}
			if(this._east){
				this.owner.addChild(this._east);
				this._east.y=this._center.y;
				this._east.x=JRectUtil.getBoundPoint(this._center).r.x+this.hgap;
				tempV=JRectUtil.getBoundPoint(this._east);
				if(this._south&&this._south.y<tempV.b.y){
					this._south.y=tempV.b.y+this.vgap;
				}
			}
		}
		
		override public function addComp(comp:DisplayObject, constrains:*=null):void{
			switch(constrains){
				case JBorderLayout.CENTER:
					this._center=comp;
					break;
				case JBorderLayout.WEST:
					this._west=comp;
					break;
				case JBorderLayout.EAST:
					this._east=comp;
					break;
				case JBorderLayout.NORTH:
					this._north=comp;
					break;
				case JBorderLayout.SOUTH:
					this._south=comp;
					break;
				default:
					break;
			}
		}
		
		override public function removeComp(comp:DisplayObject, constrains:*=null):void{
			if(!constrains){
				return;
			}else if(constrains==JBorderLayout.CENTER){
				throw new Error("JBorderLayout布局必须有CENTER部分的组件，因此不能删除此位置的子节点。");
			}
			this.owner.removeChild(comp);
			switch(constrains){
				case JBorderLayout.CENTER:
					this._center=null;
					break;
				case JBorderLayout.WEST:
					this._west=null;
					break;
				case JBorderLayout.EAST:
					this._east=null;
					break;
				case JBorderLayout.NORTH:
					this._north=null;
					break;
				case JBorderLayout.SOUTH:
					this._south=null;
					break;
				default:
					break;
			}
		}
	}
}