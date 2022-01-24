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
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@RequestMapping("")
	public String index(Model model,
			@RequestParam(value = "currentPage", required = true, defaultValue = "1") int currentPage,
			@RequestParam(value = "keyword", required = true, defaultValue = "") String keyword) {
		Map<String, Object> map = boardService.getContentsList(currentPage, keyword);

		model.addAllAttributes(map);

		return "board/list";
	}

	@RequestMapping("/delete/{no}")
	public String delete(HttpSession session, @PathVariable("no") Long boardNo,
			@RequestParam(value = "currentPage", required = true, defaultValue = "1") Integer page,
			@RequestParam(value = "keyword", required = true, defaultValue = "") String keyword) {
		/* access control */
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/";
		}

		boardService.deleteContents(boardNo, authUser.getNo());
		return "redirect:/board?currentPage=" + page + "&keyword=" + keyword;
	}

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(HttpSession session) {
		/* access control */
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/";
		}
		return "board/write";
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(HttpSession session, BoardVo boardVo) {
		/* access control */
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/";
		}
		System.out.println(boardVo);
		boardVo.setUserNo(authUser.getNo());
		Boolean result = boardService.addContents(boardVo);
		System.out.println(result);
		return "redirect:/board";
	}

	@RequestMapping(value = "/view/{no}")
	public String view(Model model, @PathVariable("no") Long boardNo) {
		BoardVo boardVo = boardService.getContents(boardNo);
		model.addAttribute(boardVo);
		return "board/view";
	}

	@RequestMapping(value = "/update/{no}", method = RequestMethod.GET)
	public String update(Model model, HttpSession session, @PathVariable("no") Long boardNo) {
		/* access control */
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/";
		}
		BoardVo boardVo = boardService.getContents(boardNo, authUser.getNo());
		if (boardVo == null) {
			return "redirect:/board";
		}
		model.addAttribute("boardVo", boardVo);
		return "board/update";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpSession session, BoardVo boardVo) {
		/* access control */
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/";
		}
		boardVo.setUserNo(authUser.getNo());
		System.out.println(boardVo);
		Boolean result = boardService.updateContents(boardVo);
		System.out.println(result);
		return "redirect:/board";
	}

	@RequestMapping(value = "/add/{no}", method = RequestMethod.GET)
	public String add(Model model, HttpSession session, @PathVariable("no") Long boardNo) {
		/* access control */
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/";
		}
		BoardVo boardVo = boardService.getContents(boardNo);
		model.addAttribute("boardVo", boardVo);
		return "board/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpSession session, BoardVo boardVo) {
		/* access control */
		System.out.println("add : " + boardVo);

		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/";
		}
		boardVo.setUserNo(authUser.getNo());
		Boolean result = boardService.addContents(boardVo);
		System.out.println(result);
		return "redirect:/board";
	}

}
