package com.poscoict.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.mysite.vo.SiteVo;

@Repository
public class SiteRepository {

	@Autowired
	private SqlSession sqlSession;

	public SiteVo findSite() {
		return sqlSession.selectOne("site.findSite");
	}
	
	public boolean update(SiteVo vo) {
		return 1 == sqlSession.insert("site.update", vo);
	}
}
