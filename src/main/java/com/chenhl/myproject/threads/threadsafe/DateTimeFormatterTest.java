package com.chenhl.myproject.threads.threadsafe;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Auther: chenhonglei
 * @Date: 2018/12/5 14:35
 * @Description:
 */
public class DateTimeFormatterTest {

    public static void main(String[] args) {
        //解析日期
        String dateStr = "2018年10月25日";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        LocalDate date = LocalDate.parse(dateStr, formatter);

        //日期转换为字符串
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy年MM月dd日 hh:mm a");
        String nowStr = now.format(format);
        System.out.println(nowStr);
    }
}
