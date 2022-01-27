package com.poscoict.mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscoict.mysite.security.Auth;
import com.poscoict.mysite.security.AuthUser;
import com.poscoict.mysite.service.BoardService;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.UserVo;
import com.poscoict.web.util.WebUtil;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@RequestMapping("")
	public String index(Model model,
			@RequestParam(value = "currentPage", required = true, defaultValue = "1") Integer currentPage,
			@RequestParam(value = "keyword", required = true, defaultValue = "") String keyword) {
		Map<String, Object> map = boardService.getContentsList(currentPage, keyword);
		model.addAllAttributes(map);
		return "board/list";
	}

	@Auth
	@RequestMapping("/delete/{no}")
	public String delete(@AuthUser UserVo authUser, @PathVariable("no") Long boardNo,
			@RequestParam(value = "currentPage", required = true, defaultValue = "1") Integer currentPage,
			@RequestParam(value = "keyword", required = true, defaultValue = "") String keyword) {
		boardService.deleteContents(boardNo, authUser.getNo());
		return "redirect:/board?currentPage=" + currentPage + "&keyword=" + WebUtil.encodeURL(keyword, "UTF-8");
	}

	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write() {
		return "board/write";
	}

	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@AuthUser UserVo authUser, BoardVo boardVo,
			@RequestParam(value = "currentPage", required = true, defaultValue = "1") Integer currentPage,
			@RequestParam(value = "keyword", required = true, defaultValue = "") String keyword) {
		boardVo.setUserNo(authUser.getNo());
		boardService.addContents(boardVo);
		return "redirect:/board?currentPage=" + currentPage + "&keyword=" + WebUtil.encodeURL(keyword, "UTF-8");
	}

	@RequestMapping(value = "/view/{no}")
	public String view(Model model, @PathVariable("no") Long boardNo) {
		BoardVo boardVo = boardService.getContents(boardNo);
		model.addAttribute(boardVo);
		return "board/view";
	}

	@Auth
	@RequestMapping(value = "/update/{no}", method = RequestMethod.GET)
	public String update(@AuthUser UserVo authUser, Model model, @PathVariable("no") Long boardNo,
			@RequestParam(value = "currentPage", required = true, defaultValue = "1") Integer currentPage,
			@RequestParam(value = "keyword", required = true, defaultValue = "") String keyword) {

		BoardVo boardVo = boardService.getContents(boardNo, authUser.getNo());
		model.addAttribute("boardVo", boardVo);
		return "board/update";
	}

	@Auth
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@AuthUser UserVo authUser, BoardVo boardVo,
			@RequestParam(value = "currentPage", required = true, defaultValue = "1") Integer currentPage,
			@RequestParam(value = "keyword", required = true, defaultValue = "") String keyword) {

		boardVo.setUserNo(authUser.getNo());
		boardService.updateContents(boardVo);
		System.out.println();
		return "redirect:/board?currentPage=" + currentPage + "&keyword=" + WebUtil.encodeURL(keyword, "UTF-8");
	}

	@Auth
	@RequestMapping(value = "/add/{no}", method = RequestMethod.GET)
	public String add(@AuthUser UserVo authUser, Model model, @PathVariable("no") Long boardNo) {

		BoardVo boardVo = boardService.getContents(boardNo);
		model.addAttribute("boardVo", boardVo);
		return "board/add";
	}

	@Auth
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@AuthUser UserVo authUser, BoardVo boardVo,
			@RequestParam(value = "currentPage", required = true, defaultValue = "1") Integer currentPage,
			@RequestParam(value = "keyword", required = true, defaultValue = "") String keyword) {

		boardVo.setUserNo(authUser.getNo());
		boardService.addContents(boardVo);
		return "redirect:/board?currentPage=" + currentPage + "&keyword=" + WebUtil.encodeURL(keyword, "UTF-8");
	}
}
