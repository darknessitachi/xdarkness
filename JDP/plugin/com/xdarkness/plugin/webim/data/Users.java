package com.xdarkness.plugin.webim.data;

import java.util.Date;
import java.util.Map;

import net.sourceforge.jtds.jdbc.DateTime;

import org.apache.lucene.util.StringHelper;

import com.xdarkness.plugin.webim.business.Profile;
import com.sybase.jdbc2.jdbc.Convert;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.orm.data.DataRow;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;

public class Users {
	private String _globalError;// 全局错误

	public String getGlobalError() {
		return _globalError;
	}

	// / <summary>
	// / 为添加好友提供数据
	// / </summary>
	// / <returns></returns>
	public DataTable GetFinder() {
		DataTable finderTable = new QueryBuilder(
				"select id, loginname, username, isoutter from im_users where isoutter = 0 order by username ")
				.executeDataTable();

		try {
			Map<String, String> mappingList = Profile
					.GetMappingList("userinformation");

			if (mappingList != null) {
				String commandText = "select ";

				for (DataColumn column : finderTable.getDataColumns()) {
					String columnName = column.getColumnName().toLowerCase();

					if (mappingList.containsKey(columnName)) {
						commandText += XString.format("{0} as {1}, ",
								mappingList.get(columnName), columnName);
					}
				}

				commandText += XString.format("'1' as isoutter from {0}",
						mappingList.get("tablename"));

				if (mappingList.get("condition").length() > 0) {
					commandText += XString.format(" where {0}", mappingList
							.get("condition"));
				}

				DataTable mapping = new QueryBuilder(commandText)
						.executeDataTable();
				finderTable.union(mapping);
			}
		} catch (Exception exception) {
			_globalError = exception.getMessage();
		}

		return finderTable;
	}

	// 获取用户树
	public DataTable GetUserTree() {
		return new QueryBuilder("select * from im_users order by username")
				.executeDataTable();
	}

	// 根据loginName检索用户是否存在
	public String CheckUserExist(String loginName, int flag) {
		String result = "";

		if (flag == 0) {

			result = new QueryBuilder(
					"select count(id) from im_users where loginname = '{0}'",
					loginName).executeString();
		} else {

			DataTable finderTable = GetFinder();
			result = finderTable.select(XString.format("loginname = '{0}'",
					loginName)).length
					+ "";
		}

		return result;
	}

	// / <summary>
	// / 为添加好友提供模糊检索用户数据
	// / </summary>
	// / <param name="userName">用户名</param>
	// / <returns></returns>
	public DataRow[] GetUsersToFind(String userName) {
		DataRow[] finderRows = null;

		try {
			DataTable finderTable = GetFinder();
			finderRows = finderTable.select(XString.format(
					"username like '%{0}%'", userName)// ,
														// "isoutter,username asc"
					);
		} catch (Exception exception) {

			_globalError = exception.getMessage();
		}

		return finderRows;
	}

	// / <summary>
	// / 根据用户id，密码来查询用户是否存在
	// / </summary>
	// / <param name="id">用户id</param>
	// / <param name="userPwd">密码</param>
	// / <returns>该用户存在返回true，否则返回false</returns>
	public boolean CheckUserExist(String id, String userPwd) {
		String commandText = String
				.Format(
						"select count(id) from im_users where id='{0}' and loginPwd='{1}'",
						id, StringHelper.DesEncrypt(userPwd, false));

		int count = Convert.ToInt32(dataHelper.ExecuteQuary(commandText));

		if (count > 0) {
			return true;
		}

		return false;
	}

	// / <summary>
	// / 根据用户id来查询用户是否存在
	// / </summary>
	// / <param name="id">用户id</param>
	// / <returns>该用户存在返回true，否则返回false</returns>
	public boolean CheckUserExistById(String id) {
		return new QueryBuilder(
				"select count(id) from im_users where id='{0}'", id)
				.executeInt() > 0;
	}

	// 根据loginName,userPwd查询出users
	public DataTable GetUser(String loginName, String userPwd) {
		return new QueryBuilder(
				"select id, username, headImg, scratch, theme from im_users where loginname = '{0}' and loginpwd = '{1}'",
				loginName, StringHelper.DesEncrypt(userPwd, false))
				.executeDataTable();
	}

	// / <summary>
	// / 根据id查询出users
	// / </summary>
	// / <param name="id"></param>
	// / <returns></returns>
	public DataTable GetUserById(String id) {
		return new QueryBuilder("select * from im_users where id = '{0}'", id)
				.executeDataTable();
	}

	// 注册单位用户
	public void RegisterUser(String id, String loginName, String userName,
			String userPwd, String sex, int isouter) {
		return new QueryBuilder(
				"insert into im_users (id, username, loginname, loginpwd, sex, isoutter) values ('{4}', '{0}', '{1}', '{2}', {3}, {5})",
				userName, loginName, StringHelper.DesEncrypt(userPwd, false),
				sex.Equals("男") ? "0" : "1", (id.Length == 0 ? Profile.GUID
						: id), isouter).executeInt();
	}

	// / <summary>
	// / 注册外来单位用户
	// / </summary>
	// / <param name="loginName">登陆名</param>
	// / <param name="userName">姓名</param>
	// / <param name="id">id</param>
	public void RegisterOutter(String id, String loginName, String userName) {
		new QueryBuilder(
				"insert into im_users (id, loginname, username, isoutter) values ('{0}', '{1}', '{2}', 1)",
				id, loginName, userName).executeInt();
	}

	// / <summary>
	// / 更新密码
	// / </summary>
	// / <param name="id">用户Id</param>
	// / <param name="passwordNew">新密码</param>
	// / <returns>更新成功返回true，否则返回失败</returns>
	public boolean ChangePassword(String id, String passwordNew) {
		return new QueryBuilder(
				"update im_users set loginpwd='{0}' where id='{1}'",
				StringHelper.DesEncrypt(passwordNew, false), id).executeInt() > 0;
	}

	// / <summary>
	// / 更新外单位用户的姓名跟登陆名
	// / </summary>
	// / <param name="userName">姓名</param>
	// / <param name="loginName">登陆名</param>
	// / <returns></returns>
	public boolean ChangeOutter(String userName, String loginName, String id) {
		return new QueryBuilder(
				"update im_users set username='{0}', loginname='{1}' where id='{2}'",
				userName, loginName, id).executeInt() > 0;
	}

	// / <summary>
	// / 更改用户自定义回复消息、个性签名、主题
	// / </summary>
	// / <param name="revertMessage">自定义回复消息</param>
	// / <param name="scratch">个性签名</param>
	// / /// <param name="personalityScratch">主题</param>
	// / <returns></returns>
	public boolean ChangeSettings(String revertMessage, String scratch,
			String theme, String id) {

		return new QueryBuilder(
				"update im_users set revertMessage='{0}', scratch='{1}', theme='{2}' where id='{3}'",
				revertMessage, scratch, theme, id).executeInt() > 0;
	}

	// / <summary>
	// /更改用户自定义回复消息、个性签名、主题、头像
	// / </summary>
	// / <param name="revertMessage">自定义回复消息</param>
	// / <param name="scratch">个性签名</param>
	// / <param name="personalityScratch">主题</param>
	// / <param name="headImg">图片名称</param>
	// / <returns></returns>
	public boolean ChangeSettings(String revertMessage, String scratch,
			String theme, String id, String headImg) {

		return new QueryBuilder(
				"update im_users set revertMessage='{0}', scratch='{1}', theme='{2}', headImg='{3}' where id='{4}'",
				revertMessage, scratch, theme, headImg, id).executeInt() > 0;
	}

	// / <summary>
	// / 更改用户自定义回复消息、个性签名、主题、头像、中文名
	// / </summary>
	// / <param name="revertMessage">自定义回复消息</param>
	// / <param name="scratch">个性签名</param>
	// / <param name="personalityScratch">主题</param>
	// / <param name="headImg">图片名称</param>
	// / <returns></returns>
	public boolean ChangeSettings(String revertMessage, String scratch,
			String theme, String id, String headImg, String userName) {

		return new QueryBuilder(
				"update im_users set revertMessage='{0}', scratch='{1}', theme='{2}', headImg='{3}', username='{4}' where id='{5}'",
				revertMessage, scratch, theme, headImg, userName, id)
				.executeInt() > 0;
	}

	// / <summary>
	// / 更新用户头像
	// / </summary>
	// / <param name="headImg">图片名称</param>
	// / <param name="id">用户Id</param>
	// / <returns></returns>
	public boolean ChangeHeadImg(String headImg, String id) {
		return new QueryBuilder(
				"update im_users set headImg='{0}' where id='{1}'", headImg, id)
				.executeInt() > 0;
	}

	private String _id;

	private String _loginName;

	private String _userName;

	private int _isOutter;

	private boolean _isFriend;

	private int _index;

	// / <summary>
	// / 更新当前用户的在线时间
	// / </summary>
	// / <param name="loginer"></param>
	// / <param name="loginTime"></param>
	public void UpdateOnLineTime(String loginer, Date loginTime) {

		new QueryBuilder(
				"update im_users set onlinetime = onlinetime + {1:.##} where id = '{0}'",
				loginer, DateTime.Now.Subtract(loginTime).TotalMinutes)
				.executeInt();
	}

	// / <summary>
	// / 获取用户在线时间
	// / </summary>
	// / <param name="loginer">当前用户</param>
	// / <returns></returns>
	public DataTable GetOnLineTime(String loginer) {
		String commandText = "";

		if (loginer.length() == 0) {
			commandText = "select username, onlinetime from users";
		} else {
			commandText = XString
					.format(
							"select username, onlinetime from im_users where username like '%{0}%'",
							loginer);
		}

		return new QueryBuilder(commandText).executeDataTable();
	}
}
