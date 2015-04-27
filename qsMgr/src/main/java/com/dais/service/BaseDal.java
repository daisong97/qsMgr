package com.dais.service;

import java.util.List;
import java.util.Map;

import com.dais.domain.BaseEntity;
import com.dais.vo.RequestPagination;
import com.dais.vo.ResponsePagination;

public interface BaseDal {
	/**
	 * 创建
	 * @param t
	 * @return
	 */
	<T extends BaseEntity> Integer create(T t);
	
	/***
	 * 删除
	 * @param ids
	 */
	void delete(Object... objects);
	
	/**
	 * 修改
	 * @param t
	 */
	<T  extends BaseEntity> void update(T t);
	/**
	 * 根据Id查询
	 * @param t
	 */
	 <T> T findById(Integer id,Class<T> clazz);
	
	/**
	 * 根据hql查询
	 * @param sql
	 * @param paramMap
	 * @return
	 */
	<T> List<T> findByHql(String sql,Map<String,Object> paramMap);
	
	/**
	 * 查询数量
	 * @param hql
	 * @param paramMap
	 * @return
	 */
	Integer queryCount(String hql,Map<String,Object> paramMap);
	
	
	/**
	 * 分页查询
	 * @param pagination
	 * @param hql
	 * @return
	 */
	ResponsePagination queryPagenition(RequestPagination pagination,String hql,Map<String,Object> paramMap);
}
