package com.xdarkness.platform.pub;

import com.xdarkness.framework.util.ImageUtil;
import com.xdarkness.framework.util.VideoUtil;
import com.xdarkness.schema.ZCVideoSchema;

public class VideoUtilEx {
	public static boolean afterUploadVideo(ZCVideoSchema video,
			String AbsolutePath, boolean hasImage)
			throws NumberFormatException, Exception {
		int[] WidthHeight = VideoUtil.getWidthHeight(AbsolutePath
				+ video.getSrcFileName());
		video.setWidth(WidthHeight[0]);
		video.setHeight(WidthHeight[1]);
		if (!"flv".equalsIgnoreCase(video.getSuffix())) {
			VideoUtil.convert2Flv(AbsolutePath + video.getSrcFileName(),
					AbsolutePath + video.getFileName());
		}

		video.setDuration(VideoUtil.getDuration(AbsolutePath
				+ video.getFileName()));
		video.setCount(1L);

		if (hasImage)
			ImageUtil.scaleRateImageFile(AbsolutePath + video.getImageName(),
					AbsolutePath + video.getImageName(), 240, 240);
		else {
			VideoUtil.captureDefaultImage(AbsolutePath + video.getFileName(),
					AbsolutePath + video.getImageName(), (int) video
							.getDuration());
		}
		return true;
	}
}

/*
 * com.xdarkness.platform.pub.VideoUtilEx JD-Core Version: 0.6.0
 */