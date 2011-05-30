/*
flex study  
Copyright (C) 2008  sban

opensource and free!

Any proposal always welcome to me. 
You can contact me in the following ways: 
Email:		sban.net at gmail.com
QQ:			137 9595 60
Blog:		http://blog.sban.com.cn/

For updated news and information:
http://code.google.com/p/fllib/

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.nano.plugins.multiselection
{
	import flash.events.Event;
	
	import mx.controls.CheckBox;

	public class CheckBoxItemDataRenderer extends CheckBox
	{
		public function CheckBoxItemDataRenderer()
		{
			super();
			
			this.addEventListener(Event.CHANGE, changeHandler);
		}
		
		override public function set data(value:Object):void
		{
			super.data = value;
			this.selected = listData.label == 'true';
		}
		
		protected function changeHandler(event : Event) : void
		{
			data.@selected = this.selected;
		}
		
	}
}