package com.xdarkness.cms.site;

import com.xdarkness.cms.pub.PubFun;
import com.xdarkness.schema.ZCImagePlayerSchema;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.util.Mapx;

public class ImagePlayerPreview extends Page {
	public static Mapx init(Mapx params) {
		String s = (String) params.get("ImagePlayerID");
		if (XString.isEmpty(s)) {
			return null;
		}
		long ImagePlayerID = Long.parseLong(params.get("ImagePlayerID")
				.toString());
		ZCImagePlayerSchema imagePlayer = new ZCImagePlayerSchema();
		imagePlayer.setID(ImagePlayerID);
		imagePlayer.fill();

		params.put("_SWFObject", PubFun.getImagePlayer(imagePlayer));
		return params;
	}
}

/*
 * com.xdarkness.cms.site.ImagePlayerPreview JD-Core Version: 0.6.0
 */