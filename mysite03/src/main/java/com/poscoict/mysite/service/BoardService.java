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
		if (vo.getNo() != null) {
			BoardVo boardVo = boardRepository.findByNo(vo.getNo());
			vo.setGroupNo(boardVo.getGroupNo());
			vo.setOrderNo(boardVo.getOrderNo());
			vo.setDepth(boardVo.getDepth());
			boardRepository.updateBeforeAdd(vo.getGroupNo(), vo.getOrderNo());
			vo.setOrderNo(boardVo.getOrderNo() + 1);
			vo.setDepth(boardVo.getDepth() + 1);
		}
		return 1 == boardRepository.insert(vo);
	}

	// 글보기
	public BoardVo getContents(Long no) {
		
		BoardVo boardVo = boardRepository.findByNo(no);
		
		if(boardVo != null) {
			boardRepository.updateHit(boardVo.getHit() + 1, no);
		}
		
		return boardVo;
	}

	// 글 수정 하기 전
	public BoardVo getContents(Long no, Long userNo) {
		BoardVo boardVo = boardRepository.findByNo(no);
		if (userNo != boardVo.getUserNo()) {
			boardVo = null;
		}
		return boardVo;
	}

	// 글 수정
	public Boolean updateContents(BoardVo boardVo) {
		return boardRepository.update(boardVo);
	}

	// 글 삭제
	public Boolean deleteContents(Long boardNo, Long userNo) {
		BoardVo vo = boardRepository.findByNo(boardNo);
		if (vo.getUserNo() == userNo) {
			return 1 == boardRepository.delete(boardNo);
		}
		return false;
	}

	// 글 리스트(찾기 결과와도 동일)
	public Map<String, Object> getContentsList(int currentPage, String keyword) {
		Map<String, Object> map = new HashMap<>();

		Integer limit = 10;
		int pageShow = 10;
		int boardCount = boardRepository.boardCnt(keyword);
		int pageCount = boardCount / limit;
		if (boardCount % limit >= 1) {
			pageCount++;
		}
		if (currentPage > pageCount && pageCount > 0) {
			currentPage = pageCount;
		} else if (currentPage <= 0) {
			currentPage = 1;
		}
		int nextPage = -1;
		int prePage = -1;

		if (currentPage != pageCount && pageCount != 0) {
			nextPage = currentPage + 1;
		}
		if (currentPage != 1 && pageCount != 0) {
			prePage = currentPage - 1;
		}

		int pageDevideCount = (currentPage - 1) / pageShow;
		Integer offset = (currentPage - 1) * limit;

		map.put("currentPage", currentPage);
		map.put("boardCount", boardCount);
		map.put("pageCount", pageCount);
		map.put("nextPage", nextPage);
		map.put("prePage", prePage);
		map.put("pageShow", pageShow);
		map.put("pageDevide", limit);
		map.put("pageDevideCount", pageDevideCount);
		map.put("keyword", keyword);
		map.put("list", boardRepository.findKwd(keyword, offset , limit));
		
		return map;
	}

}
