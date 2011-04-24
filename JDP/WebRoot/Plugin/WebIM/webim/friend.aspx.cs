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
        /// ��Ӻ���
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
                {// ������ⵥλ�û�
                    if (!users.CheckUserExistById(userId.Trim()))
                    {// ����û������ڣ�ע����û�
                        users.RegisterOutter(userId.Trim(), lName.Trim(), uName.Trim());
                    }
                    else
                    {// �û����ڣ������û�������½����ʹ��������ϵͳ���ݱ���ͬ��
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
                {// ������ⵥλ�û�
                    if (!users.CheckUserExistById(friendIds[i].Trim()))
                    {// ����û������ڣ�ע����û�
                        users.RegisterOutter(friendIds[i], loginNames[i], userNames[i]);
                    }
                    else
                    {// �û����ڣ������û�������½����ʹ��������ϵͳ���ݱ���ͬ��
                        users.ChangeOutter(userNames[i], loginNames[i], friendIds[i]);
                    }
                }

                tempGroupId = ChangeUserGroup(userId, friendIds[i], groupName);
                // ����ҵ��ú��ѵ�İ���˷�����
                ChangeUserGroup(friendIds[i], userId, "İ����");
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


        #region ɾ������
        /// <summary>
        /// ɾ������
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
                WriteMessage("ɾ���ɹ���");
            }
        }
        #endregion

        #region �ƶ����ѵ���ѡ��
        /// <summary>
        /// �ƶ����ѵ���ѡ��
        /// </summary>
        public string ChangeUserGroup(string userId, string relationshiper, string groupName)
        {
            UserGroup userGroup = new UserGroup();
            string strGroupId = null;
            if (groupName == "İ����")
            {
                strGroupId = userGroup.GetStrangerGroupId();
            }
            else if(groupName == "�Լ�")
            {
                WriteMessage("self");
                return null;
            }
            else
            {
                strGroupId = userGroup.ExistGroupByName(groupName, userId);
            }

            if (strGroupId == null || strGroupId == "") // ��������ڸ��飬���ȴ�������
            {
                string createGroupId = userGroup.CreateGroup(groupName, userId);
                if (createGroupId == "")
                {
                    WriteMessage("��������ʧ�ܣ����Ժ����ԣ�");
                    return null;
                }

                strGroupId = createGroupId;
            }

            bool flag = false;
            GroupRelation groupRelation = new GroupRelation();
            if (groupRelation.ExistGroupRelation(userId, relationshiper))
            {// ����Ѿ��ӹ��˺���
                bool existGroupRelation = groupRelation.ExistGroupRelation(userId, relationshiper, strGroupId);
                if (existGroupRelation == false)
                {// �ú��Ѳ��ڸ�����,���ƶ��ú��ѵ�������
                    flag = groupRelation.UpdateGroupRelation(userId, relationshiper, strGroupId);
                }
                else
                {
                    flag = true;
                }
            }
            else
            {// û�мӹ��˺��ѣ���Ӹú���
                flag = groupRelation.InsertGroupRelation(userId, relationshiper, strGroupId);
            }

            if (flag)
            {
                return strGroupId;
            }
            return null;
        }
        #endregion

        #region ��ȡ���ѷ�ҳ�б�ҳ��
        /// <summary>
        /// ��ȡ���ѷ�ҳ�б�ҳ��
        /// </summary>
        public void GetFriends()
        {
            int start = 0;
            int limit = 10;

            // ����ת�����쳣�򲻴�������Ĭ��ֵ
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

        #region ��DataTable���������ת����IList��
        /// <summary>
        /// ��DataTable���������ת����IList��
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

        #region ��DataRow[]���������ת����IList��
        /// <summary>
        /// ��DataRow[]���������ת����IList��
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

        #region ģ���û����ݣ�������
        /// <summary>
        /// ģ���û����ݣ�������
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

        #region ��ȡ����ģ�����ɵ�ҳ��
        /// <summary>
        /// ��ȡ����ģ�����ɵ�ҳ��
        /// </summary>
        /// <param name="fileName">�ļ���</param>
        /// <param name="vltContext">ģ�����</param>
        /// <returns></returns>
        public string GetTemplateString(string fileName, VelocityContext vltContext)
        {
            VelocityEngine vltEngine = new VelocityEngine();
            // �ļ���ģ��, �������� "assembly", ��ʹ����Դ�ļ�
            vltEngine.SetProperty(RuntimeConstants.RESOURCE_LOADER, "file");
            // ģ����Ŀ¼
            vltEngine.SetProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, Server.MapPath("~/webim/"));
            vltEngine.Init();

            // ����������ģ��Ŀ¼, �˴������·������.
            Template vltTemplate = vltEngine.GetTemplate(fileName);
            StringWriter vltWriter = new StringWriter();
            vltTemplate.Merge(vltContext, vltWriter);

            return vltWriter.GetStringBuilder().ToString();
        }
        #endregion

        #region ���������������Ϣ
        /// <summary>
        /// ���������������Ϣ
        /// </summary>
        /// <param name="message">��Ϣ</param>
        public void WriteMessage(string message)
        {
            Response.Clear();
            Response.Write(message);
            Response.End();
        }
        #endregion
    }
}
