package com.poscoict.mysite.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Auth(role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {


	@ResponseBody
	@RequestMapping("/main")
	public String main() {
		return "AdminController.main()";
	}	

	@ResponseBody
	@RequestMapping("/board")
	public String board() {
		return "AdminController.board()";
	}

	@ResponseBody
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "AdminController.guestbook()";
	}
	
}
