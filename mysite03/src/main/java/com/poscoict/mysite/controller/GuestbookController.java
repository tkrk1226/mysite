package com.poscoict.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscoict.mysite.service.GuestbookService;
import com.poscoict.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;

	@RequestMapping("/spa")
	public String spa() {
		return "guestbook/index-spa";
	}
	
	@RequestMapping("")
	public String index(Model model) {
		List<GuestbookVo> list = guestbookService.getMessageList();
		model.addAttribute("list", list);
		return "guestbook/index";
	}
	
	@RequestMapping("/add")
	public String add(GuestbookVo vo) {
		guestbookService.addMessage(vo);

		System.out.println("-------------------------------------");
		System.out.println(vo);
		System.out.println("-------------------------------------");
		
		return "redirect:/guestbook";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete(Model model,
			@RequestParam(value="no", required=true, defaultValue="") Long no) {
		if(no == null) {
			return "redirect:/guestbook";
		}
		model.addAttribute("no", no);
		return "guestbook/delete";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(GuestbookVo vo) {
		if(vo.getNo() == null) {
			return "redirect:/guestbook";
		}
		guestbookService.deleteMessage(vo.getNo(), vo.getPassword());
		return "redirect:/guestbook";
	}
}