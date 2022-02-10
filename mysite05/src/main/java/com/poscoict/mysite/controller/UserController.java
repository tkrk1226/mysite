package com.poscoict.mysite.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscoict.mysite.security.Auth;
import com.poscoict.mysite.security.AuthUser;
import com.poscoict.mysite.service.UserService;
import com.poscoict.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo vo) {
		return "user/join";
	}

	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo vo, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
//			List<ObjectError> list = result.getAllErrors();
//			도와주는 역할이지 내가 다룰 필요가 없음
//			for(ObjectError error : list) {
//				System.out.println(error);
//			}
			
//			model.addAttribute("userVo", vo);
			model.addAllAttributes(result.getModel());
			
			return "user/join";
		}
		
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}
	
//	@RequestMapping(value="/join", method=RequestMethod.POST)
//	public String join(UserVo userVo) {
//		userService.join(userVo);
//		return "redirect:/user/joinsuccess";
//	}
	
	@RequestMapping(value="/joinsuccess")
	public String joinsuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="/login")
	public String login() {
		return "user/login";
	}
	
	@RequestMapping(value="/auth", method=RequestMethod.POST)
	public void auth() {
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public void logout() {
	}
	
	@Auth
	@RequestMapping(value="/update", method = RequestMethod.GET)
	public String update(@AuthUser UserVo authUser, Model model) {
		Long userNo = authUser.getNo();
		UserVo userVo = userService.getUser(userNo);
		model.addAttribute("userVo" , userVo);
		return "user/update";
	}
	
	@Auth
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public String update(@AuthUser UserVo authUser, UserVo userVo) {

		userVo.setNo(authUser.getNo());
		userService.updateUser(userVo);
		authUser.setName(userVo.getName());
		return "redirect:/user/update";
	}
	
//	@ExceptionHandler(Exception.class)
//	public String UserControllerExceptionHandler(){
//		return "error/exception";
//	}		
}
