var Grid = { 
    dragobj : null, 
    cloneobj : null, 
	/*
	 *{
	 *	id,//容器对象，字符串 
	 *	pageSize, //分页大小,整数 
	 *	minirows,//最少行数，整数 
	 *	showAutoNum,//是否显示自动编号,boolean值 
	 *	rowInterval,//是否隔行,boolean值 
	 *	hilight,//是否高亮,boolean值 
	 *	sortby,//排序规则，0表示降序或1表示升序
	 *	columnArr,//栏目，数组
	 *	dataArr,//数据，数组 
	 *	sortable//确定哪些列可以排序，数组
	 *}
	 */
    init : function(config){ 
        this.container = $(config.id);  
        this.pageSize = config.pageSize || 15;
        this.minirows = config.minirows || this.pageSize; 
        this.rowInterval = config.rowInterval || true; 
        this.hilight = config.hilight || true; 
        this.showAutoGridNum = config.showAutoNum || true; 
        this.sortBy = config.sortby || 1;  
        this.column = config.columnArr;  
        this.data = config.dataArr; 
        this.selectedRowIndex = 0; //当前选中行的索引，整数 
        this.startPage = 0; //当前分页号，整数
        Grid.setSortAble(config.sortable);  
        Grid.drawGrid(); 
        document.onmousemove = Grid.drag; 
        document.onmouseup = Grid.end; 
    }, 
    setSortAble : function(_sortable){ 
        this.sortColumn = []; 
        if(_sortable){ 
            this.sortColumn = _sortable; 
        }else{ 
            this.sortColumn[0] = false; 
            for(var i=0;i<this.column.length;i++){ 
                this.sortColumn[i] = true; 
            } 
        } 
    }, 
    start:function(e){ 
        if(!e) e = window.event; 
        var obj = Grid.getTarget(e); 
        var objOffsetLeft = Grid.getRealPosition(obj).x; 
        var objOffsetTop = Grid.getRealPosition(obj).y; 
        Grid.dragobj = obj.cloneNode(true); 
        Grid.cloneobj = obj; 
        Grid.dragobj.style.position = "absolute"; 
        Grid.dragobj.style.left = objOffsetLeft + "px"; 
        Grid.dragobj.style.top = objOffsetTop + "px"; 
        obj.parentNode.appendChild(Grid.dragobj); 
        Grid.initMouseX = Grid.getMousePosition(e); 
    }, 
    drag : function(e) { 
        if(Grid.cloneobj==null)return; 
        if(!e) e = window.event; 
        Grid.finalMouseX = Grid.getMousePosition(e); 

        var objPrevOffsetLeft = Grid.getRealPosition(Grid.cloneobj.previousSibling).x; 
        var objNextOffsetLeft = Grid.getRealPosition(Grid.cloneobj.nextSibling).x; 
        var realOffset = Grid.finalMouseX - Grid.initMouseX; 
        if(Grid.finalMouseX < objPrevOffsetLeft +2){ 
            Grid.finalMouseX = objPrevOffsetLeft + 2; 
        }else if(Grid.finalMouseX > objNextOffsetLeft + Grid.cloneobj.nextSibling.offsetWidth - 2){ 
            Grid.finalMouseX = objNextOffsetLeft + Grid.cloneobj.nextSibling.offsetWidth - 2; 
        } 
        Grid.dragobj.style.left = Grid.finalMouseX + "px"; 
    }, 
    end : function(e) { 
        if(Grid.cloneobj==null)return; 
        var obj = Grid.cloneobj; 
        obj.previousSibling.style.width = obj.previousSibling.offsetWidth + (Grid.finalMouseX - Grid.initMouseX) + "px"; 
        obj.nextSibling.style.width = obj.nextSibling.offsetWidth - (Grid.finalMouseX - Grid.initMouseX) + "px"; 
        Grid.resizeWidth(obj); 
        Grid.cloneobj = null; 
        Grid.dragobj.parentNode.removeChild(Grid.dragobj); 
    }, 
    resizeWidth : function(obj){ 
        var j = this.getObjectIndex(this.header,obj); 
        for(var i=1;i<this.container.childNodes.length;i++){ 
            if(this.container.childNodes[i].className == 'pDiv') continue;
            if(j==1){ 
                this.container.childNodes[i].childNodes[(j-1)/2].style.width = this.container.childNodes[0].childNodes[j-1].offsetWidth + "px"; 
            }else{ 
                this.container.childNodes[i].childNodes[(j-1)/2].style.width = this.container.childNodes[0].childNodes[j-1].offsetWidth + 1 + "px"; 
            } 
            this.container.childNodes[i].childNodes[(j-1)/2+1].style.width = this.container.childNodes[0].childNodes[j+1].offsetWidth + 1 + "px";     
        } 
    }, 
    drawGrid : function(){ 
        Grid.drawHeader(); 
		Grid.drawPageBar();
		Grid.fillData(1); 
    }, 
    drawHeader : function(){ 
        var oDiv = document.createElement("div"); 
        this.container.appendChild(oDiv); 
        this.header = oDiv; 
        oDiv.className = "grid_head_row"; 
        oDiv.id = "grid_head_row"; 
        Grid.drawColumn(); 
    }, 
    drawColumn : function(){ 
        var totalWidth = 0; 
        for(var i=0;i<this.column.length;i++){ 
            if(i==0){ 
                var oDiv = document.createElement("div");     
                this.header.appendChild(oDiv); 
                oDiv.className = "grid_column grid_column0"; 
                oDiv.id = "grid_head_column0"; 
                oDiv.innerHTML = this.column[0]; 
                oDiv.style.width = oDiv.offsetWidth*2;
                totalWidth += oDiv.offsetWidth; 
            }else{ 
                var oDiv = document.createElement("div"); 
                this.header.appendChild(oDiv); 
                oDiv.className = "grid_head_split"; 
                oDiv.onmousedown = Grid.start; 
                totalWidth += oDiv.offsetWidth; 

                oDiv = document.createElement("div"); 
                this.header.appendChild(oDiv); 
                oDiv.className = "grid_column grid_column" + i; 
                oDiv.id = "grid_head_column" + i; 
                oDiv.innerHTML = this.column[i]; 
                //if(i < this.column.length - 1) 
                totalWidth += oDiv.offsetWidth; 
            } 

            if(this.sortColumn[i]){ 
                oDiv.onmouseover = function(){ 
                    this.style.cursor = "hand";
                    if(Grid.sortBy==0) 
                        this.className = this.className.replace(/grid_column /,"grid_column_sort_desc "); 
                    else 
                        this.className = this.className.replace(/grid_column /,"grid_column_sort_asc "); 
                } 
                oDiv.onmouseout =  function(){     
                    this.className = this.className.replace(/grid_column_sort_desc /,"grid_column "); 
                    this.className = this.className.replace(/grid_column_sort_asc /,"grid_column "); 
                }; 
                oDiv.onclick = function(){ 
                    var col = Grid.getObjectIndex(this.parentNode,this)/2; 
                    Grid.data.sort(function(a,b){return (Grid.sortBy ? (a[col]>b[col] ? 1:(a[col]<b[col] ? -1:0)) : (a[col]<b[col] ? 1:(a[col]>b[col] ? -1:0)));}); 
                    Grid.fillData(Grid.startPage); 
                    if(Grid.sortBy==0) 
                        this.className = this.className.replace(/grid_column /,"grid_column_sort_asc "); 
                    else 
                        this.className = this.className.replace(/grid_column /,"grid_column_sort_desc "); 
                    Grid.sortBy = (Grid.sortBy == 0 ? 1 : 0); 
                }; 
            } 
        } 
        if(totalWidth>this.header.offsetWidth){ 
            this.header.style.width  = totalWidth + "px"; 
        }else{ 
            if(totalWidth < this.header.offsetWidth - 2){ 
                this.header.lastChild.style.width = this.header.offsetWidth + this.header.lastChild.offsetWidth - totalWidth + "px"; 
            }         
        } 
        Grid.drawDataRow(); 
    }, 
    fillData : function(startPage){ 
        this.count = this.data.length;
        var pageNum = this.data.length/this.pageSize; 
        if(pageNum > parseInt(pageNum))pageNum=parseInt(pageNum)+1; 
        if(startPage<=0 || startPage>pageNum)return; 
        this.startPage = startPage; 
        Grid.removeData(); 
        var endNum = startPage * this.pageSize; 
        if(endNum > this.data.length) endNum = this.data.length; 
        var oRow = this.container.childNodes[1]; 
        for(var i=(startPage-1)*this.pageSize;i<endNum;i++){ 
            if(this.showAutoGridNum){ 
                oRow.childNodes[0].innerHTML = i+1; 
            }else{ 
                oRow.childNodes[0].innerHTML = this.data[0][0]; 
            } 
            for(var j=1 ;j < this.data[i].length; j++){ 
                oRow.childNodes[j].innerHTML = this.data[i][j]; 
            } 
            if(oRow.nextSibling)oRow=oRow.nextSibling; 
        } 
        
        var count = this.count;
        var limitValue = parseInt(document.getElementById("limit").value);
        var page = parseInt((count+limitValue-1) / limitValue) - 1;
        
        // 如果是第一页
        if(document.getElementById("start").value == 0 || startPage == 1)
        {
            document.getElementById("pFirst").disabled = "disabled";
            document.getElementById("pPrev").disabled = "disabled";
            document.getElementById("pFirst").className += " grayImg";
            document.getElementById("pPrev").className += " grayImg";
            document.getElementById("pFirst").style.cursor = "default";
            document.getElementById("pFirst").firstChild.style.cursor = "default";
            document.getElementById("pPrev").style.cursor = "default";
            document.getElementById("pPrev").firstChild.style.cursor = "default";
        }
        else
        {
            document.getElementById("pFirst").disabled = "";
            document.getElementById("pPrev").disabled = "";
            document.getElementById("pFirst").className = document.getElementById("pFirst").className.replace("grayImg","");
            document.getElementById("pPrev").className = document.getElementById("pPrev").className.replace("grayImg","");
            document.getElementById("pFirst").style.cursor = "";
            document.getElementById("pFirst").firstChild.style.cursor = "";
            document.getElementById("pPrev").style.cursor = "";
            document.getElementById("pPrev").firstChild.style.cursor = "";
        }
        
        // 如果是最后一页
        if(document.getElementById("start").value == page * limitValue)
        {
            document.getElementById("pLast").disabled = "disabled";
            document.getElementById("pLast").className += " grayImg";
            document.getElementById("pNext").disabled = "disabled";
            document.getElementById("pNext").className += " grayImg";
            document.getElementById("pLast").style.cursor = "default";
            document.getElementById("pLast").firstChild.style.cursor = "default";
            document.getElementById("pNext").style.cursor = "default";
            document.getElementById("pNext").firstChild.style.cursor = "default";
        }
        else
        {
            document.getElementById("pLast").disabled = "";
            document.getElementById("pLast").className = document.getElementById("pLast").className.replace("grayImg","");
            document.getElementById("pNext").className = document.getElementById("pNext").className.replace("grayImg","");
            document.getElementById("pNext").disabled = "";
            document.getElementById("pLast").style.cursor = "";
            document.getElementById("pLast").firstChild.style.cursor = "";
            document.getElementById("pNext").style.cursor = "";
            document.getElementById("pNext").firstChild.style.cursor = "";
        }
        
        try{
            count = parseInt(count);
            if(!count){
                count = 0;
            }
        }catch(e){
            count = 0;
        }
        
        //document.getElementById("personCount").innerText = "" + count + "??";
                    
    }, 
    removeData : function(){ 
        for(var i=1;i<this.container.childNodes.length;i++){ 
        
            if(this.container.childNodes[i].className == 'pDiv') continue;
        
            for(var j=0;j<this.container.childNodes[i].childNodes.length;j++){
				
                this.container.childNodes[i].childNodes[j].innerHTML = ""; 
            } 
        } 
    }, 
    updateData : function(i,j,value){ 
        this.data[i][j] = value; 
    }, 
    drawDataRow : function(){ 
        var endNum = this.minirows; 
        if (endNum < this.pageSize) endNum = this.pagesiz; 
        for(var i=0;i<endNum;i++){ 
            var oRow = document.createElement("div"); 
            this.container.appendChild(oRow); 
            if(this.rowInterval && i%2!=0) 
                oRow.className = "grid_row_interval";     
            else 
                oRow.className = "grid_row"; 
            oRow.style.width = this.header.offsetWidth + "px"; 
            oRow.id = "grid_row"+i; 
            oRow.oldClass = oRow.className; 
            Grid.drawDataCell(oRow,i); 
            oRow.onclick = Grid.hilightHandle; 
        } 
    }, 
    drawDataCell : function(oRow,j){ 
        for(var i=0;i<this.column.length;i++){ 
            var oCell = document.createElement("div"); 
            oRow.appendChild(oCell); 
            if(i==0){ 
                oCell.className = "grid_table_cell0"; 
                oCell.style.width = this.header.childNodes[i*2].offsetWidth + "px"; 
            }else{ 
                oCell.className = "grid_table_cell";     
                oCell.style.width = this.header.childNodes[i*2].offsetWidth + 1 + "px"; 
            } 
            oCell.id = "grid_row"+j+"_column" + i; 
        } 
    }, 
    hilightHandle : function(){ 
        if(Grid.hilight){ 
            var k = Grid.selectedRowIndex; 
            if(k>0){ 
                Grid.container.childNodes[k].className = Grid.container.childNodes[k].oldClass;  
            } 
            this.className = "grid_row_hilight"; 
        } 
         
        var j= Grid.getObjectIndex(Grid.container,this); 
        Grid.selectedRowIndex = j; 
    }, 
    getObjectIndex : function(parent,obj){ 
        
        for(var i=0;i<parent.childNodes.length;i++){ 
            if(parent.childNodes[i] == obj){ 
                return i;
            } 
        }
        return -1;
    }, 
    getGridRow : function(i){ 
        return Grid.childNodes[i]; 
    }, 
    getTarget : function(e){ 
        return e.target || e.srcElement; 
    }, 
    getRealPosition : function(o){ 
        var l = 0; t = 0;     
        while(o){ 
            l += o.offsetLeft - o.scrollLeft; 
            t += o.offsetTop - o.scrollTop; 
            o = o.offsetParent; 
        } 
        return {x:l,y:t}; 
    }, 
    getMousePosition : function(e){ 
        var x = 0; 
        if(e.pageX){ 
            x = e.pageX; 
        }else{ 
            x = e.clientX + document.body.scrollLeft - document.body.clientLeft; 
        } 
        return x; 
    }, 
    registerCallBack : function(evtname,arr,evthandle){ 
        /*注册返回函数 
        参数含义: 
        evtname:事件名称，如click,mouseover,注意，前面不要加on 
        arr:要注册到的对象索引，为数组，第一个为行号，第一个为列号，如果为-1，表示整行或者整栏 
        evthandle:外部定义好的函数名 
        */ 
        var objCollection = Grid.getObjectCollection(arr); 
        for(var i=0;i<objCollection.length;i++){ 
            if(window.attachEvent) {
                objCollection[i].attachEvent("on"+evtname,evthandle); 
                objCollection[i].attachEvent("onmouseover",(function(obj){
                    return function(){
                        obj.style.cursor = "hand";
                    }
                })(objCollection[i])); 
            }
            else if(window.addEventListener) {
                objCollection[i].addEventListener(evtname,evthandle,false); 
                objCollection[i].addEventListener("onmouseover",(function(obj){
                     return function(){
                        obj.style.cursor = "hand";
                     }
                })(objCollection[i]));
            }
        } 
    }, 
    getObjectCollection : function(arr){ 
        var objArr = []; 
        if(arr[0]==0)arr[0]==1; 
        if(arr[0]!=-1 && !this.container.childNodes[arr[0]])return objArr; 
        if(arr[0]==-1 && arr[1]==-1){ 
            for(var i=1;i<this.container.childNodes.length;i++){ 
                for(var j=0;j<this.container.childNodes[i].childNodes.length;j++){ 
                    objArr[objArr.length++]=this.container.childNodes[i].childNodes[j]; 
                } 
            } 
        }else if(arr[0]!=-1 && arr[1]==-1){ 
            for(var j=0;j<this.container.childNodes[arr[0]].childNodes.length;j++){ 
                objArr[objArr.length++]=this.container.childNodes[arr[0]].childNodes[j]; 
            } 

        }else if(arr[0]==-1 && arr[1]!=-1){ 
            for(var i=1;i<this.container.childNodes.length;i++){ 
                if(this.container.childNodes[i].childNodes[arr[1]]) 
                    objArr[objArr.length++]=this.container.childNodes[i].childNodes[arr[1]]; 
            } 
        }else{ 
            objArr[objArr.length++]=this.container.childNodes[arr[0]].childNodes[arr[1]]; 
        } 
        return objArr; 
    },
	drawPageBar: function(){
		
		var pDiv = document.createElement('div');
		pDiv.className = 'pDiv';
		pDiv.style.width = '100%';
		this.container.appendChild(pDiv);

		var pDiv2 = document.createElement('div'); 
		pDiv2.className = 'pDiv2';
		pDiv.appendChild(pDiv2);

		var pGroup = document.createElement('div'); 
		pGroup.className = 'pGroup';
		pDiv2.appendChild(pGroup);

		pGroup.innerHTML = '<div class="pSearch pButton"><span></span></div>';

		var btnseparator = document.createElement('div'); 
		btnseparator.className = 'btnseparator';
		pDiv2.appendChild(btnseparator);

		var pGroup = document.createElement('div'); 
		pGroup.className = 'pGroup';
		pDiv2.appendChild(pGroup);
        
        pGroup.innerHTML = '<input id="start" type="hidden" value="0">';
		pGroup.innerHTML += '<SELECT name="limit"><OPTION value="10">10&nbsp;&nbsp;</OPTION><OPTION value="15">15&nbsp;&nbsp;</OPTION><OPTION value="20">20&nbsp;&nbsp;</OPTION><OPTION value="25">25&nbsp;&nbsp;</OPTION><OPTION value="40">40&nbsp;&nbsp;</OPTION></SELECT>';

		var btnseparator = document.createElement('div'); 
		btnseparator.className = 'btnseparator';
		pDiv2.appendChild(btnseparator);

		var pGroup = document.createElement('div'); 
		pGroup.className = 'pGroup';
		pDiv2.appendChild(pGroup);

		pGroup.innerHTML = '<DIV id="pFirst" class="pFirst pButton" onclick="Grid.onFirstPage()"><SPAN></SPAN></DIV><DIV id="pPrev" class="pPrev pButton" onclick="Grid.onPrePage()"><SPAN></SPAN></DIV>';

		var btnseparator = document.createElement('div'); 
		btnseparator.className = 'btnseparator';
		pDiv2.appendChild(btnseparator);

		var pGroup = document.createElement('div'); 
		pGroup.className = 'pGroup';
		pDiv2.appendChild(pGroup);

		pGroup.innerHTML = '<SPAN class="pcontrol">页 <INPUT size="4" value="1" /> / <SPAN>1 </SPAN></SPAN>';

		var btnseparator = document.createElement('div'); 
		btnseparator.className = 'btnseparator';
		pDiv2.appendChild(btnseparator);

		var pGroup = document.createElement('div'); 
		pGroup.className = 'pGroup';
		pDiv2.appendChild(pGroup);

		pGroup.innerHTML = '<DIV id="pNext" class="pNext pButton" onclick="Grid.onNextPage()"><SPAN></SPAN></DIV><DIV id="pLast" class="pLast pButton" onclick="Grid.onLastPage()"><SPAN></SPAN></DIV>';

		var btnseparator = document.createElement('div'); 
		btnseparator.className = 'btnseparator';
		pDiv2.appendChild(btnseparator);

		var pGroup = document.createElement('div'); 
		pGroup.className = 'pGroup';
		pDiv2.appendChild(pGroup);

		pGroup.innerHTML = '<DIV class="pReload pButton loading"><SPAN></SPAN></DIV>';

		var btnseparator = document.createElement('div'); 
		btnseparator.className = 'btnseparator';
		pDiv2.appendChild(btnseparator);

		var pGroup = document.createElement('div'); 
		pGroup.className = 'pGroup';
		pDiv2.appendChild(pGroup);

		pGroup.innerHTML = '<SPAN class="pPageStat">数据加载中，请稍候...</SPAN>';

		var div = document.createElement('div'); 
		div.style.clear = 'both';
		pDiv.appendChild(div);
	},
	onPrePage: function(){
        var startValue = document.getElementById("start").value;
        var limitValue = document.getElementById("limit").value;
        
        var startValue = startValue - limitValue;
        if(startValue < 0){
            return;
        }
            
        document.getElementById("start").value = startValue;
        
        Grid.fillData(Grid.startPage-1);
    },
    onNextPage: function(){
        var startValue = document.getElementById("start").value;
        var limitValue = document.getElementById("limit").value;
        
        startValue = parseInt(startValue) + parseInt(limitValue);
        
        if(startValue >= this.count){
            return;
        }
        
        document.getElementById("start").value = startValue;
        
        Grid.fillData(Grid.startPage+1);
    },
    onFirstPage: function(){
        document.getElementById("start").value = 0;
        Grid.fillData(1);
    },
    onLastPage: function(){
        var limitValue = parseInt(document.getElementById("limit").value);
        var page = parseInt((this.count+limitValue-1) / limitValue) - 1;
        document.getElementById("start").value = page * limitValue;
        
        Grid.fillData(page);
    }

} 