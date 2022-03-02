package com.poscoict.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public List<GuestbookVo> findAll(){
		return sqlSession.selectList("guestbook.findAll");
	}
	
	public int insert(GuestbookVo vo) {
		return sqlSession.insert("guestbook.insert", vo);
	}
	
	public Long cntAll(){
		return sqlSession.selectOne("guestbook.cntAll");
	}
	
	public int delete(Long no, String password) {
		Map<String, Object>map = new HashMap<>();
		map.put("no", no);
		map.put("password", password);
		return sqlSession.delete("guestbook.delete", map);
	}

	public List<GuestbookVo> scroll(Long no) {
		return sqlSession.selectList("guestbook.scroll", no);
	}
}
