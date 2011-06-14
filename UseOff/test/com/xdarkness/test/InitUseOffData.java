package com.xdarkness.test;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.abigdreamer.java.net.sql.QueryBuilder;
import com.abigdreamer.java.net.util.LogUtil;

/**
 * @author Darkness
 * 
 * 		  QQ:	 893951837
 * 		  Email: darkness_sky@qq.com
 * 		  Blog:  http://depravedAngel.javaeye.com/
 *
 * 	      Copyright (c) 2009 by Darkness
 *
 * @date 2010-7-28 下午07:41:48
 * @version 1.0
 */
public class InitUseOffData {

	public static void loadConfig() {
        SAXReader reader = new SAXReader(false);
        String dataFileName = "data.xml";
        InputStream f = InitUseOffData.class.getClassLoader().getResourceAsStream(dataFileName);
        if (f==null) {
            LogUtil.warn("配置文件" + dataFileName + "未找到!");
            return;
        }
        try {
            Document doc = reader.read(f);
            Element root = doc.getRootElement();
            
            new QueryBuilder("DELETE FROM UseOff").executeNoQuery();
            
            QueryBuilder queryBuilder = new QueryBuilder("INSERT INTO UseOff(money, moneyType, useFor, description, createTime) VALUES(?,?,?,?,?)");
          
            queryBuilder.setBatchMode(true);
            
            List<?> elements = root.elements();
            System.out.println("=====total size:"+elements.size());
            for (int i = 0; i < elements.size(); i++) {
                Element ele = (Element) elements.get(i);

                Double money = Double.parseDouble(ele.attributeValue("money"));
        		String moneyType = "out";
        		if (ele.attributeValue("moneyType") != null) {
        			moneyType = ele.attributeValue("moneyType");
				}
        		String useFor = ele.attributeValue("useFor");
        		String description = "";
        		try {
        			description = ele.attributeValue("description");
        		} catch (Exception e) {
        			// 改属性可能不存在，不存在及忽略
        		}
        		String createTime = ele.attributeValue("createTime");
        		
        		queryBuilder.add(money).add(moneyType).add(useFor).add(description).add(createTime).addBatch();
            }
            queryBuilder.executeNoQuery();
            
            System.out.println("数据初始化成功");
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
	
	public static void main(String[] args) {
		InitUseOffData.loadConfig();
	}
}
