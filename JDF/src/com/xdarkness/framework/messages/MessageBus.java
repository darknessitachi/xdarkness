package com.xdarkness.framework.messages;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.xdarkness.framework.Config;
import com.xdarkness.framework.util.Mapx;

public class MessageBus {
	private static Mapx Types = new Mapx();

	static {
		loadConfig();
	}

	private static void loadConfig() {
		String path = Config.getContextRealPath()
				+ "WEB-INF/classes/framework.xml";
		SAXReader reader = new SAXReader(false);
		try {
			Document doc = reader.read(new File(path));
			Element root = doc.getRootElement();
			Element messages = root.element("messages");
			if (messages != null) {
				List eles = messages.elements();
				for (int i = 0; i < eles.size(); i++) {
					Element message = (Element) eles.get(i);
					String name = message.attributeValue("name");
					List receivers = message.elements();
					ArrayList arr = new ArrayList();
					for (int j = 0; j < receivers.size(); j++) {
						Element receiver = (Element) receivers.get(i);
						arr.add(receiver.attributeValue("class"));
					}
					Types.put(name, arr);
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public static String[] getMessageNames(MessageReceiver receiver) {
		return null;
	}

	public static void send(MessageSender sender) {
		Object o = Types.get(sender.getMessageName());
		if (o == null) {
			throw new RuntimeException("未注册的消息类别：" + sender.getMessageName());
		}
		ArrayList list = (ArrayList) o;
		for (int i = 0; i < list.size(); i++) {
			String className = (String) list.get(i);
			MessageReceiver r = null;
			try {
				r = (MessageReceiver) Class.forName(className).newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			Mapx feedback = r.receive(sender.getMessage());
			sender.receiveFeedback(feedback);
		}
	}
}