package com.poscoict.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.mysite.repository.GuestbookRepository;
import com.poscoict.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {

	@Autowired
	private GuestbookRepository guestbookRepository;
	
	public List<GuestbookVo> getMessageList(){
		
		return null;
	}
	
	public Boolean deleteMessage(Long no, String password) {
	
		return false;
	}
	
	public Boolean addMessage(GuestbookVo vo) {
		
		return false;
	}
	
}
