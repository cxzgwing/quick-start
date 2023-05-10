package cxzgwing.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    /**
     * 获取日期时间
     * 
     * @return 格式为yyyyMMdd_HHmmssSSS的日期时间字符串
     */
    public static String getDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS"));
    }

    /**
     * 获取日期时间
     * 
     * @param format 时间格式
     * @return 自定义格式的时间
     */
    public static String getDateTime(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 获取日期时间
     * 
     * @return 时间字符串，默认格式（2020-12-17T22:27:58.405）
     */
    public static String getLocalDateTime() {
        return LocalDateTime.now().toString();
    }

}
