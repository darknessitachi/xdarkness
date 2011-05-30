package com.nano.widgets {
	import com.nano.layout.graphlayout.JGridLayout;
	
	import flash.display.DisplayObject;

	/**
	 * 桌面，指除去任务栏以外的区域
	 */
	public class JDeskTop extends JPage {
		public var hgap:Number=10;
		public var vgap:Number=10;
		public var layout:JGridLayout=null;
		/**
		 * 构造方法
		 * @param	x
		 * @param	y
		 */
		public function JDeskTop(x:Number=0,y:Number=0){
			super(x,y);
			this.strokeColor=0x0000ff;
			this.strokeTrans=0;
			this.strokeW=1;
			this.fillColor=0x0000ff;
			this.fillTrans=0;
			this.isDragAble=false;	//桌面不可以被“拖拽”
			
			layout=new JGridLayout({
				startX:10,
				startY:10,
				GRID_WIDTH:90,
				GRID_HEIGHT:110
			});
			this.layout.addRule=1;
			this.layout.halign=0;
			this.layout.valign=0;
		}

		/**
		 * 强制进行布局操作，
		 * 注意：在动态添加或者删除子对象之后必须调用doLayout方法进行布局
		 * 所有通过append方法添加进来的子对象才有效
		 * 直接通过原生的addChild方法添加的组件不受布局管理器管理
		 */
		public function doLayout():void{
			this.layout.layoutWidth=this.getEndX();
			this.layout.layoutHeight=this.getEndY();
			this.layout.doLayout({animation:true});
		}
		
		/**
		 * 添加子对象
		 * @param	comp
		 * @param	constrains
		 */
		public function append(comp:DisplayObject,constrains:*=null):void{
			this.layout.addToLayout(comp);
		}
		
		/**
		 * TODO:从布局管理器中删除对象
		 * @param	comp
		 * @param	constrains
		 */
		public function removeComp(comp:DisplayObject,constrains:*=null):void{
		}
	}
}