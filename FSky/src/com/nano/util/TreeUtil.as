package com.nano.util{
	import mx.collections.ArrayCollection;
	import mx.collections.XMLListCollection;
	import mx.controls.Tree;

	/**
	 * 树工具函数
	 * 该类提供过滤XML数据的一组工具方法，
	 * 该类不能实例化
	 * @author 章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  2011-01-24
	 */
	public class TreeUtil{
		
		public function TreeUtil(){
			throw new Error("不能实例化工具类TreeUtil");
		}
		
		/**
		 * 根据自定义过滤函数过滤树节点数据
		 * @param	tree
		 * @param	fn
		 * 				fn签名模板：fn(node:*)Boolean
		 * 				fn必须能接受一个参数item，此参数为当前遍历过程中的树节点。
		 * 				fn必须返回一个布尔型数值，以表示当前节点是否符合过滤条件
		 * 				如果fn返回true，当前会被包含在结果集中
		 * @return
		 */
		public static function filterBy(tree:Tree,fn:Function):Array{
			var data:*=tree.dataProvider;
			var result:Array=[];
			
			if(data is XMLListCollection){
				filterXML(data,fn,result);
			}else{
				filterObject(data,fn,result);
			}
			return result;
		}
		
		/**
		 * 私有
		 * 对XML节点进行过滤
		 * @param	data
		 * @param	fn
		 * @param	result
		 */
		private static function filterXML(data:XMLListCollection,fn:Function,result:Array):void{
			if(!data||!result){
				return;
			}
			for each (var obj:Object in data) {
				var temp:XMLList = null;
				if (obj is XML)
					temp = (obj as XML)..node;
				else if (obj is XMLList)
					temp = (obj as XMLList)..node;
				for each(var item:XML in temp){
					if(fn.call(fn, item)){
						result.push(item);
					}
				}
			}
		}
		
		/**
		 * 私有
		 * 递归过滤
		 * @param	data
		 * @param	fn
		 * @param	result
		 */
		private static function filterObject(data:Object,fn:Function,result:Array):void{
			if(!data||!result){
				return;
			}
			CollectionUtil.each(data,function(item:*):void{
				if(item){
					if(fn.call(fn,item)){
						result.push(item);
					}
					if(item.children){
						filterObject(item.children,fn,result);
					}
				}
			});
		}
		
		/**
		 * 根据指定的函数从树中删除节点
		 * @param	tree
		 * @param	fn
		 */
		public static function removeBy(tree:Tree,fn:Function):void{
			var data:*=tree.dataProvider;
			if(data is Array){
				data=data;
			}else if(data is ArrayCollection){
				data=data.source;
			}else{
				return;
			}
			doRemove(data as Array,fn);
		}
		
		/**
		 * 私有
		 * 递归删除
		 * @param	data
		 * @param	fn
		 */
		private static function doRemove(data:Array,fn:Function):void{
			if(!data||(data.length==0)){
				return;
			}
			CollectionUtil.each(data,function(item:*):void{	
				if(item){
					if(fn.call(fn,item)){
						var index:int=CollectionUtil.getIndex(data,item);
						CollectionUtil.rmItem(data,item);
					}
					if(item.children){
						doRemove(item.children,fn);
					}
				}
			});
		}
		
		/**
		 * 展开树中的所有节点
		 * 递归展开
		 * @param	tree
		 */
		public static function expandAll(tree:Tree,data:Object=null):void{
			tree.validateNow();
			FuncUtil.delay(function(...args):void{
				if(!data){
					var cache:ArrayCollection=tree.dataProvider as ArrayCollection;
					expandItem(tree,cache);
				}else{
					tree.expandChildrenOf(tree.root,true);
					var list:XMLList=null;
					if(data is XML){
						list=new XMLList(data as XML);
					}else if(data is XMLList){
						list=data as XMLList;
					}else{//不支持的数据格式，什么都不做
						return;
					}
					expandXml(tree,list);
				}
			},300);
		}
		
		private static function expandXml(tree:Tree,data:XMLList,expand:Boolean=true):void{
			for each(var n:XML in data){
				tree.expandItem(n,expand);
				if(n.children().length()>0){
					expandXml(tree,n.children());
				}
			}
		}
		
		/**
		 * 关闭树中的所有节点
		 * 递归关闭
		 * @param	tree
		 */
		public static function closeAll(tree:Tree,data:Object=null):void {
			tree.validateNow();
			FuncUtil.delay(function(...args):void{
				if(!data){
					var cache:ArrayCollection=tree.dataProvider as ArrayCollection;
					expandItem(tree,cache,false);
				}else{
					tree.expandChildrenOf(tree.root,true);
					var list:XMLList=null;
					if(data is XML){
						list=new XMLList(data as XML);
					}else if(data is XMLList){
						list=data as XMLList;
					}else{//不支持的数据格式，什么都不做
						return;
					}
					expandXml(tree,list,false);
				}
			},300);
		}
		
		/**
		 * 私有
		 * 递归展开/关闭节点
		 * @param	tree
		 * @param	data
		 * @param	expand
		 */
		private static function expandItem(tree:Tree,data:*,expand:Boolean=true):void{
			if(data){
				CollectionUtil.each(data,function(item:*):void{
					if(item){
						tree.expandItem(item,expand);
						if(item.children){
							expandItem(tree,item.children,expand);
						}
					}
				});
			}
		}
		
		/**
		 * 强制刷新指定tree的视图
		 * 注意：此方法首先关闭所有展开的节点，然后再次展开，效率比较差。请勿频繁调用。
		 * @param	tree
		 */
		public static function refreshView(tree:Tree):void{
			closeAll(tree);
			expandAll(tree);
		}
		
		/**
		 * 根据自定义的比较函数展开树节点
		 * @param	tree
		 * @param	fn	
		 * 				fn签名模板：fn(node:*)Boolean
		 * 				fn必须能接受一个参数item，此参数为当前遍历过程中的树节点。
		 * 				fn必须返回一个布尔型数值，以表示是否需要展开当前节点
		 * 				如果fn返回true，当前节点会被展开
		 * @param	recursive 是否递归展开，如果此参数为true，将会前序遍历树，展开所有符合条件的节点
		 */
		public static function expandBy(tree:Tree,fn:Function,recursive:Boolean=false):void{
			tree.validateNow();
			FuncUtil.delay(function(...args):void{
				var data:ArrayCollection=tree.dataProvider as ArrayCollection;
				doExpand(tree,data,fn,recursive);
			},300);
		}
		
		/**
		 * 私有
		 * 递归方法
		 * @param	tree
		 * @param	data
		 * @param	fn
		 * @param	recursive
		 */
		private static function doExpand(tree:Tree,data:*,fn:Function,recursive:Boolean=false):void{
			if(data){
				CollectionUtil.each(data,function(item:*):void{
					if(item){
						var result:Boolean=fn.call(fn,item);
						if(result){
							tree.expandItem(item,true);
						}
						if(recursive){
							if(item.children){
								doExpand(tree,item.children,fn,recursive);
							}
						}
					}
				});
			}
		}
		
		/**
		 * 根据自定义的比较函数选中树中的节点
		 * @param	tree
		 * @param	fn
		 * 				fn签名模板：fn(node:*)Boolean
		 * 				fn必须能接受一个参数item，此参数为当前遍历过程中的树节点。
		 * 				fn必须返回一个布尔型数值，以表示是否需要选中当前节点
		 * 				如果fn返回true，当前节点会被设置为选中状态
		 */
		public static function selectBy(tree:Tree,fn:Function):void{
			tree.validateNow();
			FuncUtil.delay(function(...args):void{
				var data:ArrayCollection=tree.dataProvider as ArrayCollection;
				doSelect(tree,data,fn);
			},300);
		}
		
		/**
		 * 私有
		 * 递归选中
		 * @param	tree
		 * @param	data
		 * @param	fn
		 */
		private static function doSelect(tree:Tree,data:*,fn:Function):void{
			if(data){
				CollectionUtil.each(data,function(item:*):Boolean{
					if(item){
						var result:Boolean=fn.call(fn,item);
						if(result){
							tree.selectedItem=item;
							return true;
						}
						if(item.children){
							doSelect(tree,item.children,fn);
						}
					}
					return false;
				},null,true);
			}
		}
	}
}