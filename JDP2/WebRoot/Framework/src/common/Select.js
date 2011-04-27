/*
1判断select选项中 是否存在Value="paraValue"的Item 
2向select选项中 加入一个Item 
3从select选项中 删除一个Item 
4删除select中选中的项 
5修改select选项中 value="paraValue"的text为"paraText" 
6设置select中text="paraText"的第一个Item为选中 
7设置select中value="paraValue"的Item为选中 
8得到select的当前选中项的value 
9得到select的当前选中项的text 
10得到select的当前选中项的Index 
11清空select的项 
*/

/*判断select选项中 是否存在Value="paraValue"的Item */       
function selectIsExitItem(objSelect, objItemValue) {           
    for (var i = 0; i < objSelect.options.length; i++) {        
        if (objSelect.options[i].value == objItemValue) {        
            return true;        
        }        
    }        
    return false;        
}         
   
/*向select选项中 加入一个Item */       
function addItemToSelect(objSelect, objItemText, objItemValue) {        
    //判断是否存在        
    if (jsSelectIsExitItem(objSelect, objItemValue)) {        
    	alert("该Item的Value值已存在");
    } else {        
        var varItem = new Option(objItemText, objItemValue);      
        objSelect.options.add(varItem);
    }        
}        
   
/*从select选项中 删除一个Item */
function removeItemFromSelect(objSelect, objItemValue) {        
    //判断是否存在        
    if (jsSelectIsExitItem(objSelect, objItemValue)) {        
        for (var i = 0; i < objSelect.options.length; i++) {        
            if (objSelect.options[i].value == objItemValue) {        
                objSelect.options.remove(i);        
                break;        
            }        
        }        
    }        
}    
   
   
/*删除select中选中的项 */ 
function removeSelectedItemFromSelect(objSelect) {        
    var length = objSelect.options.length - 1;    
    for(var i = length; i >= 0; i--){
        if(objSelect[i].selected == true){    
            objSelect.options[i] = null;    
        }    
    }    
}      
   
/*修改select选项中 value="paraValue"的text为"paraText"*/        
function updateItemToSelect(objSelect, objItemText, objItemValue) {        
    //判断是否存在        
    if (jsSelectIsExitItem(objSelect, objItemValue)) {        
        for (var i = 0; i < objSelect.options.length; i++) {        
            if (objSelect.options[i].value == objItemValue) {        
                objSelect.options[i].text = objItemText;        
                break;        
            }        
        }
    }        
}        
   
/*设置select中text="paraText"的第一个Item为选中*/        
function selectItemByValue(objSelect, objItemText) {
    for (var i = 0; i < objSelect.options.length; i++) {
        if (objSelect.options[i].text == objItemText) {
            objSelect.options[i].selected = true;
            isExit = true;
            break;
        }        
    }      
}

/*清空select的项*/
function clear(objSelect){    
	objSelect.options.length = 0;   
}

/*得到select的当前选中项的Index*/
function selectedIndex(objSelect){
	return objSelect.selectedIndex;
}
   
/*设置select中value="paraValue"的Item为选中*/
function selectValue(objSelect,value){
	objSelect.value = value;
}
       
/*得到select的当前选中项的value*/
function selectedValue(objSelect){
	return objSelect.value;
}
       
// 9.得到select的当前选中项的text 
function selectedText(objSelect){
	return objSelect.options[objSelect.selectedIndex].text;
}