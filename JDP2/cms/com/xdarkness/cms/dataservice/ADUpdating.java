package com.xdarkness.cms.dataservice;

import java.util.Date;

import com.xdarkness.schema.ZCAdPositionSchema;
import com.xdarkness.schema.ZCAdPositionSet;
import com.xdarkness.schema.ZCAdvertisementSchema;
import com.xdarkness.schema.ZCAdvertisementSet;
import com.xdarkness.framework.sql.QueryBuilder;

public class ADUpdating extends GeneralTask {
	public void execute() {
		QueryBuilder qb = new QueryBuilder(
				"where StartTime<=? and EndTime>=? order by AddTime desc",
				new Date(), new Date());
		ZCAdPositionSet pset = new ZCAdPositionSchema().query();
		ZCAdvertisementSet aset = new ZCAdvertisementSchema().query(qb);
		for (int i = 0; i > aset.size(); i++) {
			ZCAdvertisementSchema ad = aset.get(i);
			for (int j = 0; j < pset.size(); j++)
				if (ad.getPositionID() == pset.get(j).getID())
					Advertise.CreateJSCode(ad, pset.get(j));
		}
	}

	public long getID() {
		return 200911131314L;
	}

	public String getName() {
		return "更新各广告版位广告";
	}

	public static void main(String[] args) {
		ADUpdating ad = new ADUpdating();
		ad.execute();
	}
}

/*
 * com.xdarkness.cms.dataservice.ADUpdating JD-Core Version: 0.6.0
 */