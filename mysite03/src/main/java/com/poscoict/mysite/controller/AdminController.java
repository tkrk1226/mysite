package com.poscoict.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscoict.mysite.security.Auth;
import com.poscoict.mysite.service.FileUploadService;
import com.poscoict.mysite.service.SiteService;
import com.poscoict.mysite.vo.SiteVo;

//@Auth
@Auth(role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private SiteService siteService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private ServletContext servletContext; 
	
	@RequestMapping(value={"", "/main"}, method=RequestMethod.GET)
	public String main(Model model) {
		SiteVo vo = siteService.getSite();
		model.addAttribute("siteVo", vo);
		return "admin/main";
	}	

	@RequestMapping(value="/main/update", method=RequestMethod.POST)
	public String updateMain(SiteVo vo, 
			@RequestParam(value="inputFile") MultipartFile multipartFile) {
		String url = fileUploadService.restore(multipartFile);
		vo.setProfile(url);
		if(siteService.updateSite(vo)) {
			servletContext.setAttribute("siteVo", siteService.getSite());
		}
		return "redirect:/admin/main";
	}
	
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}

	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
	
}
