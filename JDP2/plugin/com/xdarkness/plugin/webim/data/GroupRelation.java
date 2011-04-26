package com.xdarkness.plugin.webim.data;

import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;

    public class GroupRelation
    {
        private String globalError ;//全局错误
        private String providerName ;

        public GroupRelation(String providerName)
        {
            this.providerName = providerName;
        }

        public DataTable GetGroupRelation(String user)
        {
            return new QueryBuilder("select g.relationshiper, g.usergroup, u.username from im_grouprelation g inner join im_users u on g.relationshiper = u.id where g.usr = '{0}' order by u.username", 
                    user).executeDataTable();
        }

        /// <summary>
        /// 检测用户usr的好友relationshiper是否在usergroup组中
        /// </summary>
        /// <param name="usr">用户</param>
        /// <param name="relationshiper">好友</param>
        /// <param name="usergroup">组</param>
        /// <returns>如果存在返回true，否则返回false</returns>
        public boolean ExistGroupRelation(String usr, String relationshiper, String usergroup)
        {
            
            return new QueryBuilder("select count(usr) from im_grouprelation where usr='{0}' and relationshiper='{1}' and usergroup='{2}'",
                    usr, relationshiper, usergroup
                    ).executeInt() > 0;

        }

        /// <summary>
        /// 判断数据库中该用户该组下是否还存在用户
        /// </summary>
        /// <param name="groupId"></param>
        /// <returns></returns>
        public boolean ExistGroupUser(String groupId, String userId)
        {
            return new QueryBuilder("select count(relationshiper) from im_grouprelation where usr='{0}' and usergroup='{1}'",
                    userId, groupId).executeInt() > 0;
        }


        /// <summary>
        /// 检测用户usr是否已经加过好友relationshiper
        /// </summary>
        /// <param name="usr">用户</param>
        /// <param name="relationshiper">好友</param>
        /// <returns>如果存在返回true，否则返回false</returns>
        public boolean ExistGroupRelation(String usr, String relationshiper)
        {
            return new QueryBuilder( "select count(usr) from im_grouprelation where usr='{0}' and relationshiper='{1}'",
                    usr, relationshiper
                    ).executeInt() > 0;
        }

        /// <summary>
        /// 添加用户usr的好友relationshiper到组usergroup中
        /// </summary>
        /// <param name="usr">用户</param>
        /// <param name="relationshiper">好友</param>
        /// <param name="usergroup">组</param>
        /// <returns>更新成功返回true，更新失败返回false</returns>
        public boolean InsertGroupRelation(String usr, String relationshiper, String usergroup)
        {
            return new QueryBuilder("insert into im_grouprelation(usergroup,usr,relationshiper) values('{0}', '{1}', '{2}')",
                    usergroup, usr, relationshiper
                    ).executeInt() > 0;
        }

        /// <summary>
        /// 更新用户usr的好友relationshiper到组usergroup中
        /// </summary>
        /// <param name="usr">用户</param>
        /// <param name="relationshiper">好友</param>
        /// <param name="usergroup">组</param>
        /// <returns>更新成功返回true，更新失败返回false</returns>
        public boolean UpdateGroupRelation(String usr, String relationshiper, String usergroup)
        {
            return new QueryBuilder( "update im_grouprelation set usergroup='{0}' where usr='{1}' and relationshiper='{2}'",
                usergroup, usr, relationshiper
               ).executeInt() > 0;
        }

        /// <summary>
        /// 更新用户usr的好友relationshiper到组usergroup中
        /// </summary>
        /// <param name="usr">用户</param>
        /// <param name="relationshiper">好友</param>
        /// <param name="usergroup">组</param>
        /// <returns>更新成功返回true，更新失败返回false</returns>
        public boolean DeleteGroupRelation(String usr, String relationshiper)
        {
            return new QueryBuilder( "update im_grouprelation set usergroup='{0}' where usr='{1}' and relationshiper='{2}'",
                new UserGroup().GetStrangerGroupId(),
                usr, relationshiper
                ).executeInt()>0;
        }

        /// <summary>
        /// 将groupId下的人员都移动到陌生人组下
        /// </summary>
        /// <param name="groupId"></param>
        public boolean MoveToStrangerGroup(String groupId)
        {
            return new QueryBuilder("UPDATE im_grouprelation SET usergroup='{0}' WHERE usergroup='{1}'",
                new UserGroup().GetStrangerGroupId(), groupId
                ).executeInt()>0;
    }
}