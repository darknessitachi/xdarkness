Page.onLoad(function() {
	$("branch_DataGrid").afterEdit = function(tr, dr) {
		dr.set("Name", $V("Name"));
		dr.set("Memo", $V("Memo"));
		return true;
	}
});

BaseManager = Base.extend({
});

BranchManager = BaseManager.extend({
	onAdd : function() {
		var dt = DataGrid.getSelectedData("branch_DataGrid");
		var drs = dt.Rows;
		var ParentInnerCode = "";
		if (drs && drs.length > 0) {
			ParentInnerCode = drs[0].get("BranchInnerCode");
		}
		var diag = new Dialog( {
			width : 500,
			height : 200,
			title : "新建组织机构",
			showMessageRow : true,
			messageTitle : "添加组织机构",
			message : "设置机构名称、上级机构等",
			url : "Platform/Branch/BranchDialog.jsp?ParentInnerCode="
					+ ParentInnerCode,
			onLoad : function() {
				$DW.$("Name").focus();
			},
			OKEvent : this.addSave
		});
		diag.show();
	},
	addSave : function() {
		
		if ($DW.Verify.hasError()) {
			return;
		}
		var dc = $DW.Form.getData("form2");
		Server.sendRequest("com.xdarkness.platform.page.BranchPage.add", dc,
				function(response) {
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							$D.close();
							DataGrid.loadData('branch_DataGrid');
						}
					});
				});
	},

	onEdit : function() {
		var dt = DataGrid.getSelectedData("branch_DataGrid");
		var drs = dt.Rows;
		if (!drs || drs.length == 0) {
			Dialog.alert("请先选择要编辑的组织机构！");
			return;
		}
		dr = drs[0];
		var diag = new Dialog( {
			width : 500,
			height : 200,
			title : "修改机构",
			url : "Platform/Branch/BranchEditDialog.jsp?BranchInnerCode="
					+ dr.get("BranchInnerCode"),
			onLoad : function() {
				$DW.$("Name").focus();
			},
			OKEvent : this.editSave
		});

		diag.show();
	},

	editSave : function() {
		var dc = $DW.Form.getData("form2");
		if ($DW.Verify.hasError()) {
			return;
		}
		Server.sendRequest("com.xdarkness.platform.page.BranchPage.save", dc,
				function(response) {
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							$D.close();
							DataGrid.loadData('branch_DataGrid');
						}
					});
				});
	},

	onDelete : function() {
		var arr = DataGrid.getSelectedValue("branch_DataGrid");
		if (!arr || arr.length == 0) {
			Dialog.alert("请先选择要删除的行！");
			return;
		}
		var dc = new DataCollection();
		dc.add("IDs", arr.join());
		Dialog.confirm("注意：这是级联删除，下级组织机构会一起删除，您确认要删除吗？", function() {
			Server.sendRequest("com.xdarkness.platform.page.BranchPage.del",
					dc, function(response) {
						Dialog.alert(response.Message, function() {
							if (response.Status == 1) {
								DataGrid.loadData('branch_DataGrid');
							}
						});
					});
		});
	}
});

branchManager = new BranchManager();

function sortBranch(type, targetDr, sourceDr, rowIndex, oldIndex) {
	if (rowIndex == oldIndex) {
		return;
	}

	var ds = $("branch_DataGrid").DataSource;
	if (ds.get(rowIndex - 1, "BranchInnerCode").length == 4) {
		alert("您选择的是总机构，总机构不需要排序！");
		DataGrid.loadData("branch_DataGrid");
		return;
	}

	if (rowIndex - 1 == 0) {
		alert("任何子机构都不能排在总机构前面");
		DataGrid.loadData("branch_DataGrid");
		return;
	}

	var type = "";
	var orderBranch = "";
	var nextBranch = "";
	if (ds.get(rowIndex - 1, "ParentInnerCode") == ds.get(rowIndex,
			"ParentInnerCode")) {
		type = "Before";
		orderBranch = ds.get(rowIndex - 1, "BranchInnerCode");
		nextBranch = ds.get(rowIndex, "BranchInnerCode");
	} else if (ds.get(rowIndex - 1, "ParentInnerCode") == ds.get(rowIndex - 2,
			"ParentInnerCode")) {
		type = "After";
		orderBranch = ds.get(rowIndex - 1, "BranchInnerCode");
		nextBranch = ds.get(rowIndex - 2, "BranchInnerCode");
	} else {
		alert("不在同一机构下的子机构不能排序！");
		DataGrid.loadData("branch_DataGrid");
		return;
	}
	var dc = new DataCollection();
	dc.add("OrderType", type);
	dc.add("OrderBranch", orderBranch);
	dc.add("NextBranch", nextBranch);
	DataGrid.showLoading("branch_DataGrid");
	Server.sendRequest("com.xdarkness.platform.page.BranchPage.sortBranch", dc,
			function(response) {
				Dialog.alert(response.Message, function() {
					if (response.Status == 1) {
						DataGrid.loadData("branch_DataGrid");
					}
				});
			});
}
