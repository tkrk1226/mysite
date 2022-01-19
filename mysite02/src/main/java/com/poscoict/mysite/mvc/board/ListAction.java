package com.poscoict.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.PageVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		BoardDao dao = new BoardDao();
		PageVo pageVo = (PageVo)session.getAttribute("page");
		
//		if(pageVo == null) {
			pageVo = new PageVo();	
//		}
		
		String kwd = request.getParameter("kwd");
		String strPageNum = request.getParameter("pageNum");
		
		if (kwd != null && (!kwd.isBlank())) {
			System.out.println(kwd);
			pageVo.setKeyword(kwd);
		}

		int boardCount = dao.boardCnt(pageVo.getKeyword());
		int pageCount = boardCount / pageVo.getPageDivide();
		if(boardCount % pageVo.getPageDivide() >= 1) {
			pageCount++;
		}
		
		pageVo.setBoardCount(boardCount);
		pageVo.setPageCount(pageCount);
		
		if (strPageNum == null) {
			pageVo.setCurrentPage(1);
			pageVo.setNextPage(2);
			pageVo.setPrePage(-1);
			pageVo.setPageDevideCount(0);
		} else {
			int pageNum = Integer.parseInt(strPageNum);
			
			if(pageNum > pageVo.getPageCount()) {
				pageNum = pageVo.getPageCount();
			}

			pageVo.setCurrentPage(pageNum);
			
			if(pageNum == pageVo.getPageCount()) {
				pageVo.setNextPage(-1);
			} else {
				pageVo.setNextPage(pageNum + 1);
			}
			
			if(pageNum == 1) {
				pageVo.setPrePage(-1);
			} else {
				pageVo.setPrePage(pageNum - 1);
			}

			pageVo.setPageDevideCount((pageNum-1) / pageVo.getPageShow());
		}
		
		System.out.println(pageVo);
		
		request.setAttribute("list", dao.findKwd(pageVo.getKeyword(), ((pageVo.getCurrentPage() - 1) * pageVo.getPageDivide()) , pageVo.getPageDivide()));
		session.setAttribute("page", pageVo);
		
		MvcUtil.forward("board/list", request, response);
	}

}
