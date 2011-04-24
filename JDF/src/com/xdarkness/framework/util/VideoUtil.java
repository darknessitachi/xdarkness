package com.xdarkness.framework.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.xdarkness.framework.Config;
import com.xdarkness.framework.config.SystemConfig;
import com.xdarkness.framework.jaf.controls.UploadStatus;

public class VideoUtil {
	public static String taskid = "";
	public static String videoid = "";
	public static String srcPath = "";
	public static final String _ConvertAvi2Flv = " -of lavf -oac mp3lame -lameopts abr:br=56 -ovc lavc -lavcopts vcodec=flv:vbitrate=200:mbd=2:mv0:trell:v4mv:cbp:last_pred=3:dia=4:cmp=6:vb_strategy=1 -vf scale=512:-3 -ofps 12\t -srate 22050";
	public static final String _ConvertRm2Flv = " -of lavf -oac mp3lame -lameopts abr:br=56 -ovc lavc -lavcopts vcodec=flv:vbitrate=200:mbd=2:mv0:trell:v4mv:cbp:last_pred=3 -srate 22050";
	public static final String _Identify = " -nosound -vc dummy -vo null";

	public static boolean captureDefaultImage(String src, String destImage,
			int duration) {
		if (duration < 30) {
			return captureImage(src, destImage, duration / 3);
		}
		return captureImage(src, destImage, 15);
	}

	public static boolean captureImage(String src, String destImage,
			int startSecond) {
		return captureImage(src, destImage, startSecond, 240, 180);
	}

	public static boolean captureImage(String src, String destImage, int ss,
			int width, int height) {
		String fileName = "ffmpeg";
		if (SystemConfig.isWindows()) {
			fileName = "\"" + Config.getContextRealPath() + "Tools/"
					+ "ffmpeg.exe\" ";
			src = "\"" + src + "\"";
			destImage = "\"" + destImage + "\"";
		}
		return exec(fileName + " -i " + src + " -y -f image2 -ss " + ss
				+ " -t 0.001 -s " + width + "*" + height + " " + destImage,
				null, Config.getContextRealPath() + "Tools/");
	}

	public static boolean convert2Flv(String src, String dest) {
		String fileName = "mencoder ";
		if (SystemConfig.isWindows()) {
			fileName = "\"" + Config.getContextRealPath() + "Tools/"
					+ "mencoder.exe\" ";
			src = "\"" + src + "\"";
			dest = "\"" + dest + "\"";
		}
		if (src.toLowerCase().lastIndexOf(".flv") != -1)
			return true;
		if ((src.toLowerCase().lastIndexOf(".rm") != -1)
				|| (src.toLowerCase().lastIndexOf(".rmvb") != -1)) {
			return exec(
					fileName
							+ src
							+ " -o "
							+ dest
							+ " "
							+ " -of lavf -oac mp3lame -lameopts abr:br=56 -ovc lavc -lavcopts vcodec=flv:vbitrate=200:mbd=2:mv0:trell:v4mv:cbp:last_pred=3 -srate 22050",
					null, Config.getContextRealPath() + "Tools/");
		}
		return exec(
				fileName
						+ src
						+ " -o "
						+ dest
						+ " "
						+ " -of lavf -oac mp3lame -lameopts abr:br=56 -ovc lavc -lavcopts vcodec=flv:vbitrate=200:mbd=2:mv0:trell:v4mv:cbp:last_pred=3:dia=4:cmp=6:vb_strategy=1 -vf scale=512:-3 -ofps 12\t -srate 22050",
				null, Config.getContextRealPath() + "Tools/");
	}

	public static boolean convert2FlvSlit(String src, String dest, int ss,
			int endpos) {
		String fileName = "mencoder ";
		if (SystemConfig.isWindows()) {
			fileName = "\"" + Config.getContextRealPath() + "Tools/"
					+ "mencoder.exe\" ";
			src = "\"" + src + "\"";
			dest = "\"" + dest + "\"";
		}
		if ((src.toLowerCase().lastIndexOf(".rm") != -1)
				|| (src.toLowerCase().lastIndexOf(".rmvb") != -1)) {
			return exec(
					fileName
							+ src
							+ " -o "
							+ dest
							+ " "
							+ " -of lavf -oac mp3lame -lameopts abr:br=56 -ovc lavc -lavcopts vcodec=flv:vbitrate=200:mbd=2:mv0:trell:v4mv:cbp:last_pred=3 -srate 22050",
					null, Config.getContextRealPath() + "Tools/");
		}
		return exec(
				fileName
						+ src
						+ " -o "
						+ dest
						+ " -ss "
						+ ss
						+ " -endpos "
						+ endpos
						+ " "
						+ " -of lavf -oac mp3lame -lameopts abr:br=56 -ovc lavc -lavcopts vcodec=flv:vbitrate=200:mbd=2:mv0:trell:v4mv:cbp:last_pred=3:dia=4:cmp=6:vb_strategy=1 -vf scale=512:-3 -ofps 12\t -srate 22050",
				null, Config.getContextRealPath() + "Tools/");
	}

	public static int getDuration(String src) {
		String fileName = "mplayer ";
		if (SystemConfig.isWindows()) {
			fileName = "\"" + Config.getContextRealPath() + "Tools/"
					+ "mplayer.exe\" ";
			src = "\"" + src + "\"";
		}
		String command = fileName + " -identify " + src + " "
				+ " -nosound -vc dummy -vo null";
		int duration = 0;
		try {
			LogUtil.info("Video.getDuration:" + command);
			Process process = Runtime.getRuntime().exec(command, null,
					new File(Config.getContextRealPath() + "Tools/"));

			InputStream is = process.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			try {
				while ((line = br.readLine()) != null) {
					System.out.println(line);
					if (line.indexOf("ID_LENGTH=") > -1)
						duration = (int) Math.ceil(Double.parseDouble(line
								.substring(10)));
				}
			} catch (IOException e) {
				e.printStackTrace();
				return duration;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return duration;
		}
		LogUtil.info("VodeoUtil duration:" + duration);
		return duration;
	}

	public static int[] getWidthHeight(String src) {
		String fileName = "mplayer ";
		if (SystemConfig.isWindows()) {
			fileName = "\"" + Config.getContextRealPath() + "Tools/"
					+ "mplayer.exe\" ";
			src = "\"" + src + "\"";
		}
		String command = fileName + " -identify " + src + " "
				+ " -nosound -vc dummy -vo null";
		int[] WidthHeight = new int[2];
		try {
			LogUtil.info("VideoUtil.getWidthHeight:" + command);
			Process process = Runtime.getRuntime().exec(command, null,
					new File(Config.getContextRealPath() + "Tools/"));

			InputStream is = process.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			try {
				while ((line = br.readLine()) != null) {
					System.out.println(line);
					if (line.indexOf("ID_VIDEO_WIDTH=") > -1) {
						WidthHeight[0] = (int) Math.ceil(Double
								.parseDouble(line.substring(15)));
					}
					if (line.indexOf("ID_VIDEO_HEIGHT=") > -1)
						WidthHeight[1] = (int) Math.ceil(Double
								.parseDouble(line.substring(16)));
				}
			} catch (IOException e) {
				e.printStackTrace();
				return WidthHeight;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return WidthHeight;
		}
		LogUtil.info("VideoUtil WidthHeight:" + WidthHeight[0] + "x"
				+ WidthHeight[1]);
		return WidthHeight;
	}

	public static boolean exec(String command, String[] args, String dir) {
		try {
			LogUtil.info("VideoUtil.exec:" + command);
			Process process = Runtime.getRuntime().exec(command, args,
					new File(dir));

			final InputStream is1 = process.getInputStream();
			new Thread(new Runnable() {
				public void run() {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(is1));
					String line = null;
					String task = VideoUtil.taskid;
					String vid = VideoUtil.videoid;
					String src = VideoUtil.srcPath;
					try {
						while ((line = br.readLine()) != null) {
							UploadStatus.setTask(task, vid, "Video", src, line);
							System.out.println(line);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
			InputStream is2 = process.getErrorStream();
			BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
			StringBuffer buf = new StringBuffer();
			String line = null;
			while ((line = br2.readLine()) != null)
				buf.append(line);
			LogUtil.info("VideoUtil 输出为：" + buf);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}

/*
 * com.xdarkness.framework.utility.VideoUtil JD-Core Version: 0.6.0
 */