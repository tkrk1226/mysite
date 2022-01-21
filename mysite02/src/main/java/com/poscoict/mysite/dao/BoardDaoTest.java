package com.poscoict.mysite.dao;

import java.util.ArrayList;
import java.util.List;

import com.poscoict.mysite.vo.BoardVo;

public class BoardDaoTest {
	public static void main(String[] args) {
//		testFindKwd();
		testInsert();
	}

	private static void testInsert() {

		BoardDao dao = new BoardDao();
		int end = 68;

		for (int i = 0; i < end; i++) {
			String title = "물티슈" + i;
			String content = "대모산" + i;
			dao.insert(title, content, 2L, -1, -1, -1);
		}

	}
	
	public static void testFindKwd() {
		BoardDao dao = new BoardDao();
		List<BoardVo> list = new ArrayList<>();
		list = dao.findKwd("", 0, 5);

		for (BoardVo vo : list) {
			System.out.println(vo);
		}
	}
}
