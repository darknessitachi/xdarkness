package com.nano.core{
	import com.nano.core.event.DDEvent;
	import com.nano.core.event.SelectionEvent;
	
	import flash.display.DisplayObject;
	import flash.display.Sprite;
	import flash.events.MouseEvent;
	import flash.geom.Matrix;

	/**
	 * JComponent是NanoSystem所有可视组件的基类，NanoSystem中所有纯自定义的显示组件都必须继承此类。
	 * NanoSystem一共有3种类型的可视组件：
	 * 1、可以使用鼠标动态绘制的组件；
	 *    这种类型的组件一般继承JWidget类，JWidget类使用Sprite的Graphics对象提供的绘图API进行动态绘图操作，并进行必要的坐标系转换处理。
	 * 	    这种类型的组件一般位于widgets包中。
	 * 2、用Flash制作并导出到SWC中的组件；
	 * 	    这种类型的组件一般是JFlComponent的子类，JFlComponent提供一个包装器组件包装导出的flash元件，以便在Flex中方便地使用它们。
	 *    这种类型的组件一般位于flcomp包中。
	 * 3、直接从Flex组件扩展而来的组件。
	 *    例如扩展自Canvas的自定义组件，它们由Flex自己的渲染和布局机制进行管理，NanoSystem不会对它们进行任何显示层的管理。
	 *    这种类型的组件一般位于flexcomp包中，或者位于mxml包下对应模块的子包中。
	 *    由于继承Flex的组件既可以使用纯代码的方式进行，也可以使用mxml标签进行继承。因此，分包约定为：使用代码进行扩展的组件位于flexcomp包中，
	 * 	    使用标签进行扩展的组件位于mxml包下的对应子包中。
	 * @since	2010-05-18
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 */
	public class JComponent extends Sprite implements Observable{
		include "__observable.as";
		/**
		 * id:每一个组件的实例都具有全局唯一的id作为标识
		 * 此id由全局工具类NanoSystem自动生成
		 * @see #NanoSystem.id()
		 */
		public var id:String = NanoSystem.id();
		/**
		 * 组件的UI界面
		 */
		protected var compUI:DisplayObject;
		/**
		 * 组件绑定的数据，可以是任意对象
		 * 注意：此属性标记为public是为了能使用反射机制获取到此属性
		 * 如果代码直接对此属性赋值，将不会触发change事件。
		 * 一般情况下代码不应该直接对此属性赋值，而是使用setDataObj方法进行。
		 */
		public var cdata:*={};
		/**
		 * 组件当前是否可编辑标志
		 */ 
		public var isEditAble:Boolean=true;
		/**
		 * 组件是否处于选中状态
		 */
		public var isSelected:Boolean=false;
		/**
		 * 是否可拖拽
		 */
		public var isDragAble:Boolean = true;
		/**
		 * 组件在父容器中的叠放次序，
		 * 在保存数据时，XML解析器会根据组件在显示列表中的位置自动计算该值，
		 * 手工修改此属性不会产生任何效果，
		 * 此属性为系统内部使用的参数，修改此属性的名称可能会引起历史版本的XML文件无法解析
		 */ 
		public var _zIndex:int=0;
		
		/**
		 * 构造方法
		 */
		public function JComponent() {
			//全局唯一id
			this.cdata.id=this.id;
			//允许组件双击	
			this.doubleClickEnabled = true;
			
			this.addEvents(["moving","resize","b4remove","remove","afterlayout",
							"beforerender","afterrender","b4destory","destory",
							"show","hidden","select","deselect",
							"__dbclick","datachanged"]);
			
			SysEventMgr.on(this,MouseEvent.DOUBLE_CLICK, __dbClick);
			SysEventMgr.on(this,SelectionEvent.SELECT,onSelect);
			SysEventMgr.on(this,SelectionEvent.DESELECT,onDeSelect);
			SysEventMgr.on(this,DDEvent.ON_DRAGGING, onDragging);
			SysEventMgr.on(this,DDEvent.END_DRAG, dragEnd);
			SysEventMgr.on(this,SelectionEvent.REMOVE, onRemove);
		}
		
		protected function clickHandler(e:MouseEvent):void{
			this.fireEvent("click");
		}
		protected function mouseOverHandler(e:MouseEvent):void{
			this.fireEvent("mouseOver");
		}
		protected function mouseMoveHandler(e:MouseEvent):void{
			this.fireEvent("mouseMove");
		}
		protected function mouseOutHandler(e:MouseEvent):void{
			this.fireEvent("mouseOut");
		}
		
		protected function onRemove(...args):void{
			this.fireEvent("b4remove");
			this.purgeListeners();
			SysEventMgr.un(this,MouseEvent.CLICK,clickHandler);
			SysEventMgr.un(this,MouseEvent.MOUSE_MOVE,mouseMoveHandler);
			SysEventMgr.un(this,MouseEvent.MOUSE_OUT,mouseOutHandler);
			SysEventMgr.un(this,MouseEvent.MOUSE_OVER,mouseOverHandler);
			SysEventMgr.un(this,MouseEvent.DOUBLE_CLICK, __dbClick);
			SysEventMgr.un(this,SelectionEvent.SELECT,onSelect);
			SysEventMgr.un(this,SelectionEvent.DESELECT,onDeSelect);
			SysEventMgr.un(this,DDEvent.ON_DRAGGING, onDragging);
			SysEventMgr.un(this,DDEvent.END_DRAG, dragEnd);
			if(this.parent&&this.parent.contains(this)){
				this.parent.removeChild(this);
			}
		}
		
		/**
		 * 转发对象上的鼠标双击事件
		 * @param	e
		 */
		protected function __dbClick(e:MouseEvent):void {
			this.fireEvent("__dbclick",this);
		}
		
		/**
		 * 设置组件的x/y坐标
		 * 此方法通过直接设置组件的x/y坐标来改变组件的位置
		 * 同时更新创建在组件上的连接线
		 * @param	newX
		 * @param	newY
		 */
		public function setPosition(newX:Number, newY:Number):void {
			this.x = newX;
			this.y = newY;
		}
		
		override public function set x(newX:Number):void {
			super.x = newX;
			this.fireEvent("moving");
		}
		
		override public function set y(newY:Number):void {
			super.y = newY;
			this.fireEvent("moving");
		}
		
		/**
		 * 更新位置
		 * 此方法调用对象的变形矩阵进行位置设置
		 * 同时更新创建在组件上的连接线
		 * @param	tx
		 * @param	ty
		 */
		public function updatePosition(tx:Number,ty:Number):void {
			var thisMatrix:Matrix = this.transform.matrix.clone();
			thisMatrix.tx += tx;
			thisMatrix.ty += ty;
			this.transform.matrix = thisMatrix;
			this.fireEvent("moving");
		}
		
		/**
		 * 显示选中状态的矩形边框
		 */
		public function onSelect(e:SelectionEvent):void {
			this._drawSelRect();
			this.isSelected=true;
			SysDepthManager.bringToTop(this);
			this.fireEvent("select",this);
		}

		/**
		 * 非选中状态
		 */ 
		public function onDeSelect(e:SelectionEvent):void{
			this.graphics.clear();
			this.isSelected=false;
			this.fireEvent("deselect",this);
		}
		
		/**
		 * 获得子节点排列好后的最大y坐标
		 */ 
		protected function getChildrenY():Number{
			var result:Number=0;
			var len:int=this.numChildren;
			for(var i:int=0;i<len;i++){
				var ch:DisplayObject=this.getChildAt(i);
				result+=ch.height;
			}
			return result;
		}
		
		/**
		 * 获得子节点排列好后的最大x坐标
		 */ 
		protected function getChildrenX():Number{
			var result:Number=0;
			var len:int=this.numChildren;
			for(var i:int=0;i<len;i++){
				var ch:DisplayObject=this.getChildAt(i);
				result+=ch.width;
			}
			return result;
		}
		
		/**
		 * 获得子节点中最大的x坐标
		 */ 
		protected function getMaxX():Number{
			var result:Number=0;
			var len:int=this.numChildren;
			for(var i:int=0;i<len;i++){
				var ch:DisplayObject=this.getChildAt(i);
				if(ch.x>result){
					result=ch.x;
				}
			}
			return result;
		}
		
		/**
		 * 获得子节点中最大的y坐标
		 */ 
		protected function getMaxY():Number{
			var result:Number=0;
			var len:int=this.numChildren;
			for(var i:int=0;i<len;i++){
				var ch:DisplayObject=this.getChildAt(i);
				if(ch.y>result){
					result=ch.y;
				}
			}
			return result;
		}
				
		/**
		 * 拖拽中，对象将跟随鼠标的位置变化
		 * 所有拖拽源都必须实现此方法
		 * 并监听拖拽事件
		 * @param	e
		 */
		public function onDragging(e:DDEvent):void {
			this.updatePosition(e.deltaX,e.deltaY);
		}
		
		/**
		 * 组件被拖拽结束事件处理器
		 * @param	e
		 */
		public function dragEnd(e:DDEvent):void{
			this.fireEvent("moveend");
		}
		
		/**
		 * 绘制选中状态矩形框
		 */
		protected function _drawSelRect():void{
			this.graphics.clear();
			this.graphics.lineStyle(1,0x7BA1D2,1);
			this.graphics.beginFill(0x7BA1D2,0.6);
			this.graphics.drawRect(0,0,this.width/this.scaleX,this.height/this.scaleY);
			this.graphics.endFill();
		}
		
		/**
		 * 为对象添加滤镜
		 * @param	type
		 */
		public function addBitmapFilter(type:String):void {
//			var filter:BitmapFilter= JFilterFactory.createFilter(type);
//			NanoSystem.addFilter(this, filter);
		}
		
		/**
		 * 克隆，子类可以覆盖此方法提供特定的克隆操作
		 * @param	...args
		 * @return
		 */
		public function clone(...args):JComponent{
			return new JComponent();
		}
		
		/**
		 * 使用其它组件替换当前对象
		 * @param	comp
		 */
		public function replaceWith(comp:JComponent):void{
			if (comp&&this.parent) {
				var zIndex:int = this.parent.getChildIndex(this);
				this.parent.removeChild(this);
				this.parent.addChildAt(this,zIndex);
			}
		}
		
		public function getId():String{
			return this.id;
		}
		
		public function getBeginX():Number{
			return this.x;
		}

		public function getBeginY():Number{
			return this.y;
		}

		public function getEndX():Number{
			return this.x+this.width;
		}

		public function getEndY():Number{
			return this.y+this.height;
		}
		
		public function canEdit():Boolean {
			return this.isEditAble;
		}
		
		public function setEditAble(b:Boolean):void{
			this.isEditAble=true;
		}
		
		public function getSize():*{
			return {width:this.width,height:this.height}
		}
		
		public function isVisible():Boolean{
			return this.visible;
		}
		
		public function setVisible(b:Boolean):void{
			if(this.isVisible()!=b){
				this.visible=b;
				if(this.visible){
					this.fireEvent("show");
				}else{
					this.fireEvent("hidden");
				}
			}
		}
		
		public function setWH(obj:*):void{
			this.width=obj.width;
			this.height=obj.height;
			this.fireEvent("resize");
		}
		
		public function getWH():*{
			return {width:this.width,height:this.height};
		}
		
		public function setXY(obj:*):void {
			setPosition(obj.x,obj.y);
		}
		
		public function getXY():*{
			return {x:this.x,y:this.y};
		}
		
		public function setCompUI(obj:DisplayObject):void{
			this.compUI=obj;
			this.addChild(obj);
		}
		
		public function setDataObj(data:*):void{
			data.id=this.id;
			this.cdata=data;
			this.fireEvent("datachanged");
		}
		
		public function getDataObj():*{
			return this.cdata;
		}
		
		public function set transformMatrix(matrix:Matrix):void{
			this.transform.matrix=matrix;
			this.fireEvent("moving");//暂用moving事件替代
		}
	}
}