package cn.damei.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public final class ArithUtils {
	
	private static final int  DEF_DIV_SCALE  = 10;
	 /**
    * 提供精确的加法运算。
    * @param v1 被加数
    * @param v2 加数
    * @return 两个参数的和
    */
   public static double add(double v1,double v2){
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
   public static double sub(double v1,double v2){
       BigDecimal b1 = new BigDecimal(Double.toString(v1));
       BigDecimal b2 = new BigDecimal(Double.toString(v2));
       return b1.subtract(b2).doubleValue();
   } 
   
   
   /**
    * 提供精确的乘法运算
    * @param v1 被减数
    * @param v2 减数
    * @return 两个参数的乘积
    */
   public static double mutiply(double v1,double v2){
       BigDecimal b1 = new BigDecimal(Double.toString(v1));
       BigDecimal b2 = new BigDecimal(Double.toString(v2));
       return  b1.multiply(b2).doubleValue();
   } 
	
   
   /**提供相对精确的除法运算,当除不净时，保留10小数，小数点10位后的数字四舍五入
    * @param v1 被除数
    * @param v2 除数
    * @return 返回v1/v2的商，最多保留10小数,小数点10位后的数字四舍五入
    */
   public static double div(double v1,double v2) {
	   return div(v1,v2,DEF_DIV_SCALE);
   }
   
   
   /**提供相对精确的除法运算,当除不净时，保留scale位小数，小数点scale位后的数字四舍五入
    * @param v1 被除数
    * @param v2 除数
    * @param scale 表示要精确到小数点后几位
    * @return 返回v1/v2的商，最多保留scale小数,小数点scale位后的数字四舍五入
    */
   public static double div(double v1,double v2,int scale) {
	   if(scale<0){
           throw new IllegalArgumentException(
               "The scale must be a positive integer or zero");
       }
	   BigDecimal d1 = new BigDecimal(Double.toString(v1));
	   BigDecimal d2 = new BigDecimal(Double.toString(v2));
	  return d1.divide(d2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
   }
   
   
   
   /**
    * 提供精确的小数位四舍五入处理。
    * @param v 需要四舍五入的数字
    * @param scale 小数点后保留几位
    * @return 四舍五入后的结果
    */
   public static double round(double v,int scale){
       if(scale<0){
           throw new IllegalArgumentException(
               "The scale must be a positive integer or zero");
       }
       BigDecimal b = new BigDecimal(Double.toString(v));
       BigDecimal one = new BigDecimal("1");
       return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
   }
   
   
   /**
    *向下取整数 12.9 =>12 
    */
   public static int roundDown(double v) {
	   BigDecimal b = new BigDecimal(Double.toString(v));
	   BigDecimal one = new BigDecimal("1");
	   return b.divide(one, 0, BigDecimal.ROUND_DOWN).intValue();
   }
   
   
   /**
    * 向上取整数  12.1 =>13
   */
   public static int roundUp(double v) {
	   BigDecimal b = new BigDecimal(Double.toString(v));
	   BigDecimal one = new BigDecimal("1");
	   return b.divide(one, 0, BigDecimal.ROUND_UP).intValue();
   }
   
   /**
    * 四舍五入取整
    */
   public static int roundHalfUp(double v){
	  return  new BigDecimal(v).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
   }
   
   
   /**返回现金表示形式保留两位小数
    * 如果money是100.53600,返回￥100.54
    * 100 = >￥100.00
    */
   public  static String getCurrency(double money){   
       NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.CHINA);   
       return formatter.format(money);   
   }

    /**
     * 12.3% => 0.123
     *
     * @param percent 分数 转换成小数
     * @return
     */
    public static double percentToDouble(String percent) {
        String floatNum = percent.substring(0, percent.length() - 1);
        double n = NumberUtils.toDouble(floatNum);
        return n = n / 100;
    }

    /***
     * 处理科学计数法表示的金额 <br/>
     * 100.0 =>100 <br/>
     * 1.24e2 =>124
     *
     * @param amount
     * @return
     */
    public static String formatMoney(double amount) {
        String money = StringUtils.EMPTY;
        int intPart = (int) amount;
        if (amount != intPart) {
            NumberFormat fmt = NumberFormat.getInstance();
            fmt.setMaximumFractionDigits(4);
            money = fmt.format(amount);
            money = money.replace(",", StringUtils.EMPTY);
        } else {
            money = String.valueOf(intPart);
        }
        return money;
    }
}