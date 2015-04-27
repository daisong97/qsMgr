package com.dais.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dais.app.Constants;
import com.dais.app.MixHelper;
import com.dais.domain.JobRecord;
import com.dais.domain.Qs;
import com.dais.service.BaseDal;
import com.dais.vo.JrList;
import com.dais.vo.JrVO;
import com.dais.vo.RequestPagination;
import com.dais.vo.ResponsePagination;
import com.dais.vo.ResponseStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/mgr")
public class MgrJobRecordController {
	@Autowired
	private BaseDal baseDal;
	
	@RequestMapping("/listJr.json")
	@ResponseBody
	public ResponsePagination listJr(RequestPagination pagination,String jobNum){
		Map<String,Object> paramMap = MixHelper.newMap();
		String where = ""; 
		if(StringUtils.isNotEmpty(jobNum)){
			paramMap.put("jobNum", jobNum);
			where = "where jobNumber=:jobNum";
		}
		ResponsePagination page = baseDal.queryPagenition(pagination, "from JobRecord "+where, paramMap);
		if(page.getRows() != null){
			List<JrVO> list = new ArrayList<JrVO>();
			for(Object jrObj :page.getRows()){
				JobRecord jr =(JobRecord)jrObj;
				JrVO jv = new JrVO();
				jv.setId(jr.getId());
				jv.setIp(jr.getIp());
				jv.setJobNumber(jr.getJobNumber());
				if(jr.getQs() != null){
					jv.setqPrice(jr.getQs().getPrice());
					jv.setQsId(jr.getQs().getId());
					jv.setQsName(jr.getQs().getName());
					Map<String, Object> qMap = MixHelper.newQueryMap("jbNum", jr.getJobNumber());
					qMap.put("qsId", jr.getQs().getId());
					Integer count = baseDal.queryCount("from JobRecord where jobNumber=:jbNum and qs.id=:qsId", qMap);
					jv.setReadyNum(count);
				}
				list.add(jv);
			}
			page.setRows(list);
		}
		return page;
	}
	@RequestMapping("/showJr")
	public String showlistJr(Model model){
		List<Qs> qs = baseDal.findByHql("from Qs where status =:status", MixHelper.newQueryMap("status", new Character('Y')));
		ObjectMapper om = new ObjectMapper();
		try {
			model.addAttribute("qsData", om.writeValueAsString(qs));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "jr/jrMgr";
	}
	@RequestMapping("/saveOrUpdateJr")
	@ResponseBody
	public ResponseStatus saveOrUpdateQs(JrList jrList){
		ResponseStatus rs = new ResponseStatus("保存成功!", Constants.success);
		
		if(jrList != null && jrList.getList() != null){
			try{
				for(JobRecord jr : jrList.getList()){
					jr.setQs(baseDal.findById(jr.getQsId(), Qs.class));
					if(jr.getId() == null){
						baseDal.create(jr);
					}else{
						baseDal.update(jr);
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
	@RequestMapping("/deleteJr")
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
				JobRecord[] ql= new JobRecord[ids.length];
				for(int i = 0 ;i<ids.length;i++){
					ql[i] = new JobRecord(ids[i]);
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
