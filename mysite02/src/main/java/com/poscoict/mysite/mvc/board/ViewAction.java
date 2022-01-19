package com.poscoict.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class ViewAction implements Action {
	// one day, 24 * 60 * 60		
	private static final int COOKIE_LIFETIME = 60; // 60 seconds
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String strNo = request.getParameter("no");
		if(strNo != null) {
			Long no = Long.parseLong(strNo);
			BoardDao dao = new BoardDao();
			BoardVo vo = dao.findByNo(no);
			request.setAttribute("view", vo);
			
			Cookie[] cookies = request.getCookies();
			Cookie viewCookie = null;
					
			if(cookies != null && cookies.length > 0) {
				for(Cookie cookie : cookies) {
					if(strNo.equals(cookie.getName())) {
						viewCookie = cookie;
						break; // 다른 쿠키는 관심이 없기 때문
					}
				}
			}
			
			if(viewCookie == null) {
				Cookie cookie = new Cookie(strNo, String.valueOf((vo.getHit() + 1)));
				cookie.setPath(request.getContextPath());
				cookie.setMaxAge(COOKIE_LIFETIME); 	
				response.addCookie(cookie);
				dao.updateHit(vo.getHit() + 1, no);
			}
			
			MvcUtil.forward("board/view", request, response);	
		} else {
			MvcUtil.redirect(request.getContextPath() + "/board", request, response);
		}
	}
}
