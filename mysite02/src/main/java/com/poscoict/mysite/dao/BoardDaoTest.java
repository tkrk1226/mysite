package com.poscoict.mysite.dao;

import java.util.ArrayList;
import java.util.List;

import com.poscoict.mysite.vo.BoardVo;

public class BoardDaoTest {
	public static void main(String[] args) {
		testFindKwd();
	}
	
	public static void testFindKwd() {
		BoardDao dao = new BoardDao();
		List<BoardVo> list = new ArrayList<>();
		list = dao.findKwd("", 0, 5);
		
		for(BoardVo vo : list) {
			System.out.println(vo);
		}
	}
}
