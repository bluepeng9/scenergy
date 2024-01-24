package com.wbm.scenergyspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
	@GetMapping({"","/"})
	public @ResponseBody String index() {
		return "index";
	}
	@GetMapping("/login")
	public String loginForm() {
		return "loginForm";
	}

}
