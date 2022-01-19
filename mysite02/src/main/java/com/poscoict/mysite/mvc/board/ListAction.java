package com.poscoict.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.PageVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		BoardDao dao = new BoardDao();
		PageVo pageVo = new PageVo();

		String kwd = request.getParameter("kwd");
		String strPageNum = request.getParameter("pageNum");

		if (kwd != null && (!kwd.isBlank())) {
			pageVo.setKeyword(kwd);
		}

		int boardCount = dao.boardCnt(pageVo.getKeyword());
		int pageCount = boardCount / pageVo.getPageDivide();
		if (boardCount % pageVo.getPageDivide() >= 1) {
			pageCount++;
		}

		pageVo.setBoardCount(boardCount);
		pageVo.setPageCount(pageCount);

		if (strPageNum == null || strPageNum.isBlank()) {
			pageVo.setCurrentPage(1);
			if (pageVo.getCurrentPage() >= pageVo.getPageCount()) {
				pageVo.setNextPage(-1);
			} else {
				pageVo.setNextPage(2);
			}
			pageVo.setPrePage(-1);
			pageVo.setPageDevideCount(0);

		} else {
			int pageNum = Integer.parseInt(strPageNum);

			if (pageNum > pageVo.getPageCount()) {
				pageNum = pageVo.getPageCount();
			} else if (pageNum < 0) {
				pageNum = 1;
			}

			pageVo.setCurrentPage(pageNum);

			if (pageNum == pageVo.getPageCount()) {
				pageVo.setNextPage(-1);
			} else {
				pageVo.setNextPage(pageNum + 1);
			}

			if (pageNum == 1) {
				pageVo.setPrePage(-1);
			} else {
				pageVo.setPrePage(pageNum - 1);
			}

			pageVo.setPageDevideCount((pageNum - 1) / pageVo.getPageShow());
		}

		request.setAttribute("list", dao.findKwd(pageVo.getKeyword(),((pageVo.getCurrentPage() - 1) * pageVo.getPageDivide()), pageVo.getPageDivide()));
		request.setAttribute("page", pageVo);
		MvcUtil.forward("board/list", request, response);
	}

}
