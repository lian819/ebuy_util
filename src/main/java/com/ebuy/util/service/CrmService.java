package com.ebuy.util.service;

import com.ebuy.util.util.CommonUtil;
import org.apache.log4j.Logger;

import java.util.HashSet;

/**
 * @version 1.0
 * @user lianxinzhong
 * @date 2019/8/16
 */
public class CrmService {

    private static Logger logger = Logger.getLogger(CrmService.class);

    public static void main(String[] args) {
        String dataStr = CommonUtil.formatLongDate(System.currentTimeMillis(), "yyyy-MM-dd'T'HH:mm:ss");
        logger.info(dataStr);

        String str = "|1";

        String[] strArr = str.split("[|]]");
        logger.info(strArr.length);
        logger.info(strArr[0]);

        HashSet<String> hashSet = new HashSet<>();
        logger.info(hashSet);
        hashSet.add("adfaf");
        logger.info("[" + hashSet + "]");
    }
}
