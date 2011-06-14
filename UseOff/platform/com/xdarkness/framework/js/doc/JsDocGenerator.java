package com.xdarkness.framework.js.doc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JS 文档生成器
 * @author Darkness 
 * create on 2010 2010-12-14 下午02:30:46
 * @version 1.0
 * @since JDP 1.0
 */
public class JsDocGenerator {

	private String[] docFilePath;// 需要生成文档的目录列表
	public JsDocGenerator(String... path) {
		docFilePath = path;
	}
	
	private List<File> fileList = new ArrayList<File>();// 需要生成文档的文件列表
	private List<DocClass> docClassList = new ArrayList<DocClass>();
	
	/**
	 * 分析文件或目录，迭代分析所有子目录，将后缀为.js的文件添加入需要生成文档的文件列表fileList中
	 * @param fileName 需分析的文件或目录
	 */
	public void analysisFileOrDir(File fileName) {
		if (fileName.isFile()) {
			fileList.add(fileName);
			return;
		}
		
		File[] fileArray = fileName.listFiles();

		for (File inArray : fileArray) {
			if (inArray.isFile() && inArray.getName().matches(".*\\.js$")) {
				fileList.add(inArray);
			} else if (inArray.isDirectory()) {
				analysisFileOrDir(inArray);
			}
		}
	}

	/**
	 * 分析js文件，将分析结果生成DocClass
	 * @param fileName 需分析的文件
	 */
	private void analyze(File fileName) {
		
		String[] strArray = fileName.toString().split("\\\\");
		String shortFileName = strArray[strArray.length - 1];

		BufferedReader br = null;
		boolean comment = false;
		try {
			br = new BufferedReader(new FileReader(fileName));
			String line = "";
			DocClass docClass = null;
			DocMethod docMethod = null;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.matches("^[\\s&&[^\\n]]*$")) {
					// blankLine;
				} else if (line.startsWith("/*") && !line.endsWith("*/")) {
					// commentLine;
					comment = true;
				} else if (line.startsWith("/*") && line.endsWith("*/")) {
					// commentLine;
				} else if (true == comment) {
					// commentLine;
					if(line.indexOf("@class")>0){
						line = line.replace("@class", "").replace("*", "").trim();
						String[] info = line.split("\\s");
						
						docClass = new DocClass(info);
						docClass.DefinedInFileName = shortFileName;
						
						docClassList.add(docClass);
					} else if(line.indexOf("@method")>0){
						line = line.replace("@method", "").replace("*", "").trim();
						String[] info = line.split("\\s");
						
						docMethod = new DocMethod(info);
						
						docClass.methodList.add(docMethod);
					}  else if(line.indexOf("@property")>0){
						line = line.replace("@property", "").replace("*", "").trim();
						String[] info = line.split("\\s");
						
						DocProperty docProperty = new DocProperty(info);
						
						docClass.propertyList.add(docProperty);
					} else if(line.indexOf("@param")>0){
						line = line.replace("@param", "").replace("*", "").trim();
						String[] info = line.split("\\s");
						
						DocProperty docProperty = new DocProperty(info);
						docMethod.Properties.add(docProperty);
					} else if(line.indexOf("@return")>0){
						line = line.replace("@return", "").replace("*", "").trim();
						String[] info = line.split("\\s");
						
						try {
							docMethod.Return = new DocProperty(info);
						} catch (Exception e) {
							System.out.println(line);
							System.exit(1);
						}
					} else if(line.indexOf("@static")>0){
						docMethod.Type = "static";
					} else if(line.indexOf("@private")>0){
						docMethod.Type = "private";
					}
					if (line.endsWith("*/")) {
						comment = false;
					}
				} else if (line.startsWith("//")) {
					// commentLine;
				} else {
					// codeLine;
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 分析文件目录列表中的所有文件，提取其中的说有.js结尾的文件
	 * 分析提取的js文件列表，得出分析结果DocClass列表
	 * 将所有分析的DocClass生成对应的html文件
	 */
	public void generate() {
		for (int i = 0; i < docFilePath.length; i++) {
			analysisFileOrDir(new File(docFilePath[i]));
		}
		
		for (int i = 0; i < fileList.size(); i++) {
			analyze(fileList.get(i));
		}
		
		String path = "D:\\java\\JDP\\JDP\\doc\\";//realPath + File.separator + "WEB-INF" + File.separator + "resources" + File.separator;

//        Velocity.setProperty(Velocity.INPUT_ENCODING, "GBK");
//        Velocity.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path);
//        try {
//			Velocity.init();
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
		for (int i = 0; i < docClassList.size(); i++) {
			DocClass docClass = docClassList.get(i);
			System.out.print("class:");
			System.out.println(docClass.Name);
			System.out.println(docClass.Comment);
			System.out.println(docClass.DefinedInFileName);
			
			for (int j = 0; j < docClass.methodList.size(); j++) {
				DocMethod method = docClass.methodList.get(j);
				System.out.print("				method:");
				System.out.println("					"+method.Name);
				System.out.println("					"+method.Type);
				System.out.println("					"+method.Comment);
				for (int k = 0; k < method.Properties.size(); k++) {
					DocProperty property = method.Properties.get(k);
					System.out.print("							property:");
					System.out.println("								" + property.Name);
					System.out.println("								" + property.Type);
					System.out.println("								" + property.Comment);
				}
				
				DocProperty property = method.Return;
				System.out.print("						" + "return:");
				System.out.println("								" + property.Type);
				System.out.println("								" + property.Name);
				System.out.println("								" + property.Comment);
			}
			
//	        VelocityContext context = new VelocityContext();
//	        StringWriter sw = new StringWriter();
//	        context.put("ClassName",  docClass.Name);
//	        context.put("DefinedInFileName",  docClass.DefinedInFileName);
//	        context.put("ClassComment",  docClass.Comment);
//	        
//	        context.put("Methods",  docClass.methodList);
//	        context.put("Properties",  docClass.propertyList);
//	        
//			String JSDOC_TEMPLATE_NAME = "template-doc.vm";
//			try {
//				 org.apache.velocity.Template template = Velocity.getTemplate(JSDOC_TEMPLATE_NAME);
//				 template.merge(context, sw);
//			}  catch (Exception e) {
//				e.printStackTrace();
//			}
//			System.out.println(sw.toString());
//			try {
//				FileWriter writer = new FileWriter(new File(path + docClass.Name + ".html"));
//				writer.write(sw.toString());
//				writer.flush();
//				writer.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		
	}
	
	public static void main(String[] args) {
//		String path = "D:\\java\\MyEclipseWorkspace\\pt\\source\\util\\";
		String corePath = "D:\\java\\JDP\\JDP\\WebRoot\\Framework\\src\\";
		JsDocGenerator generator = new JsDocGenerator(
				//path + "String.js", 
				//path + "Array.js",
				corePath + "Sky.js");
		generator.generate();
	}
}
