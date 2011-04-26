using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Collections.Generic;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.HtmlControls;
using Sky.ImData;
using System.Text;
using System.IO;

using NVelocity;
using NVelocity.App;
using NVelocity.Runtime;

namespace Sky.WebIm
{
    public partial class friend : Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            string method = Request.Params["method"];
            try
            {
                if (method == null)
                {
                    return;
                }

                if ("getFriends".Equals(method.Trim()))
                {
                    GetFriends();
                }
                else if ("addFriends".Equals(method.Trim()))
                {
                    AddFriends();
                }
                else if ("deleteFriend".Equals(method.Trim()))
                {
                    DeleteFriend();
                }
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        /// <summary>
        /// 添加好友
        /// </summary>
        public void AddFriends()
        {
            string[] friendIds = Request.Params["friendId"].Split(',');
            string[] loginNames = Request.Params["loginName"].Split(',');
            string[] userNames = Request.Params["userName"].Split(',');
            string[] isOutters = Request.Params["isOutter"].Split(',');
            
            string userId = Request.Params["userId"];
            
            string uName = Request.Params["uName"];
            string lName = Request.Params["lName"];
            string isOutter = Request.Params["iIsOutter"];

            Users users = new Users();
            try
            {
                if (int.Parse(isOutter) == 1)
                {// 如果是外单位用户
                    if (!users.CheckUserExistById(userId.Trim()))
                    {// 如果用户不存在，注册该用户
                        users.RegisterOutter(userId.Trim(), lName.Trim(), uName.Trim());
                    }
                    else
                    {// 用户存在，更新用户名跟登陆名，使其与整合系统数据保持同步
                        users.ChangeOutter(uName.Trim(), lName.Trim(), userId.Trim());
                    }
                }
            }
            catch { }

            string groupId = Request.Params["groupId"];
            string groupName = Request.Params["groupName"];

            bool flag = true;
            string tempGroupId = null;
            
            for (int i = 0; i < isOutters.Length; i++)
            {
                if (int.Parse(isOutters[i]) == 1)
                {// 如果是外单位用户
                    if (!users.CheckUserExistById(friendIds[i].Trim()))
                    {// 如果用户不存在，注册该用户
                        users.RegisterOutter(friendIds[i], loginNames[i], userNames[i]);
                    }
                    else
                    {// 用户存在，更新用户名跟登陆名，使其与整合系统数据保持同步
                        users.ChangeOutter(userNames[i], loginNames[i], friendIds[i]);
                    }
                }

                tempGroupId = ChangeUserGroup(userId, friendIds[i], groupName);
                // 添加我到该好友的陌生人分组下
                ChangeUserGroup(friendIds[i], userId, "陌生人");
                if (tempGroupId == null)
                {
                    flag = false;
                }
                else
                {
                    flag = flag && true;
                }
                
            }
            if (flag)
            {
                WriteMessage(tempGroupId);
            }
            else
            {
                WriteMessage("0");
            }
        }


        #region 删除好友
        /// <summary>
        /// 删除好友
        /// </summary>
        public void DeleteFriend()
        {
            string userId = Request.Params["userId"];
            string relationshiper = Request.Params["relationshiper"];
            
            GroupRelation groupRelation = new GroupRelation();
            if (!groupRelation.DeleteGroupRelation(userId, relationshiper))
            {
                WriteMessage("0");
            }
            else
            {
                WriteMessage("删除成功！");
            }
        }
        #endregion

        #region 移动好友到所选组
        /// <summary>
        /// 移动好友到所选组
        /// </summary>
        public string ChangeUserGroup(string userId, string relationshiper, string groupName)
        {
            UserGroup userGroup = new UserGroup();
            string strGroupId = null;
            if (groupName == "陌生人")
            {
                strGroupId = userGroup.GetStrangerGroupId();
            }
            else if(groupName == "自己")
            {
                WriteMessage("self");
                return null;
            }
            else
            {
                strGroupId = userGroup.ExistGroupByName(groupName, userId);
            }

            if (strGroupId == null || strGroupId == "") // 如果不存在该组，须先创建该组
            {
                string createGroupId = userGroup.CreateGroup(groupName, userId);
                if (createGroupId == "")
                {
                    WriteMessage("创建该组失败，请稍候重试！");
                    return null;
                }

                strGroupId = createGroupId;
            }

            bool flag = false;
            GroupRelation groupRelation = new GroupRelation();
            if (groupRelation.ExistGroupRelation(userId, relationshiper))
            {// 如果已经加过此好友
                bool existGroupRelation = groupRelation.ExistGroupRelation(userId, relationshiper, strGroupId);
                if (existGroupRelation == false)
                {// 该好友不在该组中,则移动该好友到该组中
                    flag = groupRelation.UpdateGroupRelation(userId, relationshiper, strGroupId);
                }
                else
                {
                    flag = true;
                }
            }
            else
            {// 没有加过此好友，添加该好友
                flag = groupRelation.InsertGroupRelation(userId, relationshiper, strGroupId);
            }

            if (flag)
            {
                return strGroupId;
            }
            return null;
        }
        #endregion

        #region 获取好友分页列表页面
        /// <summary>
        /// 获取好友分页列表页面
        /// </summary>
        public void GetFriends()
        {
            int start = 0;
            int limit = 10;

            // 类型转换出异常则不处理，采用默认值
            try {start = int.Parse(Request.Params["start"]);}catch{}
            try { limit = int.Parse(Request.Params["limit"]); }catch {}

            string txtSearch = Request.Params["search"];
            string userId = Request.Params["userId"];

            Users users = new Users();
            DataTable dt = null;
            DataRow[] dr = null;
            ICollection<Users> listUsers = null;
            VelocityContext vltContext = new VelocityContext();

            if (txtSearch == null || "".Equals(txtSearch.Trim()))
            {
                dt = users.GetFinder();
                string relationships = GetRelationships(userId);
                listUsers = GetUserList(dt, relationships, start, limit);

                vltContext.Put("Count", dt.Rows.Count);
            }
            else
            {
                dr = users.GetUsersToFind(txtSearch);
                string relationships = GetRelationships(userId);
                listUsers = GetUserList(dr, relationships, start, limit);

                vltContext.Put("Count", dr.Length);
            }
            
            vltContext.Put("ListUsers", listUsers);

            Response.Write(GetTemplateString("friendsTemplate.htm", vltContext));
        }
        #endregion

        public string GetRelationships(string userId)
        {
            string relationships = "";
            GroupRelation groupRelation = new GroupRelation();
            DataTable dtRelations = groupRelation.GetGroupRelation(userId);
            for (int i = 0; i < dtRelations.Rows.Count; i++)
            {
                DataRow dt = dtRelations.Rows[i];
                relationships += "'" + dt["relationshiper"] + "',";
            }
            relationships += "'" + userId + "',";
            return relationships.TrimEnd(',');
        }

        #region 将DataTable里面的数据转换入IList中
        /// <summary>
        /// 将DataTable里面的数据转换入IList中
        /// </summary>
        /// <returns></returns>
        public ICollection<Users> GetUserList(DataTable dt, string relationships, int start, int limit)
        {
            ICollection<Users> userList = new LinkedList<Users>();

            DataRow[] isNotFriends = null;
            DataRow[] isFriends = null;

            if (relationships != "")
            {
                isNotFriends = dt.Select(string.Format(" id not in({0})", relationships), "isoutter,username asc");
                isFriends = dt.Select(string.Format(" id in({0})", relationships), "isoutter,username asc");

                for (int i = 0; i < dt.Rows.Count; i++)
                {
                    if (i < start)
                    {
                        continue;
                    }
                    if (i >= start + limit)
                    {
                        break;
                    }

                    DataRow dr = null;
                    Users user = new Users();

                    if (i < isNotFriends.Length)
                    {
                        dr = isNotFriends[i];
                        user.IsFriend = false;
                    }
                    else
                    {
                        dr = isFriends[i - isNotFriends.Length];
                        user.IsFriend = true;
                    }

                    user.Index = i+1;
                    user.Id = dr["id"].ToString();
                    user.LoginName = dr["loginname"].ToString();
                    user.UserName = dr["username"].ToString();
                    user.IsOutter = int.Parse(dr["isoutter"].ToString());

                    userList.Add(user);
                }
            }
            else
            {
                for (int i = 0; i < dt.Rows.Count; i++)
                {
                    if (i < start)
                    {
                        continue;
                    }
                    if (i >= start + limit)
                    {
                        break;
                    }

                    DataRow dr = null;
                    Users user = new Users();

                    dr = dt.Rows[i];
                    user.IsFriend = false;

                    user.Id = dr["id"].ToString();
                    user.LoginName = dr["loginname"].ToString();
                    user.UserName = dr["username"].ToString();
                    user.IsOutter = int.Parse(dr["isoutter"].ToString());

                    userList.Add(user);
                }
            }
            return userList;
        }
        #endregion

        #region 将DataRow[]里面的数据转换入IList中
        /// <summary>
        /// 将DataRow[]里面的数据转换入IList中
        /// </summary>
        /// <returns></returns>
        public ICollection<Users> GetUserList(DataRow[] drs, string relationships, int start, int limit)
        {

            ICollection<Users> userList = new LinkedList<Users>();
           
            for (int i = 0; i < drs.Length; i++)
            {
                if (i < start)
                {
                    continue;
                }
                if (i >= start + limit)
                {
                    break;
                }

                DataRow dr = drs[i];
                Users user = new Users();

                user.Id = dr["id"].ToString();
                if (relationships.Contains("'" + user.Id.Trim() + "'"))
                {
                    user.IsFriend = true;
                }
                else
                {
                    user.IsFriend = false;
                }
               
                user.Index = i+1;
                user.LoginName = dr["loginname"].ToString();
                user.UserName = dr["username"].ToString();
                user.IsOutter = int.Parse(dr["isoutter"].ToString());

                userList.Add(user);
            }
            return userList;
        }
        #endregion

        #region 模拟用户数据，测试用
        /// <summary>
        /// 模拟用户数据，测试用
        /// </summary>
        /// <returns></returns>
        public DataTable GetTestUsers()
        {
            DataTable dt = new DataTable();
            dt.Columns.Add(new DataColumn("id", typeof(string)));
            dt.Columns.Add(new DataColumn("username", typeof(string)));
            dt.Columns.Add(new DataColumn("loginname", typeof(string)));
            dt.Columns.Add(new DataColumn("isoutter", typeof(string)));

            for (int i = 0; i < 10; i++)
            {
                DataRow dr = dt.NewRow();
                dr[0] = "id" + i;
                dr[1] = "userName" + i;
                dr[2] = "loginName" + i;
                dr[3] = "isOutter" + i;

                dt.Rows.Add(dr);
            }

            return dt;
        }
        #endregion

        #region 读取根据模板生成的页面
        /// <summary>
        /// 读取根据模板生成的页面
        /// </summary>
        /// <param name="fileName">文件名</param>
        /// <param name="vltContext">模板参数</param>
        /// <returns></returns>
        public string GetTemplateString(string fileName, VelocityContext vltContext)
        {
            VelocityEngine vltEngine = new VelocityEngine();
            // 文件型模板, 还可以是 "assembly", 则使用资源文件
            vltEngine.SetProperty(RuntimeConstants.RESOURCE_LOADER, "file");
            // 模板存放目录
            vltEngine.SetProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, Server.MapPath("~/webim/"));
            vltEngine.Init();

            // 上面已设置模板目录, 此处用相对路径即可.
            Template vltTemplate = vltEngine.GetTemplate(fileName);
            StringWriter vltWriter = new StringWriter();
            vltTemplate.Merge(vltContext, vltWriter);

            return vltWriter.GetStringBuilder().ToString();
        }
        #endregion

        #region 辅助函数，输出消息
        /// <summary>
        /// 辅助函数，输出消息
        /// </summary>
        /// <param name="message">消息</param>
        public void WriteMessage(string message)
        {
            Response.Clear();
            Response.Write(message);
            Response.End();
        }
        #endregion
    }
}
