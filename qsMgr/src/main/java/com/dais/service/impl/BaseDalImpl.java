package com.dais.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.dais.app.BeanUtilsExt;
import com.dais.domain.BaseEntity;
import com.dais.service.BaseDal;
import com.dais.vo.RequestPagination;
import com.dais.vo.ResponsePagination;

@Service
public class BaseDalImpl implements BaseDal{
	@Autowired
	private SessionFactory factory;
	
	public Session getCurrentSession(){
		return factory.getCurrentSession();
	} 

	@SuppressWarnings("unchecked")
	@Override
	public <T  extends BaseEntity> Integer create(T t) {
		t.setCreateTime(new Date());
		return  (Integer)getCurrentSession().save(t);
	}

	@Override
	public void delete(Object... objects) {
		for(Object obj : objects){
			getCurrentSession().delete(obj);
		}
		
	}

	@Override
	public <T  extends BaseEntity> void update(T t) {
		@SuppressWarnings("unchecked")
		T t2 = (T) findById(t.getId(), t.getClass());
		if(t2 != null){
			BeanUtilsExt.copyProps(t, t2);
			t2.setUpdateTime(new Date());
			getCurrentSession().update(t2);
		}else{
			throw new RuntimeException("更新失败,无效的Id:"+t.getId() +" "+t);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T findById(Integer id, Class<T> clazz) {
		return (T) getCurrentSession().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findByHql(String hql, Map<String, Object> paramMap) {
		Query query=getCurrentSession().createQuery(hql);
		query.setProperties(paramMap);
		return query.list();
	}

	@Override
	public Integer queryCount(String hql, Map<String, Object> paramMap) {
		String perfix = hql.substring(hql.indexOf("from"));
		int index = perfix.indexOf(" order ");
		if(index != -1){
			perfix = perfix.substring(0, index+1);
		}
		perfix = "select count(1) "+perfix;
		Query query = getCurrentSession().createQuery(perfix);
		query.setProperties(paramMap);
		Iterator<?> iterate = query.iterate();
		if(iterate.hasNext()){
			return ((Number)iterate.next()).intValue();
		}
		return -1;
	}

	@Override
	public ResponsePagination queryPagenition(RequestPagination pagination,
			String hql, Map<String, Object> paramMap) {
		ResponsePagination rp = new ResponsePagination();
		rp.setTotal(queryCount(hql, paramMap));

		Query q = getCurrentSession().createQuery(hql);
		Integer page = pagination.getPage();
		Integer rows = pagination.getRows();
		rows = rows == null ? 20 : rows;
		page = page == null ? 0 : (page - 1) * rows;
		q.setFirstResult(page);
		q.setMaxResults(rows);
		q.setProperties(paramMap);
		rp.setRows(q.list());
		return rp;
	}
}
