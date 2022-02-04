package com.poscoict.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.poscoict.mysite.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 1. handler 종류 확인
		if (handler instanceof HandlerMethod == false) {
			return true; // css, ... 등은 검사할 필요 없이 그냥 지나가게
		}

		// 2. casting (default servlet handler가 아닌 method handler로 올 수 있게)
		// 물론 assets에 들어가지 않게 해놔서 default servlet handler가 들어오지는 않지만
		// 어떤 상황이 생길지 모름

		HandlerMethod handlerMethod = (HandlerMethod) handler;

		// 3. Handler Method의 @Auth 받아오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);

		// 4. Handler Method @Auth가 없다면 Type에 있는지 확인(과제)
		if (auth == null) {
//			auth = handlerMethod.getBeanType().getAnnotation(Auth.class);
			auth = handlerMethod
					.getMethod()
					.getDeclaringClass()
					.getAnnotation(Auth.class);
		}

		// 5. type과 method에 @Auth 가 적용이 안되어 있는 경우
		if (auth == null) {
			return true;
		}

		// auth 가져오기
//		String getAuth = auth.role();
//		System.out.println("getAuth : " + getAuth);

		// 5. @Auth가 적용이 되어 있기 때문에 인증(Authentication) 여부 확인
		HttpSession session = request.getSession();
		if (session == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}

		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}

//		if ("ADMIN".equals(auth.role())) {
//			if (!"ADMIN".equals(authUser.getRole())) {
//				response.sendRedirect(request.getContextPath());
//				return false;
//			}
//		}
		
//		if("ADMIN".equals(authUser.getRole())) {
//			return true;
//		}
//		
//		if (!auth.role().equals(authUser.getRole())) {
//			response.sendRedirect(request.getContextPath());
//			return false;
//		}
		
//		 7. 권한(Authorization) 체크를 위해서 @Auth의 role 가져오기("USER", "ADMIN")
		String role = auth.role();
		
//		 8. @Auth의 role이 "USER" 인 경우, authUser의 role은 상관이 없다.
		if ("USER".equals(role)) {
			return true;
		}
		
//		 9. @Auth의 role이 "ADMIN" 인 경우, authUser은 "ADMIN" 이어야 한다.
		if("ADMIN".equals(authUser.getRole()) == false) {
			response.sendRedirect(request.getContextPath());
			return false;
		}

//		 10. 옳은 관리자
//		 @Auth의 role : "ADMIN"
//	     authUser의 role : "ADMIN" 
		return true;
	}
}
