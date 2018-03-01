package com.damei.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Utils {

	/**
	 * 获取	当前时间 timestamp,年-月-日 时:分:秒
	 * @return
	 */
	public static Timestamp currentDate(){
		Timestamp ts = new Timestamp(System.currentTimeMillis());   
		return ts;
	}
	
	/**
	 * 获取	当前时间  年-月-日
	 * @return
	 */
	public static String currentDateStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}
	
	/**
	 * timestamp转字符串
	 * @return
	 */
	public static String timestampToString(Timestamp dte){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(dte);
	}
	
	/**
	 * 字符串日期转Timestamp
	 * @param dte
	 * @return
	 */
	public static Timestamp stringToDate(String dte){
		return Timestamp.valueOf(dte);
	}
	
	/**
	 * 根据传入工时统计工作状态
	 * @param map
	 * @param countHour
	 * @return
	 */
	public static Map<String,Object> formatWorkParam(Map<String,Object> map,int countHour){
//		if(countHour > 0){
			if(countHour <= 20){
				map.put("userStatu", "清闲");
				map.put("statuColor", "#14ef18");
			}else if(countHour > 20 && countHour <=35){
				map.put("userStatu", "轻松");
				map.put("statuColor", "#14c");
			}else if(countHour > 35 && countHour <=40){
				map.put("userStatu", "满负荷");
				map.put("statuColor", "#f8ac59");
			}else if(countHour > 40){
				map.put("userStatu", "超负荷");
				map.put("statuColor", "#F00");
			}
//		}else{
//			map.put("userStatu", "暂无数据");
//			map.put("statuColor", "#808080");
//		}
		
		return map;
	}
}
