package com.sinosoft.fsky.component.treechat
{
	import com.sinosoft.fsky.component.XLine;
	
	import flash.geom.Point;
	import mx.controls.*;
	import mx.core.*;
	import mx.effects.*;
	import mx.effects.Move;
	import spark.components.SkinnableContainer;

	/**
	 * @demo
	 *	  <fx:Declarations>
	 *		<fx:XML id="treeData">
	 *			<root>
	 *				<node title="净排放">
	 *					<node title="碳排放">
	 *						<node title="能源活动">
	 *							<node title="能源工业" value="$11615" />
	 *							<node title="制造业及建筑" value="$348" />
	 *							<node title="交通运输" value="$5227" />
	 *						</node>
	 *						<node title="工业生产过程">
	 *							<node title="钢铁生产" value="$2033" />
	 *							<node title="水泥生产" value="$2323" />
	 *						</node>
	 *					</node>
	 *					<node title="碳吸收" value="$746"/>
	 *				</node>
	 *			</root>
	 *		</fx:XML>
	 *	</fx:Declarations>
	 *	
	 *	<treechat:TreeChat dataProvider="{treeData.children()}" />
	 * 
	 * @author Darkness
	 * @version 2011-05-30 05:09 PM
	 * @description 树状图表，实现根据xml数据源显示柱状图表
	 */
	public class TreeChat extends SkinnableContainer
	{
		public function TreeChat()
		{
		}
		
		private var BUTTON_WIDTH:uint = 80;
		private var BUTTON_HEIGHT:uint = 40;
		private var BUTTON_BETWEEN_WIDTH:uint = 40;// 按钮的水平间距
		private var BUTTON_BETWEEN_HEIGHT:uint = 20;// 按钮的垂直间距
		private var maxLevel:Number = 0;
		
		public function set dataProvider(nodes:XMLList):void {
			createButton(nodes, 0);	
		}
		
		private function createButton(nodes:XMLList, level:uint):void {
			
			if(level == maxLevel) {
				level++;
				maxLevel++;
			} else {
				level++;
			}
			
			for(var i:int=0; i<nodes.length(); i++) {
				
				var node:XML = nodes[i] as XML;
				
				if(node.children().length() > 0) {
					createButton(node.children(), level);
				}
				
				var valueButton:ValueButton = new ValueButton();
				valueButton.title = node.attribute("title");
				valueButton.value = node.attribute("value");
				
				var frontCount:Number = 0;
				if(maxLevel == level) {// 是最深层级
					frontCount = getFrontCount(node);
					node.@y = (frontCount + i) * (BUTTON_HEIGHT+BUTTON_BETWEEN_HEIGHT);
				} else {
					var nodeChilds:XMLList = node.children() as XMLList;
					if(nodeChilds.length() > 0) {
						trace("根据子节点计算父节点y轴：" + "子节点1y：" + nodeChilds[0].y + "子节点2y：" + nodeChilds[nodeChilds.length()-1].y);
						node.@y = ((nodeChilds[0].@y - 0) + (nodeChilds[nodeChilds.length()-1].@y - 0))/2;
					} else {
						var children:XMLList = node.parent().children();
						for(var j:int=0; j<children.length(); j++) {
							if(node == (children[j] as XML)) {// 判断当前节点在父节点子节点中的索引
								node.@y = children[j-1].y + BUTTON_BETWEEN_HEIGHT;
								break;
							}
						}
						
					}
					
				}
				
				node.@x = valueButton.x = (BUTTON_WIDTH + BUTTON_BETWEEN_WIDTH) * level;
				valueButton.y = node.@y;
				
				trace(node.attribute("title") + ",x:" + node.@x + ",y:" + node.@y);
				
				addElement(valueButton);
				
				lineWithChildren(node);
			}
		}
		
		/**
		 * 获取当前节点前面所有兄弟节点下子节点的总数
		 */
		private function getFrontCount(node:XML):Number {
			
			var parent:XML = node.parent().parent() as XML;
			
			var children:XMLList = parent.children();
			for(var j:int=0; j<children.length(); j++) {
				
				if(node.parent() == (children[j] as XML)) {// 判断当前节点在父节点子节点中的索引
					
					var index:Number = 0;
					while(j!=0) {
						j--;
						var num:Number = children[j].children().length();
						index = index + num;
					}
					
					return index;
				}
			}
			
			return 0;
		}
		
		/**
		 * 将节点跟子节点连线
		 */
		public function lineWithChildren(node:XML):void
		{
			var children:XMLList = node.children();
			
			var line:XLine = XLine.getLine(0);//0xd43dd6,0x0099ff
			var point:Point = new Point(node.@x-0+BUTTON_WIDTH + 3, node.@y-0 +BUTTON_HEIGHT-15);
			var centerX:uint = point.x + BUTTON_BETWEEN_WIDTH/2;
			var centerPoint:Point = new Point(centerX, point.y);
			
			if(children.length() > 0) {
				line.connectPoint(point, centerPoint);
			}
			
			for(var i:int=0; i<children.length(); i++) {
				
				var y:int = children[i].@y-0+BUTTON_HEIGHT/2+5;
				
				line.connectPoint(new Point(centerX, y), new Point(children[i].@x-0, y));
				
				if(i==0) {
					line.connectPoint(centerPoint, new Point(centerX, y));
				} else if(i==(children.length()-1)) {
					line.connectPoint(centerPoint, new Point(centerX, y));
				}
			}
			
			addElement(line.wrapper());
		}
		
		//			public function Testmove():void
		//			{
		//				var move:Move = new Move();
		//				if(btn2.x!=lbl.x) {
		//					move.target=btn2;
		//					move.end();
		//					move.xTo=lbl.x;
		//					move.yTo=lbl.y;
		//					move.play(); 
		//				} else {
		//					move.target=btn2;
		//					move.end();
		//					move.xTo=340;
		//					move.yTo=104;
		//					move.play(); 
		//				}
		//				
		//			}
	}
}