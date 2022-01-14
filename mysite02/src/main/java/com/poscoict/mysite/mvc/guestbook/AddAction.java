package com.poscoict.mysite.mvc.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.GuestbookDao;
import com.poscoict.mysite.vo.GuestbookVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class AddAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
		String content = request.getParameter("content");
		
		GuestbookDao dao = new GuestbookDao();
		GuestbookVo vo = new GuestbookVo();
		
		vo.setName(name);
		vo.setPassword(pass);
		vo.setMessage(content);
		
		boolean result = dao.insert(vo);
		System.out.println(result ? "success" : "fail");
		MvcUtil.redirect(request.getContextPath() + "/guestbook", request, response);
	}
}
