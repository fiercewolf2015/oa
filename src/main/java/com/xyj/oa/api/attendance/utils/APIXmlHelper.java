package com.xyj.oa.api.attendance.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.xyj.oa.api.attendance.exception.BusinessException;
import com.xyj.oa.api.attendance.exception.WrongParamsException;
import com.xyj.oa.api.attendance.exception.WrongXMLFormatException;
import com.xyj.oa.util.StringHelper;

public class APIXmlHelper {

	public static <V> String getXMLFromObj(V xmlObj, String rootAlias, Class<V> clazz) {
		if (StringHelper.isEmpty(rootAlias) || xmlObj == null)
			return null;
		XStream stream = new XStream(new StaxDriver());
		stream.processAnnotations(clazz);
		stream.alias(rootAlias, clazz);
		String xml = stream.toXML(xmlObj);
		return xml;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getMsgFromXML(String xml, String rootAlias, Class<T> clazz) throws WrongXMLFormatException, WrongParamsException {
		if (StringHelper.isEmpty(xml))
			return null;
		T result = null;
		try {
			XStream stream = new XStream(new StaxDriver());
			stream.processAnnotations(clazz);
			stream.alias(rootAlias, clazz);
			result = (T) stream.fromXML(xml);
		} catch (Exception e) {
			if (e instanceof BusinessException)
				throw new WrongParamsException(e.getMessage());
			else
				throw new WrongXMLFormatException(e.getMessage());
		}
		return result;
	}
}
