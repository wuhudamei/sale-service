package com.rocoinfo.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 
 * @author Andy 2017-3-9 11:10:54
 */
public class DynamicDataSource extends AbstractRoutingDataSource{
	
	private static Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);//日志
	
	@Override
	protected Object determineCurrentLookupKey() {
	  // 从自定义的位置获取数据源标识
		String dataSource = DynamicDataSourceHolder.getDataSource();
		logger.info("当前数据源 = " + dataSource);
	  return dataSource;
	}
}
