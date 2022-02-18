package com.poscoict.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.mysite.vo.GalleryVo;

@Repository
public class GalleryRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public Boolean insert(GalleryVo galleryVo) {
		return 1 == sqlSession.insert("gallery.insert", galleryVo);
	}
	
	public Boolean delete(Long no) {
		return 1 == sqlSession.delete("gallery.delete", no);
	}
	
	public List<GalleryVo> findGallery() {
		return sqlSession.selectList("gallery.findGallery");
	}
	
}
