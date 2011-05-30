package com.nano.dd {
	import com.nano.core.SysEventMgr;
	import com.nano.core.event.DDEvent;
	import com.nano.core.event.SelectionEvent;
	import com.nano.data.JHashMap;
	import com.nano.widgets.JPage;
	import com.nano.widgets.JPen;
	import com.nano.widgets.JRect;
	import com.nano.core.JEditAble;
	import com.nano.widgets.link.JLink;
	
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	
	import mx.collections.ArrayCollection;

	/**
	 * 组件拖放管理器，
	 * 提供拖拽、落下操作的一系列工具方法
	 * @version	1.3 2010-05-18
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */
	public class DDMgr {
		/**
		 * 舞台对象，为了能监测到所有的鼠标和键盘事件，DDMgr将把事件监听器注册到此对象上
		 */
		private var evtRoot:DisplayObjectContainer = null;
		/**
		 * 所属的画笔
		 */
		private var pen:JPen;
		/**
		 * 被拖拽的对象
		 */
		public var dragSrcs:JHashMap = new JHashMap();
		/**
		 * 可以作为落下区域的对象
		 */
		public var dropZones:JHashMap = new JHashMap();
		/**
		 * 当前选中的对象
		 */
		public var selectedObjs:JHashMap = new JHashMap();
		/**
		 * 记录被选中对象的初始位置
		 */
		public var selObjWithStartLocs:JHashMap = new JHashMap();
		/**
		 *标志：拖拽管理器是否已经初始化
		 */
		public var isReady:Boolean = false;
		/**
		 * 记录鼠标上一次的位置
		 */
		public var mouseLastLoc:Point = new Point();
		/**
		 * 记录鼠标当前的位置
		 */
		public var mouseCurrLoc:Point = new Point();
		/**
		 * 鼠标移动量
		 */
		public var deltaLoc:Point = new Point();
		/**
		 * 标志，拖拽源
		 */
		public static const DRAG_SRC:String = "DragSource";
		/**
		 * 标志，落下区域
		 */
		public static const DROP_ZONE:String = "DropZone";
		/**
		 * 进行多选用的矩形拉框
		 */
		private var multiSelRect:JRect = null;

		/**
		 * 构造方法
		 */
		public function DDMgr(pen:JPen){
			this.pen = pen;
			this.evtRoot = pen.currPage;
			this.isReady = true;
			multiSelRect=new JRect();
			multiSelRect.setStrokeStyle(1,0xffffff,1);
			multiSelRect.setFillStyle(0xffffff,0.5);
		}

		/**
		 * 向拖拽管理器注册，
		 * 所有拖动、落下区域的对象都必须使用此方法向拖拽管理器进行注册，
		 * 以便完成拖拽操作，
		 * 没有使用该方法进行注册的对象均不能进行拖拽操作
		 * @param	obj
		 * @param	type
		 */
		public function reg(obj:Object, type:String):void {
			if (type == DDMgr.DRAG_SRC){
				if (!this.dragSrcs.containsValue(obj)){
					this.dragSrcs.put(obj.id, obj);
				}
			} else if (type == DDMgr.DROP_ZONE){
				if (!this.dropZones.containsValue(obj)){
					this.dropZones.put(obj.id, obj);
				}
			}
		}

		/**
		 * 反注册，
		 * 对象从拖拽管理器中反注册后，拖拽管理器不再向对象发送拖拽事件，
		 * 对象不再能拖拽
		 * @param	id
		 */
		public function unReg(id:String):void {
			this.selectedObjs.remove(id);
			this.dragSrcs.remove(id);
			this.dropZones.remove(id);
			this.selObjWithStartLocs.remove(id);
		}

		/**
		 * 清理所有对象
		 */
		public function clear():void {
			this.dragSrcs.clear();
			this.dropZones.clear();
			this.selectedObjs.clear();
			this.selObjWithStartLocs.clear();
		}

		/**
		 * 开始交互
		 * 编辑框将监听其容器上的鼠标按下事件
		 */
		public function startInteraction():void {
			SysEventMgr.on(evtRoot, MouseEvent.MOUSE_DOWN, __mouseDown);
		}

		/**
		 * 结束交互
		 */
		public function stopInteraction():void {
			SysEventMgr.un(evtRoot, MouseEvent.MOUSE_DOWN, __mouseDown);
			SysEventMgr.un(evtRoot, MouseEvent.MOUSE_MOVE, __ddMouseMove);
			SysEventMgr.un(evtRoot, MouseEvent.MOUSE_UP, __ddMouseUp);
		}

		/**
		 * 鼠标按下事件处理
		 * @param	e
		 */
		private function __mouseDown(e:MouseEvent):void {
			this.mouseLastLoc = new Point(e.stageX, e.stageY);
			trace("选中>" + e.target + "-->" + e.currentTarget);
			
			//向所有连接线发送反选事件
			this.pen.objs.eachValue(function(item:*):void {
				if (item is JLink){
					SysEventMgr.sendMsg(new SelectionEvent(SelectionEvent.DESELECT), item);
				}
			});

			if (e.target is JPage){ //背景页面
				//向所有选中的对象发送反选事件
				SysEventMgr.sendMsg(new SelectionEvent(SelectionEvent.DESELECT), this.selectedObjs);
				this.selectedObjs.clear();
				this.selObjWithStartLocs.clear();

				SysEventMgr.un(evtRoot, MouseEvent.MOUSE_MOVE, __ddMouseMove);
				SysEventMgr.un(evtRoot, MouseEvent.MOUSE_UP, __ddMouseUp);
				
				SysEventMgr.un(this.pen.stage, MouseEvent.MOUSE_MOVE, _bgDragMouseMove);
				SysEventMgr.un(this.pen.stage, MouseEvent.MOUSE_UP, _bgDragMouseUp);
				
				SysEventMgr.on(this.pen.stage, MouseEvent.MOUSE_MOVE, _bgDragMouseMove);
				SysEventMgr.on(this.pen.stage, MouseEvent.MOUSE_UP, _bgDragMouseUp);
				
				if(this.pen.currPage&&this.pen.currPage.multiSelect){
					var p:Point = new Point(e.stageX, e.stageY);
					this.multiSelRect.setBeginPoint(p.x,p.y);
					this.multiSelRect.setEndPoint(0,0);
					SysEventMgr.on(this.pen.stage,MouseEvent.MOUSE_MOVE,__multiSelMoseMove);
					SysEventMgr.on(this.pen.stage,MouseEvent.MOUSE_UP,__multiSelMoseUp);
				}
			} else if (e.target is JLink){ //选中连接线
				this.rmAllFromSelected();
				this.selectedObjs.put(e.target.id, e.target);
				SysEventMgr.sendMsg(new SelectionEvent(SelectionEvent.SELECT), e.target);
			} else if (e.target is JEditAble){ //选中可编辑的对象
				if(!(e.target.isDragAble)){	   //是否可拖拽
					return;
				}
				
				//如果被点击的对象已经处于选中状态
				if (this.selectedObjs.containsKey(e.target.id)){
					SysEventMgr.on(this.evtRoot, MouseEvent.MOUSE_MOVE, __ddMouseMove);
					SysEventMgr.on(this.evtRoot, MouseEvent.MOUSE_UP, __ddMouseUp);
					return;
				}

				//按住Ctrl键进行复选
				if (!e.ctrlKey){
					SysEventMgr.sendMsg(new SelectionEvent(SelectionEvent.DESELECT), this.dragSrcs);
					this.selectedObjs.clear();
					this.selObjWithStartLocs.clear();
				}

				//将对象添加到选中列表中
				addToSelected(e.target);
				if (this.selectedObjs.size() > 0){
					SysEventMgr.on(evtRoot, MouseEvent.MOUSE_MOVE, __ddMouseMove);
					SysEventMgr.on(evtRoot, MouseEvent.MOUSE_UP, __ddMouseUp);
				}
			}
		}

		/**
		 * 背景拖拽状态下鼠标移动事件监听
		 * @param	e
		 */
		private function _bgDragMouseMove(e:MouseEvent):void {
			mouseCurrLoc = new Point(e.stageX, e.stageY);
			deltaLoc = mouseCurrLoc.subtract(this.mouseLastLoc);
			if (this.pen.currPage&&this.pen.currPage.isDragAble){
				this.pen.currPage.x += deltaLoc.x;
				this.pen.currPage.y += deltaLoc.y;
			}
			mouseLastLoc = mouseCurrLoc.clone();
		}

		/**
		 * 背景拖拽状态下鼠标弹起事件监听
		 * @param	e
		 */
		private function _bgDragMouseUp(e:MouseEvent):void {
			SysEventMgr.un(this.pen.stage, MouseEvent.MOUSE_MOVE, _bgDragMouseMove);
			SysEventMgr.un(this.pen.stage, MouseEvent.MOUSE_UP, _bgDragMouseUp);
		}

		/**
		 * 对象拖拽状态下的鼠标移动事件监听
		 * @param	e
		 */
		private function __ddMouseMove(e:MouseEvent):void {
			//判断鼠标位置是否超出编辑区域
			var isOnEditor:Boolean = this.pen.enforceBorder(e.stageX, e.stageY);
			if (!isOnEditor){
				return;
			}
			//鼠标位置已经超出整个舞台(evtRoot)区域
			if (!evtRoot.stage.hitTestPoint(e.stageX, e.stageY)){
				return;
			}
			
			//根据鼠标移动量以及页面的缩放状态，生成拖拽事件
			mouseCurrLoc = new Point(e.stageX, e.stageY);
			deltaLoc = mouseCurrLoc.subtract(this.mouseLastLoc);
			mouseLastLoc = mouseCurrLoc.clone();

			var ddEvt:DDEvent = new DDEvent(DDEvent.ON_DRAGGING);
			ddEvt.deltaX = deltaLoc.x / this.pen.currPage.scaleX;
			ddEvt.deltaY = deltaLoc.y / this.pen.currPage.scaleY;
			ddEvt.targetObj = e.target;

			//向所有选中的对象发送拖拽事件
			SysEventMgr.sendMsg(ddEvt,this.selectedObjs);
			
//			if (!(e.target is JDropZone)){
//				var dropZone:DisplayObject = getDropTarget(e.target);
//				if (dropZone){
//					ddEvt = new DDEvent(DDEvent.DROP_OVER); //发送拖拽经过事件
//					ddEvt.deltaX = deltaLoc.x;
//					ddEvt.deltaY = deltaLoc.y;
//					ddEvt.targetObj = e.target;
//					SysEventMgr.sendMsg(ddEvt, dropZone);
//				}
//			}
		}

		/**
		 * 对象拖拽状态下的鼠标弹起事件监听
		 * @param	e
		 */
		private function __ddMouseUp(e:MouseEvent):void {
			var pointTemp:Point = new Point(e.stageX, e.stageY);
			var ddEvt:DDEvent = new DDEvent(DDEvent.END_DRAG);
			ddEvt.stageX = e.stageX;
			ddEvt.stageY = e.stageY;
			ddEvt.deltaX = deltaLoc.x;
			ddEvt.deltaY = deltaLoc.y;
			ddEvt.targetObj = e.target;
			//向所有被拖拽的对象发送拖拽结束事件
			SysEventMgr.sendMsg(ddEvt, this.selectedObjs);

			this.selectedObjs.eachValue(function(item:*):void {
					ddEvt = new DDEvent(DDEvent.SRC_DRAG_END);
					ddEvt.deltaX = deltaLoc.x;
					ddEvt.deltaY = deltaLoc.y;
					ddEvt.targetObj = item;
					SysEventMgr.sendMsg(ddEvt, e.target);

					//向所有落下区域发送拖拽源拖拽结束事件
					SysEventMgr.sendMsg(ddEvt, dropZones);

					//如果被拖拽的不是容器对象，向目标区域发送落下事件
					if (!(item is JDropZone)){
						var dropZone:DisplayObject = getDropTarget(item);
						if (dropZone){
							ddEvt = new DDEvent(DDEvent.DROP);
							ddEvt.stageX = e.stageX;
							ddEvt.stageY = e.stageY;
							ddEvt.deltaX = deltaLoc.x;
							ddEvt.deltaY = deltaLoc.y;
							ddEvt.targetObj = item;
							ddEvt.origPosition=selObjWithStartLocs.get(item.id);
							SysEventMgr.sendMsg(ddEvt, dropZone);
						}
					}
				});
			SysEventMgr.un(this.evtRoot, MouseEvent.MOUSE_MOVE, __ddMouseMove);
			SysEventMgr.un(this.evtRoot, MouseEvent.MOUSE_UP, __ddMouseUp);
		}

		/**
		 * 拉框式多选，鼠标移动事件监听
		 * 绘制选择框
		 * @param	e
		 */
		private function __multiSelMoseMove(e:MouseEvent):void {
			var p:Point = new Point(e.stageX, e.stageY);
			p = multiSelRect.globalToLocal(p);
			multiSelRect.setEndPoint(p.x, p.y);
			this.pen.stage.addChild(multiSelRect);
		}

		/**
		 * 拉框式多选，鼠标弹起事件监听
		 * @param	e
		 */
		private function __multiSelMoseUp(e:MouseEvent):void {
			//交叉检测
			this.pen.objs.eachValue(function(item:*):void {
				if (multiSelRect.hitTestObject(item)){
					addToSelected(item);
				}
			});
			
			if (this.pen.stage.contains(multiSelRect)){
				this.pen.stage.removeChild(multiSelRect);
			}
			
			SysEventMgr.un(this.pen.stage, MouseEvent.MOUSE_MOVE, __multiSelMoseMove);
			SysEventMgr.un(this.pen.stage, MouseEvent.MOUSE_UP, __multiSelMoseUp);
		}

		/**
		 * 将对象添加到选中对象列表中
		 * @param	item
		 */
		public function addToSelected(item:*):void {
			//向目标对象发送选中事件
			var selEvt:SelectionEvent = new SelectionEvent(SelectionEvent.SELECT);
			selEvt.targetObj = item;
			SysEventMgr.sendMsg(selEvt, item);

			if (!this.selectedObjs.containsValue(item)){
				this.selectedObjs.put(item.id, item);
			}
			if (!this.selObjWithStartLocs.containsKey(item.id)){
				this.selObjWithStartLocs.put(item.id, {origX: item.x, origY: item.y, item: item});
			}
			//组件容器和其中的成员不能同时处于选中状态
//			this.selectedObjs.eachValue(function(item:*):void {
//				if (item is JGroup){
//					CollectionUtil.each((item as JGroup).getMembers(), function(mem:*):void {
//						removFromSelected(mem);
//					})
//				}
//			});
		}

		/**
		 * 删除一个对象的选中状态
		 * @param	item
		 */
		public function removFromSelected(item:*):void {
			if (!selectedObjs.containsValue(item)){
				return;
			}
			var selEvt:SelectionEvent = new SelectionEvent(SelectionEvent.DESELECT);
			selEvt.targetObj = item;
			SysEventMgr.sendMsg(selEvt, item);
			selectedObjs.remove(item.id);
			selObjWithStartLocs.remove(item.id);
		}

		/**
		 * 清除所有对象的选中状态
		 */
		public function rmAllFromSelected():void {
			var selEvt:SelectionEvent = new SelectionEvent(SelectionEvent.DESELECT);
			selectedObjs.eachValue(function(item:*):void {
					selEvt.targetObj = item;
					SysEventMgr.sendMsg(selEvt, item);
					selectedObjs.remove(item.id);
				});

			selObjWithStartLocs.clear();
			selObjWithStartLocs.clear();
		}

		/**
		 * 查找落下区域
		 */
		private function getDropTarget(srcObj:Object, type:Class = null):DisplayObject {
			if (!srcObj){
				return null;
			}
			var acrossObjs:ArrayCollection = new ArrayCollection(); //落下时相交叉的对象
			var i:Number = 0;

			this.dropZones.eachValue(function(item:*):void {
					if ((item as DisplayObject).hitTestObject(srcObj as DisplayObject)){
						acrossObjs.addItem(item);
					}
				});

			if (acrossObjs.length == 0){
				return null;
			} else if (acrossObjs.length == 1){
				return acrossObjs.getItemAt(0) as DisplayObject;
			}

			//如果存在多个交叉的对象，选最上层的对象作为目标
			i = 0;
			var maxItemIndex:Number = 0;
			var index1:Number = 0;
			var cmp:Number = -1;
			for (i; i < acrossObjs.length; i++){
				var dropZoneTemp1:DisplayObject = acrossObjs.getItemAt(i) as DisplayObject;
				index1 = dropZoneTemp1.parent.getChildIndex(dropZoneTemp1);
				if (index1 >= cmp){
					cmp = index1;
					maxItemIndex = i;
				}
			}

			return acrossObjs.getItemAt(maxItemIndex) as DisplayObject;
		}
		
		public function setEvtRoot(evtRoot:DisplayObjectContainer):void{
			this.evtRoot=evtRoot;
		}
	}
}