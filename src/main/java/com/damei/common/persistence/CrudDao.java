/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.damei.common.persistence;

import java.util.List;
import java.util.Map;

public interface CrudDao<T> extends BaseDao {

	T getById(Long id);

	void insert(T entity);

	void update(T entity);

	void deleteById(Long id);

	List<T> findAll();

	List<T> search(Map<String, Object> params);

	Long searchTotal(Map<String, Object> params);
}