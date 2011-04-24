package com.xdarkness.framework.jaf.controls;

import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.util.Mapx;

public class UploadStatus extends Page {
	public static Mapx uptaskMap = new Mapx(100);

	public void getTaskStatus() {
		String taskID = $V("taskID");
		String FileIDs = "";
		String Types = "";
		String Paths = "";
		String Status = "";
		if (uptaskMap.get(taskID) != null) {
			String[][] taskArr = (String[][]) uptaskMap.get(taskID);
			for (int i = 0; i < taskArr.length; i++) {
				FileIDs = FileIDs + taskArr[i][0];
				Types = Types + taskArr[i][1];
				Paths = Paths + taskArr[i][2];
				Status = Status + taskArr[i][3];
				if (i != taskArr.length - 1) {
					FileIDs = FileIDs + ",";
					Types = Types + ",";
					Paths = Paths + ",";
					Status = Status + ",";
				}
			}
		} else {
			FileIDs = FileIDs + "empty";
			Types = Types + "empty";
			Paths = Paths + "empty";
			Status = Status + "处理中";
		}
		this.response.put("FileIDs", FileIDs);
		this.response.put("Types", Types);
		this.response.put("Paths", Paths);
		this.response.put("Status", Status);
	}

	public static void setTask(String taskID, String fileID, String fileType,
			String filePath) {
		setTask(taskID, fileID, fileType, filePath, "处理中");
	}

	public static void setTask(String taskID, String fileID, String fileType,
			String filePath, String Status) {
		if (uptaskMap.get(taskID) != null) {
			String[][] tempArr = (String[][]) uptaskMap.get(taskID);
			boolean hasFile = false;
			for (int i = 0; i < tempArr.length; i++) {
				if (tempArr[i][0].equals(fileID)) {
					hasFile = true;
					tempArr[i] = new String[] { fileID, fileType, filePath,
							Status };
					uptaskMap.put(taskID, tempArr);
					return;
				}
			}
			if (!hasFile) {
				String[][] newArr = new String[tempArr.length + 1][1];
				for (int i = 0; i < tempArr.length; i++) {
					newArr[i] = tempArr[i];
				}
				newArr[tempArr.length] = new String[] { fileID, fileType,
						filePath, Status };
				uptaskMap.put(taskID, newArr);
			}
		} else {
			String[][] fileArr = { { fileID, fileType, filePath, Status } };
			uptaskMap.put(taskID, fileArr);
		}
	}
}

/*
 * com.xdarkness.framework.controls.UploadStatus JD-Core Version: 0.6.0
 */