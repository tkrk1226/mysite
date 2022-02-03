package com.poscoict.mysite.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.poscoict.mysite.repository.SiteRepository;
import com.poscoict.mysite.vo.SiteVo;

@Service
public class SiteService {
		
	@Autowired
	private SiteRepository siteRepository;

	@Autowired
	private ServletContext servletContext; 

	private static final String SAVE_PATH = "/upload-images";
	private static final String URL_BASE = "/images";
	
	public SiteVo getSite() {
		return siteRepository.findSite();
	}
	
	public Boolean updateSite(SiteVo vo, MultipartFile multipartFile) {
		
		String url = restore(multipartFile);
		
		if(url != null) {
			vo.setProfile(url);
		}
		
		if(siteRepository.update(vo)) {
			servletContext.setAttribute("siteVo", siteRepository.findSite());
			return true;
		} else {
			return false;
		}
	}
	
	public String restore(MultipartFile multipartFile) {
		String url = null;
		
		try {
		if(multipartFile.isEmpty()) {
			return url;
		}
		
		String originFileName = multipartFile.getOriginalFilename();
		String extName = originFileName.substring(originFileName.lastIndexOf('.') + 1); // jpg (확장자만)
		String saveFileName = generateSaveFileName(extName);
		long fileSize = multipartFile.getSize();
		System.out.println("# originFileName : " + originFileName);
		System.out.println("# fileSize : " + fileSize);
		System.out.println("# saveFileName : " + saveFileName);
		
		byte[] data = multipartFile.getBytes(); // error catch
		
		OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFileName);
		os.write(data);
		os.close();
		
		url = URL_BASE + "/" + saveFileName;
		
		} catch(IOException ex) {
			throw new RuntimeException("file upload error : " + ex);
		}		
		return url;
	}

	private String generateSaveFileName(String extName) {
		String filename = "";
		Calendar calendar = Calendar.getInstance(); // new 필요하지 않음

		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += "." + extName;
		
		return filename;
	}
	
}
