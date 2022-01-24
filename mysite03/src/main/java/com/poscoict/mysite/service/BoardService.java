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

		if (vo.getGroupNo() != null) {
			increaseGroupOrderNo(vo);
		}

		return boardRepository.insert(null, null, null, 0, 0, 0);
	}

	// 글보기
	public BoardVo getContents(Long no) {
		return null;
	}

	// 글 수정 하기 전
	public BoardVo getContents(Long no, Long userNo) {
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
		Map<String, Object> map = new HashMap<>();

		int limit = 5;
		int pageShow = 10;
		int boardCount = boardRepository.boardCnt(keyword);
		int pageCount = boardCount / limit;
		if (boardCount % limit >= 1) {
			pageCount++;
		}
		if (currentPage > pageCount) {
			currentPage = pageCount;
		} else if (currentPage < 0) {
			currentPage = 1;
		}
		int nextPage = -1;
		int prePage = -1;
		if (currentPage != pageCount) {
			nextPage = currentPage + 1;
		}
		if (currentPage != 1) {
			prePage = currentPage - 1;
		}
		int pageDevideCount = (currentPage - 1) / pageShow;

		map.put("currentPage", currentPage);
		map.put("boardCount", boardCount);
		map.put("pageCount", pageCount);
		map.put("nextPage", nextPage);
		map.put("prePage", prePage);
		map.put("pageShow", pageShow);
		map.put("pageDevide", limit);
		map.put("pageDevideCount", pageDevideCount);
		map.put("keyword", keyword);
		map.put("list", boardRepository.findKwd(keyword, (currentPage - 1) * pageDevideCount, limit));

		return map;
	}

	private boolean increaseGroupOrderNo(BoardVo vo) {
		return false;
	}

}
