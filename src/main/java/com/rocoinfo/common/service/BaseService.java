package com.rocoinfo.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <dl>
 * <dd>描述:service基础类</dd>
 * <dd>公司: 大城若谷信息技术有限公司</dd>
 * <dd>创建时间：15/7/30 上午11:50</dd>
 * <dd>创建人： asher</dd>
 * </dl>
 */
@SuppressWarnings("all")
public abstract class BaseService<T> implements IBaseService<T> {

	protected Logger logger = LoggerFactory.getLogger(getClass());
}