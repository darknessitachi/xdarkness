package com.xdarkness.cms.webservice;

public interface CmsService {
	long addArticle(long paramLong, String paramString1, String paramString2,
			String paramString3, String paramString4);

	boolean editArticle(long paramLong, String paramString1,
			String paramString2, String paramString3, String paramString4);

	boolean deleteArticle(long paramLong);

	boolean publishArticle(long paramLong);

	long addCatalog(long paramLong, String paramString1, int paramInt,
			String paramString2);

	boolean editCatalog(long paramLong, String paramString1, String paramString2);

	boolean deleteCatalog(long paramLong);

	boolean publishCatalog(long paramLong);

	long addUser(String paramString1, String paramString2, String paramString3,
			String paramString4, String paramString5);

	boolean editUser(String paramString1, String paramString2,
			String paramString3, String paramString4, String paramString5);

	boolean deleteUser(String paramString);

	long addMember(String paramString1, String paramString2,
			String paramString3, String paramString4);

	boolean editMember(String paramString1, String paramString2,
			String paramString3, String paramString4);

	boolean deleteMember(String paramString);
}
