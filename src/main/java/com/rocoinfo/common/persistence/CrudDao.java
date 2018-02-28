/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.rocoinfo.common.persistence;

import java.util.List;
import java.util.Map;

/**
 * <dl>
 * <dd>描述:Dao Crud基础类</dd>
 * <dd>公司: 大城若谷信息技术有限公司</dd>
 * <dd>创建时间：15/7/30 上午11:50</dd>
 * <dd>创建人： asher</dd>
 * </dl>
 */
public interface CrudDao<T> extends BaseDao {

	/**
	 * 获取单条数据
	 * 
	 * @param id
	 */
	T getById(Long id);

	/**
	 * 插入数据
	 * 
	 * @param entity
	 */
	void insert(T entity);

	/**
	 * 更新数据
	 * 
	 * @param entity
	 */
	void update(T entity);

	void deleteById(Long id);

	List<T> findAll();

	List<T> search(Map<String, Object> params);

	Long searchTotal(Map<String, Object> params);
}