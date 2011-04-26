package admin;

// DtreeNode，树型的节点。
public class DtreeNode {
	public String Id = "";// 节点ID
	public String Pid = "-1";// 父节点ID
	public String Name = "";// 节点名称
	public String Url = "";// 节点URL
	public String Title = "";// 节点TITLE
	public String Target = "";
	public String Icon = "";
	public String IconOpen = "";
	public String Open = "";
	public String D = "d";

	public DtreeNode() {
	}

	// 生成节点的script代码语句
	private String generate() {
		if (Id.length() == 0 || Pid.length() == 0 || Name.length() == 0)
			return "";
		String outVal = D
				+ ".add(\'"
				+ Id
				+ "\',\'"
				+ Pid
				+ "\',\'"
				+ Name
				+ "\',\'"
				+ Url
				+ "\',\'"
				+ Title
				+ "\',\'"
				+ Target
				+ "\',\'"
				+ Icon
				+ "\',\'"
				+ IconOpen
				+ "\',"
				+ ((Open.equals("open") || Open.equals("1") || Open
						.equals("true")) ? "true" : "\'\'") + ");\r\n";
		while (outVal.indexOf(",\'\');") > 0)
			outVal = outVal.replace(",\'\');", ");");
		return outVal;

	}

	// / 生成节点的script代码语句
	public String toString() {
		return generate();
	}
}
