package com.poscoict.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.UserVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* 접근 제어 */
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		if (authUser == null) {
			MvcUtil.redirect(request.getContextPath() + "/user?a=loginform", request, response);
			return;
		}

		// 게시글 본인 수정
		String strNo = request.getParameter("no");
		BoardVo vo = null;
		BoardDao dao = new BoardDao();
		if (strNo != null) {
			vo = dao.findByNo(Long.parseLong(strNo));
		}

		if (authUser.getNo() == vo.getUserNo()) {
			dao.delete(vo.getNo());
		}
		MvcUtil.redirect(request.getContextPath() + "/board", request, response);

	}
}
