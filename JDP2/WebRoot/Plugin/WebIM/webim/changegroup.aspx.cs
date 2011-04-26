using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.HtmlControls;

using Sky.ImData;

namespace Sky.WebIm
{
    public partial class changegroup : Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            string method = Request.Params["method"];

            if (method == null)
            {
                return;
            }

            if ("getUserGroups".Equals(method.Trim()))
            {
                GenerateUserGroups();
            }
            else if ("changeUserGroup".Equals(method.Trim()))
            {
                ChangeUserGroup();
            }
            else if ("changePassword".Equals(method.Trim()))
            {
                PasswordChange();
            }
            else if ("changeName".Equals(method.Trim()))
            {
                ChangeGroupName();
            }
            else if ("deleteGroup".Equals(method.Trim()))
            {
                DeleteGroup();
            }
            else if ("addGroup".Equals(method.Trim()))
            {
                AddGroup();
            }
        }

        /// <summary>
        /// ��ӷ���
        /// </summary>
        public void AddGroup()
        {
            string groups = Request.Params["groups"];
            string userId = Request.Params["userId"];

            groups = groups.Replace("��",",");
            string[] groupNames = groups.Split(',');

            UserGroup userGroup = new UserGroup();
            string groupIds = "";
            string changedGroupNames = "";

            for(int i=0;i<groupNames.Length;i++){

                string groupName = groupNames[i];
                string tempGroupName = groupName;
                string strGroupId = null;

                if (groupName == "İ����")
                {
                    //strGroupId = userGroup.GetStrangerGroupId();
                    WriteMessage("������������Ϊ��İ���ˡ����飡");
                    return;
                }
                else if (groupName == "�Լ�")
                {
                    WriteMessage("������������Ϊ���Լ������飡");
                    return;
                }
                else
                {
                    strGroupId = userGroup.ExistGroupByName(tempGroupName, userId);
                    while (strGroupId != null && strGroupId != "")
                    { // ������ڸ��飬���������һ�����ּ�1��û�����֣�������Ϊ1
                        tempGroupName += "(1)";
                        strGroupId = userGroup.ExistGroupByName(tempGroupName, userId);
                    }
                }
                
                // ��������
                string createGroupId = userGroup.CreateGroup(tempGroupName, userId);
                if (createGroupId == "")
                {
                    WriteMessage("��������ʧ�ܣ����Ժ����ԣ�");
                    return;
                }

                changedGroupNames += i + ":" + tempGroupName + ",";
                groupIds += createGroupId + ",";
               
            }

            WriteMessage(groupIds.TrimEnd(',') + "��" + changedGroupNames.TrimEnd(','));
        }

        /// <summary>
        /// ����������
        /// </summary>
        public void ChangeGroupName()
        {
            string groupName = Request.Params["groupName"];
            string groupId = Request.Params["groupId"];
            string userId = Request.Params["userId"];
            UserGroup userGroup = new UserGroup();

            if (groupName == "İ����"
                || groupName == "�Լ�"
                || userGroup.ExistGroupByName(groupName, userId) != null)
            {
                WriteMessage("0");
                return;
            }

            if (userGroup.ChangeGroupName(groupId, groupName))
            {// ���������Ƴɹ�
                WriteMessage("���³ɹ���");
            }
            else
            {
                WriteMessage("0");
            }
        }

        /// <summary>
        /// ɾ����
        /// </summary>
        public void DeleteGroup()
        {
            string groupId = Request.Params["groupId"];
            string userId = Request.Params["userId"];

            GroupRelation groupRelation = new GroupRelation();
            if (groupRelation.ExistGroupUser(groupId, userId))
            {// ���û����»������û�
                groupRelation.MoveToStrangerGroup(groupId);
            }

            UserGroup userGroup = new UserGroup();
            if (userGroup.DeleteGroup(groupId))
            {// ɾ����ɹ�
                WriteMessage("ɾ���ɹ���");
            }
            else
            {
                WriteMessage("0");
            }
        }

        /// <summary>
        /// �����������б�div
        /// </summary>
        public void GenerateUserGroups()
        {
            string userId = Request.Params["userId"];
            UserGroup userGroup = new UserGroup();
            DataTable groupList = userGroup.GetUserGroup(userId.Trim());

            StringBuilder groupTreeBuilder = new StringBuilder("<div class=\"itemList\" id=\"divUserGroups\" style=\"padding:1px;width:110px;\">");
            
            for (int i = 0; i < groupList.Rows.Count; i++)
            {
                DataRow group = groupList.Rows[i];
                if (i == 0)
                {
                    groupTreeBuilder.AppendFormat("<a href=\"#\" id='aDefaultUserGroup' groupId='{1}' onmousedown=\"Effect.setUserGroup('{0}','{1}'); return false;\" style=\"font-size: 12px;\">{0}</a>"
                        , group["groupName"], group["id"]);
                }
                else
                {
                    groupTreeBuilder.AppendFormat("<a href=\"#\" groupId='{1}' onmousedown=\"Effect.setUserGroup('{0}','{1}'); return false;\" style=\"font-size: 12px;\">{0}</a>"
                    , group["groupName"], group["id"]);
                }
            }
            
            groupTreeBuilder.Append("</div>");

            WriteMessage(groupTreeBuilder.ToString());
        }

        /// <summary>
        /// �ƶ����ѵ���ѡ��
        /// </summary>
        public void ChangeUserGroup()
        {
            string userId = Request.Params["userId"];
            string relationshiper = Request.Params["relationshiper"];
            string groupName = Request.Params["groupName"];

            UserGroup userGroup = new UserGroup();

            string strGroupId;
            if (groupName == "İ����")
            {
                strGroupId = userGroup.GetStrangerGroupId();
            }
            else if (groupName == "�Լ�")
            {
                WriteMessage("������������Ϊ���Լ������飡");
                return;
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
                    return;
                }

                strGroupId = createGroupId;
            }
            
            GroupRelation groupRelation = new GroupRelation();
            bool existGroupRelation = groupRelation.ExistGroupRelation(userId, relationshiper, strGroupId);
            if (existGroupRelation == true)// ����ú����Ѿ��ڸ����У���ֱ�ӷ���
            {
                return;
            }

            // �ú��Ѳ��ڸ�����,���ƶ��ú��ѵ�������
            bool flag = groupRelation.UpdateGroupRelation(userId, relationshiper, strGroupId);
            if (flag == true)
            {
                WriteMessage("�ƶ��ɹ���");
            }
            else
            {
                WriteMessage("�ƶ�ʧ�ܣ����Ժ����ԣ�");
            }
            
        }

        /// <summary>
        /// �޸�����
        /// </summary>
        public void PasswordChange()
        {
            string userId = Request.Params["userId"];// �û�Id
            string passwordBefore = Request.Params["passwordBefore"];// ԭʼ����
            string passwordNew = Request.Params["passwordNew"];// ������

            Users users = new Users();
            if (users.CheckUserExist(userId, passwordBefore))// ����û�������ȷ
            {
                if (users.ChangePassword(userId, passwordNew))// ��������û�����ɹ�
                {
                    WriteMessage("��������ɹ���");
                }
                else
                {
                    WriteMessage("��������ʧ�ܣ�");
                }
            }
            else
            {
                WriteMessage("1");
            }
        }

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
    }
}
