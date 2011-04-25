<%@ page language="java" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@include file="../../views/js/importjs.jsp" %>
<%@ taglib prefix="sky" uri="/WEB-INF/tags/sky.tld" %>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Framework�ؼ�API�ĵ�</title>
        <link rel="stylesheet" type="text/css" href="resources/docs.css" />
	<script type="text/javascript" src="resources/docs.js"></script>
        <script type="text/javascript" src="output/tree.js"></script>
	<script type="text/javascript">
		var root = {
			'text':' API�ĵ�','pid':'-1',icon:'api',
			'children':[{
				text:'���ӻ��ؼ�',icon:'pkg',
				children:[{
				text: ' �Ի���Dialog��',icon:'cmp', 
				action: function(){
					addTab({
						MENUID: 'dialog',
						PAGE_URL: '/Framework/demo/output/Dialog.jsp',
						MENU_NAME: '�Ի���'//,
						//ICON
					});
				}
			},{
				text: ' ���ڡ�Datepicker��',icon:'cmp', 
				action: function(){
					addTab({
						MENUID: 'Datepicker',
						PAGE_URL: '/Framework/demo/output/Datepicker.jsp',
						MENU_NAME: '����'//,
						//ICON
					});
				}
			},{
				text: ' ����Tree��',icon:'cmp', 
				action: function(){
					addTab({
						MENUID: 'Tree',
						PAGE_URL: '/Framework/demo/output/Tree.jsp',
						MENU_NAME: '��'//,
						//ICON
					});
				}
			},{
				text: ' ��ť��Button��',icon:'cmp', 
				action: function(){
					addTab({
						MENUID: 'Button',
						PAGE_URL: '/Framework/demo/output/Button.jsp',
						MENU_NAME: '��ť'//,
						//ICON
					});
				}
			},{
				text: ' ������Select��',icon:'cmp', 
				action: function(){
					addTab({
						MENUID: 'Select',
						PAGE_URL: '/Framework/demo/output/Select.jsp',
						MENU_NAME: '������'//,
						//ICON
					});
				}
			},{
				text: ' ѡ�ˡ�SelectUser��',icon:'cmp', 
				action: function(){
					addTab({
						MENUID: 'SelectUser',
						PAGE_URL: '/Framework/demo/output/ChooseUser.jsp',
						MENU_NAME: 'ѡ��'//,
						//ICON
					});
				}
			},{
				text: ' ���Datagrid��',icon:'cmp', 
				action: function(){
					addTab({
						MENUID: 'Datagrid',
						PAGE_URL: '/Framework/demo/output/Datagrid.jsp',
						MENU_NAME: '���'//,
						//ICON
					});
				}
			}]
			},{
				text:'������', icon:'pkg',
				children:[{
					text: 'Browser', icon:'class',
					action: function(){
						addTab({
							MENUID: 'Browser',
							PAGE_URL: '/Framework/demo/output/Browser.html',
							MENU_NAME: '���������'//,
							//ICON
						});
					}
				},{
					text: 'Sky', icon:'class',
					action: function(){
						addTab({
							MENUID: 'Sky',
							PAGE_URL: '/Framework/demo/output/Sky.html',
							MENU_NAME: '���Ķ���'//,
							//ICON
						});
					}
				},{
					text: 'ExtElement', icon:'class',
					action: function(){
						addTab({
							MENUID: 'ExtElement',
							PAGE_URL: '/Framework/demo/output/ExtElement.html',
							MENU_NAME: 'DOM��չ����'//,
							//ICON
						});
					}
				},{
					text: 'Page', icon:'class',
					action: function(){
						addTab({
							MENUID: 'Page',
							PAGE_URL: '/Framework/demo/output/Page.html',
							MENU_NAME: 'ҳ�����'//,
							//ICON
						});
					}
				},{
					text: 'MultipleSelect', icon:'class',
					action: function(){
						addTab({
							MENUID: 'MultipleSelect',
							PAGE_URL: '/Framework/demo/output/MultipleSelect.html',
							MENU_NAME: '����ѡ��'//,
							//ICON
						});
					}
				}]
			},{
				text:'������', icon:'pkg',
				children:[{
					'text': 'Array', icon:'class',
					action: function(){
						addTab({
							MENUID: 'Array',
							PAGE_URL: '/Framework/demo/output/Array.html',
							MENU_NAME: '����'//,
							//ICON
						});
					}
				},{
					'text': 'Checkbox', icon:'class'
				},{
					'text': 'common', icon:'class'
				},{
					'text': 'Date', icon:'class'
				},{
					'text': 'Money', icon:'class'
				},{
					'text': 'Select', icon:'class'
				},{
					'text': 'String', icon:'class',
					action: function(){
						addTab({
							MENUID: 'String',
							PAGE_URL: '/Framework/demo/output/String.html',
							MENU_NAME: '�ַ���'//,
							//ICON
						});
					}
				}]
			}]
		};
		
		Page.onLoad(function(){
			var tree = new TreePanel({
				'root' : root,
				renderTo: 'tree',
				iconPath: "<%=request.getContextPath()%>/Framework/resources/images/default/tree/"		
			});
			
			tree.icon['api'] = 'docs.gif';
			tree.icon['cmp'] = 'cmp.gif';
			tree.icon['class'] = 'class.gif';
			tree.icon['pkg'] = 'pkg.gif';
		
			tree.render();
		});
	</script>
	<script>
		var leftmenuWidth=350;//���˵����Ŀ��;
		var topMenuHeight=83;
		var topTabHeight=30;

		Page.onLoad(function(){
			$('tree').style.height = document.body.clientHeight-topMenuHeight;
		});
		//ȷ��MainFrame�ĳ���
		function resizeMainFrame(iframe){
		    iframe.style.width=document.body.clientWidth-leftmenuWidth;
		    iframe.style.height=document.body.clientHeight-topMenuHeight-topTabHeight;
		}
		
		Page.onLoad(function(){
			resizeMainFrame($('iframeAPIHome'));
		});
		
		Tab.BasePath = "<%=request.getContextPath()%>/";

		function addTab(menu){
		   
		   	var tabId = menu["MENUID"]+"Tab";
		   	if(Tab['tab'].Tabs.contains(tabId)){
		   		Tab.activeTab(tabId, true);
				return;
			}
		   
		    var div=document.createElement("div");
		    div.id=menu["MENUID"]+"Div";
		    $("tabBarTd").appendChild(div);
		    
		    var iframe=document.createElement("iframe");
		    
		    iframe.id=menu["MENUID"]+"Frame";
		    iframe.scrolling="auto";
		    iframe.frameBorder="no";
		    iframe.border=0;
		    div.appendChild(iframe);
		    iframe.src="<%=request.getContextPath()%>"+menu["PAGE_URL"];
		    
		    resizeMainFrame(iframe,leftmenuWidth);
		    
		    
		    
		    
		    var _title = menu["MENU_NAME"];
		    if(_title.length>4) {
		    	_title = _title.substring(0,4);
		    }
		    
		    Tab.addTab('tab', {
						id: menu["MENUID"]+"Tab",
						title: _title,
						tip: menu["MENU_NAME"],
		                //icon: "<%=request.getContextPath()%>/Framework/resources/menuIcons/"+menu["ICON"],
		                renderTo: div.id,
						showClose: true
			});
		 }
	</script>
	<style type="text/css">
	#header{
	   border:0 none;
	   background:#1E4176 url(./resources/hd-bg.gif) repeat-x 0 0;
	   padding-top:3px;
	   padding-left:3px;
	   padding-bottom:3px;
	}
	#header .api-title {
	    font:normal 16px tahoma, arial, sans-serif;
	    color:white;
	    margin:5px;
	}
	#docs{
		background-color: #dfe8f6
	}
	.xtb-sep {
		background-image: url(Framework/demo/resources/blue-split.gif);
		background-position: center;
		background-repeat: no-repeat;
		display: block;
		font-size: 1px;
		height: 16px;
		width:4px;
		overflow: hidden;
		cursor:default;
		margin: 0 2px 0;
		border:0;
	}
	.xtb-spacer {
	    width:2px;
	}
	.x-toolbar-cell {
	    vertical-align:middle;
	}
	</style>
  </head>
<body scroll="no" id="docs">
  <div id="header">
	<div class="api-title">Framework 1.0 API �ĵ�</div>
  </div>
<div style="padding-left:5px;padding-top:5px;">
	<table>
		<TR class="x-toolbar-left-row">
			<TD class="x-toolbar-cell">
				<INPUT class=" x-form-text x-form-field x-form-empty-field" style="WIDTH: 200px" autocomplete="off" value="�������" />
			</TD>
			<TD class="x-toolbar-cell">
				<DIV class="xtb-spacer"></DIV>
			</TD>
			<TD class="x-toolbar-cell">
				<BUTTON class=" x-btn-text icon-expand-all"  
						title="Expand All">&nbsp;</BUTTON>
								
			</TD>
			<TD class="x-toolbar-cell">
				<SPAN class="xtb-sep"></SPAN>
			</TD>
			<TD class="x-toolbar-cell">
				<BUTTON class=" x-btn-text icon-collapse-all"
						title="Collapse All">&nbsp;</BUTTON>
							
			</TD>
			<td valign="top" id="tabBarTd" rowspan="2">
            	<sky:tab id="tab" max="50" showToolBar="true">
                	<sky:childtab src="" id="ImageUpload" title="API����" showClose="true" icon="Framework/resources/icons/icon030a13.gif" selected="true">
						<iframe src="" id="iframeAPIHome"></iframe>
					</sky:childtab>
               	</sky:tab>
			</td>
		</TR>
		<tr>
			<td colspan="5">
				<div style="padding-top:5px;background-color: #ffffff;">
					<div id="tree" style="height:700px;width:300px;padding: 5px 0 0 5px;"></div>
				</div>	
			</td>
		</tr>
	</table>
	</div>
</body>
</html>
