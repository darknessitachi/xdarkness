package com.xdarkness.test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.plugin.useoff.UseOff;

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

	public static void getConnection() {
		 
         
	}
	public static void save(Element ele) {
		
		Double money = Double.parseDouble(ele.attributeValue("money"));
		String moneyType = "out";
		String useFor = ele.attributeValue("moneyType");
		String description = "";
		try {
			ele.attributeValue("description");
		} catch (Exception e) {
			// TODO: handle exception
		}
		String createTime = ele.attributeValue("createTime");
		
		UseOff useOff = new UseOff();
		useOff.setCreateTime(new java.util.Date());
		useOff.setDiscription(description);
		useOff.setMoney(money);
		useOff.setMoneyType(moneyType);
		useOff.setUseFor(useFor);
		
		
		try {
			new QueryBuilder("INSERT INTO UseOff(money, moneyType, useFor, description, createTime) VALUES("+money+",'"+moneyType+"','"+useFor+"','"+description+"','"+createTime+"')").executeNoQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void loadConfig() {
        SAXReader reader = new SAXReader(false);
        String dataFileName = "data.xml";
        InputStream f = InitUseOffData.class.getClassLoader().getResourceAsStream(dataFileName);
        if (f==null) {
//            LogUtil.warn("配置文件" + dataFileName + "未找到!");
        	System.out.println("文件不存在");
            return;
        }
        try {
            Document doc = reader.read(f);
            Element root = doc.getRootElement();
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zcms",
                    "root", "depravedAngel");
            String sql = "DELETE FROM UseOff";
            java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            
            sql = "INSERT INTO UseOff(money, moneyType, useFor, description, createTime) VALUES(?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
//            Element application = root.element("application");
            List elements = root.elements();
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
        			// TODO: handle exception
        		}
        		String createTime = ele.attributeValue("createTime");
        		
        		pstmt.setDouble(1, money);
        		pstmt.setString(2, moneyType);
        		pstmt.setString(3, useFor);
        		pstmt.setString(4, description);
        		pstmt.setString(5, createTime);
        		pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("数据初始化成功");
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	
        }
    }
	
	public static void main(String[] args) {
		InitUseOffData.loadConfig();
	}
}
