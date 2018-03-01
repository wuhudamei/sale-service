package com.damei.common.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IBaseService<T> {

    T getById(Long id);

    void insert(T entity);

    void update(T entity);

    void deleteById(Long id);

    List<T> findAll();

    Page<T> searchScrollPage(Map<String, Object> params, Pageable pageable);
}
