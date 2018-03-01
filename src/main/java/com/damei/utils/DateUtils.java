package com.damei.utils;


import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.LocalDate;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {

    private DateUtils() {
        super();
    }

    /**
     * 字符串->Date类型
     *
     * @param date     string
     * @param patterns 格式
     * @return
     */
    public static Date parse(String date, String... patterns) {
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(date, patterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * timestamp -> date
     * required: JDK1.8+
     *
     * @param timestamp 时间戳
     * @return
     */
    public static Date parse(Timestamp timestamp) {
        if (timestamp == null)
            return null;
        java.time.LocalDateTime localDateTime = timestamp.toLocalDateTime();
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }
    
    /**
     * date -> timestamp
     *
     * @param timestamp 时间戳
     * @return
     */
    public static Timestamp parse(Date date){
    	return new Timestamp((date).getTime());
    }

    /**
     * Date->字符串类型
     *
     * @param date     Date
     * @param patterns 格式
     * @return
     */
    public static String format(Date date, String patterns) {
        return DateFormatUtils.format(date, patterns);
    }

    /**
     * 获取指定日期当周的 周一的日期
     *
     * @param date 指定日期
     * @return
     */
    public static Date getMondayByDate(Date date) {
        if (date == null)
            return null;
        LocalDate localDate = new LocalDate(date);
        int week = localDate.getDayOfWeek();
        return localDate.minusDays(week - 1).toDate();
    }
    
    /**
     * 取传入日期的前几天或后几天数据
     * @param date	日期
     * @param dateNum	传入日期的前或后几天
     * @description 
     * 如传入日期为:
     * date = 2017-02-09
     * dateNum = 1 则返回的是02月09号后一天的数据为:2017-02-10
     * 
     * 如传入日期为:
     * date = 2017-02-09
     * dateNum = -6 则返回的是02月09号前6天的数据为:2017-02-03
     * @return
     */
    public static String getDateBeforOrAfterDate(Date date,int dateNum) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, dateNum);
        return simpleDateFormat(calendar.getTime());
    }
    
    /**
     * 格式化日期为字符串
     * @param date
     * @return
     */
    private static String simpleDateFormat(Date date){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	return sdf.format(date);
    }
    
    /**
     * 根据传入的时间戳格式的字符串日期转换成自用标准格式
     * @param date 日期参数
     * @description  
     * 		2016-05-09T08:03:57.095Z
     * 		转换成
     * 		2016-05-09 08:03:57
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String simpleDateForString(String date){
    	return date.indexOf("T") != -1 ? date.substring(0,date.indexOf("T")) + " "+ date.substring(date.indexOf("T") + 1 ,date.indexOf(".")) : date;
    }

    /**
     * 获取指定日期当周的 周日的日期
     *
     * @param date 指定日期
     * @return
     */
    public static Date getSundayByDate(Date date) {
        if (date == null)
            return null;
        LocalDate localDate = new LocalDate(date);
        int week = localDate.getDayOfWeek();
        return localDate.plusDays(7 - week).toDate();
    }

    /**
     * 获取日期的星期(从1开始到7)
     *
     * @param date date
     * @return
     */
    public static int getWeek(Date date) {
        if (date != null) {
            LocalDate localDate = LocalDate.fromDateFields(date);
            return localDate.getDayOfWeek();
        }
        return 0;
    }

    /**
     * 获取一段时间的集合
     *
     * @param startDate    开始时间
     * @param endDate      结束时间
     * @param containStart 是否包含开始时间
     * @param containEnd   是否包含结束时间
     * @return
     */
    public static List<Date> getIntervalDate(Date startDate, Date endDate, boolean containStart, boolean containEnd) {
        List<Date> list = new ArrayList<>();
        if (startDate != null && endDate != null) {
            // 转成joda time
            LocalDate start = new LocalDate(startDate);
            LocalDate end = new LocalDate(endDate);

            // 如果开始时间小于结束时间 返回空list
            if (start.compareTo(end) > 1)
                return list;

            // 判断是否包含开始时间
            if (containStart)
                list.add(startDate);

            //开始时间等于结束时间,返回list
            if (start.compareTo(end) == 0)
                return list;

            // 求中间的日期
            boolean b = true;
            LocalDate d = start.plusDays(1);
            while (b) {
                // 如果加1天后小于结束日期,则将日期添加到list中
                if (d.compareTo(end) < 0) {
                    list.add(d.toDate());
                    d = d.plusDays(1);
                } else {
                    b = false;
                }
            }

            // 判断是否包含结束日期
            if (containEnd)
                list.add(endDate);
        }
        return list;
    }


    /**
     * 获取两个日期相差的天数
     * @param fDate
     * @param oDate
     * @return
     * @description 原有取年中的天,在跨年日期会有问题出现负数.
     */
    public static int daysOfTwo(Date fDate, Date oDate) {
//        Calendar aCalendar = Calendar.getInstance();
//        aCalendar.setTime(fDate);
//        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
//        aCalendar.setTime(oDate);
//        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
//        return day2-day1;
        return Integer.parseInt( format(oDate,"yyyyMMdd")) - Integer.parseInt(format(fDate,"yyyyMMdd"));
    }
}
