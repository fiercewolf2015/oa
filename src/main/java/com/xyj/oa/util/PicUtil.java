/*
 * PicUtil.java
 *
 * 版本信息	v1.0
 *
 * 日期		2006-6-21 14:33:30
 *
 * 修改历史	
 * 
 * 作者		刘鹏
 *
 * Copyright (C) 2006，天津信海方舟网络技术有限公司
 */
package com.xyj.oa.util;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

public class PicUtil {

	public static String doResize(InputStream is, String descFile, double length, double pwidth) {
		Image src = null;
		try {
			src = ImageIO.read(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (src == null)
			return null;
		ImageProcessor ip = new ColorProcessor(src);
		int outWidth = ((int) (pwidth));
		int outHeight = ((int) (length));
		ip = ip.resize(outWidth, outHeight);
		ImagePlus imp = new ImagePlus("sm", ip);
		FileSaver fs = new FileSaver(imp);
		String smFileName = descFile;
		fs.saveAsJpeg(smFileName);
		ip = null;
		imp = null;
		fs = null;
		return smFileName;
	}

	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists())
			return;
		if (!file.isDirectory())
			return;
		String[] tempList = file.list();
		File temp = null;
		String newPath = null;
		if (tempList == null || tempList.length == 0)
			return;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator))
				newPath = path + tempList[i];
			else
				newPath = path + File.separator + tempList[i];
			temp = new File(newPath);
			if (temp.isFile()) {
				temp.delete();
				continue;
			}
			if (temp.isDirectory())
				delAllFile(newPath);//第归删除子文件夹里面的文件
		}
	}

}
