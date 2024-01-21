package com.wbm.scenergyspring.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class IntexController {
	@GetMapping("/login")
	public  String loginForm() {
		return "loginForm";
	}
}
