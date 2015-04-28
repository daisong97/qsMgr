package com.dais.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dais.app.Constants;
import com.dais.app.MixHelper;
import com.dais.service.BaseDal;

@Controller
public class SecurityMgrController {
	@Autowired
	private BaseDal baseDal;
	
	@RequestMapping("/login")
	public String login(){
		return "/login";
	}
	@RequestMapping("/mgr/main")
	public String mgrMain(){
		return "/main";
	}
	@RequestMapping("/doLogin")
	public String doLogin(@RequestParam("name") String name,
			@RequestParam("name") String password,HttpServletRequest request){
		Map<String,Object> paramMap = MixHelper.newQueryMap("name", name);
		paramMap.put("password", password);
		Integer count = baseDal.queryCount("from User where name=:name and password=:password", paramMap);
		if(count != null && count > 0){
			request.getSession().setAttribute(Constants.LAO_PO_ACCOUNT_SESSION_KEY, name);
			return "/mgr/main";
		}
		return "/login";
	}
}
