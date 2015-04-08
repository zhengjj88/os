/**
 * Licensed to Kingmed
 */
package com.kingmed.yuyt.bean;

import com.kingmed.yuyt.util.Constants;
import com.kingmed.yuyt.util.XMLHandler;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author spike
 * @createDate 2015/03/25
 * @version 1.0
 * @desc LIR系统消息队列YUYT对应的消息响应/处理类
 */
public class SpecimenStatusResponseBean implements Processor {

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(SpecimenStatusResponseBean.class);

	// 字符串未匹配代码
	public static final int NOT_MATCH = -1;

	/**
	 * 用于解析消息对应的常量
	 */
	// 历史条码
	public static final String HIS_BARCODE_BEGIN = "<hisBarcode>";
	public static final String HIS_BARCODE_END = "</hisBarcode>";
	// 金域条码
	public static final String KM_BARCODE_BEGIN = "<kmBarcode>";
	public static final String KM_BARCODE_END = "</kmBarcode>";
	// 检测项目代码
	public static final String TEST_CODE_BEGIN = "<testCode>";
	public static final String TEST_CODE_END = "</testCode>";
	// 检测项目名称
	public static final String TEST_NAME_BEGIN = "<testName>";
	public static final String TEST_NAME_END = "</testName>";
	// 是否已读
	public static final String TYPE_BEGIN = "<type_>";
	public static final String TYPE_END = "</type_>";
	// TAT时间
	public static final String TIME_BEGIN = "<time>";
	public static final String TIME_END = "</time>";

	/**
	 * 处理从LIR系统消息队列YUYT中的消息, 并更新域医通的数据库中对应标本状态
	 * 
	 * @param msg
	 * @throws Exception
	 */
	@Override
	public void process(Exchange msg) throws Exception {
		String record = msg.getIn().getBody(String.class);

		String hisBarcode = null;
		String kmBarcode = null;
		String testCode = null;
		String testName = null;
		String type = null;
		String time = null;

		int beginIndex = NOT_MATCH;
		int endIndex = NOT_MATCH;

		// 解析历史条码
		beginIndex = record.indexOf(HIS_BARCODE_BEGIN);
		endIndex = record.indexOf(HIS_BARCODE_END);
		if (beginIndex > NOT_MATCH && endIndex > NOT_MATCH && endIndex > beginIndex) {
			hisBarcode = record.substring(beginIndex + HIS_BARCODE_BEGIN.length(), endIndex);
		}

		// 解析金域条码
		beginIndex = record.indexOf(KM_BARCODE_BEGIN);
		endIndex = record.indexOf(KM_BARCODE_END);
		if (beginIndex > NOT_MATCH && endIndex > NOT_MATCH && endIndex > beginIndex) {
			kmBarcode = record.substring(beginIndex + KM_BARCODE_BEGIN.length(), endIndex);
		}

		// 解析检测项目代码
		beginIndex = record.indexOf(TEST_CODE_BEGIN);
		endIndex = record.indexOf(TEST_CODE_END);
		if (beginIndex > NOT_MATCH && endIndex > NOT_MATCH && endIndex > beginIndex) {
			testCode = record.substring(beginIndex + TEST_CODE_BEGIN.length(), endIndex);
		}

		// 解析检测项目名称
		beginIndex = record.indexOf(TEST_NAME_BEGIN);
		endIndex = record.indexOf(TEST_NAME_END);
		if (beginIndex > NOT_MATCH && endIndex > NOT_MATCH && endIndex > beginIndex) {
			testName = record.substring(beginIndex + TEST_NAME_BEGIN.length(), endIndex);
		}

		// 解析是否已读
		beginIndex = record.indexOf(TYPE_BEGIN);
		endIndex = record.indexOf(TYPE_END);
		if (beginIndex > NOT_MATCH && endIndex > NOT_MATCH && endIndex > beginIndex) {
			type = record.substring(beginIndex + TYPE_BEGIN.length(), endIndex);
		}

		// 解析TAT时间
		beginIndex = record.indexOf(TIME_BEGIN);
		endIndex = record.indexOf(TIME_END);
		if (beginIndex > NOT_MATCH && endIndex > NOT_MATCH && endIndex > beginIndex) {
			time = record.substring(beginIndex + TIME_BEGIN.length(), endIndex);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("enable", Constants.ENABLE_YES);
		map.put("msg_type", Constants.MSG_TYPE_QUERY_REPORT_RESPONSE);
		map.put("his_barcode", hisBarcode);
		map.put("km_barcode", kmBarcode);
		if (kmBarcode != null && kmBarcode.length() > 0) {
			map.put("km_barcode_length", kmBarcode.length());
		} else {
			map.put("km_barcode_length", 0);
		}
		map.put("test_code", testCode);
		map.put("test_name", testName);
		map.put("type", type);
		map.put("create_time", time);
		map.put("direction", Constants.MSG_IN);
		map.put("msg", record);
		map.put("status", Constants.MSG_STATUS_NEW);

		msg.getOut().setBody(map);
		// logger.info("KM Record=" + record);
		// logger.info("kmBarcode=" + kmBarcode);
	}
}
