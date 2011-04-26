<%@page import="com.xdarkness.schema.ZCBoardMessageSchema"%>
<%@page import="com.xdarkness.member.Member"%>
<%@page import="com.xdarkness.framework.User"%>
<%@page import="com.xdarkness.platform.pub.NoUtil"%>
<%@page import="com.xdarkness.framework.utility.StringUtil"%>
<%@page import="com.xdarkness.framework.data.QueryBuilder"%>
<%@page import="java.util.*"%>
<%
String UserName = request.getParameter("UserName");
String PassWord = request.getParameter("PassWord");
String QQ = request.getParameter("QQ");
String IP = request.getRemoteAddr();
String HiddenUserName = request.getParameter("HiddenUserName");
String Email = request.getParameter("Email");
String Title = request.getParameter("Title");
String Content = request.getParameter("MsgContent");
String BoardID = request.getParameter("BoardID");
if(!StringUtil.isEmpty(Content)||!StringUtil.isEmpty(BoardID)||new QueryBuilder("select count(*) from ZCMessageBoard where ID = ?",BoardID).executeInt()>0){
	if(User.isLogin()){
		UserName = User.getUserName();
		if(StringUtil.isEmpty(Email)){
			Member member = new Member(UserName);	
			member.fill();
			Email = member.getEmail();
		}
	}else{
		if("on".equals(HiddenUserName)){
			UserName = "匿名网友";
		}else{
			if(StringUtil.isNotEmpty(UserName)&&StringUtil.isNotEmpty(PassWord)){
				Member member = new Member(UserName);	
				if(member.isExists()&&member.fill()&&member.checkPassWord(PassWord)){
					User.setManager(false);
					User.setLogin(true);
					User.setUserName(member.getUserName());
					User.setRealName(member.getName());
					User.setType(member.getType());
					User.setMember(true);
					User.setValue("SiteID",member.getSiteID()+"");
					User.setValue("UserName", member.getUserName() + "");
					User.setValue("Type", member.getType());
				}else{
					out.print("<script type='text/javascript'>alert('用户名不存在或密码错误');window.history.go(-1);</script>");
					return;	
				}
			}else{
				out.print("<script type='text/javascript'>alert('用户名或密码错误');window.history.go(-1);</script>");
				return;	
			}
		}
	}
	if(StringUtil.isEmpty(Title)){
		Title = "[无主题]";
	}
	String IsOpen = new QueryBuilder("select IsOpen from ZCMessageBoard where ID = ?",BoardID).executeString();
	ZCBoardMessageSchema message = new ZCBoardMessageSchema();
	message.setID(NoUtil.getMaxID("BoardMessageID"));
	message.setBoardID(BoardID);
	message.setTitle(StringUtil.htmlEncode(Title));
	message.setContent(StringUtil.htmlEncode(Content));
	if(IsOpen.equals("Y")){
		message.setPublishFlag("N");
	}else{
		message.setPublishFlag("Y");
	}
	message.setReplyFlag("N");
	message.setEMail(Email);
	message.setQQ(QQ);
	message.setIP(IP);
	message.setAddUser(UserName);
	message.setAddTime(new Date());
	if(message.insert()){
		out.println("<script type='text/javascript'>alert('您的留言已经成功提交');window.location='"+request.getHeader("REFERER")+"';</script>");
	}
}else{
	out.println("<script type='text/javascript'>alert('未知错误');window.history.go(-1);</script>");
	return;
}

%>