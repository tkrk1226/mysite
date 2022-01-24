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
		return guestbookRepository.findAll();
	}
	
	public Boolean deleteMessage(Long no, String password) {
		Boolean result = false;
		if(guestbookRepository.delete(no, password)) {
			result = true;
		}
		return result;
	}
	
	public Boolean addMessage(GuestbookVo vo) {
		Boolean result = false;
		if(guestbookRepository.insert(vo)) {
			result = true;
		}
		return result;
	}
	
}
