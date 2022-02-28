package com.poscoict.mysite.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

	@RequestMapping({ "", "/main" })
	public String index() {
		return "main/index";
//		return "/WEB-INF/views/main/index.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/msg01")
	public String message01() {
		return "안녕";
	}

	@ResponseBody
	@RequestMapping("/msg02")
	public void message02(HttpServletResponse resp) throws Exception {
		resp.setContentType("application/json; charset=UTF-8");
		resp.getWriter().print("{\"message\":\"Hello World\"}");
	}
	
	@ResponseBody
	@RequestMapping("/msg03")
	public Object message03() {
		Map<String, Object> map = new HashMap<>();
		map.put("message", "Hello World");
		map.put("food", "hamburger");
		return map;
	}	
}