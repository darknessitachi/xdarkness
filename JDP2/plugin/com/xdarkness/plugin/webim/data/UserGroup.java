package com.xdarkness.plugin.webim.data;

import com.xdarkness.plugin.webim.business.Profile;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;

    public class UserGroup
    {
        private String _globalError ;
        private String _providerName ;

        public String getGlobalError()
        {
                return _globalError;
        }

        public UserGroup()
        {
        }

        public UserGroup(String providerName)
        {
            _providerName = providerName;
        }

        public DataTable GetUserGroup(String user)
        {
            DataTable groupTable = new QueryBuilder("select id, groupname, userid from im_usergroup where userid = '{0}' order by groupname",
                    user
                    ).executeDataTable();

            DataTable globalTable = new QueryBuilder( "select id, groupname, userid from im_usergroup where userid = 'global'").executeDataTable();

            globalTable.union(groupTable);
            return globalTable;
        }

        /// <summary>
        /// 判断数据库中是否存在该组
        /// </summary>
        /// <param name="groupId"></param>
        /// <param name="groupId"></param>
        /// <returns></returns>
        public boolean ExistGroupById(String groupId, String user)
        {
            return new QueryBuilder( "select count(id) from im_usergroup where id='" + groupId + "' and userid = '" + user + "'").executeInt()>0;
        }

        
        /// <summary>
        /// 判断数据库中是否存在该组
        /// </summary>
        /// <param name="groupName"></param>
        /// <param name="user"></param>
        /// <returns>该组的主键id</returns>
        public String ExistGroupByName(String groupName, String user)
        {
            return new QueryBuilder("select id from im_usergroup where groupName = '" + groupName + "' and userid = '" + user + "'").executeString();
        }

        /// <summary>
        /// 创建组
        /// </summary>
        /// <param name="groupName">组名称</param>
        /// <param name="user">用户ID</param>
        /// <returns>如果创建成功返回true，失败返回false</returns>
        public String CreateGroup(String groupName, String user)
        {
String genid = Profile.GUID;
    return new QueryBuilder("insert into im_usergroup values('{2}', '{0}', '{1}')", groupName, user, genid
                ).executeInt()>0?genid:"";

        }

        /// <summary>
        /// 修改组名
        /// </summary>
        /// <param name="groupId"></param>
        /// <returns></returns>
        public boolean ChangeGroupName(String groupId, String groupName)
        {
            return new QueryBuilder("update im_usergroup set groupName='{0}' where id='{1}'",
                groupName, groupId).executeInt()>0;
        }

        /// <summary>
        /// 删除组
        /// </summary>
        /// <param name="groupId">组id</param>
        /// <returns></returns>
        public boolean DeleteGroup(String groupId)
        {
            return new QueryBuilder( "delete from im_usergroup where id='{0}'",
                groupId).executeInt()>0;
        }

        /// <summary>
        /// 取得陌生人组的Id
        /// </summary>
        /// <returns></returns>
        public String GetStrangerGroupId()
        {
            return new QueryBuilder("select id from im_usergroup where groupName='{0}' AND userId='global'",
                Profile.unknowGroup
                ).executeString();
        }
    }
