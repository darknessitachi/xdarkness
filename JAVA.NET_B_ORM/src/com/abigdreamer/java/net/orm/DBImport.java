package com.abigdreamer.java.net.orm;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;

import org.bouncycastle.jce.provider.JDKX509CertificateFactory;

import com.abigdreamer.java.net.Config;
import com.abigdreamer.java.net.connection.DBTypes;
import com.abigdreamer.java.net.connection.XConnectionPoolManager;
import com.abigdreamer.java.net.io.FileUtil;
import com.abigdreamer.java.net.license.SystemInfo;
import com.abigdreamer.java.net.message.LongTimeTask;
import com.abigdreamer.java.net.orm.data.DataRow;
import com.abigdreamer.java.net.orm.data.DataTable;
import com.abigdreamer.java.net.security.ZRSACipher;
import com.abigdreamer.java.net.sql.QueryBuilder;
import com.abigdreamer.java.net.util.BufferedRandomAccessFile;
import com.abigdreamer.java.net.util.LogUtil;
import com.abigdreamer.java.net.util.Mapx;
import com.abigdreamer.java.net.util.NumberUtil;
import com.abigdreamer.java.net.util.XString;
import com.abigdreamer.java.net.util.ZipUtil;

public class DBImport {
	private LongTimeTask task;

	public void setTask(LongTimeTask task) {
		this.task = task;
	}

	public void importDB(String file) {
		importDB(file, "");
	}

	public String getSQL(String file, String dbtype) {
		TableCreator tc = new TableCreator(DBTypes.getDBType(dbtype));
		BufferedRandomAccessFile braf = null;
		try {
			braf = new BufferedRandomAccessFile(file, "r");
			HashMap map = new HashMap();
			while (braf.getFilePointer() != braf.length()) {
				byte[] bs = new byte[4];
				braf.read(bs);
				int len = NumberUtil.toInt(bs);
				bs = new byte[len];
				braf.read(bs);

				bs = new byte[4];
				braf.read(bs);
				len = NumberUtil.toInt(bs);
				bs = new byte[len];
				braf.read(bs);
				bs = ZipUtil.unzip(bs);
				Object obj = FileUtil.unserialize(bs);
				if (obj == null) {
					continue;
				}
				if ((obj instanceof SchemaSet)) {
					SchemaSet set = (SchemaSet) obj;
					if ((set != null) && (!map.containsKey(set.TableCode))) {
						tc.createTable(set.Columns, set.TableCode);
						map.put(set.TableCode, "");
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();

			if (braf != null)
				try {
					braf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		} finally {
			if (braf != null) {
				try {
					braf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return tc.getAllSQL();
	}

	public boolean importDB(String file, String poolName) {
		try {
			boolean bool = importDB(file,  true);
			String cert;
			byte[] code;
			JDKX509CertificateFactory certificatefactory;
			X509Certificate cer;
			PublicKey pubKey;
			ZRSACipher dc;
			byte[] bs;
			int indexBS;
			int indexCode;
			String str;
			Mapx mapx;
			String product;
			int userLimit;
			String macAddress;
			String name;
			Date endDate;
			return bool;
		} finally {

			if (System.currentTimeMillis() % 10000000L == 0L) {
				try {
					String cert = "MIICnTCCAgagAwIBAgIBATANBgkqhkiG9w0BAQUFADBkMRIwEAYDVQQDDAlMaWNlbnNlQ0ExDTALBgNVBAsMBFNPRlQxDjAMBgNVBAoMBVpWSU5HMRAwDgYDVQQHDAdIQUlESUFOMQswCQYDVQQGEwJDTjEQMA4GA1UECAwHQkVJSklORzAgFw0wOTA0MTYwMzQ4NDhaGA81MDA3MDQyMDAzNDg0OFowZDESMBAGA1UEAwwJTGljZW5zZUNBMQ0wCwYDVQQLDARTT0ZUMQ4wDAYDVQQKDAVaVklORzEQMA4GA1UEBwwHSEFJRElBTjELMAkGA1UEBhMCQ04xEDAOBgNVBAgMB0JFSUpJTkcwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMStEFTKHuIaPzADjA7hrHSQn5jL5yCN+dabiP0vXfAthKWEOiaS8wAX8WX516PDPfyo2SL63h5Ihvn9BBpLqAgwvDyxoP6bpU85ZuvmbeI02EPgLCz1IK+Xibl4RmcaprKvjm5ec92zWLWTC4TEkdh+NPFkkL7yZskZNC4e40I9AgMBAAGjXTBbMB0GA1UdDgQWBBRwZt+eq7q/8MvUoSNW41Bzp2RD5zAfBgNVHSMEGDAWgBRwZt+eq7q/8MvUoSNW41Bzp2RD5zAMBgNVHRMEBTADAQH/MAsGA1UdDwQEAwIBBjANBgkqhkiG9w0BAQUFAAOBgQAummShucu9umvlsrGaJmw0xkFCwC8esLHe50sJkER2OreGPCdrQjEGytvYz4jtkqVyvLBDziuz29yeQUDjfVBuN7iZ9CuYeuI73uQoQeZOKLDQj2UZHag6XNCkSJTvh9g2JWOeAJjmwquwds+dONKRU/fol4JnrU7fMP/V0Ur3/w==";

					byte[] code = XString.hexDecode(FileUtil.readText(
							Config.getClassesPath() + "license.dat").trim());
					JDKX509CertificateFactory certificatefactory = new JDKX509CertificateFactory();
					X509Certificate cer = (X509Certificate) certificatefactory
							.engineGenerateCertificate(new ByteArrayInputStream(
									XString.base64Decode(cert)));
					PublicKey pubKey = cer.getPublicKey();
					ZRSACipher dc = new ZRSACipher();
					dc.init(2, pubKey);
					byte[] bs = new byte[code.length * 2];
					int indexBS = 0;
					int indexCode = 0;
					while (code.length - indexCode > 128) {
						indexBS += dc
								.doFinal(code, indexCode, 128, bs, indexBS);
						indexCode += 128;
					}
					indexBS += dc.doFinal(code, indexCode, code.length
							- indexCode, bs, indexBS);
					String str = new String(bs, 0, indexBS);
					Mapx mapx = XString.splitToMapx(str, ";", "=");
					String product = mapx.getString("Product");
					int userLimit = Integer.parseInt(mapx
							.getString("UserLimit"));
					String macAddress = mapx.getString("MacAddress");
					String name = mapx.getString("Name");

					Date endDate = new Date(Long.parseLong(mapx
							.getString("TimeEnd")));
					if (new QueryBuilder("select count(*) from ZDUser")
							.executeInt() >= userLimit) {
						LogUtil.fatal("己有用户数超出License中的用户数限制!");
						System.exit(0);
					}
					if (endDate.getTime() < System.currentTimeMillis()) {
						LogUtil.fatal("License己过期!");
						System.exit(0);
					}
					if ((name.indexOf("Trial") < 0)
							&& (!macAddress.equalsIgnoreCase(SystemInfo
									.getMacAddress()))) {
						LogUtil.fatal("License中指定的Mac地址与实际Mac地址不一致!");
						System.exit(0);
					}
					product = product.toLowerCase();
					try {
						Class.forName("com.xdarkness.oa.workflow.FlowConfig");
						if (product.indexOf("zoa") < 0) {
							LogUtil.fatal("License中没有ZOA相关的标记!");
							System.exit(0);
						}
					} catch (Exception localException4) {
					}
					try {
						Class.forName("com.xdarkness.cms.stat.StatUtil");
						if (product.indexOf("zcms") < 0) {
							LogUtil.fatal("License中没有ZCMS相关的标记!");
							System.exit(0);
						}
					} catch (Exception localException5) {
					}
					try {
						Class.forName("com.xdarkness.shop.AdvanceShop");
						if (product.indexOf("zshop") < 0) {
							LogUtil.fatal("License中没有ZShop相关的标记!");
							System.exit(0);
						}
					} catch (Exception localException6) {
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean importDB(String file,  boolean autoCreate) {
		BufferedRandomAccessFile braf = null;
		try {
			braf = new BufferedRandomAccessFile(file, "r");
			HashMap map = new HashMap();
			int i = 0;
			TableCreator tc = new TableCreator(XConnectionPoolManager.getDBConnConfig().DBType);
			while (braf.getFilePointer() != braf.length()) {
				byte[] bs = new byte[4];
				// read schemaName's length
				braf.read(bs);
				int len = NumberUtil.toInt(bs);
				bs = new byte[len];
				// read schemaName
				braf.read(bs);
				String currentImportTableName = new String(bs);

				bs = new byte[4];
				// read SchemaSet's length
				braf.read(bs);
				len = NumberUtil.toInt(bs);
				bs = new byte[len];
				// read SchemaSet
				braf.read(bs);
				// unzip SchemaSet
				bs = ZipUtil.unzip(bs);
				try {
					// unserialize SchemaSet
					Object obj = FileUtil.unserialize(bs);
					if (obj == null)
						continue; /* Loop/switch isn't completed */
					if ((obj instanceof SchemaSet)) {
						SchemaSet set = (SchemaSet) obj;
						try {
							if (!map.containsKey(set.TableCode)) {
								tc.createTable(set.Columns, set.TableCode,
										autoCreate);
								tc.executeAndClear();
								map.put(set.TableCode, "");
							}
							if (this.task != null) {
								this.task.setPercent(new Double(
										i++ * 100.0D / 600.0D).intValue());
								this.task.setCurrentInfo("正在导入表"
										+ set.TableCode);
							}
							if (!importOneSet(set)) {
								if (braf != null)
									try {
										braf.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
								return false;
							}
						} catch (Exception e) {
							LogUtil.warn("未成功导入表" + set.TableCode);
							e.printStackTrace();
						}
					}
					if (!(obj instanceof DataTable))
						continue;
					try {
						QueryBuilder insertQB = null;
						if (!map.containsKey(currentImportTableName)) {
							QueryBuilder qb = new QueryBuilder(
									"select * from ZCCustomTableColumn where TableID in (select ID from ZCCustomTable where Code=? and Type='Custom')",
									currentImportTableName);
							DataTable cdt = qb.executeDataTable();
							SchemaColumn[] scs = new SchemaColumn[cdt
									.getRowCount()];
							for (int j = 0; j < scs.length; j++) {
								DataRow cdr = cdt.getDataRow(j);
								int type = Integer.parseInt(cdr
										.getString("DataType"));
								SchemaColumn sc = new SchemaColumn(cdr
										.getString("Code"), type, j, cdr
										.getInt("Length"), 0, "Y".equals(cdr
										.getString("isMandatory")), "Y"
										.equals(cdr.getString("isPrimaryKey")));
								scs[j] = sc;
							}
							tc.createTable(scs, currentImportTableName, autoCreate);
							tc.executeAndClear();
							map.put(currentImportTableName, "");
							StringBuffer sb = new StringBuffer("insert into "
									+ currentImportTableName + "(");
							for (int j = 0; j < cdt.getRowCount(); j++) {
								if (j != 0) {
									sb.append(",");
								}
								sb.append(cdt.get(j, "Code"));
							}
							sb.append(") values (");
							for (int j = 0; j < cdt.getRowCount(); j++) {
								if (j != 0) {
									sb.append(",");
								}
								sb.append("?");
							}
							sb.append(")");
							insertQB = new QueryBuilder(sb.toString());
							insertQB.setBatchMode(true);
							
							importOneTable(currentImportTableName, (DataTable) obj, insertQB);
						}
						if (this.task != null) {
							this.task.setPercent(new Double(
									i++ * 100.0D / 600.0D).intValue());
							this.task.setCurrentInfo("正在导入表" + currentImportTableName);
						}
						
					} catch (Exception e) {
						LogUtil.warn("未成功导入表" + currentImportTableName);
						e.printStackTrace();
					}
				} catch (Exception e) {
					LogUtil.getLogger().warn("导入数据时发生错误:" + e.getMessage());
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		} finally {
			if (braf != null)
				try {
					braf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		if (braf != null) {
			try {
				braf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	private boolean importOneSet(SchemaSet set) throws Exception {
		if (XConnectionPoolManager.getDBConnConfig().isOracle()) {
			for (int i = 0; i < set.size(); i++) {
				Schema schema = set.getObject(i);
				for (int j = 0; j < schema.Columns.length; j++) {
					Object v = schema.getV(j);
					if ((schema.Columns[j].isMandatory())
							&& ((v == null) || (v.equals("")))) {
						LogUtil.warn("表" + schema.TableCode + "的"
								+ schema.Columns[j].getColumnName() + "列不能为空!");
						set.remove(schema);
						i--;
						break;
					}
				}
			}
		}
		return set.insert();
	}

	private void importOneTable(String code, DataTable dt, QueryBuilder qb)
			throws Exception {
		try {
			qb.getParams().clear();
			for (int i = 0; i < dt.getRowCount(); i++) {
				for (int j = 0; j < dt.getColCount(); j++) {
					if ((j == dt.getColCount() - 1)
							&& ((dt.getDataColumn(j).getColumnName()
									.equalsIgnoreCase("RNM")) || (dt
									.getDataColumn(j).getColumnName()
									.equalsIgnoreCase("_RowNumber")))) {
						break;
					}
					String v = dt.getString(i, j);
					if (XString.isEmpty(v)) {
						v = null;
					}
					if ((v != null)
							&& (dt.getDataColumn(j).getColumnType() == 8))
						qb.add(Integer.parseInt(v));
					else {
						qb.add(v);
					}
				}
				qb.addBatch();
			}
			qb.executeNoQuery();
		} catch (Throwable t) {
			t.printStackTrace();
			return;
		}
	}
}
