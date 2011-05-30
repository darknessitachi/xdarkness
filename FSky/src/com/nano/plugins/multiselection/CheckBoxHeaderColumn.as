package com.nano.plugins.multiselection{
	import mx.controls.dataGridClasses.DataGridColumn;
	
	[Event(name="headerClick", type="flash.events.Event")]

	public class CheckBoxHeaderColumn extends DataGridColumn{
		public var selected:Boolean = false;
		public function CheckBoxHeaderColumn(columnName:String=null){
			super(columnName);
		}
	}
}