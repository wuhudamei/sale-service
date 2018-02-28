package com.rocoinfo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
	 /**定义常量**/
    public static final String DATE_JFP_STR="yyyyMM";
    public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FULL_STR_ = "yyyyMMddHHmmss";
    public static final String DATE_SMALL_STR = "yyyy-MM-dd";
    public static final String DATE_KEY_STR = "yyMMddHHmmss";
    public static final String DATE_YYYY_MM_STR = "yyyy-MM";

    static final SimpleDateFormat YMD_SDF = new SimpleDateFormat(DATE_SMALL_STR);
    static final SimpleDateFormat YMD_H_M_S_SDF = new SimpleDateFormat(DATE_FULL_STR);
    static final SimpleDateFormat Y_M_SDF = new SimpleDateFormat(DATE_YYYY_MM_STR);
    /**
     * 使用预设格式提取字符串日期
     * @param strDate 日期字符串
     * @return
     */
    public static Date parseToDateTime(String strDate) {
        return parse(strDate,DATE_FULL_STR);
    }
    /**
     * 使用预设格式提取字符串日期
     * @param strDate 日期字符串
     * @return
     */
    public static Date parseToDate(String strDate) {
        return parse(strDate,DATE_SMALL_STR);
    }
    /**
     * 使用用户格式提取字符串日期
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
     
    /**
     * 两个时间比较
     * @param date
     * @return
     */
    public static int compareDateWithNow(Date date1){
        Date date2 = new Date();
        int rnum =date1.compareTo(date2);
        return rnum;
    }
    /**
     * 两个date类型比较
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDate(Date date1,Date date2){
    	 int rnum =date1.compareTo(date2);
         return rnum;
    }
    /**
     * 两个String类型比较
     * @param beginTime
     * @param endTime
     * @return
     */
    public static Long compareDate(String beginTime,String endTime){
    	SimpleDateFormat dfs = new SimpleDateFormat(DATE_FULL_STR);
        
        try{
        Date begin = dfs.parse(beginTime);
        Date end = dfs.parse(endTime);
        long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
        return between;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
     
    /**
     * 两个时间比较(时间戳比较)
     * @param date
     * @return
     */
    public static int compareDateWithNow(long date1){
        long date2 = dateToUnixTimestamp();
        if(date1>date2){
            return 1;
        }else if(date1<date2){
            return -1;
        }else{
            return 0;
        }
    }
     
 
    /**
     * 获取系统当前时间
     * @return
     */
    public static String getNowTime() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FULL_STR);
        return df.format(new Date());
    }
     
    /**
     * 获取系统当前时间
     * @return
     */
    public static String getNowTime(String type) {
        SimpleDateFormat df = new SimpleDateFormat(type);
        return df.format(new Date());
    }
    /**
     * 给日期加paramInt天
     * @param paramDate
     * @param paramInt
     * @return
     * @throws Exception
     */
    public static Date addDays(Date paramDate, int paramInt){  
        Calendar localCalendar = Calendar.getInstance();  
        localCalendar.setTime(paramDate);  
        int i = localCalendar.get(6);  
        localCalendar.set(6, i + paramInt);  
        return localCalendar.getTime();  
    }  
    /**
     * 获取系统当前计费期
     * @return
     */
    public static String getJFPTime() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_JFP_STR);
        return df.format(new Date());
    }
     
    /**
     * 将指定的日期转换成Unix时间戳
     * @param String date 需要转换的日期 yyyy-MM-dd HH:mm:ss
     * @return long 时间戳
     */
    public static long dateToUnixTimestamp(String date) {
        long timestamp = 0;
        try {
            timestamp = new SimpleDateFormat(DATE_FULL_STR).parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }
     
    /**
     * 将指定的日期转换成Unix时间戳
     * @param String date 需要转换的日期 yyyy-MM-dd
     * @return long 时间戳
     */
    public static long dateToUnixTimestamp(String date, String dateFormat) {
        long timestamp = 0;
        try {
            timestamp = new SimpleDateFormat(dateFormat).parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }
    
    /**
     * 将指定的日期转换成Unix时间戳
     * @param Date date 需要转换的日期 日期格式如:yyyy-MM-dd
     * @return String 时间戳
     */
    public static String dateToUnixTimestamp(Date date, String dateFormat) {
        String timestamp = "0";
        try {
        	timestamp = new SimpleDateFormat(dateFormat).format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return timestamp;
    }
     
    /**
     * 将当前日期转换成Unix时间戳
     * @return long 时间戳
     */
    public static long dateToUnixTimestamp() {
        long timestamp = new Date().getTime();
        return timestamp;
    }
     
     
    /**
     * 将Unix时间戳转换成日期
     * @param long timestamp 时间戳
     * @return String 日期字符串
     */
    public static String unixTimestampToDate(long timestamp) {
        SimpleDateFormat sd = new SimpleDateFormat(DATE_FULL_STR);
        sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sd.format(new Date(timestamp));
    }


    /**
     * 某个日期上加上或减去n天,如果date为空,为当前日期
     *
     * @param date
     * @param n 可正可负 如:1 或-3
     * @return
     */
    public static Date addNDate(Date date, int n) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date == null ? new Date() : date);
        c1.add(Calendar.DATE, n);
        return c1.getTime();
    }

    /**
     * @param date 格式化成 yyyy-MM-dd
     */
    public static String formatDate(Date date) {
        if (date == null)
            return null;
        return YMD_SDF.format(date);
    }
}
