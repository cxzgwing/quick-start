package cxzgwing.utils;

public class CommonUtil {

    /**
     * 判断字符串是否为空
     * 
     * @param str 需要判断的字符
     * @return 如果字符串为null或字符串为空字符串则返回true，否则返回false
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

}
