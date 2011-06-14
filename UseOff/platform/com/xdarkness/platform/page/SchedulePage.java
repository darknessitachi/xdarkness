package com.xdarkness.platform.page;

import java.util.Calendar;
import java.util.Date;

import com.abigdreamer.java.net.User;
import com.abigdreamer.java.net.data.Transaction;
import com.abigdreamer.java.net.jaf.Page;
import com.abigdreamer.java.net.jaf.controls.HtmlUtil;
import com.abigdreamer.java.net.jaf.controls.grid.DataGridAction;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.orm.data.DataTable;
import com.abigdreamer.java.net.schedule.AbstractTaskManager;
import com.abigdreamer.java.net.schedule.CronManager;
import com.abigdreamer.java.net.schedule.CronMonitor;
import com.abigdreamer.java.net.sql.QueryBuilder;
import com.abigdreamer.java.net.util.DateUtil;
import com.abigdreamer.java.net.util.Mapx;
import com.abigdreamer.java.net.util.XString;
import com.xdarkness.platform.pub.NoUtil;
import com.zving.schema.ZDScheduleSchema;

public class SchedulePage extends Page {
	public static Mapx init(Mapx map) {
		Mapx params = CronManager.getInstance().getTaskTypes();
		String types = HtmlUtil.mapxToOptions(params, null, true);
		map.put("TypeCode", types);
		return map;
	}

	public void getUsableTask() {
		String type = $V("TypeCode");
		Mapx map = CronManager.getInstance().getConfigEnableTasks(type);
		Object[] ks = map.keyArray();
		Object[] vs = map.valueArray();
		for (int i = 0; i < map.size(); i++)
			$S(ks[i].toString(), vs[i]);
	}

	public static void dg1DataBind(DataGridAction dga) {
		String sql = "select * from ZDSchedule " + dga.getSortString();
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		Mapx map = new Mapx();
		map.put("Y", "启用");
		map.put("N", "停用");
		dt.decodeColumn("IsUsing", map);

		map = CronManager.getInstance().getTaskTypes();
		dt.decodeColumn("TypeCode", map);

		dt.insertColumn("SourceIDName");
		dt.insertColumn(new DataColumn("NextRunTime", 0));
		for (int i = dt.getRowCount() - 1; i >= 0; i--) {
			String typeCode = dt.getString(i, "TypeCode");
			String sourceID = dt.getString(i, "SourceID");

			Mapx taskMap = CronManager.getInstance().getConfigEnableTasks(
					typeCode);
			if (taskMap == null) {
				dt.deleteRow(i);
			} else {
				String sourceIDName = taskMap.getString(sourceID);
				if (XString.isEmpty(sourceIDName)) {
					dt.deleteRow(i);
				} else {
					dt.set(i, "SourceIDName", sourceIDName);
					Date nextRunTime = null;
					try {
						nextRunTime = CronMonitor.getNextRunTime(dt.getString(
								i, "CronExpression"));
					} catch (Exception e) {
						nextRunTime = DateUtil.parse("2999-01-01");
					}
					dt.set(i, "NextRunTime", nextRunTime);
				}
			}
		}
		dga.setTotal(dt.getRowCount());
		DataTable result = new DataTable(dt.getDataColumns(), null);
		for (int i = dga.getPageIndex() * dga.getPageSize(); (i < (dga
				.getPageIndex() + 1)
				* dga.getPageSize())
				&& (i < dt.getRowCount());) {
			result.insertRow(dt.getDataRow(i));

			i++;
		}

		dga.bindData(result);
	}

	public void save() {
		ZDScheduleSchema schedule = new ZDScheduleSchema();
		String id = $V("ID");
		if (XString.isEmpty(id)) {
			schedule.setID(NoUtil.getMaxID("ScheduleID"));
			schedule.setAddTime(new Date());
			schedule.setAddUser(User.getUserName());
		} else {
			schedule.setID(Long.parseLong(id));
			schedule.fill();
			schedule.setModifyTime(new Date());
			schedule.setModifyUser(User.getUserName());
		}
		String startTime = $V("StartDate") + " " + $V("StartTime");
		this.request.put("StartTime", startTime);
		if ($V("PlanType").equals("Period")) {
			String period = $V("Period");
			Calendar c = Calendar.getInstance();
			c.setTime(DateUtil.parseDateTime(startTime));
			StringBuffer sb = new StringBuffer();
			if ($V("PeriodType").equals("Minute")) {
				int minute = c.get(12);
				sb.append(minute);
				sb.append("-");
				if (minute == 0)
					sb.append("59");
				else {
					sb.append(minute - 1);
				}
				sb.append("/");
				sb.append(period);
				sb.append(" * * * *");
			} else if ($V("PeriodType").equals("Hour")) {
				int minute = c.get(12);
				int hour = c.get(11);
				sb.append(minute);
				sb.append(" ");
				sb.append(hour);
				sb.append("-");
				if (hour == 0)
					sb.append("23");
				else {
					sb.append(hour - 1);
				}
				sb.append("/");
				sb.append(period);
				sb.append(" * * *");
			} else if ($V("PeriodType").equals("Day")) {
				int minute = c.get(12);
				int hour = c.get(11);
				int day = c.get(5);
				sb.append(minute);
				sb.append(" ");
				sb.append(hour);
				sb.append(" ");
				sb.append(day);
				sb.append("-");
				sb.append(day - 1);
				sb.append("/");
				sb.append(period);
				sb.append(" * *");
			} else if ($V("PeriodType").equals("Month")) {
				int minute = c.get(12);
				int hour = c.get(11);
				int day = c.get(5);
				int month = c.get(2);
				sb.append(minute);
				sb.append(" ");
				sb.append(hour);
				sb.append(" ");
				sb.append(day);
				sb.append(" ");
				sb.append(month);
				sb.append("-");
				sb.append(month - 1);
				sb.append("/");
				sb.append(period);
				sb.append(" *");
			}
			this.request.put("CronExpression", sb.toString());
		}
		schedule.setValue(this.request);
		try {
			CronMonitor.getNextRunTime(schedule.getCronExpression());
			boolean flag = XString.isEmpty(id) ? schedule.insert()
					: schedule.update();
			if (!flag)
				this.response.setError("发生错误!");
			else
				this.response.setMessage("操作成功!");
		} catch (Exception e) {
			this.response.setError("发生错误:Cron表达式不正确!");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		String sql = "delete from ZDSchedule where id in (" + ids + ")";
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder(sql));
		if (!trans.commit()) {
			UserLogPage.log("System", "DelSchedule", "删除定时任务失败", this.request
					.getClientIP());
			this.response.setError("发生错误!");
		} else {
			UserLogPage.log("System", "DelSchedule", "删除定时任务成功", this.request
					.getClientIP());
			this.response.setMessage("操作成功!");
		}
	}

	public void execute() {
		long id = Long.parseLong($V("ID"));
		ZDScheduleSchema s = new ZDScheduleSchema();
		s.setID(id);
		if (!s.fill()) {
			this.response.setError("任务不存在或己被删除!");
			return;
		}
		AbstractTaskManager ctm = CronManager.getInstance().getCronTaskManager(
				s.getTypeCode());
		if (ctm.isRunning(s.getSourceID())) {
			this.response
					.setMessage("<font color=red>任务正在运行，请等待该任务运行完毕!</font>");
		} else {
			ctm.execute(s.getSourceID());
			this.response.setMessage("任务开始运行!");
		}
	}

	public static void main(String[] args) {
		try {
			Date d = CronMonitor.getNextRunTime("50 16 25-24/1 * *");
			System.out.println(DateUtil.toDateTimeString(d));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
