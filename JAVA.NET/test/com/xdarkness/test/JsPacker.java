package com.xdarkness.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Create on Apr 29, 2010 4:06:31 PM
 * 
 * @author Darkness
 * @version 1.0
 */
public class JsPacker {

	public static void main(String[] args) {

		List<String> files = getPackerCoreFiles();
		String packFileName = "D:\\MyEclipse 8.6\\sky-orm11\\WebRoot\\Framework\\Main.js";
		// packerFile(files, packFileName);
		//		
		// files = getPackerTreeFiles();
		// packFileName = "D:\\compressjs\\tree.js";
		// packerFile(files, packFileName);
		//		
		// files = getPackerDialogFiles();
		// packFileName = "D:\\compressjs\\dialog.js";
		// packerFile(files, packFileName);

		// files = getPackerUtilFiles();
		// packFileName = "D:\\compressjs\\util.package.js";

		// files = getPackerCssFiles();
		// packFileName = "D:\\compressjs\\default-all.css";
		packerFile(files, packFileName);
	}

	/**
	 * 打包util
	 * 
	 * @return
	 */
	private static List<String> getPackerUtilFiles() {
		List<String> fileList = new ArrayList<String>();
		fileList
				.add("D:\\MyEclipse 8.6\\sky-orm11\\WebRoot\\Framework\\src");

		return fileList;
	}

	/**
	 * 打包文件
	 * 
	 * @param files
	 * @param packFileName
	 */
	public static void packerFile(List<String> files, String packFileName) {

		File packFile = new File(packFileName);

		if (packFile.exists()) {
			packFile.delete();
		}
		try {
			packFile.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		BufferedWriter packWriter = null;
		try {
			packWriter = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(packFileName), "UTF-8"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		for (String filePath : files) {
			File file = new File(filePath);
			if (file.isDirectory()) {
				try {
					dealFloder(file, packWriter);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				if (!file.getName().toLowerCase().endsWith(".js")) {
					continue;
				}
				System.out.println("正在整合文件【" + file.getName() + "】...");
				try {
//					BufferedReader reader = new BufferedReader(new FileReader(
//							file));
					BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(
							file.getAbsolutePath()), "UTF-8"));
					String line = "";
					try {
						while ((line = reader.readLine()) != null) {
							packWriter.write(line);
							packWriter.write("\n");
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					dealServerFile(file.getName(), packWriter);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		try {
			System.out.println("整合所有文件成功！");
			packWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void dealFloder(File file, BufferedWriter packWriter) throws UnsupportedEncodingException {
		File[] childFiles = file.listFiles();
		for (File childFile : childFiles) {

			if (childFile.isDirectory()) {
				dealFloder(childFile, packWriter);
			}

			if (!childFile.getName().toLowerCase().endsWith(".js")) {
				continue;
			}

			System.out.println("正在整合文件【" + childFile.getName() + "】...");

			try {
				
//				FileReader fileReader = new FileReader(childFile);
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(childFile), "UTF-8"));

				String line = "";
				try {
					while ((line = reader.readLine()) != null) {
						packWriter.write(line);
						packWriter.newLine();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				dealServerFile(childFile.getName(), packWriter);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private static void dealServerFile(String fileName, BufferedWriter packWriter){
		if ("Server.js".equals(fileName)) {
			try {
				packWriter
						.write("Server.loadScript(\"Framework/Drag.js\");\r\n"
								+ "Server.loadScript(\"Framework/Controls/Tabpage.js\");\r\n"
								+ "Server.loadScript(\"Framework/Controls/Select.js\");\r\n"
								+ "Server.loadScript(\"Framework/Controls/Dialog.js\");\r\n"
								+ "Server.loadScript(\"Framework/Controls/MessagePop.js\");\r\n"
								+ "Server.loadScript(\"Framework/Controls/DataGrid.js\");\r\n"
								+ "Server.loadScript(\"Framework/Controls/DataList.js\");\r\n"
								+ "Server.loadScript(\"Framework/Controls/Menu.js\");\r\n"
								+ "Server.loadScript(\"Framework/Controls/DateTime.js\");\r\n"
								+ "Server.loadScript(\"Framework/Controls/Tree.js\");\r\n"
								+ "Server.loadScript(\"Framework/Controls/Tip.js\");\r\n"
								+ "Server.loadScript(\"Framework/Controls/Progress.js\");\r\n"
								+ "Server.loadScript(\"Framework/Application.js\");\r\n"
								+ "Server.loadScript(\"Framework/Verify.js\");\r\n"
								+ "Server.loadScript(\"Framework/Style.js\");\r\n"
								+ "Server.loadScript(\"Framework/Resize.js\");\r\n"
								+ "Server.loadScript(\"Framework/Console.js\");");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String BaseDir = "D:\\MyEclipse 8.6\\sky-orm11\\WebRoot\\Framework\\src\\";

	/**
	 * 打包核心库
	 * 
	 * @return
	 */
	public static List<String> getPackerCoreFiles() {
		List<String> fileList = new ArrayList<String>();
		String path = BaseDir;
		fileList.add(path + "common");
		fileList.add(path + "data");
		fileList.add(path + "Constant.js");
		
		fileList.add(path + "Sky.js");
		fileList.add(path + "addEvent.js");
		fileList.add(path + "Browser.js");
		fileList.add(path + "domReady.js");
		fileList.add(path + "GeckoExt.js");
		fileList.add(path + "Page.js");
		fileList.add(path + "Server.js");

		fileList.add(path + "Afloat.js");
		fileList.add(path + "Ajax.js");
		fileList.add(path + "Base.js");

		fileList.add(path + "Console.js");

		fileList.add(path + "Afloat.js");
		fileList.add(path + "Element-more.js");
		fileList.add(path + "Form.js");

		fileList.add(path + "Misc.js");

		fileList.add(path + "Request.js");
		fileList.add(path + "MultipleSelect.js");

		return fileList;
	}

	/**
	 * 打包树组件
	 * 
	 * @return
	 */
	public static List<String> getPackerTreeFiles() {
		List<String> fileList = new ArrayList<String>();
		fileList
				.add("D:\\java\\MyEclipseWorkspace\\pt\\source\\Framework\\src\\tree\\TreeConfig.js");
		fileList
				.add("D:\\java\\MyEclipseWorkspace\\pt\\source\\Framework\\src\\tree\\TreeHandler.js");
		fileList
				.add("D:\\java\\MyEclipseWorkspace\\pt\\source\\Framework\\src\\tree\\TreeNode.js");
		fileList
				.add("D:\\java\\MyEclipseWorkspace\\pt\\source\\Framework\\src\\tree\\AjaxTreeNode.js");
		fileList
				.add("D:\\java\\MyEclipseWorkspace\\pt\\source\\Framework\\src\\tree\\TreePanel.js");
		fileList
				.add("D:\\java\\MyEclipseWorkspace\\pt\\source\\Framework\\src\\tree\\xmlextras.js");

		return fileList;
	}

	/**
	 * 打包对话框组件
	 * 
	 * @return
	 */
	public static List<String> getPackerDialogFiles() {
		List<String> fileList = new ArrayList<String>();
		fileList
				.add("D:\\java\\MyEclipseWorkspace\\pt\\source\\Framework\\src\\Drag.js");
		fileList
				.add("D:\\java\\MyEclipseWorkspace\\pt\\source\\Framework\\src\\Effect.js");
		fileList
				.add("D:\\java\\MyEclipseWorkspace\\pt\\source\\Framework\\src\\Controls\\Dialog.js");

		return fileList;
	}

	public static List<String> getPackerCssFiles() {
		List<String> fileList = new ArrayList<String>();

		fileList
				.add("D:\\java\\MyEclipseWorkspace\\pt\\source\\Framework\\resources\\css\\button.css");
		fileList
				.add("D:\\java\\MyEclipseWorkspace\\pt\\source\\Framework\\resources\\css\\icon.css");
		fileList
				.add("D:\\java\\MyEclipseWorkspace\\pt\\source\\Framework\\resources\\css\\tabstrip.css");

		fileList
				.add("D:\\java\\MyEclipseWorkspace\\pt\\source\\Framework\\resources\\css\\default.css");
		fileList
				.add("D:\\java\\MyEclipseWorkspace\\pt\\source\\Framework\\resources\\css\\gt_grid.css");
		fileList
				.add("D:\\java\\MyEclipseWorkspace\\pt\\source\\Framework\\resources\\css\\tree.css");

		return fileList;
	}
}
