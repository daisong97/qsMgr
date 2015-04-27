package com.dais.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dais.vo.ResponseStatus;

@Controller
public class SecurityMgrController {
	
	@RequestMapping("/login")
	public String login(){
		return "/login";
	}
	@RequestMapping("/doLogin")
	public ResponseStatus doLogin(String name,String password){
		
		return null;
	}
}
