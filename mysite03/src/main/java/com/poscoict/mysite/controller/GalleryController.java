package com.poscoict.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscoict.mysite.security.Auth;
import com.poscoict.mysite.service.GalleryService;
import com.poscoict.mysite.service.SiteService;
import com.poscoict.mysite.vo.GalleryVo;

@Controller
@RequestMapping("/gallery")
public class GalleryController {

	@Autowired
	private SiteService siteService;
	
	@Autowired
	private GalleryService galleryService;
	
	@RequestMapping("")
	public String index(Model model) {
		
		List<GalleryVo> list = galleryService.getImages();
		model.addAttribute("list", list);
		
		return "gallery/index";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping("/delete/{no}")
	public String delete(@PathVariable("no") Long no) {
		galleryService.removeImage(no);
		System.out.println("delete : " + no);
		
		return "redirect:/gallery";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile multipartFile,
			@RequestParam(value="comments", required = true, defaultValue = "") String comments) {
		
		GalleryVo galleryVo = new GalleryVo();
		String url = siteService.restore(multipartFile);
		
		galleryVo.setComments(comments);
		galleryVo.setUrl(url);		
		galleryService.saveImage(galleryVo);
	
		return "redirect:/gallery";
	}
	
}
