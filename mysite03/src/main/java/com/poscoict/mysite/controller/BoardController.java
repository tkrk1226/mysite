package com.poscoict.mysite.controller;


import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscoict.mysite.service.BoardService;
import com.poscoict.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String index(Model model,
			@RequestParam(value="currentPage", required=true, defaultValue="1") int currentPage,
			@RequestParam(value="keyword", required=true, defaultValue="") String keyword) {
		Map<String, Object> map = boardService.getContentsList(currentPage, keyword);

		model.addAllAttributes(map);
		
		return "board/list";
	}
		
	@RequestMapping(value="/delete", method = RequestMethod.GET)
	public String delete(HttpSession session,
			@RequestParam(value="no", required=true, defaultValue="-1") Long no) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/board";
		}
		boolean result = boardService.deleteContents(no, authUser.getNo());
		System.out.println(result);
		return "redirect:/board";
	}
	
}
