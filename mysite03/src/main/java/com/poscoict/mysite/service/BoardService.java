package com.poscoict.mysite.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.mysite.repository.BoardRepository;
import com.poscoict.mysite.vo.BoardVo;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;

	// 새글, 답글 달기
	public Boolean addContents(BoardVo vo) {
		
		if(vo.getGroupNo() != null) {
			increaseGroupOrderNo(vo);
		}
		
		return boardRepository.insert(null, null, null, 0, 0, 0);
	}
	
	// 글보기
	public BoardVo getContents(Long no){
		return null;
	}
	
	// 글 수정 하기 전 
	public BoardVo getContents(Long no, Long userNo){
		return null;
	}
	
	// 글 수정
	public Boolean updateContents(BoardVo vo) {
		return false;
	}
	
	// 글 삭제
	public Boolean deleteContents(Long no, Long userNo) {
		return false;
	}

	// 글 리스트(찾기 결과와도 동일)
	public Map<String, Object> getContentsList(int currentPage, String keyword) {
		Map<String, Object>map = new HashMap<>();
		
		map.put("list", null);
		map.put("totalCount", 0);
		map.put("...", map);
		
		return map;
	}
	
	
	private boolean increaseGroupOrderNo(BoardVo vo) {
		return false;
	}
	
}
