package com.xdarkness.bbs;

import com.xdarkness.schema.ZCForumConfigSchema;
import com.xdarkness.schema.ZCForumConfigSet;
import com.xdarkness.schema.ZCForumGroupSchema;
import com.xdarkness.schema.ZCForumGroupSet;
import com.xdarkness.schema.ZCForumSchema;
import com.xdarkness.schema.ZCForumScoreSchema;
import com.xdarkness.schema.ZCForumScoreSet;
import com.xdarkness.schema.ZCForumSet;
import com.xdarkness.framework.cache.CacheProvider;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class ForumCache extends CacheProvider {
	public String getProviderName() {
		return "Forum";
	}

	public void onKeyNotFound(String type, Object key) {
		if (type.equals("Config")) {
			ZCForumConfigSchema schema = new ZCForumConfigSchema();
			schema.setID(String.valueOf(key));
			schema.fill();
			CacheManager.set(getProviderName(), type, key, schema);
		}
		if (type.equals("Forum")) {
			ZCForumSchema schema = new ZCForumSchema();
			schema.setID(String.valueOf(key));
			schema.fill();
			CacheManager.set(getProviderName(), type, key, schema);
		}
		if (type.equals("Group")) {
			ZCForumGroupSchema schema = new ZCForumGroupSchema();
			schema.setID(String.valueOf(key));
			schema.fill();
			CacheManager.set(getProviderName(), type, key, schema);
		}
		if (type.equals("Score")) {
			ZCForumScoreSchema schema = new ZCForumScoreSchema();
			schema.setID(String.valueOf(key));
			schema.fill();
			CacheManager.set(getProviderName(), type, key, schema);
		}
	}

	public void onTypeNotFound(String type) {
		if (type.equals("Config")) {
			ZCForumConfigSet set1 = new ZCForumConfigSchema().query();
			for (int i = 0; i < set1.size(); i++) {
				set1.get(i);
				CacheManager.set(getProviderName(), type, set1.get(i).getID(),
						set1.get(i));
			}
		}
		if (type.equals("Forum")) {
			ZCForumSet set2 = new ZCForumSchema().query();
			for (int i = 0; i < set2.size(); i++) {
				set2.get(i);
				CacheManager.set(getProviderName(), type, set2.get(i).getID(),
						set2.get(i));
			}
		}
		if (type.equals("Group")) {
			ZCForumGroupSet set3 = new ZCForumGroupSchema().query();
			for (int i = 0; i < set3.size(); i++) {
				set3.get(i);
				CacheManager.set(getProviderName(), type, set3.get(i).getID(),
						set3.get(i));
			}
		}
		if (type.equals("Score")) {
			ZCForumScoreSet set4 = new ZCForumScoreSchema().query();
			for (int i = 0; i < set4.size(); i++) {
				set4.get(i);
				CacheManager.set(getProviderName(), type, set4.get(i).getID(),
						set4.get(i));
			}
		}
	}

	public static ZCForumScoreSchema getScoreBySiteID(String SiteID) {
		Mapx map = CacheManager.getMapx("Forum", "Score");
		Object[] vs = map.valueArray();
		for (int i = 0; i < vs.length; i++) {
			ZCForumScoreSchema schema = (ZCForumScoreSchema) vs[i];
			if (schema.getSiteID() == Long.parseLong(SiteID)) {
				return schema;
			}
		}
		return null;
	}

	public static ZCForumGroupSchema getGroup(long GroupID) {
		return getGroup(String.valueOf(GroupID));
	}

	public static ZCForumGroupSchema getGroup(String GroupID) {
		return (ZCForumGroupSchema) CacheManager.get("Forum", "Group", GroupID);
	}

	public static ZCForumGroupSchema getGroupBySystemName(String SiteID,
			String name) {
		Mapx map = CacheManager.getMapx("Forum", "Group");
		Object[] vs = map.valueArray();
		for (int i = 0; i < vs.length; i++) {
			ZCForumGroupSchema schema = (ZCForumGroupSchema) vs[i];
			if ((schema.getSiteID() == Long.parseLong(SiteID))
					&& (schema.getSystemName().equals(name))) {
				return schema;
			}
		}
		return null;
	}

	public static ZCForumSchema getForum(String ID) {
		return (ZCForumSchema) CacheManager.get("Forum", "Forum", ID);
	}

	public static ZCForumSchema getForum(long ID) {
		return (ZCForumSchema) CacheManager.get("Forum", "Forum", ID);
	}

	public static ZCForumConfigSchema getConfig(String ID) {
		return (ZCForumConfigSchema) CacheManager.get("Forum", "Config", ID);
	}

	public static ZCForumConfigSchema getConfigBySiteID(String SiteID) {
		Mapx map = CacheManager.getMapx("Forum", "Config");
		Object[] vs = map.valueArray();
		for (int i = 0; i < vs.length; i++) {
			ZCForumConfigSchema schema = (ZCForumConfigSchema) vs[i];
			if (schema.getSiteID() == Long.parseLong(SiteID)) {
				return schema;
			}
		}
		return null;
	}

	public static ZCForumSet getForumSet(String siteID, String forumID) {
		Mapx map = CacheManager.getMapx("Forum", "Forum");
		if (map == null) {
			ZCForumSet set = new ZCForumSchema().query(new QueryBuilder(
					"where SiteID=? order by OrderFlag", siteID));
			for (int i = 0; i < set.size(); i++) {
				CacheManager.set("Forum", "Forum", set.get(i).getID(), set
						.get(i));
			}
			map = CacheManager.getMapx("Forum", "Forum");
		}
		Object[] vs = map.valueArray();
		ZCForumSet set = new ZCForumSet();
		for (int i = 0; i < vs.length; i++) {
			ZCForumSchema schema = (ZCForumSchema) vs[i];
			if ((schema.getSiteID() != Long.parseLong(siteID))
					|| (!"Y".equals(schema.getVisible()))
					|| ((XString.isNotEmpty(forumID)) && (schema.getID() != Long
							.parseLong(forumID)))) {
				continue;
			}
			if (schema.getParentID() != 0L) {
				continue;
			}
			set.add(schema);
		}

		return set;
	}

	public static ZCForumSet getChildForumSet(String siteID, String forumID) {
		Mapx map = CacheManager.getMapx("Forum", "Forum");
		Object[] vs = map.valueArray();
		ZCForumSet set = new ZCForumSet();
		for (int i = 0; i < vs.length; i++) {
			ZCForumSchema schema = (ZCForumSchema) vs[i];
			if ((schema.getSiteID() != Long.parseLong(siteID))
					|| (!"Y".equals(schema.getVisible()))
					|| (!XString.isNotEmpty(forumID))
					|| (schema.getParentID() != Long.parseLong(forumID)))
				continue;
			set.add(schema);
		}

		return set;
	}
}

/*
 * com.xdarkness.bbs.ForumCache JD-Core Version: 0.6.0
 */