package com.dais.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dais.app.Constants;
import com.dais.app.MixHelper;
import com.dais.domain.JobRecord;
import com.dais.domain.Qs;
import com.dais.service.BaseDal;
import com.dais.vo.ResponseStatus;

@Controller
public class FillQsController {
	private static Logger logger = Logger.getLogger(FillQsController.class);

	private static final String JOB_NUM = "JOB_NUM_INFO";

	private static final String IS_SELECT_QS = "is_select_qs";
	@Autowired
	private BaseDal baseDal;

	@RequestMapping("/")
	public String fQs(String jobNum, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		List<Qs> qs = baseDal.findByHql("from Qs where status =:status", MixHelper.newQueryMap("status", new Character('Y')));
		if (!CollectionUtils.isEmpty(qs)) {
			model.addAttribute("qss", qs);
		}
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (JOB_NUM.equals(cookie.getName())) {
					if (StringUtils.isNoneBlank(cookie.getName())) {
						Map<String,Object> paramMap = MixHelper.newQueryMap("jobNumber",cookie.getValue().trim());
						Integer num = baseDal.queryCount("from JobRecord where jobNumber=:jobNumber order by createTime desc",paramMap);
						if (num != null && num != -1 && num >= 0) {
							model.addAttribute("alreadyComplete", num);
						} else {
							model.addAttribute("alreadyComplete", "未知，请联系管理员!");
						}
					}
					break;
				}
			}
		}
		return "qs/fillQs";
	}

	@RequestMapping("/addJobRecord")
	@ResponseBody
	private ResponseStatus addJobRecord(@RequestParam("jobNum") String jobNum,
			@RequestParam("qsId") Integer qsId, @RequestParam("ip") String ip,
			Model model, HttpServletResponse response) {
		ResponseStatus status = new ResponseStatus("添加成功!", Constants.success);
		try {
			model.addAttribute(IS_SELECT_QS, qsId);
			response.addCookie(new Cookie(IS_SELECT_QS, qsId.toString()));
			Map<String, Object> paramMap = MixHelper.newQueryMap("ip", ip);
			paramMap.put("qsId", qsId);
			Integer count = baseDal.queryCount(
					"from JobRecord where ip=:ip and qs.id=:qsId", paramMap);
			if (count > 0) {
				status.setMsg("ip已经填写过了！");
				status.setStatus(Constants.repeat);
			} else {
				Qs qs = baseDal.findById(qsId, Qs.class);
				JobRecord jr = new JobRecord();
				jr.setIp(ip);
				jr.setJobNumber(jobNum);
				jr.setQs(qs);
				baseDal.create(jr);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			status.setMsg("服务器发生异常!:" + e.getMessage());
			status.setStatus(Constants.exception);
		}
		return status;
	}
	@RequestMapping("/queryUserInfo")
	@ResponseBody
	public Map<String,Object> queryUserInfo(@RequestParam("jobNum") String jobNum,@RequestParam("qsId") Integer qsId){
		Map<String, Object> map = MixHelper.newQueryMap("jbNum", jobNum);
		map.put("qsId", qsId);
		Integer count = baseDal.queryCount(
				"from JobRecord where jobNumber=:jbNum and qs.id=:qsId", map);
		Integer allCount = baseDal.queryCount(
				"from JobRecord where qs.id=:qsId", map);
		Map<String,Object> result = MixHelper.newMap();
		result.put("completeNum", count);
		result.put("allCount", allCount);
		return result;
		
	}
}