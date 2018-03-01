package com.damei.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumUtils {
	 
	/** 
	* 提供精确的加法运算。 
	* @param v1 被加数 
	* @param v2 加数 
	* @return 两个参数的和 
	*/  
	  
	public static double add(double v1, double v2) {  
	   BigDecimal b1 = new BigDecimal(Double.toString(v1));  
	   BigDecimal b2 = new BigDecimal(Double.toString(v2));  
	   return b1.add(b2).doubleValue();  
	} 
	
	/** 
	* 提供精确的减法运算。 
	* @param v1 被减数 
	* @param v2 减数 
	* @return 两个参数的差 
	*/  
	  
	public static double sub(double v1, double v2) {  
	   BigDecimal b1 = new BigDecimal(v1);  
	   BigDecimal b2 = new BigDecimal(v2);  
	   return b1.subtract(b2).doubleValue();  
	}  
	
	/** 
	* 提供精确的乘法运算。 
	* @param v1 被乘数 
	* @param v2 乘数 
	* @return 两个参数的积 
	*/  
	public static double mul(double v1, double v2) {  
	   BigDecimal b1 = new BigDecimal(v1);  
	   BigDecimal b2 = new BigDecimal(v2);  
	   return formatDouble(b1.multiply(b2).doubleValue());  
	}
	
	/**
	 * 格式化数字两位小数
	 * @param num
	 * @return
	 */
	public static double formatDouble(double num){
		DecimalFormat df = new DecimalFormat("#.00");
		return Double.parseDouble(df.format(num));
	}
	
	/** 
	* 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 
	* 定精度，以后的数字四舍五入。 
	* @param v1 被除数 
	* @param v2 除数 
	* @param scale 表示表示需要精确到小数点以后几位。 
	* @return 两个参数的商 
	*/  
	public static double div(double v1, double v2, int scale) {  
	   int defScale = 2;
	   if (scale < 0) {  
		   scale = defScale;
	   }  
	   BigDecimal b1 = new BigDecimal(v1);  
	   BigDecimal b2 = new BigDecimal(v2);  
	   return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();  
	} 

}
