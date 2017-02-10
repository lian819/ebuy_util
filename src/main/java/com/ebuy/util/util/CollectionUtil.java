package com.ebuy.util.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

/**
 * $
 *
 * @auth Lian
 * @date 2016/10/11
 * @since 1.0
 */
public class CollectionUtil {

	/**
	 * 将所有参数连同商户号按照规则拼接成一个字符串
	 *
	 * @param map	请求参数
	 * @param key	商户key
	 * @return		排序处理后的字符串
	 */
	public static String getSortMapString(Map<String, String> map, String key) {

		if(map == null || map.size() == 0) {
			return "{}";
		}

		ArrayList<Map.Entry<String, String>> list = new ArrayList<>(map.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
			// 升序排序
			@Override
			public int compare(Map.Entry<String, String> entry1, Map.Entry<String, String> entry2) {
				return entry1.getKey().compareTo(entry2.getKey());
			}
		});

		// 拼接字符串
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		for (Map.Entry<String, String> mapping : list) {
			buffer.append(mapping.getKey());
			buffer.append("=");
			buffer.append(mapping.getValue());
			buffer.append("_");
		}
		buffer.append("}");
		// 去掉最后一个'_'
		buffer.replace(buffer.length() - 2, buffer.length() -1, "");

		// 最后面添加key
		buffer.append(key);

		return buffer.toString();
	}
}
