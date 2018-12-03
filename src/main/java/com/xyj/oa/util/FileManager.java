package com.xyj.oa.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileManager {
	private final static Log logger = LogFactory.getLog(FileManager.class);

	public static void delFile(String location) {
		if (location == null)
			return;
		File f = new File(location);
		if (f.exists())
			f.delete();
	}

	public static String[] saveFile(InputStream im, String fileName, HttpServletRequest request) {
		FileOutputStream foutput = null;
		File file = null;
		String webUrl = null;
		String[] path = new String[2];
		Long time = System.currentTimeMillis();
		try {
			String saveurl = request.getSession().getServletContext().getRealPath("/uploadFile");
			int available = im.available();
			String url = saveurl + "/" + time;
			file = new File(url);
			if (!file.exists())
				file.mkdirs();
			file = new File(url + "/" + fileName);
			webUrl = "/oa/uploadFile/" + time + "/" + fileName;
			byte[] b = new byte[available];
			foutput = new FileOutputStream(file);
			im.read(b);
			foutput.write(b);
		} catch (IOException e) {
			logger.error(LogUtil.stackTraceToString(e));
		} finally {
			try {
				foutput.flush();
				foutput.close();
				im.close();
			} catch (IOException e) {
				logger.error(LogUtil.stackTraceToString(e));
			}
		}
		path[0] = file.getPath();
		path[1] = webUrl;
		return path;
	}
}