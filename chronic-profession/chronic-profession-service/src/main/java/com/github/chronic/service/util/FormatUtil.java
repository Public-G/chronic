package com.github.chronic.service.util;

import java.text.DecimalFormat;

/**
 * @ClassName FormatUtil
 * @Description 格式化工具
 * @Author ZEALER
 * @Date 2018/6/23 20:55
 * @Version 1.0
 **/
public class FormatUtil {


    public static String formatStr(String format, Long count){

        DecimalFormat df = new DecimalFormat(format);

        String str = df.format(count);

        return str;
    }
}
