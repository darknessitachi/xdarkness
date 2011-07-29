
Dialog.wait = function(msg){//某些地方不需要进度条但后台执行时间又比较长的可以使用此方法
    
    var script = [];
    Dialog.alert(msg, null);
    $TW.Dialog.WaitID = $TW.setTimeout($TW.Dialog.waitAction, 1000);
    var diag = $TW.Dialog.getInstance("_DialogAlert" + ($TW.Dialog.AlertNo - 1));
    diag.CancelButton.disable();
    diag.CancelButton.onclick = function(){
    };
    $TW.Dialog.WaitSecondCount = 0;
}

Dialog.waitAction = function(){
    var diag = $TW.Dialog.getInstance("_DialogAlert" + ($TW.Dialog.AlertNo - 1));
    $TW.Dialog.WaitSecondCount++;
    diag.CancelButton.value = "请等待(" + $TW.Dialog.WaitSecondCount + ")..."
    $TW.Dialog.WaitID = $TW.setTimeout($TW.Dialog.waitAction, 1000);
}

