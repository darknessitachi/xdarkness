<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
    <head>
        <title>Type Ahead</title>
        <%@ include file="/views/js/importjs.jsp" %>
        <script type="text/javascript">
            
            window.onload = function(){
                var elemSpan = document.createElement("span");
                elemSpan.id = "spanOutput";
                elemSpan.className = "spanTextDropdown";
                document.body.appendChild(elemSpan);
                
                /* 用来给文本框设置属性，用定制的对象给这个属性赋值，这样就能够在整个脚本中引用它来获取值，而不是使用全局变量来获取值 */
                document.getElementById('txtUserInput').obj = SetProperties(document.getElementById('txtUserInput'), $('txtUserValue'), 'typeAheadXML.js', true, true, true, true, "No matching Data", false, null);
            };
            
            /**
             * 将属性分配给对象
             * @param {Object} xElem 要分配输入前提示功能的文本框
             * @param {Object} xHidden 用来保存值的hidden元素
             * @param {Object} xserverCode 服务器端URL
             * @param {Object} xignoreCase 搜索过程中忽略大小写？
             * @param {Object} xmatchAnywhere 字符串中匹配任意位置的文本？
             * @param {Object} xmatchTextBoxWidth 匹配文本框宽度？
             * @param {Object} xshowNoMatchMessage　显示无匹配消息？
             * @param {Object} xnoMatchingDataMessage　用于显示的消息
             * @param {Object} xuseTimeout　选项显示一段时间后是否隐藏？
             * @param {Object} xtheVisibleTime　span保持打开的时间
             */
            function SetProperties(xElem, xHidden, xserverCode, xignoreCase, xmatchAnywhere, xmatchTextBoxWidth, xshowNoMatchMessage, xnoMatchingDataMessage, xuseTimeout, xtheVisibleTime){
            
                /**
                 * 对于xignoreCase,xmatchAnywhere的处理，没有在属性中保持布尔值，而是保存了等价的正则表达式
                 * 在正则表达式中使用i来忽略大小写，使用^来匹配字符串的开始位置
                 * 在这里设置正则表达式参数，而不是在每次调用函数时使用if语句，对我们来说更加容易些
                 */
                var props = {
                    elem: xElem,
                    hidden: xHidden,
                    serverCode: xserverCode,
                    regExFlags: ((xignoreCase) ? "i" : ""),
                    regExAny: ((xmatchAnywhere) ? "" : "^"),
                    matchAnywhere: xmatchAnywhere,
                    matchTextBoxWidth: xmatchTextBoxWidth,
                    theVisibleTime: xtheVisibleTime,
                    showNoMatchMessage: xshowNoMatchMessage,
                    noMatchingDataMessage: xnoMatchingDataMessage,
                    useTimeout: xuseTimeout
                };
                /* 将事件处理函数分配给文本框 */
                AddHandler(xElem);
                return props;
            }
            /**
             * 附加事件处理函数，监听用户的输入、是否离开了文本框
             */
            function AddHandler(objText){
                /* 键盘按键释放 */
                objText.onkeyup = GiveOptions;
                objText.onblur = function(){
                    if (this.obj.useTimeout) 
                        StartTimeout();
                }
                
                /* Opera浏览器出发onkeyup事件处理函数的方式与其他浏览器不同。
                 * 当触发onkeyup事件时，Opera不会再包括当前按键的文本框中显示值
                 * 我们为Opera添加onkeypress事件处理函数纠正了这个问题
                 */
                if (Browser.isOpera) {
                    objText.onkeypress = GiveOptions;
                }
            }
            
            //Listing 10.9
            var arrOptions = new Array();/* 从服务器查询中获取的所有可用选项 */
            var strLastValue = "";/* 文本框中包含的最后的字符串 */
            var bMadeRequest;/* 请求是否已经发送到服务器，而不必持续发送附加的请求，该标志正对快速打字员，这样我们就不必操心使用像Google那样的超时设置了 */
            var theTextBox;/* 保存对拥有焦点的文本框的引用 */
            var objLastActive;/* 最后激活的文本框 ，如果用户切换了文本框，这个变量将用来确定数据集是否需要重新刷新。在一个拥有多个文本框的窗口中实现这个解决方案，就需要知道哪一个文本框拥有焦点 */
            var currentValueSelected = -1;/* 作用于选择列表的selectedIndex类似，-1表示没有选项被选中 */
            var bNoResults = false;/* 是否有结果？这样我们就不必费心去试图找到任何结果了 */
            var isTiming = false;/* 允许确定页面上是否运行了一个定时器，如果在一段时间内没有操作选项列表，那么运行的这个定时器会将选项列表从用户的视线中隐藏起来 */
            
			/**
             * 检测用户按键 
             * @param {Object} e
             */
            function GiveOptions(e){
            
                /* 按下键的键编码 */
                var intKey = -1;
                /* 检测用户按键 */
				if (window.event) {
                    intKey = event.keyCode;
                    theTextBox = event.srcElement;
                }
                else {
                    intKey = e.which;
                    theTextBox = e.target;
                }
                
                /* 重置定时器 */
                if (theTextBox.obj.useTimeout) {
                    if (isTiming) {
						EraseTimeout();/* 取消定时器 */
					}
					/* 重启定时器 */
                    StartTimeout();
                }
                
                /* 确定是否存在文本 ，如果文本框不包含文本，隐藏下拉列表*/
                if (theTextBox.value.length == 0 && !Browser.isOpera) {
                    arrOptions = new Array();
                    HideTheBox();
                    strLastValue = "";
                    return false;
                }
                
                /* 在检测回车键、箭头键之前，需要验证当前激活的文本框是否是最后激活的文本框 */
                if (objLastActive == theTextBox) {
                    if (intKey == 13) {/* 回车键 */
                        GrabHighlighted();
                        theTextBox.blur();
                        return false;
                    }
                    else if (intKey == 38) {
                        MoveHighlight(-1);
                        return false;
                    }
                    else if (intKey == 40) {
                        MoveHighlight(1);
                        return false;
                    }
                }
				
				/* 处理按键操作
				 * 使用脚本缓存的机制，来限制回送喝减少服务器的副段
				 * 我们执行一些检查来查看是否得到了新的结果
				 */
				/* 访问服务器获取值 ，如果这些检查中的任何一个通过了，就需要检查服务器以获取数据*/
                if (objLastActive != theTextBox/* 确定最后激活的文本框是否是当前拥有焦点的文本框 */ 
					|| theTextBox.value.indexOf(strLastValue) != 0 /* 检查文本框中输入的文本与上一次相同，只是在末尾处附加了一些内容 */
					|| ((arrOptions.length == 0 || arrOptions.length == 15) && !bNoResults)/* 如果没有结果，或者结果集包含了15个或者更少的元素 */ 
               		|| (theTextBox.value.length <= strLastValue.length)/* 确保当前的长度大于最后的长度 */) {
                    objLastActive = theTextBox;
                    bMadeRequest = true;
                    TypeAhead(theTextBox.value);
                }
                else if (!bMadeRequest) {/* 使用已经从服务器获取的列表 */
                    BuildList(theTextBox.value);
                }
				
				/* 保存用户输入 到“最近使用” */
                strLastValue = theTextBox.value;
            }
            
            /* 发送请求道服务器 */
            function TypeAhead(xStrText){
                var strParams = "q=" + xStrText + "&where=" + theTextBox.obj.matchAnywhere;
                new Sky.Ajax({
                	url: theTextBox.obj.serverCode,
                	params: strParams,
                	onload: BuildChoices,
                	method: "POST"
                });
            }
            
            /* 将resopnseText属性转换为数组 */
            function BuildChoices(data){
                eval(data);
                BuildList(strLastValue);
                bMadeRequest = false;/* 通知脚本的其他部分发送到服务器的请求已经完成 */
            }
            
            /* 将结果格式化为可显示的格式 */
            function BuildList(theText){
                SetElementPosition(theTextBox);/* 设置元素的位置，需要将span元素动态定位为直接位于实现输入前提示功能的文本框的底部 */
                var theMatches = MakeMatches(theText);/* 格式化匹配的文本 */
                theMatches = theMatches.join("");
                if (theMatches.length > 0) {/* 显示结果 */
                    document.getElementById("spanOutput").innerHTML = theMatches;
                    document.getElementById("OptionsList_0").className = "spanHighElement";
                    currentValueSelected = 0;
                    bNoResults = false;
                }
                else {/* 显示没有匹配 */
                    currentValueSelected = -1;
                    bNoResults = true;
                    if (theTextBox.obj.showNoMatchMessage) 
                        document.getElementById("spanOutput").innerHTML = "<span class='noMatchData'>" +
                        theTextBox.obj.noMatchingDataMessage +
                        "</span>";
                    else 
                        HideTheBox();
                }
            }
            
            /* 动态查找未定位元素的位置 */
            function SetElementPosition(theTextBoxInt){
                var selectedPosX = 0;
                var selectedPosY = 0;
                var theElement = theTextBoxInt;
                if (!theElement) 
                    return;
                var theElemHeight = theElement.offsetHeight;
                var theElemWidth = theElement.offsetWidth;
				/* 遍历文档树，获取元素想对于其父节点在X,Y方向上的位置。通过遍历每一个已定位的父节点，增加对应于父节点位置的偏移量，就能够得到元素的准确位置 */
                while (theElement != null) {
                    selectedPosX += theElement.offsetLeft;
                    selectedPosY += theElement.offsetTop;
                    theElement = theElement.offsetParent;
                }
				
				
                xPosElement = document.getElementById("spanOutput");
                xPosElement.style.left = selectedPosX;
                if (theTextBoxInt.obj.matchTextBoxWidth) {/* 匹配文本框的宽度 */
					xPosElement.style.width = theElemWidth;
				}
                xPosElement.style.top = selectedPosY + theElemHeight;
                xPosElement.style.display = "block";/* 显示下拉列表 */
                if (theTextBoxInt.obj.useTimeout) {
                    xPosElement.onmouseout = StartTimeout;
                    xPosElement.onmouseover = EraseTimeout;
                }
                else {
                    xPosElement.onmouseout = null;
                    xPosElement.onmouseover = null;
                }
            }
            
            /* 使用正则来限制结果数量 */
            var countForId = 0;
            function MakeMatches(xCompareStr){
                countForId = 0;
                var matchArray = new Array();
                var regExp = new RegExp(theTextBox.obj.regExAny + xCompareStr, theTextBox.obj.regExFlags);
                for (i = 0; i < arrOptions.length; i++) {
                    var theMatch = arrOptions[i][0].match(regExp);
                    if (theMatch) {
                        matchArray[matchArray.length] = CreateUnderline(arrOptions[i][0], xCompareStr, i);
                    }
                }
                return matchArray;
            }
            
            
            /* 操作字符串 */ 
            var undeStart = "<span class='spanMatchText'>";/* span的起始标签 */
            var undeEnd = "</span>";/* span的结束标签 */
			/* 容器，提供背景及确定单元格是否被点击  */
            var selectSpanStart = "<span style='width:100%;display:block;' class='spanNormalElement' onmouseover='SetHighColor(this)' ";
            var selectSpanEnd = "</span>";
            function CreateUnderline(xStr, xTextMatch, xVal){
                selectSpanMid = "onclick='SetText(" + xVal + ")'" +
                	"id='OptionsList_" + countForId + "' theArrayNumber='" + xVal + "'>";
                var regExp = new RegExp(theTextBox.obj.regExAny + xTextMatch, theTextBox.obj.regExFlags);
                var aStart = xStr.search(regExp);
                var matchedText = xStr.substring(aStart, aStart + xTextMatch.length);
                countForId++;
                return selectSpanStart + selectSpanMid + xStr.replace(regExp, undeStart + matchedText + undeEnd) + selectSpanEnd;
            }
            
            /* 高亮 */
            function MoveHighlight(xDir){
                if (currentValueSelected >= 0) {
                    newValue = parseInt(currentValueSelected) + parseInt(xDir);
                    if (newValue > -1 && newValue < countForId) {
                        currentValueSelected = newValue;
                        SetHighColor(null);
                    }
                }
            }
            
            function SetHighColor(theTextBox){
                if (theTextBox) {
                    currentValueSelected = theTextBox.id.slice(theTextBox.id.indexOf("_") + 1, theTextBox.id.length);
                }
                for (i = 0; i < countForId; i++) {
                    document.getElementById('OptionsList_' + i).className = 'spanNormalElement';
                }
                document.getElementById('OptionsList_' +
                currentValueSelected).className = 'spanHighElement';
            }
            
            /* 处理箭头、鼠标点击事件 */
            function SetText(xVal){
                theTextBox.value = arrOptions[xVal][0]; //set text value
                theTextBox.obj.hidden.value = arrOptions[xVal][1];
                document.getElementById("spanOutput").style.display = "none";
                currentValueSelected = -1; //remove the selected index
            }
            
			/* 获取选中条目的文本和值 */
            function GrabHighlighted(){
                if (currentValueSelected >= 0) {
                    xVal = document.getElementById("OptionsList_" + currentValueSelected).getAttribute("theArrayNumber");
                    SetText(xVal);
                    HideTheBox();
                }
            }
            
            function HideTheBox(){
                document.getElementById("spanOutput").style.display = "none";
                currentValueSelected = -1;
                EraseTimeout();
            }
            
            //Listing 10.19
            function EraseTimeout(){
                clearTimeout(isTiming);
                isTiming = false;
            }
            
            function StartTimeout(){
                isTiming = setTimeout("HideTheBox()", theTextBox.obj.theVisibleTime);
            }
        </script>
        <style type="text/css">
            /* Listing 10.5--> */
            span.spanTextDropdown {
                position: absolute;
                top: 0px;
                left: 0px;
                width: 150px;
                z-index: 101;
                background-color: #C0C0C0;
                border: 1px solid #000000;
                padding-left: 2px;
                overflow: visible;
                display: none;
            }
            
            span.spanMatchText {
                text-decoration: underline;
                font-weight: bold;
            }
            
            span.spanNormalElement {
                background: #C0C0C0;
            }
            
            span.spanHighElement {
                background: #000040;
                color: white;
                cursor: pointer;
            }
            
            span.noMatchData {
                font-weight: bold;
                color: #0000FF;
            }
        </style>
    </head>
    <body>
 AutoComplete Text Box:<input type="text" id="txtUserInput"/><input type="hidden" id="txtUserValue" ID="hidden1" />
    </body>
</html>