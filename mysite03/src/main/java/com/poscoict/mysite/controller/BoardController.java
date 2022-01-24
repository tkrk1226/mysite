package com.poscoict.mysite.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscoict.mysite.service.BoardService;
import com.poscoict.mysite.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	//http://localhost:8080/mysite03/board?currentPage=2&keyword=
	@RequestMapping("")
	public String index(Model model,
			@RequestParam(value="currentPage", required=true, defaultValue="1") int currentPage,
			@RequestParam(value="keyword", required=true, defaultValue="") String keyword) {
		Map<String, Object> map = boardService.getContentsList(currentPage, keyword);

		System.out.println("------------------------------------------------------");
		System.out.println("currentPage : " + currentPage);
		System.out.println("map(currentPage) : " + map.get("currentPage"));
		System.out.println("keyword : " + keyword);
		System.out.println("map(keyword) : " + map.get("keyword"));
		System.out.println("map(boardCount) : " + map.get("boardCount"));
		System.out.println("map(pageCount) : " + map.get("pageCount"));
		System.out.println("map(nextPage) : " + map.get("nextPage"));
		System.out.println("map(prePage) : " + map.get("prePage"));
		System.out.println("map(pageShow) : " + map.get("pageShow"));
		System.out.println("map(pageDevide) : " + map.get("pageDevide"));
		System.out.println("map(pageDevideCount) : " + map.get("pageDevideCount"));
		System.out.println("------------------------------------------------------");
		
		
		model.addAttribute("currentPage", map.get("currentPage"));
		model.addAttribute("boardCount", map.get("boardCount"));
		model.addAttribute("pageCount", map.get("pageCount"));
		model.addAttribute("nextPage", map.get("nextPage"));
		model.addAttribute("prePage", map.get("prePage"));
		model.addAttribute("pageShow", map.get("pageShow"));
		model.addAttribute("pageDevide", map.get("pageDevide"));
		model.addAttribute("pageDevideCount", map.get("pageDevideCount"));
		model.addAttribute("keyword", map.get("keyword"));		
		model.addAttribute("list", (List<BoardVo>)map.get("list"));
		
//		System.out.println(((List<BoardVo>)map.get("list")).size());
//		System.out.println(map.get("list").getClass().getName());
//		List<BoardVo> list =  map.get("list");
		
		return "board/list";
	}
	
}
