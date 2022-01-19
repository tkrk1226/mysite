package com.poscoict.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.BoardDao;
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
		
		
		request.setAttribute("list", new BoardDao().findAll());
		MvcUtil.forward("board/list", request, response);
	}

}
