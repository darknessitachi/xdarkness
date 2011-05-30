package com.nano.layout.graphlayout{
	import com.linkage.cmp.util.CollectionUtil;
	
	import flash.display.Sprite;
	import flash.events.TimerEvent;
	import flash.utils.Timer;

	/**
	 * 自动斥力布局
	 * 基础用法：
	 * 	第一步：创建两个可以显示的对象sp1、sp2；
	 * 	第二步：把sp1、sp2添加到布局中：addToLayout；
	 * 	第三步：使用当前布局的link()方法建立sp1、sp2之间的“软连接”；
	 * 	第四步：调用doLayout()方法开始布局操作；
	 * 	如果需要停止布局，可以调用此布局的stopLayout()接口
	 * 重要：
	 * 	1、此布局管理器内部使用“图”算法进行布局操作，支持“有向无环图”闭环检测等高级功能，使用此布局可以支持对象之间的数学表达式关系。
	 *  2、在布局管理器调整对象位置的过程中支持用户交互，当对象被点击后处于“选中”状态，此时布局管理将不再调整此对象的位置。
	 * 
	 * TODO:添加自动停止机制，当整个布局到达“平衡”状态之后可以选择自动停止定时器，以免过度消耗资源
	 */ 
	public class JForceDirectedLayout extends JDiagramLayout{
		//布局定时器，默认定时100毫秒移动每个对象的位置，此定时器在本布局管理器构造方法中启动
		private var timer:Timer;
		//边
		private var edges:Array=[];
		//最大移动速度
		public var maxMotion:Number=0;
		//最后一次最大移动速度
		public var lastMaxMotion:Number=0;
		//运动变化率：lastMaxMotion/maxMotion-1
		public var motionRatio:Number=0;
		//是否启用阻尼
		public var damping:Boolean=true;
		//阻尼系数
		public var damper: Number=0;
		//弹性系数---此值越小，对象之间的距离越靠近；此值越大，对象之间的距离越远
		public var rigidity: Number = 0.5;
		public var newRigidity: Number = 0.5;
		//当前被拖拽的节点
		private var dragNode:JVetex=null;
		public var maxMotionA: Array;
		//运动量限制，显示对象每次移动的像素数将不会超过此值
		public var motionLimit: Number = 0.5;
		
		/**
		 * 是否忽略孤立节点，如果此参数为true，在布局时将不计算孤立节点的排斥力
		 * 注意：此参数非常重要，由于孤立节点不与任何节点产生“引力”，因此它们只有“斥力”
		 * 如果设置此参数为false，一旦存在孤立节点，那么整个布局中的所有图形会“紧贴”边缘。
		 */
		public var ignoreOrphan:Boolean=true;
		
		/**
		 * 构造方法
		 */ 
		public function JForceDirectedLayout(startX:Number=0,startY:Number=0,layoutWidth:Number=0,layoutHeight:Number=0){
			super(startX,startY,layoutWidth,layoutHeight);
			timer=new Timer(50);
			timer.addEventListener(TimerEvent.TIMER,timerHandler);
		}
		
		/**
		 * 连接两个对象
		 * 此“连接”是一个逻辑连接，没有对应的显示对象
		 * 说明2个对象之间存在引用关系
		 * 此连接没有方向
		 * @param	s1
		 * @param	s2
		 */
		public function link(s1:Sprite,s2:Sprite):void{
			if(s1==null||s2==null){
				return;
			}
			var n1:JVetex=null;
			var n2:JVetex=null;
			for each(var node:JVetex in this.nodes){
				if(node.ui==s1){
					n1=node;
				}
				if(node.ui==s2){
					n2=node;
				}
			}
			
			//如果2个顶点之间已经存在边，忽略
			for each(var edge:JEdge in this.edges){
				if(edge.existEdge(n1,n2)){
					n1.degree++;
					n2.degree++;
					return;
				}	
			}
			var newEdge:JEdge=new JEdge(n1,n2);
			n1.degree++;
			n2.degree++;
			this.edges.push(newEdge);
		}
		
		/**
		 * 位置计算定时器
		 * @param	e
		 */
		private function timerHandler(e:TimerEvent):void{
			//同步UI对象与代理的Vetex对象之间的位置数据
			for each(var node:JVetex in this.nodes){
		        node.refresh();
		    }
		    
		    //吸引：收缩每条边连接的节点之间的距离
	        for each(var edge:JEdge in this.edges){
	        	this.forEachEdge(edge);
	        }
	        
	        //排斥：计算每一对顶点之间的“排斥力”---这里会形成笛卡尔积式的运算
	        for each (var nodeI: JVetex in nodes) {
				for each (var nodeJ: JVetex in nodes) {
					if(nodeI != nodeJ) {
						this.forEachNodePair(nodeI, nodeJ);
					}
				}
			}
			
			//移动每个节点
			this.moveNodes();
			
			if(rigidity!=newRigidity) rigidity= newRigidity;
			
			//提交所有变化叠加起来的效果
			for each(var node1:JVetex in this.nodes){
	        	node1.commit();
	        }
		}
		
		/**
		 * 把可显示对象添加到布局中
		 * @param	node
		 */
		override public function addToLayout(config:*):void{
			var vetex=new JVetex(config as Sprite);
			this.nodes.push(vetex);
			this.resetDamper();
		}
		
		/**
		 * 布局操作
		 * 定时器不断调用监听函数进行布局运算
		 */ 
		override public function doLayout(config:*=null):void{
			this.timer.reset();
			this.timer.start();
		}
		
		/**
		 * 停止布局操作
		 */ 
		public function stopDoLayout():void{
			if(this.timer&&this.timer.running){
				this.timer.stop();
			}
		}
		
		/**
		 * 移动节点
		 */ 
		private  function moveNodes(): void {
		 	lastMaxMotion = maxMotion;
	        maxMotionA = new Array();
	        maxMotionA[0]=0;
	        
	        for each(var node:JVetex in this.nodes){
	        	this.forEachNode(node);
	        }
	        
	        maxMotion=maxMotionA[0];
	        if (maxMotion>0){
	         	motionRatio = lastMaxMotion/maxMotion-1;
	        }else {
	         	motionRatio = 0;
	        }
	        damp();
		}
		
		/**
		 * 移动每个节点
		 * @param	n
		 */
		private function forEachNode(n: JVetex): void {
		 	var dx: Number = n.dx;
		    var dy: Number = n.dy;
		    dx*=damper;
		    dy*=damper;
		    n.dx = dx/2;
		    n.dy = dy/2;
		    
		    var distMoved: Number = Math.sqrt(dx*dx+dy*dy);
		    if (!n.fixed && !(n==dragNode) ) {//节点不固定并且没有被拖拽时才调整节点位置
		        n.x = n.x + Math.max(-30, Math.min(30, dx));
		        n.y = n.y + Math.max(-30, Math.min(30, dy));
		        
		        //如果节点的位置超过了布局限定的矩形范围，不改变节点的x和y位置
		        if(n.x<this.startX){
		        	n.x=this.startX;
		        }
		        if(n.x>(this.startX+this.layoutWidth)){
		        	n.x=this.startX+this.layoutWidth;
		        }
		        if(n.y<this.startY){
		        	n.y=this.startY;
		        }
		        if(n.y>(this.startY+this.layoutHeight)){
		        	n.y=this.startY+this.layoutHeight;
		        }
		    }
		    maxMotionA[0]=Math.max(distMoved,maxMotionA[0]);
		}
		
		/**
		 * 移动每条边连接的节点
		 * @param	e
		 */
		private function forEachEdge(e:JEdge): void {
			  var vx: Number = e.toNode.x-e.fromNode.x;
			  var vy: Number = e.toNode.y-e.fromNode.y;
			  var len: Number = Math.sqrt(vx * vx + vy * vy); //长度
			
			  var dx: Number=vx*rigidity;
			  if(isNaN(dx)) {
				 dx = dx;
			  }
			  var dy: Number=vy*rigidity;
			  if(isNaN(dy)) {
				 dy = dy;
			  }
			
			  var length: int = e.getLength();
			  var div: int = length * 100;
			 
			  dx /=div;
			  if(isNaN(dx)) {
				 dx = dx;
			  }
			  dy = dy / div;
			  if(isNaN(dy)) {
				dy = dy;
			  }
			  
			  e.toNode.dx=e.toNode.dx-dx*len;
			  e.toNode.dy=e.toNode.dy-dy*len;
			  e.fromNode.dx=e.fromNode.dx+dx*len;
			  e.fromNode.dy=e.fromNode.dy+dy*len;
		}
		
		/**
		 * 计算一对节点之间的“排斥”关系
		 * @param	n1
		 * @param	n2
		 */
		private function forEachNodePair(n1: JVetex, n2: JVetex): void {
			if(this.ignoreOrphan){
				if(n1.degree==0||n2.degree==0){
					return;
				}
			}
			var dx: Number=0;
		    var dy: Number=0;
		    var vx: Number = n1.x - n2.x;
		    var vy: Number = n1.y - n2.y;
		    var len: Number = vx * vx + vy * vy;
		    
		    if (len == 0) {
		        dx = Math.random();
		        dy = Math.random();
		    }else{
		        dx = vx / len;
		        dy = vy / len;
		    }
		
		    var repSum: Number = n1.getRepulsion() * n2.getRepulsion()/100;
		    var factor: Number = repSum*rigidity;
	        n1.dx += dx*factor;
	        n1.dy += dy*factor;
	        n2.dx -= dx*factor;
	        n2.dy -= dy*factor;
		}
		
		/**
		 * 停止运动
		 */
		private function stopMotion(): void {
	        damping = true;
	        if (damper>0.3) 
	            damper = 0.3;
	        else
	            damper = 0;
    	}
		
		/**
		 * 阻尼效果
		 */ 
		private function damp():void{
			if (damping) {
	            if(motionRatio<=0.001) {
		            if ((maxMotion<0.2 || (maxMotion>1 && damper<0.9)) && damper > 0.01){
		                damper -= 0.01;
		            }else if (maxMotion<0.4 && damper > 0.003){
		                damper -= 0.003;
		            }else if(damper>0.0001) {
		                damper -=0.0001;
		            }
		        }
	        }
	        if(maxMotion<motionLimit && damping) {
	            damper=0;
	        }
		}
		
		/**
		 * 停止阻尼效果
		 */ 
		private function stopDamper(): void {
	        damping = false;
	        damper = 1.0;
	    }
		
		/**
		 * 重置阻尼效果
		 */ 
	    private function resetDamper(): void {
	        damping = true;
	        damper = 1.0;
	    }
	    
	    /**
	     * 设置当前拖动的节点
		 * 运动定时器不会改变“当前被拖动节点”的位置
	     * @param	s
	     */
	    public function setDragNode(s:Sprite):void{
	    	for each(var node:JVetex in this.nodes){
	    		if(node&&node.ui&&node.ui==s){
	    			this.dragNode=node;
	    			return;
	    		}
	    	}
	    }
	    
		/**
		 * 删除当前被拖动节点
		 * @param	s
		 */
	    public function delDragNode(s:Sprite):void{
	    	if(this.dragNode&&this.dragNode.ui&&this.dragNode.ui==s){
	    		this.dragNode=null;
	    		return;
	    	}
	    }
		
		/**
		 * 固定所有“孤立”节点
		 */
		public function fixOrphan():void{
			CollectionUtil.each(this.nodes,function(node:JVetex):void{
				if(node.degree==0){
					node.fixed=true;
				}
			});
		}
		
		/**
		 * 获取所有孤立节点
		 * @return
		 */
		public function getOrphans():Array {
			var result:Array = [];
			CollectionUtil.each(this.nodes,function(node:JVetex):void{
				if(node.degree==0){
					result.push(node);
				}
			});
			return result;
		}
	}
}