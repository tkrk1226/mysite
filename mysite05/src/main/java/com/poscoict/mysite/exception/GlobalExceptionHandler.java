package com.poscoict.mysite.exception;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poscoict.mysite.dto.JsonResult;

// AOP
@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Log LOGGER = LogFactory.getLog(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public void ExceptionHandler(
			HttpServletRequest request,
			HttpServletResponse response,
			Exception e) throws Exception {
		// 1. 로깅
		// 나중에 String으로 뽑아서 파일로 저장해야한다.
		// 혹은 서비스 론칭 전에 개발용으로 할때는 
		// Error 내용을 뿌리는 것도 나쁘지 않음
		
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		// 추후 로깅을 위함
		LOGGER.error(errors.toString()); 
		
		// 2. 요청 구분
		// JSON 요청인 경우 : request header의 accept에 application/json
		// HTML 요청인 경우 : request header의 accept에 text/html
		
		String accept = request.getHeader("accept");
		
		if(accept.matches(".*application/json.*")) {
			// 3. JSON 응답
			JsonResult result = JsonResult.fail(errors.toString());
			
			// error내용을 메세지에 담아서 전달
			
			// import com.fasterxml.jackson.databind.ObjectMapper;
			String jsonString = new ObjectMapper().writeValueAsString(result); 
			response.setContentType("application/json; charset=UTF-8");
			
			// write 대신에 Byte단위로 쓴다.
			OutputStream os = response.getOutputStream(); // Byte 단위 Stream 따라서 각 관계간 decode, encode가 필요하다.
			os.write(jsonString.getBytes("utf-8"));
			os.close();
			
		} else {
			// 4. 사과페이지(HTML 응답, 정상종료)
			request.setAttribute("exception", errors.toString());
			request
				.getRequestDispatcher("/WEB-INF/views/error/exception.jsp")
				.forward(request, response);
			//model.addAttribute("exception", errors.toString());
			//return "error/exception";
		}
	}
}





