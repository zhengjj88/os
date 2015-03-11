/**
 * Licensed to Kingmed
 */
package com.kingmed.yuyt.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 *
 * @author zhengjunjie
 */
public class XMLHandler {
        // 将xml文件的内容解析成Document，并存入hashMap对象中(单行数据）
		public static Map<String,String> transXmltoMap(String xmlInfo) throws DocumentException {
			Document doc = DocumentHelper.parseText(xmlInfo); // 将字符串转为xml
			Map<String,String> map = new HashMap<String,String>();
			Element rootElt = doc.getRootElement();
			Iterator iter = rootElt.elementIterator();
			while (iter.hasNext()) {
				Element dataElement = (Element) iter.next();
				map.put(dataElement.getName(), dataElement.getText());
			}
			return map;
		}
}
