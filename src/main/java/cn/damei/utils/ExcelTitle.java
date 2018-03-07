package cn.damei.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelTitle {
	/**
	 * 属性的标题名称
	 */
	String title();

	/**
	 * 在excel的顺序
	 */
	int order() default 9999;
}