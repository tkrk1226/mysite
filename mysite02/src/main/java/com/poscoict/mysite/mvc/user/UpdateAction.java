package com.poscoict.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscoict.mysite.dao.UserDao;
import com.poscoict.mysite.vo.UserVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*접근 제어*/
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if(authUser == null) {
			MvcUtil.redirect(request.getContextPath() + "/user?a=loginform", request, response);
			return;
		} 
		else {
			UserDao dao = new UserDao();
			UserVo vo = new UserVo();
//					dao.findByNo(authUser.getNo());			
		
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			String password = request.getParameter("password");
			
			vo.setNo(authUser.getNo());
			vo.setName(name);
//			authUser.setName(name);
			session.setAttribute("authUser", vo);

			vo.setGender(gender);
			if(password.isBlank() == false) {
				vo.setPassword(password);
			}
			
			dao.update(vo);

			// 논리적으로 말이 안되는 상황인데?
			
			MvcUtil.redirect(request.getContextPath(), request, response);
		}
	}
}
