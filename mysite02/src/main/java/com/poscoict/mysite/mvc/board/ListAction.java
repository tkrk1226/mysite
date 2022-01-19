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

//		int pageCount = 10;
//		int currentPage = 2;
//		int nextPage = -1; // 없으면 -1
//		int startPage = 3;
//		int prePage = 2; // page객체를 만들던지 map에다가 담아놓고 쓰던지 둘 중 하나를 할 것
//		Map m;
//		m.put();

		HttpSession session = request.getSession(true);
		BoardDao dao = new BoardDao();
		PageVo pageVo = new PageVo();
		
		String kwd = request.getParameter("kwd");
		String pageNum = request.getParameter("");
		
		if (kwd == null || kwd.isBlank()) {
			if (kwd == null) {
				System.out.println("kwd==null : " + (kwd == null));	
			} else {
				System.out.println("kwd.isBlank() : " + kwd.isBlank());
			}
		}
//		pageCnt , findKwd를 만들어서 2개로 다시 처리한다.
		
		pageVo.setPageCount(dao.pageCnt(pageVo.getKeyword()));
		request.setAttribute("list", dao.findKwd(pageVo.getKeyword(), ((pageVo.getStartPage() - 1) * pageVo.getPageDivide()) , pageVo.getPageDivide()));
//		request.setAttribute("list", dao.findKwd(pageVo.getKeyword(), 0, pageVo.getPageDivide()));
		session.setAttribute("page", pageVo);
		
		MvcUtil.forward("board/list", request, response);
	}

}
