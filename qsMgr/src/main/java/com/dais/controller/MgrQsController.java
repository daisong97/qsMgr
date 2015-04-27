package com.dais.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dais.app.Constants;
import com.dais.app.MixHelper;
import com.dais.domain.Qs;
import com.dais.service.BaseDal;
import com.dais.vo.QsList;
import com.dais.vo.QsVO;
import com.dais.vo.RequestPagination;
import com.dais.vo.ResponsePagination;
import com.dais.vo.ResponseStatus;

@Controller
@RequestMapping("/mgr")
public class MgrQsController {
	@Autowired
	private BaseDal baseDal;
	
	@RequestMapping("/listQs.json")
	@ResponseBody
	public ResponsePagination listQs(RequestPagination pagination,String name){
		Map<String,Object> paramMap = MixHelper.newMap();
		String where = ""; 
		if(StringUtils.isNotEmpty(name)){
			where += " where name like :name";
			paramMap.put("name", "%"+name+"%");
		}
		ResponsePagination page = baseDal.queryPagenition(pagination, "from Qs "+where+" order by createTime desc", paramMap);
		if(page != null && page.getRows() != null){
			List<QsVO> list = new ArrayList<QsVO>();
			for(Object obj : page.getRows()){
				Qs q = (Qs)obj;
				QsVO qv= new QsVO();
				qv.setId(q.getId());
				qv.setName(q.getName());
				qv.setPrice(q.getPrice());
				qv.setReadyNum(baseDal.queryCount("from JobRecord where qs.id=:qId", MixHelper.newQueryMap("qId", q.getId())));
				qv.setTotalNum(q.getTotalNum());
				qv.setStatus(q.getStatus());
				list.add(qv);
			}
			page.setRows(list);
		}
		return page;
	}
	@RequestMapping("/showQsMgr")
	public String showQsMgr(){
		return "/qs/qsMgr";
	}
	@RequestMapping("/saveOrUpdateQs")
	@ResponseBody
	public ResponseStatus saveOrUpdateQs(QsList qsList){
		ResponseStatus rs = new ResponseStatus("保存成功!", Constants.success);
		
		if(qsList != null && qsList.getList() != null){
			try{
				for(Qs qs : qsList.getList()){
					if(qs.getId() == null){
						baseDal.create(qs);
					}else{
						baseDal.update(qs);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				rs.setMsg("报错失败"+e.getMessage());
				rs.setStatus(Constants.exception);
			}
		}
		return rs;
	}
	@RequestMapping("/deleteQs")
	@ResponseBody
	public ResponseStatus deleteQs(Integer[] ids){
		ResponseStatus rs = new ResponseStatus("删除成功!", Constants.success);
		try{
			boolean isCheckPass = true;
			if(ids!= null){
				for(Integer id:ids){
					Integer count = baseDal.queryCount("select count(1) from JobRecord where qs.id = :qsId", MixHelper.newQueryMap("qsId", id));
					if(count > 0){
						isCheckPass = false;
						break;
					}
				}
			}
			if(isCheckPass){
				Qs[] ql= new Qs[ids.length];
				for(int i = 0 ;i<ids.length;i++){
					ql[i] = new Qs(ids[i]);
					baseDal.delete(ids);
				}
			}else{
				rs.setMsg("删除数据中已经存在使用数据!");
				rs.setStatus(Constants.failure);
			}
		}catch(Exception e){
			e.printStackTrace();
			rs.setMsg(e.getMessage());
			rs.setStatus(Constants.failure);
		}
		return rs;
	}
}
