package com.nano.widgets {
	import com.greensock.TweenMax;
	import com.nano.core.JComponent;
	import com.nano.core.SysClassMgr;
	import com.nano.core.SysConfMgr;
	import com.nano.core.SysDataMgr;
	import com.nano.core.SysEventMgr;
	import com.nano.core.SysKeyMgr;
	import com.nano.core.SysMouseMgr;
	import com.nano.core.event.DDEvent;
	import com.nano.core.event.SelectionEvent;
	import com.nano.data.JHashMap;
	import com.nano.dd.DDMgr;
	import com.nano.dd.JDropZone;
	import com.nano.plugins.LightMsg;
	import com.nano.util.CollectionUtil;
	import com.nano.widgets.link.JDirectionLink;
	import com.nano.widgets.link.JFluxLink;
	import com.nano.widgets.link.JLink;
	import com.nano.widgets.link.JLinkAble;
	import com.nano.widgets.link.JSimpleLink;
	import com.nano.widgets.link.JSingleDirectionLink;
	
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	import flash.display.Loader;
	import flash.display.Sprite;
	import flash.display.Stage;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.net.URLRequest;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.events.CloseEvent;
	
	import org.as3commons.lang.ClassUtils;
	import org.as3commons.reflect.Type;

	/**
	 * 绘图画笔类，该类被设计作为一支”画笔“，可以在一个”页面“JPage上进行绘图操作。
	 * 既可以直接使用鼠标绘制基本的图形，也可以使用拖拽的方式绘制外部制作好的swf组件。
	 * @version	1.4 2011-04-05
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */
	public class JPen{
		include "../core/__observable.as";
		/**
		 * 缓存模型类名和完整名称之间的映射关系，
		 * 在系统初始化时，可以从后台读取配置文件，把类名和类的完整名映射关系加载到此属性中，
		 * 或者手工向此Hash表中加入映射关系
		 * 在绘制图形时，根据此属性中的映射关系，通过反射创建对象
		 * 如果通过名称查找类型信息失败， 则无法绘制任何图形
		 */
		public static var moduleClassMap:JHashMap=new JHashMap();
		/**
		 * 画板状态定义
		 */
		public static const PEN_STATUS:Object = {DRAW:0,TRANSFORM:1,EDIT:2,LINK:3,DRAG_BG:4};
		/**
		 * 连接线类型静态常量:SIMPLE---简单无方向的连接线、FLUX---流动的连接线、BOTH_DIRECTION---双向连接线、 SINGLE_DIRECTION---单向连接线
		 */ 
		public static const LINK_TYPE:Object={SIMPLE:0,FLUX:1,BOTH_DIRECTION:2,SINGLE_DIRECTION:3};
		/**
		 * 当前绘制的图形类型
		 */
		private var currShape:*= null;
		/**
		 * 当前绘制出来的对象
		 */
		public var obj:*= null;
		/**
		 * 数组，用来保存绘制出来的对象
		 * 注意：所有绘制出来的对象都必须添加到此集合中
		 * 没有添加到此集合的对象都不能根据对象id获取对
		 * 象引用，执行保存操作时也不会保存
		 */
		public  var objs:JHashMap=new JHashMap();
		/**
		 * 不能作为拖拽落下区域的组件集合
		 * 在使用拖拽的方法绘制组件时，只有组件被拖拽到合法的编辑区域并放开才进行
		 * 组件绘制，在非法的落下区域放开时，不绘制组件
		 */
		public  var invalidDropTarget:ArrayCollection=new ArrayCollection();
		/**
		 * 笔触宽度
		 */
		public  var strokeW:Number = 1;
		/**
		 * 笔触颜色
		 */
		public  var strokeColor:Number = 0x000000;
		/**
		 * 笔触不透明度
		 */
		public  var strokeTrans:Number = 1;
		/**
		 * 填充颜色
		 */
		public  var fillColor:Number = 0xffffff;
		/**
		 * 填充不透明度
		 */
		public  var fillTrans:Number = 0;
		/**
		 * 圆角宽度
		 */
		public var cornerW:Number=0;
		/**
		 * 圆角高度
		 */
		public var cornerH:Number=0;
		/**
		 * 画笔状态，默认为绘制状态
		 */
		public  var penStatus:Number = JPen.PEN_STATUS.DRAW;
		/**
		 * 连接线的类型
		 */
		public  var currLinkType:Number=JPen.LINK_TYPE.SIMPLE;
		/**
		 * 舞台对象
		 */
		public var stage:Stage;
		/**
		 * 根容器对象
		 */
		public var rootContainer:DisplayObjectContainer;
		/**
		 * 当前正在绘制的页面
		 */
		public  var currPage:JPage;
		/**
		 * 以下3个为拖拽方式创建组件参数：dragGhost/_startX/_startY
		 */ 
		private  var dragGhost:DisplayObject=null;
		private  var _startX:Number=0;
		private  var _startY:Number=0;
		/**
		 * 所有页面的哈希表
		 */
		public var pages:JHashMap = new JHashMap();
		private  var dragUpHand:Sprite=new DragUpHand();
		private  var dragDownHand:Sprite=new DragDownHand();
		/**
		 * 图片加载器
		 */ 
		private  var loader:Loader = new Loader();
		/**
		 * 键盘交互管理器
		 */
		public var sysKeyMgr:SysKeyMgr;
		/**
		 * 鼠标交互管理器
		 */
		public var sysMouseMgr:SysMouseMgr;
		/**
		 * 拖拽管理器
		 */
		private var ddMgr:DDMgr;
		/**
		 * 类管理器
		 */
		private var skinMgr:SysClassMgr;
		
		/**
		 * 构造方法：创建画笔
		 * @param	配置参数
		 */
		public function JPen(conf:*){
			rootContainer=conf.editor;
			this.stage=conf.editor.stage;
			
			//初始化键盘按键管理
			this.sysKeyMgr=new SysKeyMgr(this);
			if(conf.enableKeyboard){
				this.sysKeyMgr.startInteraction();
			}

			//初始化鼠标交互管理器
			this.sysMouseMgr=new SysMouseMgr(this);
			if(conf.enableMouse){
				this.sysMouseMgr.startInteraction();
			}
			
			//初始化拖拽管理器
			this.ddMgr=new DDMgr(this);
			
			this.addEvents(["initcomplete","addcomplete","removecomplete","b4remove",
							"grouping","ungrouping","drawcomplete",,"ctrl+s","ctrl+c","ctrl+v","ctrl+a"]);
		}
		
		/**
		 * 绘制状态下鼠标按下事件处理
		 * 所有组件的绘制都通过此方法进行
		 * 基本组件使用检测root对象的MOUSE_DOWN事件进行绘制
		 * 高级组件将在stage的MOUSE_UP事件中手工调用此方法进行绘制
		 */
		public  function drawMouseDown(e:MouseEvent):void {
			drawComp(e);
			if(!obj){
				return;
			}
			this.addToView(new ArrayCollection([obj]));
			//在将绘制出的组件添加到容器之后，必须触发JPen.ADD_COMPLETE事件，通知对象开始监听鼠标的移动和弹起
			if(obj is JWidget){
				SysEventMgr.sendMsg(new Event("addcomplete"),obj);
			}
		}
		
		/**
		 * 静态工厂方法
		 * 绘制组件
		 */ 
		public  function drawComp(e:MouseEvent):void{
			//根据名称创建对象
			var fullClassName:String=moduleClassMap.get(this.currShape);
			if(!fullClassName){
				var msg:LightMsg=new LightMsg(this.stage);
    			msg.show({
					title:"警告",
					msg:"未能找到指定的对象类型，请检查配置文件。"
				});
    			return;
			}
			var ClassType:Class =SysClassMgr.getClassFromName(fullClassName);
			var type:Type=Type.forName(fullClassName);
			var flag:int=type.constructor.parameters.length;
			if(flag==1){
				obj = new ClassType({x:currPage.mouseX,y:currPage.mouseY});
			}else{
				obj = new ClassType(currPage.mouseX,currPage.mouseY);
			}
			
			//根据对象类型设置笔触、填充初始值
			if(obj is JFilledShape){
				obj.strokeStyle(this.strokeW,this.strokeColor,this.strokeTrans);
				obj.fillStyle(this.fillColor, this.fillTrans);
				if(obj is JRoundRect){
					obj.ellipseHeight=this.cornerH;
					obj.ellipseWidth=this.cornerW;
				}
			}else if(obj is JWidget){
				obj.strokeStyle(this.strokeW,this.strokeColor,this.strokeTrans);
				obj.fillStyle(this.fillColor, this.fillTrans);
			}
		}
		
		/**
		 * 切换绘制的图形类型
		 * @param	type
		 */
		public  function setType(type:*):void {
			this.currShape = type;
		}
		
		/**
		 * 在开始绘制对象之前设置边框样式
		 * @param	strokeW
		 * @param	strokeColor
		 * @param	strokeTrans
		 */
		public  function strokeStyle(strokeW:Number,strokeColor:Number,strokeTrans:Number):void {
			this.strokeW = strokeW;
			this.strokeColor = strokeColor;
			this.strokeTrans = strokeTrans;
		}
		
		/**
		 * 在开始绘制对象之前设置填充样式
		 * @param	fillColor			填充颜色
		 * @param	fillTrans			填充透明度
		 */
		public  function fillStyle(fillColor:Number,fillTrans:Number):void {
			this.fillColor = fillColor;
			this.fillTrans = fillTrans;
		}
		
		/**
		 * 连接状态鼠标按下
		 * @param	e
		 */
		public  function linkMouseDown(e:MouseEvent):void {
			var tar:Object = e.target;
			if(tar is JPage){
				return;
			}
			var linkFrom:Sprite=null;
			if (tar is JLinkAble) {
				switch(this.currLinkType){
					case JPen.LINK_TYPE.SIMPLE:
						obj = tar.addLinkStart(new JSimpleLink());
						break;
					case JPen.LINK_TYPE.BOTH_DIRECTION:
						obj = tar.addLinkStart(new JDirectionLink());
						break;
					case JPen.LINK_TYPE.SINGLE_DIRECTION:
					    obj = tar.addLinkStart(new JSingleDirectionLink());
						break;
					case JPen.LINK_TYPE.FLUX:
						obj = tar.addLinkStart(new JFluxLink({x1:0,y1:0,strokeW:3,strokeColor:0xff0000,strokeTrans:1,autoFlux:true}));
						break;
					default:
						break;
				}
				currPage.addEventListener(MouseEvent.MOUSE_MOVE, this.linkMouseMove);
				currPage.addEventListener(MouseEvent.MOUSE_UP, this.linkMouseUp);
			}
		}
		
		/**
		 * 连接状态鼠标移动
		 * @param	e
		 */
		public  function linkMouseMove(e:MouseEvent):void {
			var p:Point=new Point(e.stageX,e.stageY);
			p=obj.globalToLocal(p);
			obj.lineTo(p.x,p.y);
		}
		
		/**
		 * 连接状态鼠标弹起
		 * @param	e
		 */
		public  function linkMouseUp(e:MouseEvent):void {
			var tar:Object = e.target;
			if (tar is JLinkAble&&(tar!=obj.getLinkFrom())) {		//在可连接的对象上鼠标弹起，添加一个以该对象为终点的连接线
				tar.addLinkEnd(obj);
				this.objs.put(obj.id,obj);
			}else {													//在其它区域鼠标弹起，删除绘出的连接线，同时删除对象上的监听器
				(obj as JLink).getLinkFrom().rmLinkStart(obj);
				currPage.removeChild(obj);
				obj = null;
			}
			currPage.removeEventListener(MouseEvent.MOUSE_MOVE,linkMouseMove);
			currPage.removeEventListener(MouseEvent.MOUSE_UP, linkMouseUp);
		}
		
		/**
		 * 设置画笔状态，不同的编辑状态对应不同的事件注册方式
		 * @param	status @see #JPen.PEN_STATUS
		 */
		public  function setPenStatus(status:Number):void {
			if(!this.currPage){
				LightMsg.alert("提示","没有创建绘图页面，无法设置画笔状态。");
				return;
			}
			//清除所有的监听器
			this.removeAllListeners();
			//通知所有对象删除其上的编辑框
			SysEventMgr.sendMsg(new SelectionEvent(SelectionEvent.DESELECT),this.objs.values());
			
			//停止拖拽交互
			if(this.ddMgr.isReady){
				this.ddMgr.stopInteraction();
			}
			
			this.penStatus = status;
			switch(this.penStatus){
				case JPen.PEN_STATUS.DRAW:		//基本组件绘制
					currPage.addEventListener(MouseEvent.MOUSE_DOWN, this.drawMouseDown);
					break;
				case JPen.PEN_STATUS.EDIT:		//编辑状态
					this.ddMgr.startInteraction();
					break;
				case JPen.PEN_STATUS.LINK:		//连接对象状态
					currPage.addEventListener(MouseEvent.MOUSE_DOWN, this.linkMouseDown);
					break;
				default:						//默认为编辑状态
					this.ddMgr.startInteraction();
					break;
			}
		}
		
		/**
		 * 删除对象上的事件监听器
		 * @param	obj
		 */
		public  function removeAllListeners():void {
			currPage.removeEventListener(MouseEvent.MOUSE_DOWN,drawMouseDown);
			currPage.removeEventListener(MouseEvent.MOUSE_DOWN,linkMouseDown);
			currPage.removeEventListener(MouseEvent.MOUSE_MOVE,linkMouseMove);
			currPage.removeEventListener(MouseEvent.MOUSE_UP, linkMouseUp);
		}
		
		/**
		 * 加载图片
		 * @param	imgUrl	图片路径
		 */
		public  function loadImg(imgUrl:String):void {
			loader.load(new URLRequest(imgUrl));
			loader.x = 100;
			loader.y = 100;
			currPage.addChild(loader);
		}
		
		/**
		 * 删除选中的对象
		 */ 
		public  function delSelectedObjs():void{
			//删除所有选中的对象
			if(this.ddMgr.selectedObjs.size()==0){
				var lightMsg:LightMsg=new LightMsg(this.stage);
				lightMsg.show({
					title:"删除对象",
					msg:"请先选中需要删除的对象，按住Ctrl键可以进行多选。"
				});
				return;
			}
			
			 Alert.yesLabel = "是";
             Alert.noLabel = "否";
             Alert.show("删除操作无法回退，是否继续？", "删除对象", 3,null, function(e:CloseEvent):void{
             	if(e.detail==Alert.YES){
             		//向所有选中的对象发送删除事件
             		ddMgr.selectedObjs.eachValue(function(item:*):void{
             			removeFromView(item);
             		});
             		ddMgr.selectedObjs.clear();
             	}
             });
		}
		
		/**
		 * 根据id获取对象的引用
		 */ 
		public  function $(id:String):Object{
			return this.objs.get(id);
		}
		
		/**
		 *	清除所有绘制出来的对象
		 *  清理拖拽管理器
		 */ 
		public  function clearAll():void{
     		objs.eachValue(function(val:*):void{
     			removeFromView(val);
     			val=null;
			});
			objs.clear();
			ddMgr.clear();
		}
		 
		/**
		 * 清理一组集合中的对象
		 * @param	arr
		 */
		public  function clear(arr:ArrayCollection):void{
			for each(var item:* in arr){
				if(this.objs.containsValue(item)){
					if((this.currPage as DisplayObjectContainer).contains(item)){
						(this.currPage as DisplayObjectContainer).removeChild(item);
					}
					this.objs.remove(item.id);
				}
			}
		}
		
		
		/**
		 * 高级组件使用拖拽的方式进行绘制
		 * 此方法用于在拖拽时创建一个跟随鼠标移动的“影子对象”（一个虚框矩形）
		 * 结合_mouseMoveHandler_1()和_mouseUpHandler_1()两个方法用来完成高级组件的拖拽绘制功能
		 * @param	e
		 * @return
		 */
		public  function createDragGhost(e:MouseEvent):void {
			var ghost:JDashedRect = new JDashedRect(e.stageX-25,e.stageY-25);
			_startX=e.stageX-25;
			_startY=e.stageY-25;
			ghost.setEndPoint(50, 50);
			
			var dropInvalid:Sprite=new DropInvalid();
			ghost.addChild(dropInvalid);
			dropInvalid.x=(ghost.width-dropInvalid.width);
			dropInvalid.y=(ghost.height-dropInvalid.height);
			
			var dropValid:Sprite=new DropValid();
			
			//合法落下区域，去除禁止落下标志，添加可以落下标志
			SysEventMgr.on(ghost,DDEvent.VALID_DROP_TARGET,function(e:DDEvent):void{	
				if(ghost.contains(dropInvalid)){
					ghost.removeChild(dropInvalid);	
				}
				if(!ghost.contains(dropValid)){
					ghost.addChild(dropValid);
					dropValid.x=(ghost.width-dropValid.width);
					dropValid.y=(ghost.height-dropValid.height);
				}
			});
			
			//非法落下区域，添加禁止落下标志
			SysEventMgr.on(ghost,DDEvent.INVALID_DROP_TARGET,function(e:DDEvent):void{	
				if(ghost.contains(dropValid)){
					ghost.removeChild(dropValid);	
				}
				if(!ghost.contains(dropInvalid)){
					ghost.addChild(dropInvalid);
					dropInvalid.x=(ghost.width-dropInvalid.width);
					dropInvalid.y=(ghost.height-dropInvalid.height);
				}
			});
			
			dragGhost=ghost;
			currPage.stage.addChild(dragGhost);
			currPage.stage.addEventListener(MouseEvent.MOUSE_MOVE,_mouseMoveHandler_1);
			currPage.stage.addEventListener(MouseEvent.MOUSE_UP,_mouseUpHandler_1);
		}
		
		private  function _mouseMoveHandler_1(e:MouseEvent):void{
			dragGhost.x=e.stageX-25;
			dragGhost.y=e.stageY-25;
			var isValidTarget:Boolean=_isOnEidtor(dragGhost);
			if(isValidTarget){
				SysEventMgr.sendMsg(new DDEvent(DDEvent.VALID_DROP_TARGET),dragGhost);
			}else{
				SysEventMgr.sendMsg(new DDEvent(DDEvent.INVALID_DROP_TARGET),dragGhost);
			}
		}
		
		private  function _mouseUpHandler_1(e:MouseEvent):void{
			if(dragGhost){
				//目标区域合法性检测
				var isValidTarget:Boolean=_isOnEidtor(dragGhost);
				if(isValidTarget){
					currPage.stage.removeChild(dragGhost);
					this.drawMouseDown(e);
				}else{
					TweenMax.to(dragGhost,0.3,{x:_startX,y:_startY,onComplete:function():void{
						currPage.stage.removeChild(dragGhost);
					}});
				}
			}
			currPage.stage.removeEventListener(MouseEvent.MOUSE_MOVE,_mouseMoveHandler_1);
			currPage.stage.removeEventListener(MouseEvent.MOUSE_UP,_mouseUpHandler_1);
			//返回到编辑状态
			this.setPenStatus(JPen.PEN_STATUS.EDIT);
			this.fireEvent("drawcomplete");
		}
		
		/**
		 * 判断被拖拽的对象是否位于页面边界内
		 * 在拖拽绘制高级组件时，此方法用来辅助边界检测
		 * @param	obj
		 * @return
		 */
		private  function _isOnEidtor(obj:DisplayObject):Boolean{
			for each(var item:* in this.invalidDropTarget){
				if(item&&(item as DisplayObject).hitTestObject(obj)){
					return false;
				}
			}
			if (this.currPage.hitTestObject(obj)) {
				return true;
			}
			return false;
		}
		
		/**
		 * 在拖拽或者绘制对象时
		 * 可以使用此方法，判断当前鼠标位置是否位于合法的绘制区域内
		 * @param	p
		 * @return
		 */
		public  function enforceBorder(x:Number,y:Number):Boolean{
			for each(var item:* in this.invalidDropTarget){
				if(item&&(item as DisplayObject).hitTestPoint(x,y)){
					return false;
				}
			}
			if (this.currPage.hitTestPoint(x,y)) {
				return true;
			}
			return false;
		}
		
		/**
		 * 设置当前正在编辑的页面
		 * 当打开或新建了多个页面时，使用此方法设置当前正在编辑的页面
		 * @param	page
		 */
		public  function setCurrPage(currPage:*):void {
			this.currPage=currPage;
			this.ddMgr.setEvtRoot(this.currPage);
		}
		
		/**
		 * 将一组对象添加到视图中
		 * 在把对象添加到显示列表之前先进行分类处理，首先添加对象，然后再添加连接线
		 * 此顺序不能颠倒，因为对于连接来说，必须存在起点和终点对象
		 * 可以是单个显示对象，也可以是一个Array或者ArrayCollection
		 * @param	arr		
		 */
		public function addToView(obj:*):void{
			var arr:Array=[];
			if(obj is Array||obj is ArrayCollection){
				obj is Array?arr=obj:arr=obj.source;
			}else{
				arr.push(obj);
			}
			
			//对象分类
			var _links:Array=CollectionUtil.filterByClass(arr,JLink);
			var _modules:Array=CollectionUtil.minusCollection(arr,_links);
			
			//首先添加组件对象
			for each(var item:* in _modules){
				this.currPage.addChild(item);
				this.objs.put(item.id,item);
				
				this.ddMgr.reg(item,DDMgr.DRAG_SRC);
				if(item is JDropZone){
					this.ddMgr.reg(item,DDMgr.DROP_ZONE);
				}
				this.obj=item;
				this.fireEvent("addcomplete",item);
			}
			
			//然后建立连接关系
			for each(var _link:* in _links){
				var linkTemp:JLink=_link as JLink;
				if(!this.$(linkTemp.getFromId())||(!this.$(linkTemp.getToId()))){//连接信息缺失
					continue;
				}
				var linkStart:JLinkAble=this.$(linkTemp.getFromId()) as JLinkAble;
				var linkEnd:JLinkAble=this.$(linkTemp.getToId()) as JLinkAble;
				linkStart.addLinkStart(linkTemp);
				linkEnd.addLinkEnd(linkTemp);
				//设置连接样式
				this.objs.put(linkTemp.id,linkTemp);
			}
		}
		
		/**
		 * 从视图中删除对象
		 * 如果对象是可以使用连接线连接的，首先从画笔和拖拽管理器中反注册所有连接线
		 * 然后删除对象自己
		 * @param	comp
		 */
		public  function removeFromView(comp:*):void{
			this.fireEvent('b4remove',comp);
			//如果对象是可以使用连接线连接的，首先从画笔和拖拽管理器中反注册所有连接线
			if(comp is JLinkAble){
				var temp:JLinkAble=comp as JLinkAble;
				CollectionUtil.each(temp.getLinkStart(),function(link2:*):void{
					objs.remove(link2.id);
					ddMgr.unReg(link2.id);
				});
				CollectionUtil.each(temp.getLinkEnd(),function(link1:*):void{
					objs.remove(link1.id);
					ddMgr.unReg(link1.id);
				});
			}
			//然后删除对象
			var selEvt:SelectionEvent=new SelectionEvent(SelectionEvent.REMOVE);
			SysEventMgr.sendMsg(selEvt,comp);
			this.objs.remove(comp.id);
			this.ddMgr.unReg(comp.id);
			this.fireEvent("removecomplete",comp);
		}
		
		/**
		 * Ctrl+A组合键调用此方法将所有绘制的对象设置为选中状态，
		 * 向所有对象发送选中事件
		 */
		public  function selectAll():void {
			this.objs.eachValue(function(item:*):void {
				ddMgr.addToSelected(item);
			});
		}
		
		/**
		 * 加载模型类名映射，此操作是从XML数据反射出模型的基础
		 */ 
		public static function loadModuleMapping():void{
			SysDataMgr.req({
				url:SysConfMgr.getUrl('loadFileUrl'),
				param:{fileName:SysConfMgr.getUrl('moduleConfigFileName',true)},
				success:function(...args):void{
					var xmlStr:String=args[0];
					parseModuleConfigFile(xmlStr);
				},
				failure:function():void{
					Alert.show("无法加载类型映射文件","严重");
				}
			});
		}
		
		/**
		 * 解析xml配置文件
		 * @param	xmlStr
		 */
		private static function parseModuleConfigFile(xmlStr:String):void{
			if(xmlStr){
				var xmlModules:XML = new XML(xmlStr);
				var xmlList:XMLList = xmlModules.children();
				var type:String = null;
				var fullName:String = null;
				for each (var v:XML in xmlList) {
					type = String(v.name);
					fullName = String(v.value);
					if (type&&fullName) {
						moduleClassMap.put(type,fullName);
					}
				}
			}else{
				
			}
		}
		
		/**
		 * 获取当前绘图页面所有组件中的最小坐标值
		 */ 
		public function getMinPos():Point{
			var x:Number=0;
			var y:Number=0;
			var counter:Number=0;
			this.objs.eachValue(function(item:*):void{
				if(item&&item is JComponent){
					if(!counter){
						x=(item as JComponent).getBeginX();
						y=(item as JComponent).getBeginY();
					}else{
						if((item as JComponent).getBeginX()<x){
							x=(item as JComponent).getBeginX();
						}
						if((item as JComponent).getBeginY()<y){
							y=(item as JComponent).getBeginY();
						}
					}
					counter++;
				}
			});
			x=-x*this.currPage.scaleX;
			y=-y*this.currPage.scaleY;
			return new Point(x,y);
		}
		
		/**
		 * 设置当前绘图页面的位置
		 */ 
		public function setPagePos(p:Point=null):void{
			if(!p){
				p=this.getMinPos();
			}
//			this.currPage.x+=p.x;
//			this.currPage.y+=p.y;
			p.x+=20;
			p.y+=20;
			this.currPage.updatePosition(p.x,p.y);
		}
		
		/**
		 * 在两个对象之间添加一条连接线
		 * config参数可以接受三个参数
		 * from:连接线的起始节点，可以是页面中已经存在的可视对象，也可以是此可视对象的id
		 * to:连接线的终止节点，可以是页面中已经存在的可视对象，也可以是此可视对象的id
		 * type:连接线的类型
		 * @param	config
		 */
		public function link(config:*):void{
			if(!config){
				LightMsg.alert("提示","连接参数不能为空");
				return;
			}
			if(config is Array||config is ArrayCollection){
				var me:JPen=this;
				CollectionUtil.each(config,function(item:*):void{
					me.link(item);
				});
				return;
			}
			
			if(!config.from||!config.to){
				LightMsg.alert("提示","必须为连接线提供起始节点和终止节点。");
				return;
			}
			if(config.from is String){
				config.from=this.objs.get(config.from);
			}
			if(config.to is String){
				config.to=this.objs.get(config.to);
			}
			if(!this.currPage.contains(config.from)||!this.currPage.contains(config.to)){
				LightMsg.alert("提示","当前视图中不存在需要连接的对象。");
				return;
			}
			var link:JLink=null;
			switch(config.type){
				case "singleDir"://单向连接
					link=new JSingleDirectionLink();
					break;
				case "doubleDir"://双向连接
					link=new JDirectionLink();
					break;
				case "flux"://流动连接
					link=new JFluxLink();
					break;
				case "simple"://简单连线
					link=new JSimpleLink();
					break;
				default://默认采用单向连接线
					link=new JSingleDirectionLink();
					break;
			}
			if(!link){
				LightMsg.alert("提示","连接对象["+config.from+"]和["+config.from+"]失败");
				return;
			}
			link.fromId=config.from.id;
			link.toId=config.to.id;
			this.addToView(link);
		}
		
		/**
		 * 根据起始节点id获取连接
		 * @param	fromId
		 * @return
		 */
		public function getLinkByFromId(fromId:String,single:Boolean=false):*{
			var link:JLink=null;
			var arr:Array=this.objs.values();
			var result:Array=[];
			CollectionUtil.each(arr,function(item:*):Boolean{
				if(item is JLink){
					if((item as JLink).fromId==fromId){
						link=item;
						result.push(link);
						if (single) {
							return true;
						}
					}
				}
				return false;
			},null, single);
			if (single) {
				return result[0];
			}else {
				return result;
			}
		}
		
		/**
		 * 根据终止节点id获取连接
		 * @param	toId
		 * @return
		 */
		public function getLinkByToId(toId:String,single:Boolean=false):*{
			var link:JLink=null;
			var arr:Array=this.objs.values();
			var result:Array=[];
			CollectionUtil.each(arr,function(item:*):Boolean{
				if(item is JLink){
					if((item as JLink).toId==toId){
						link=item;
						result.push(link);
						if(single){
							return true;
						}
					}
				}
				return false;
			},null,true);
			if (single) {
				return result[0];
			}else {
				return result;
			}
		}
		
		public function getLink(fromId:String,toId:String):*{
			var arr:Array=this.objs.values();
			for(var i:int=0;i<arr.length;i++){
				var obj:Object=arr[i];
				if(obj is JLink){
					var fId:String=(obj as JLink).fromId;
					var tId:String=(obj as JLink).toId;
					if(fId==fromId&&tId==toId){
						return obj;
					}
				}
			}
		}
		
		public function getAllLinks():Array{
			var arr:Array=this.objs.values();
			var result:Array=[];
			CollectionUtil.each(arr,function(item:*):void{
				if(item is JLink){
					result.push(item);
				}
			});
			return result;
		}
		
		public function stopInteraction():void{
			this.sysKeyMgr.stopInteraction();
			this.sysMouseMgr.stopInteraction();
		}
		
		public function startInteration():void{
			this.sysKeyMgr.startInteraction();
			this.sysMouseMgr.startInteraction();
		}
		
		public function getSeledObjs():JHashMap{
			return this.ddMgr.selectedObjs;
		}
	}
}