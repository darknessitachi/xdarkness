
package com.nano.flexcomp
{
	[Event(name="execCommand", type="com.nano.core.event.CommandEvent")]

	import com.nano.core.event.CommandEvent;
	
	import flash.events.Event;
	import flash.events.FocusEvent;
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	import flash.events.TextEvent;
	import flash.ui.Keyboard;
	
	import flashx.textLayout.operations.DeleteTextOperation;
	import flashx.textLayout.operations.FlowOperation;
	import flashx.textLayout.operations.InsertTextOperation;
	import flashx.textLayout.operations.SplitParagraphOperation;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ArrayList;
	import mx.events.FlexEvent;
	
	import spark.components.TextArea;
	import spark.events.TextOperationEvent;
	

	public class CommandTextArea extends TextArea {
		private var _lastOperation:FlowOperation = null;
		private var _lastCommand:String = null;
		private var _lastCursorPosition:int = 0;
		
		private var _cmdBeginIndex:int = 0;
		private var _allWriteCmd:Array = ['ls', 'll', 'cd', 'who', 
											 'rm', 'cp', 'mkdir'];	// 白名单
		private var _allBlackCmd:Array = ['more', 'tail', 'vi'];	// 黑名单
		private var _recentCommand:ArrayList = new ArrayList();
		private var _recentCommandIndex:int = -1;
		private var _cmdAllowMode:int = 0;
		
		private var _promptStr:String = null;
		
		public static const CMDMODE_WRITELIST_ALLOW:int = 0;
		public static const CMDMODE_BLACKLIST_LIMIT:int = 1;
		public static const CMDMODE_ALL_ALLOW:int = 2;
		
		public static const MAX_RECENT_COUNT:int = 10;
		
		public function CommandTextArea() {
			this.setStyle("color", "#00FF00");
			this.setStyle("contentBackgroundColor", "#000000");
			this.setStyle("fontSize", "12");
			this.setStyle("fontFamily", "Lucida Console");
			
			this.addEventListener(KeyboardEvent.KEY_DOWN, 
									commandTextArea_keyDownHandler);
			this.addEventListener(TextOperationEvent.CHANGING, 
									commandTextArea_changingHandler);
			this.addEventListener(MouseEvent.MOUSE_DOWN,
									commandTextArea_mouseDownHandler);
		}
		
		public function keyFocusChangeHandler(evt:FocusEvent):void {
			trace('keyFocusChangeHandler...' + String.fromCharCode(evt.keyCode));
			if (evt.keyCode == Keyboard.LEFT)
				evt.preventDefault();
		}
		
		public function commandTextArea_keyDownHandler(evt:KeyboardEvent):void {
			trace('cmd_keyDownHandler...' + String.fromCharCode(evt.keyCode));
			trace(evt.cancelable);
			var needSetLastCursor:Boolean = true;
			if (evt.keyCode == Keyboard.LEFT) {
				if (this.selectionStartPosition == this.selectionEndPosition &&
					this.selectionStartPosition < _cmdBeginIndex) {
					
					evt.preventDefault();
					this.selectRange(_cmdBeginIndex, _cmdBeginIndex);
				}
			} else if (evt.keyCode == Keyboard.UP) {
				if (this.selectionStartPosition == this.selectionEndPosition && 
						_lastCursorPosition >= _cmdBeginIndex) {
					replaceCommandWith(getRecentCommand(-1));
					needSetLastCursor = false;
				}
			} else if (evt.keyCode == Keyboard.DOWN) {
				if (this.selectionStartPosition == this.selectionEndPosition && 
					_lastCursorPosition >= _cmdBeginIndex) {
					replaceCommandWith(getRecentCommand(1));
					needSetLastCursor = false;
				}
			}
			if (needSetLastCursor) {
				_lastCursorPosition = selectionStartPosition;
			}
		}
		
		public function commandTextArea_mouseDownHandler(evt:MouseEvent):void {
			if (this.selectionActivePosition == this.selectionAnchorPosition &&
				this.selectionActivePosition < _cmdBeginIndex) {
				evt.preventDefault();
			}
		}
		
		private function commandTextArea_changingHandler(evt:TextOperationEvent):void {
			trace(this.selectionStartPosition + "-" + this.selectionEndPosition);
			_lastOperation = evt.operation;
			trace(String(_lastOperation));
			if (_lastOperation is SplitParagraphOperation) {
				
				if (hasCommand()) {
					var command:String = getCommand();
					putCommandToRecentArray(command);
					
					if (command == 'clear') {
						this.text = getPromptBeforeCmd();
						_cmdBeginIndex = this.text.length;
						setCursorPosition(_cmdBeginIndex);
					} else if (command == 'exit') {
						dispatchEvent(CommandEvent.getExitCommandEvent());
					} else {
						if (_cmdAllowMode != CMDMODE_ALL_ALLOW) {
							var cmd:String = command.split(' ')[0];
							
							if ((_cmdAllowMode == CMDMODE_WRITELIST_ALLOW &&
									_allWriteCmd.indexOf(cmd) < 0) ||
								(_cmdAllowMode == CMDMODE_BLACKLIST_LIMIT &&
									_allBlackCmd.indexOf(cmd) >= 0)) {
								_promptStr = getPromptBeforeCmd();
								
								this.appendText(String.fromCharCode(Keyboard.ENTER));
								this.appendText('command not supported');
								this.appendText(String.fromCharCode(Keyboard.ENTER));
								this.appendText(_promptStr);
								
								evt.preventDefault();
								return;
							}
						}
						_lastCommand = command;
						dispatchEvent(CommandEvent.getExecCommandEvent(command));
					}
				} else {
					evt.preventDefault();
				}
				
			} else if (_lastOperation is InsertTextOperation) {
				var insertOperation:InsertTextOperation = _lastOperation as InsertTextOperation;
				trace(insertOperation.text);
				
				if (!isRangeSelected() && this.selectionStartPosition < _cmdBeginIndex) {
					setCursorPosition(_cmdBeginIndex);
					evt.preventDefault();
					super.appendText(insertOperation.text);
				} else if (this.selectionStartPosition < _cmdBeginIndex) {
					evt.preventDefault();	// 到命令行位置，禁止删除操作
				}
			} else if (_lastOperation is DeleteTextOperation) {
				var deleteOperation:DeleteTextOperation = _lastOperation as DeleteTextOperation;
				if (deleteOperation.absoluteStart < _cmdBeginIndex) {
					evt.preventDefault();	// 到命令行位置，禁止删除操作
				}
			}
		}
		
		override public function appendText(v:String):void {
			if (v.indexOf(_lastCommand + "\r\n") == 0) {
				v = v.substr(_lastCommand.length + 2);
			}
			v = v.replace(/\x1b\[*[0-9;]*[m|H|g]/ig, "");
			v = v.replace(/\x1b\[K.*\r\n/ig, "");
			
			super.appendText(v);
			_cmdBeginIndex = this.text.length;
			setCursorPosition(_cmdBeginIndex);
			this.setFocus();
		}
		
		public function hasCommand():Boolean {
			return _cmdBeginIndex < this.text.length;
		}
		
		public function getCommand():String {
			return this.text.substr(_cmdBeginIndex);
		}
		
		private function setCursorPosition(position:int):void {
			selectRange(position, position);
			_lastCursorPosition = position;
		}
		
		private function isRangeSelected():Boolean {
			return this.selectionActivePosition != this.selectionAnchorPosition;
		}
		
		private function getPromptBeforeCmd():String {
			var lastLineBegin:int = this.text.lastIndexOf(String.fromCharCode(Keyboard.ENTER));
			if (lastLineBegin < 0)	// 如果没有已经是最后一行了
				lastLineBegin = 0;
			else
				lastLineBegin += 2;
			
			return this.text.substring(lastLineBegin, _cmdBeginIndex);
		}
		
		private function get selectionStartPosition():int {
			return this.selectionActivePosition > this.selectionAnchorPosition ?
					this.selectionAnchorPosition : this.selectionActivePosition;
		}
		
		private function get selectionEndPosition():int {
			return this.selectionActivePosition < this.selectionAnchorPosition ?
				this.selectionAnchorPosition : this.selectionActivePosition;
		}
		
		public function set cmdAllowMode(mode:int):void {
			_cmdAllowMode = mode;
		}
		
		public function set allBlackCmd(blackCmds:Array):void {
			_allBlackCmd = blackCmds;
		}
		
		public function set allWriteCmd(writeCmds:Array):void {
			_allWriteCmd = writeCmds;
		}
		
		public function getRecentCommand(distance:int):String {
			if (_recentCommand.length == 0)	// 还没有最近使用命令
				return null;
			
			if (_recentCommandIndex < 0) {	// 初始化情况，如果向后则第一个，否则最后一个
				_recentCommandIndex = distance > 0 ? 0 : _recentCommand.length - 1;
			} else {
				_recentCommandIndex = _recentCommandIndex + distance;
				if (_recentCommandIndex < 0) 
					_recentCommandIndex = 0;
				else if (_recentCommandIndex >= _recentCommand.length)
					_recentCommandIndex = _recentCommand.length - 1;
			}
			return _recentCommand.getItemAt(_recentCommandIndex) as String;
		}
		
		public function replaceCommandWith(newCommand:String):void {
			selectRange(_cmdBeginIndex, this.text.length);
			insertText(newCommand);
			setCursorPosition(this.text.length);
		}
		
		public function putCommandToRecentArray(command:String):void {
			_recentCommand.removeItem(command);
			_recentCommand.addItem(command);
			if (_recentCommand.length > MAX_RECENT_COUNT)
				_recentCommand.removeItemAt(0);
			_recentCommandIndex = -1;
		}
		
	}
	
}