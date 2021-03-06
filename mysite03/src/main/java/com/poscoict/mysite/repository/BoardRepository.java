package com.poscoict.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.mysite.vo.BoardVo;

@Repository
public class BoardRepository {

	@Autowired
	private SqlSession sqlSession;

	public List<BoardVo> findAll() {
		return sqlSession.selectList("board.findAll");
	}

	public int insert(BoardVo vo) {
		return sqlSession.insert("board.insert", vo);
	}
	
	public int delete(Long no) {
		return sqlSession.delete("board.delete", no);
	}

	public BoardVo findByNo(Long no) {
		return sqlSession.selectOne("board.findByNo", no);
	}

	public boolean update(BoardVo vo) {
		int count = sqlSession.update("board.update", vo);
		return count == 1;
	}

	public boolean updateHit(int hit, Long no) {
		Map<String, Object>map = new HashMap<>();
		map.put("hit", hit);
		map.put("no", no);
		int count = sqlSession.update("board.updateHit", map);
		return count == 1;
	}
	
	public boolean updateBeforeAdd(Integer groupNo, Integer orderNo) {
		Map<String, Object>map = new HashMap<>();
		map.put("groupNo", groupNo);
		map.put("orderNo", orderNo);
		int count = sqlSession.update("board.updateBeforeAdd", map);
		return count == 1;
	}
	
	public int boardCnt(String keyword) {
		return sqlSession.selectOne("board.boardCnt", keyword);
	}

	public List<BoardVo> findKwd(String keyword, Integer offset, Integer limit) {
		Map<String, Object>map = new HashMap<>();
		map.put("offset", offset);
		map.put("limit", limit);
		map.put("keyword", keyword);
		return sqlSession.selectList("board.findKwd", map); 
	}
}
