package com.smartcontactmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("user")
@Controller
public class UserController {

	@RequestMapping(path = { "", "/", "/index" })
	public String dashboard() {
		return "user/dashboard";
	}
}
