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
                
                /* �������ı����������ԣ��ö��ƵĶ����������Ը�ֵ���������ܹ��������ű�������������ȡֵ��������ʹ��ȫ�ֱ�������ȡֵ */
                document.getElementById('txtUserInput').obj = SetProperties(document.getElementById('txtUserInput'), $('txtUserValue'), 'typeAheadXML.js', true, true, true, true, "No matching Data", false, null);
            };
            
            /**
             * �����Է��������
             * @param {Object} xElem Ҫ��������ǰ��ʾ���ܵ��ı���
             * @param {Object} xHidden ��������ֵ��hiddenԪ��
             * @param {Object} xserverCode ��������URL
             * @param {Object} xignoreCase ���������к��Դ�Сд��
             * @param {Object} xmatchAnywhere �ַ�����ƥ������λ�õ��ı���
             * @param {Object} xmatchTextBoxWidth ƥ���ı����ȣ�
             * @param {Object} xshowNoMatchMessage����ʾ��ƥ����Ϣ��
             * @param {Object} xnoMatchingDataMessage��������ʾ����Ϣ
             * @param {Object} xuseTimeout��ѡ����ʾһ��ʱ����Ƿ����أ�
             * @param {Object} xtheVisibleTime��span���ִ򿪵�ʱ��
             */
            function SetProperties(xElem, xHidden, xserverCode, xignoreCase, xmatchAnywhere, xmatchTextBoxWidth, xshowNoMatchMessage, xnoMatchingDataMessage, xuseTimeout, xtheVisibleTime){
            
                /**
                 * ����xignoreCase,xmatchAnywhere�Ĵ���û���������б��ֲ���ֵ�����Ǳ����˵ȼ۵�������ʽ
                 * ��������ʽ��ʹ��i�����Դ�Сд��ʹ��^��ƥ���ַ����Ŀ�ʼλ��
                 * ����������������ʽ��������������ÿ�ε��ú���ʱʹ��if��䣬��������˵��������Щ
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
                /* ���¼�������������ı��� */
                AddHandler(xElem);
                return props;
            }
            /**
             * �����¼��������������û������롢�Ƿ��뿪���ı���
             */
            function AddHandler(objText){
                /* ���̰����ͷ� */
                objText.onkeyup = GiveOptions;
                objText.onblur = function(){
                    if (this.obj.useTimeout) 
                        StartTimeout();
                }
                
                /* Opera���������onkeyup�¼��������ķ�ʽ�������������ͬ��
                 * ������onkeyup�¼�ʱ��Opera�����ٰ�����ǰ�������ı�������ʾֵ
                 * ����ΪOpera���onkeypress�¼��������������������
                 */
                if (Browser.isOpera) {
                    objText.onkeypress = GiveOptions;
                }
            }
            
            //Listing 10.9
            var arrOptions = new Array();/* �ӷ�������ѯ�л�ȡ�����п���ѡ�� */
            var strLastValue = "";/* �ı����а����������ַ��� */
            var bMadeRequest;/* �����Ƿ��Ѿ����͵��������������س������͸��ӵ����󣬸ñ�־���Կ��ٴ���Ա���������ǾͲ��ز���ʹ����Google�����ĳ�ʱ������ */
            var theTextBox;/* �����ӵ�н�����ı�������� */
            var objLastActive;/* ��󼤻���ı��� ������û��л����ı����������������ȷ�����ݼ��Ƿ���Ҫ����ˢ�¡���һ��ӵ�ж���ı���Ĵ�����ʵ������������������Ҫ֪����һ���ı���ӵ�н��� */
            var currentValueSelected = -1;/* ������ѡ���б��selectedIndex���ƣ�-1��ʾû��ѡ�ѡ�� */
            var bNoResults = false;/* �Ƿ��н�����������ǾͲ��ط���ȥ��ͼ�ҵ��κν���� */
            var isTiming = false;/* ����ȷ��ҳ�����Ƿ�������һ����ʱ���������һ��ʱ����û�в���ѡ���б���ô���е������ʱ���Ὣѡ���б���û����������������� */
            
			/**
             * ����û����� 
             * @param {Object} e
             */
            function GiveOptions(e){
            
                /* ���¼��ļ����� */
                var intKey = -1;
                /* ����û����� */
				if (window.event) {
                    intKey = event.keyCode;
                    theTextBox = event.srcElement;
                }
                else {
                    intKey = e.which;
                    theTextBox = e.target;
                }
                
                /* ���ö�ʱ�� */
                if (theTextBox.obj.useTimeout) {
                    if (isTiming) {
						EraseTimeout();/* ȡ����ʱ�� */
					}
					/* ������ʱ�� */
                    StartTimeout();
                }
                
                /* ȷ���Ƿ�����ı� ������ı��򲻰����ı������������б�*/
                if (theTextBox.value.length == 0 && !Browser.isOpera) {
                    arrOptions = new Array();
                    HideTheBox();
                    strLastValue = "";
                    return false;
                }
                
                /* �ڼ��س�������ͷ��֮ǰ����Ҫ��֤��ǰ������ı����Ƿ�����󼤻���ı��� */
                if (objLastActive == theTextBox) {
                    if (intKey == 13) {/* �س��� */
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
				
				/* ����������
				 * ʹ�ýű�����Ļ��ƣ������ƻ��ͺȼ��ٷ������ĸ���
				 * ����ִ��һЩ������鿴�Ƿ�õ����µĽ��
				 */
				/* ���ʷ�������ȡֵ �������Щ����е��κ�һ��ͨ���ˣ�����Ҫ���������Ի�ȡ����*/
                if (objLastActive != theTextBox/* ȷ����󼤻���ı����Ƿ��ǵ�ǰӵ�н�����ı��� */ 
					|| theTextBox.value.indexOf(strLastValue) != 0 /* ����ı�����������ı�����һ����ͬ��ֻ����ĩβ��������һЩ���� */
					|| ((arrOptions.length == 0 || arrOptions.length == 15) && !bNoResults)/* ���û�н�������߽����������15�����߸��ٵ�Ԫ�� */ 
               		|| (theTextBox.value.length <= strLastValue.length)/* ȷ����ǰ�ĳ��ȴ������ĳ��� */) {
                    objLastActive = theTextBox;
                    bMadeRequest = true;
                    TypeAhead(theTextBox.value);
                }
                else if (!bMadeRequest) {/* ʹ���Ѿ��ӷ�������ȡ���б� */
                    BuildList(theTextBox.value);
                }
				
				/* �����û����� �������ʹ�á� */
                strLastValue = theTextBox.value;
            }
            
            /* ��������������� */
            function TypeAhead(xStrText){
                var strParams = "q=" + xStrText + "&where=" + theTextBox.obj.matchAnywhere;
                new Sky.Ajax({
                	url: theTextBox.obj.serverCode,
                	params: strParams,
                	onload: BuildChoices,
                	method: "POST"
                });
            }
            
            /* ��resopnseText����ת��Ϊ���� */
            function BuildChoices(data){
                eval(data);
                BuildList(strLastValue);
                bMadeRequest = false;/* ֪ͨ�ű����������ַ��͵��������������Ѿ���� */
            }
            
            /* �������ʽ��Ϊ����ʾ�ĸ�ʽ */
            function BuildList(theText){
                SetElementPosition(theTextBox);/* ����Ԫ�ص�λ�ã���Ҫ��spanԪ�ض�̬��λΪֱ��λ��ʵ������ǰ��ʾ���ܵ��ı���ĵײ� */
                var theMatches = MakeMatches(theText);/* ��ʽ��ƥ����ı� */
                theMatches = theMatches.join("");
                if (theMatches.length > 0) {/* ��ʾ��� */
                    document.getElementById("spanOutput").innerHTML = theMatches;
                    document.getElementById("OptionsList_0").className = "spanHighElement";
                    currentValueSelected = 0;
                    bNoResults = false;
                }
                else {/* ��ʾû��ƥ�� */
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
            
            /* ��̬����δ��λԪ�ص�λ�� */
            function SetElementPosition(theTextBoxInt){
                var selectedPosX = 0;
                var selectedPosY = 0;
                var theElement = theTextBoxInt;
                if (!theElement) 
                    return;
                var theElemHeight = theElement.offsetHeight;
                var theElemWidth = theElement.offsetWidth;
				/* �����ĵ�������ȡԪ��������丸�ڵ���X,Y�����ϵ�λ�á�ͨ������ÿһ���Ѷ�λ�ĸ��ڵ㣬���Ӷ�Ӧ�ڸ��ڵ�λ�õ�ƫ���������ܹ��õ�Ԫ�ص�׼ȷλ�� */
                while (theElement != null) {
                    selectedPosX += theElement.offsetLeft;
                    selectedPosY += theElement.offsetTop;
                    theElement = theElement.offsetParent;
                }
				
				
                xPosElement = document.getElementById("spanOutput");
                xPosElement.style.left = selectedPosX;
                if (theTextBoxInt.obj.matchTextBoxWidth) {/* ƥ���ı���Ŀ�� */
					xPosElement.style.width = theElemWidth;
				}
                xPosElement.style.top = selectedPosY + theElemHeight;
                xPosElement.style.display = "block";/* ��ʾ�����б� */
                if (theTextBoxInt.obj.useTimeout) {
                    xPosElement.onmouseout = StartTimeout;
                    xPosElement.onmouseover = EraseTimeout;
                }
                else {
                    xPosElement.onmouseout = null;
                    xPosElement.onmouseover = null;
                }
            }
            
            /* ʹ�����������ƽ������ */
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
            
            
            /* �����ַ��� */ 
            var undeStart = "<span class='spanMatchText'>";/* span����ʼ��ǩ */
            var undeEnd = "</span>";/* span�Ľ�����ǩ */
			/* �������ṩ������ȷ����Ԫ���Ƿ񱻵��  */
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
            
            /* ���� */
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
            
            /* �����ͷ��������¼� */
            function SetText(xVal){
                theTextBox.value = arrOptions[xVal][0]; //set text value
                theTextBox.obj.hidden.value = arrOptions[xVal][1];
                document.getElementById("spanOutput").style.display = "none";
                currentValueSelected = -1; //remove the selected index
            }
            
			/* ��ȡѡ����Ŀ���ı���ֵ */
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