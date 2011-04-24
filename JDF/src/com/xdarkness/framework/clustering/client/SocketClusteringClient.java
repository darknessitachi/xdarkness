package com.xdarkness.framework.clustering.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import com.xdarkness.framework.clustering.Clustering.Server;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.XString;

public class SocketClusteringClient extends ClusteringClient {
	private Server server;
	private Socket so;

	public SocketClusteringClient(Server server) {
		this.server = server;
		if (!server.URL.startsWith("socket://"))
			throw new RuntimeException("错误的集群服务器URL：" + server.URL);
	}

	private void init() {
		if (this.so == null)
			synchronized (this) {
				if (this.so == null) {
					String url = this.server.URL;
					String address = url.substring("socket://".length());
					int index = address.indexOf(":");
					if (index < 1) {
						throw new RuntimeException("错误的集群服务器URL："
								+ this.server.URL);
					}
					String portStr = address.substring(index + 1);
					address = address.substring(0, index);
					int port = 0;
					try {
						port = Integer.parseInt(portStr);
					} catch (Exception e) {
						throw new RuntimeException("错误的集群服务器URL："
								+ this.server.URL);
					}
					try {
						this.so = new Socket(address, port);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
	}

	public String executeMethod(String type, String key, String action,
			String value) {
		init();
		for (int i = 0; i < this.server.RetryCount;) {
			try {
				OutputStream outStream = this.so.getOutputStream();
				PrintWriter out = new PrintWriter(outStream);
				out.println("Type:Data");
				out.println("Action:Get");
				out.println("Key:" + XString.javaEncode(key));
				if (value != null) {
					out.println("Value:" + XString.javaEncode(value));
				}
				out.println("End");
				InputStream is = this.so.getInputStream();
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is));
				StringBuffer sb = new StringBuffer();
				String line;
				while ((line = br.readLine()) != null) {
					if (line.equals("End")) {
						break;
					}
					sb.append(line);
					sb.append("\n");
				}
				return sb.toString();
			} catch (Exception e) {
				LogUtil.info("HttpClusteringClient.get()发生异常:" + e.getMessage()
						+ ";URL=" + this.server.URL);

				i++;
			}

		}

		synchronized (this.server) {
			this.server.isAlive = false;
		}
		throw new RuntimeException("HttpClusteringClient.get()发生错误，重试"
				+ this.server.RetryCount + "次皆失败;URL=" + this.server.URL);
	}
}

/*
 * com.xdarkness.framework.clustering.client.SocketClusteringClient JD-Core Version:
 * 0.6.0
 */