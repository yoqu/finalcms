package org.yoqu.cms.core.util;

import java.util.regex.Pattern;

/**
 * @author yoqu
 * @date 2016/5/11 0011
 * @description
 */
public class StringUtils {


    /**
     * 判断是否是数字
     *
     * @param value
     * @return
     */
    public static boolean isNumbervalue(String value) {
        return Pattern.compile("^\\d+$").matcher(value).find();
    }
}
