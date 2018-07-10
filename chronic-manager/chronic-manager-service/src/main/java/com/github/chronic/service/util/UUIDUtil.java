package com.github.chronic.service.util;

import java.util.UUID;

/**
 * @ClassName UUIDUtil
 * @Description 生成随机字符串的工具类
 * @Author ZEALER
 * @Date 2018/6/25 13:29
 * @Version 1.0
 **/
public class UUIDUtil {

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }


    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    public static String generateShortUUID() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4); //将UUID分成8组，每4个为一组
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]); //模62操作，结果作为索引取出字符
        }
        return shortBuffer.toString();

    }



}
