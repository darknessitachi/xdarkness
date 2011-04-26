package com.xdarkness.cms.dataservice;

import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.platform.pub.OrderUtil;
import com.xdarkness.schema.ZCVoteItemSchema;
import com.xdarkness.schema.ZCVoteSchema;
import com.xdarkness.schema.ZCVoteSubjectSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.util.Mapx;

public class VoteItem extends Page {
	public static Mapx init(Mapx params) {
		String VoteID = params.get("ID").toString();
		ZCVoteSchema vote = new ZCVoteSchema();
		vote.setID(VoteID);
		vote.fill();
		params.put("VoteName", vote.getTitle());
		return params;
	}

	public void save() {
		Mapx map = this.request;
		String voteID = $V("ID");
		ZCVoteSubjectSchema subject = new ZCVoteSubjectSchema();
		ZCVoteItemSchema item = new ZCVoteItemSchema();
		subject.setVoteID(voteID);
		item.setVoteID(voteID);
		String key = null;
		Transaction trans = new Transaction();
		trans.add(subject.query(), OperateType.DELETE_AND_BACKUP);
		trans.add(item.query(), OperateType.DELETE_AND_BACKUP);

		Object[] ks = map.keyArray();
		for (int i = 0; i < map.size(); i++) {
			key = (String) ks[i];
			if (key.startsWith("Subject_")) {
				String subjectID = key.substring(8);
				subject = new ZCVoteSubjectSchema();
				subject.setID(subjectID);
				if (!subject.fill()) {
					subject.setID(NoUtil.getMaxID("VoteSubjectID"));
				}
				subject.setVoteID(voteID);
				subject.setType(map.getString("Type_" + subjectID));
				if (XString.isEmpty(subject.getType())) {
					subject.setType("S");
				}
				subject.setSubject(map.getString(key));
				subject.setOrderFlag(OrderUtil.getDefaultOrder());
				trans.add(subject, OperateType.INSERT);

				for (int j = 0; j < map.size(); j++) {
					key = (String) ks[j];
					if (key.startsWith("Item_" + subjectID + "_")) {
						item = new ZCVoteItemSchema();
						item.setID(key.substring(("Item_" + subjectID + "_")
								.length()));
						if (!item.fill()) {
							item.setID(NoUtil.getMaxID("VoteItemID"));
						}
						item.setVoteID(subject.getVoteID());
						item.setSubjectID(subject.getID());
						if (XString.isNotEmpty(map.getString(key))) {
							item.setItem(map.getString(key));
						} else if ("W".equals(map
								.getString("Type_" + subjectID))) {
							item.setItem("其他");
						} else if (("1".equals(map.getString("ItemType_"
								+ key.substring(5))))
								|| ("2".equals(map.getString("ItemType_"
										+ key.substring(5))))) {
							item.setItem("其他");
						} else {
							this.response.setLogInfo(0, "选项内容不能为空！");
							return;
						}
						item.setScore(map
								.getString("Score_" + key.substring(5)));
						item.setItemType(map.getString("ItemType_"
								+ key.substring(5)));
						if (XString.isEmpty(item.getItemType())) {
							item.setItemType("0");
						}
						item.setOrderFlag(OrderUtil.getDefaultOrder());
						trans.add(item, OperateType.INSERT);
					} else if (("W".equals(map.getString("Type_" + subjectID)))
							&& (key.startsWith("ItemType_" + subjectID + "_"))) {
						item = new ZCVoteItemSchema();
						item.setID(key
								.substring(("ItemType_" + subjectID + "_")
										.length()));
						if (!item.fill()) {
							item.setID(NoUtil.getMaxID("VoteItemID"));
						}
						item.setVoteID(subject.getVoteID());
						item.setSubjectID(subject.getID());
						item.setItem(subject.getSubject());
						item.setScore(map.getString("Score_"
								+ subjectID
								+ "_"
								+ key.substring(new StringBuffer("ItemType_")
										.append(subjectID).append("_")
										.toString().length())));
						item.setItemType(map.getString("ItemType_"
								+ subjectID
								+ "_"
								+ key.substring(new StringBuffer("ItemType_")
										.append(subjectID).append("_")
										.toString().length())));
						if (XString.isEmpty(item.getItemType())) {
							item.setItemType("1");
						}
						item.setOrderFlag(OrderUtil.getDefaultOrder());
						trans.add(item, OperateType.INSERT);
					}
				}
			}
		}
		if (trans.commit()) {
			this.response.setLogInfo(1, "保存成功");
			Vote.generateJS(voteID);
		} else {
			this.response.setLogInfo(0, "保存失败");
		}
	}
}

/*
 * com.xdarkness.cms.dataservice.VoteItem JD-Core Version: 0.6.0
 */