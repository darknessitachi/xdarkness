package com.sinosoft.fsky.component
{
import flash.events.Event;
import flash.events.MouseEvent;
import flash.utils.setTimeout;

import mx.controls.Alert;
import mx.controls.Image;
import mx.core.FlexGlobals;
import mx.core.UIComponent;
import mx.events.FlexEvent;
import mx.managers.CursorManager;
import mx.managers.DragManager;
import mx.states.Transition;

import spark.components.Group;
import spark.components.SkinnableContainer;
import spark.effects.Resize;

[Event(name="expand", type="flash.events.Event")]
[Event(name="shrink", type="flash.events.Event")]

[Event(name="expandTop", type="flash.events.Event")]
[Event(name="shrinkTop", type="flash.events.Event")]

[SkinState("expand")]
[SkinState("shrink")]
[SkinState("expandTop")]
[SkinState("shrinkTop")]

/**
 * @author Darkness
 * @date 2011-03-24 08:15 PM
 * @version 1.0 简单的面板容器
 * 
 * @date 2011-04-08 04:29 PM
 * @version 1.1 完成点击展开隐藏面板功能【四个方向】
 * 
 * @date 2011-05-19 00:05 AM
 * @version 1.2 面板显示隐藏添加动画效果
 * @description 原来contentGroup隐藏方式为控制其visible：
 *	visible.shrink="false"
 * 	visible.expand="true"
 * 	visible.shrinkTop="false"
 * 	visible.expandTop="true"
 * 	includeInLayout.shrink="false"
 * 	includeInLayout.expand="true"
 * 	includeInLayout.shrinkTop="false"
 * 	includeInLayout.expandTop="true"， 现改成在XPanel中点击显示隐藏按钮后通过Resize动画方式控制其wigth、height
 * 	因为不采用visible方式了，所以第一次显示时需保存其当前的height到realHeight，然后将height设置为0以达到默认隐藏效果，
 *  动画Resize中的高度采用realHeight
 * 
 * @date 2011-05-19 08:30 AM
 * @version 1.2.1 修正XPanel下contenGroup数据是动态的情况时，realHeight高度获取不正确的情况，改成获取其contentGroup的高度
 * 
 * @date 2011-05-19 10:00 AM
 * @version 1.3 添加head 背景点击，如果是展开状态就折叠，如果是折叠状态就展开
 */
public class XPanel extends SkinnableContainer 
{
	[Embed(source="/assets/images/XPanel_head.png")]
	[Bindable]
	public var borderBG:Class;
	
	[Embed(source="/assets/images/XPanel_content_bg.png")]
	[Bindable]
	public var contentBg:Class;
	
	[Embed(source="/assets/images/XPanel_head_icon.png")]
	[Bindable]
	public var headerIcon:Class;
	
    [SkinPart(required="false")]
    public var header:Group;

	[SkinPart(required="false")]
	public var widgetFrame:Group;
	
	[SkinPart(required="false")]
	public var headerToolGroup:Group;
	
	[SkinPart(required="false")]
	public var expandButton:Image;
	
	[SkinPart(required="false")]
	public var shrinkButton:Image;
	
	[SkinPart(required="false")]
	public var expandTopButton:Image;
	
	[SkinPart(required="false")]
	public var shrinkTopButton:Image;
	
	[SkinPart(required="false")]
	public var headBgImage:Image;
	
	private static const WIDGET_EXPAND:String = "expand";
	private static const WIDGET_SHRINK:String = "shrink";
	private static const WIDGET_EXPAND_TOP:String = "expandTop";
	private static const WIDGET_SHRINK_TOP:String = "shrinkTop";
	private static const HORIZONTAL:String = "horizontal";
	private static const VERTICAL:String = "vertical";

    private var _widgetId:Number;
    private var _widgetState:String = WIDGET_EXPAND;

	private var _widgetTitle:String = "";
	private var _hiddenDirection:String;
	
	[Bindable]
	public function get widgetTitle():String
	{
		return _widgetTitle;
	}
	
	public function set widgetTitle(value:String):void
	{
		_widgetTitle = value;
	}
	
	[Bindable]
	public function get hiddenDirection():String
	{
		return _hiddenDirection;
	}
	
	public function set hiddenDirection(value:String):void
	{
		_hiddenDirection = value;
		
		if(this.hiddenDirection == VERTICAL) {
			this._widgetState = WIDGET_SHRINK_TOP;
		} else {
			this._widgetState = WIDGET_EXPAND;
		}
	}
	
    public function XPanel()
    {
        super();
		
		setStyle("skinClass", XPanelSkin);
    }

	/**
	 * 覆写父类方法：添加part时加入监听事件，给按钮添加监听函数
	 */
    protected override function partAdded(partName:String, instance:Object):void
    {
        super.partAdded(partName, instance);
		
        if (instance == expandButton) {
			expandButton.addEventListener(MouseEvent.CLICK, expand_clickHandler);
        } else if (instance == shrinkButton) {
			shrinkButton.addEventListener(MouseEvent.CLICK, shrink_clickHandler);
        } else if (instance == expandTopButton) {
			expandTopButton.addEventListener(MouseEvent.CLICK, expandTop_clickHandler);
		} else if (instance == shrinkTopButton) {
			shrinkTopButton.addEventListener(MouseEvent.CLICK, shrinkTop_clickHandler);
		} else if(instance == header) {
			header.addEventListener(MouseEvent.CLICK, expandOnOff_clickHandler);
		}
    }
	
	/**
	 * 覆写父类方法：移除part时加入监听事件，移除按钮上的监听函数
	 */
	protected override function partRemoved(partName:String, instance:Object):void
	{
		super.partRemoved(partName,instance);
		
		if (instance == expandButton) {
			expandButton.removeEventListener(MouseEvent.CLICK, expand_clickHandler);
		} else if (instance == shrinkButton) {
			shrinkButton.removeEventListener(MouseEvent.CLICK, shrink_clickHandler);
		} else if (instance == expandTopButton) {
			expandButton.removeEventListener(MouseEvent.CLICK, expandTop_clickHandler);
		} else if (instance == shrinkTopButton) {
			shrinkButton.removeEventListener(MouseEvent.CLICK, shrinkTop_clickHandler);
		} else if(instance == header) {
			header.removeEventListener(MouseEvent.CLICK, expandOnOff_clickHandler)
		}
	}

	override protected function getCurrentSkinState():String
	{
		return _widgetState;
	}
	
	public function set widgetState(value:String):void
	{
		_widgetState = value;
		invalidateSkinState();
	}
	
	private var _inited:Boolean = false;
	override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
		
		super.updateDisplayList(unscaledWidth, unscaledHeight);
		
		if(!_inited && (hiddenDirection == VERTICAL)) {
			_inited = true;
			this.height = 33;
		}
	}
	
	private var oldWidth:Number;
	public function expand_clickHandler(event:MouseEvent):void
	{
		widgetState = XPanel.WIDGET_EXPAND;	
		this.width = oldWidth;
		
		this.dispatchEvent(new Event(XPanel.EXPANDED));
	}
	
	public static const EXPANDED:String = "XPanelExpanded";
	public static const SHRINKED:String = "XPanelShrinked";
	public static const EXPANDED_TOP:String = "XPanelTopExpanded";
	public static const SHRINKED_TOP:String = "XPanelTopShrinked";
	
	public function shrink_clickHandler(event:MouseEvent):void
	{
		
		widgetState = XPanel.WIDGET_SHRINK;
		oldWidth = this.width;
		this.width = 25;
		
		this.dispatchEvent(new Event(XPanel.SHRINKED));
	}
	
	/**
	 * head 背景点击，如果是展开状态就折叠，如果是折叠状态就展开
	 */
	private function expandOnOff_clickHandler(evetn:MouseEvent):void {
		if(_widgetState == XPanel.WIDGET_EXPAND_TOP) {
			shrinkTop_clickHandler(evetn);
		} else if(_widgetState == XPanel.WIDGET_SHRINK_TOP) {
			expandTop_clickHandler(evetn);
		}
	}
	
	/**
	 * 向下展开面板
	 */
	public function expandTop_clickHandler(event:MouseEvent):void
	{
		widgetState = XPanel.WIDGET_EXPAND_TOP;	
		
		this.dispatchEvent(new Event(XPanel.EXPANDED_TOP));
		
		var _resize:Resize = new Resize();
		_resize.duration = 500;
		_resize.target = this;
		_resize.heightFrom = 33;
		_resize.heightTo = getContentGroupHeight();
		
		var i:Number = this.width;
		_resize.widthFrom = this.width;
		_resize.widthTo =  this.width;
		
		trace("this.i:" + i);
		trace("this.width:" + this.width);
		trace("this.parent.width:" + this.parent.width);
		
		_resize.play();
		
		setTimeout(fixWidth, 1000);
		trace("f:this.width:" + this.width);
		trace("f:this.parent.width:" + this.parent.width + "," + this.parent.parent.width);
	}
	
	function fixWidth():void {
		this.width = this.parent.width;
	}
	
	/**
	 * 向上收缩面板
	 */
	public function shrinkTop_clickHandler(event:MouseEvent):void
	{
		widgetState = XPanel.WIDGET_SHRINK_TOP;
		
		this.dispatchEvent(new Event(XPanel.SHRINKED_TOP));
		
		var _resize:Resize = new Resize();
		_resize.target = this;
		_resize.heightFrom = getContentGroupHeight();
		_resize.heightTo = 33;
		
		var i:Number = this.width;
		_resize.widthFrom =  this.width;
		_resize.widthTo =  this.width;
		
		trace("this.i:" + i);
		trace("this.width:" + this.width);
		trace("this.parent.width:" + this.parent.width);
		_resize.play();
		setTimeout(fixWidth, 1000);
		trace("f:this.width:" + this.width);
		trace("f:this.parent.width:" + this.parent.width + "," + this.parent.parent.width);
	}
	
	/**
	 * 获取contentGroup的高度
	 */
	private function getContentGroupHeight():Number {
		
		var height:Number = 33;
		for(var i:Number=0; i<this.contentGroup.numChildren; i++) {
			height += this.contentGroup.getChildAt(0).height;
		}
		return height;	
	}

}

}
