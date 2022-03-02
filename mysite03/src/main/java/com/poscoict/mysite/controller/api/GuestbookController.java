package com.poscoict.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poscoict.mysite.dto.JsonResult;
import com.poscoict.mysite.service.GuestbookService;
import com.poscoict.mysite.vo.GuestbookVo;


// 같은 이름 충돌 방지
@RestController("/guestApiController")
@RequestMapping("/api/guestbook")
public class GuestbookController {

	@Autowired
	private GuestbookService guestbookService;
	
	@PostMapping("/add") // ternalling이 되지 않도록 url setting 필요
	public JsonResult ex01(@RequestBody GuestbookVo vo) {
		guestbookService.addMessage(vo);
		vo.setPassword("");		
		return JsonResult.success(vo); 
	}
	
	@GetMapping("/list")
	public JsonResult ex02(@RequestParam(value="sn", required = true, defaultValue = "-1") Long no) {

		System.out.println("====================================");
		System.out.println(no);
		System.out.println("====================================");
		
		List<GuestbookVo> list = guestbookService.findAll(no);
//		List<GuestbookVo> list = guestbookService.getMessageList();
		return JsonResult.success(list); 
	}
	
	@RequestMapping("/delete/{no}")
	public JsonResult ex03(
			@PathVariable("no") Long no,
			@RequestParam(value="password", required = true, defaultValue = "") String password) {
		
		Boolean result = guestbookService.deleteMessage(no, password);
		
		// default
		Long data = 0L;
		
		if(result) {
			// 2. 삭제가 잘 된 경우
			data = no;
		} else {
			// 1. 삭제가 안된 경우 (비밀번호 오류)
			data = -1L;
		}
				
		return JsonResult.success(data);
	}
	
}
