package com.abigdreamer.java.net.connection;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.abigdreamer.java.net.Config;
import com.abigdreamer.java.net.security.EncryptUtil;
import com.ibm.db2.jcc.b.db;

/**
 * //	<Type>MYSQL</Type>
//	<ServerAddress>localhost</ServerAddress>
//	<Port>3306</Port>
//	<Name>zcms</<Name>>
//	<UserName>root</UserName>
//	<Password>$KEYw//j8IH+fBtReFR4D88/Hw==</Password>
//	<MaxConnCount>1000</MaxConnCount>
//	<InitConnCount>0</InitConnCount>
//	<TestTable>ZDMaxNo</TestTable>
 * @author Administrator
 *
 */
public class DBConfig extends Config {

	private static Map<String, Database> databaseList = new HashMap<String,Database>();
	
	static {
		loadDataBasesConfig();
	}
	static Database getDatabase(String dbname) {
		return databaseList.get(dbname);
	}
	@SuppressWarnings("unchecked")
	static void loadDataBasesConfig() {
		
		SAXReader reader = new SAXReader(false);
		File file = new File(CONFIG_FILE_NAME);
		Document doc = null;
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element root = doc.getRootElement();
		Element databases = root.element("databases");
		
		if (databases != null) {
			List<Element> dbs = databases.elements();
			for (int i = 0; i < dbs.size(); i++) {
				Element ele = dbs.get(i);
				String dbname = ele.attributeValue("name").trim();
				
				Database database = new Database();
				database.Type = ele.element("Type").getTextTrim();
				database.ServerAddress= ele.element("ServerAddress").getTextTrim();
				database. Port= ele.element("Port").getTextTrim();
				database. Name= ele.element("Name").getTextTrim();
				database. UserName= ele.element("UserName").getTextTrim();
				database. MaxConnCount= ele.element("MaxConnCount").getTextTrim();
				database. InitConnCount= ele.element("InitConnCount").getTextTrim();
				database. TestTable= ele.element("TestTable").getTextTrim();
				String password = ele.element("Password").getTextTrim();
				
				if (password.startsWith("$KEY")) {
					password = EncryptUtil.decrypt3DES(password.substring(4), "27jrWz3sxrVbR+pnyg6j");
				}
				database. Password= password;
				
				databaseList.put(dbname, database);
			}
		}
	}
	
}

class Database {
	String Type;
	String ServerAddress;
	String Port;
	String Name;
	String UserName;
	String Password;
	String MaxConnCount;
	String InitConnCount;
	String TestTable;
	
}
