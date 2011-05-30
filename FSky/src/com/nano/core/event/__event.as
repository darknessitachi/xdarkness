// ActionScript file
import com.nano.core.SysEventMgr;

/**
 * 添加一个事件监听
 */ 
public function on(evtName:String,func:Function):void{
	SysEventMgr.on(this,evtName,func);
}

/**
 * 删除一个事件监听
 */ 
public function un(evtName:String,func:Function):void{
	SysEventMgr.un(this,evtName,func);
}