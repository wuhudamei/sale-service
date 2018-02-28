package com.rocoinfo.common.service;

import com.rocoinfo.Constants;
import com.rocoinfo.common.persistence.CrudDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * <dl>
 * <dd>描述:service基础类--获取和更新</dd>
 * <dd>公司: 大城若谷信息技术有限公司</dd>
 * <dd>创建时间：15/7/30 上午11:50</dd>
 * <dd>创建人： asher</dd>
 * </dl>
 */
@SuppressWarnings("all")
public abstract class CrudService<D extends CrudDao<T>, T extends Serializable> extends BaseService<T> {

    /**
     * 持久层对象
     */
    @Autowired
    protected D entityDao;

    @Override
    public T getById(Long id) {
        if (id == null || id < 1)
            return null;
        return entityDao.getById(id);
    }

    @Override
    @Transactional
    public void insert(T entity) {
        if (entity == null)
            return;
        entityDao.insert(entity);
    }

    @Override
    public void update(T entity) {
        if (entity == null)
            return;
        entityDao.update(entity);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null || id < 1)
            return;
        this.entityDao.deleteById(id);
    }

    @Override
    public List<T> findAll() {
        return entityDao.findAll();
    }

    @Override
    public Page<T> searchScrollPage(Map<String, Object> params, Pageable pageable) {
        List<T> pageData = Collections.emptyList();
        Long count = this.entityDao.searchTotal(params);

        params.put(Constants.PAGE_OFFSET, pageable.getOffset()/pageable.getPageSize());
        params.put(Constants.PAGE_SIZE, pageable.getPageSize());
        params.put(Constants.PAGE_SORT, pageable.getSort());

        if (count > 0) {
            pageData = entityDao.search(params);
        }
        return new PageImpl<T>(pageData, pageable, count);
    }
}
