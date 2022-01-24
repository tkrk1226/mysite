//package com.poscoict.mysite.repository;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import com.poscoict.mysite.vo.BoardVo;
//
//public class BoardRepositoryTest {
//
//	public static void main(String[] args) {
//		findMapTest();
//		
//	}
//	
//	public static void findMapTest() {
//		Map<String, Object> map = new HashMap<>();
//		
//		BoardRepository boardRepo = new BoardRepository();
//		
//		map.put("list", boardRepo.findAll());
//		
//		System.out.println("map list : " + map.get("list"));
//		System.out.println("Class Name : " + map.get("list").getClass().getName());
//		
//		System.out.println(map.get("list"));
//		System.out.println(map.get("list").getClass().getName());
//		List<BoardVo> list = (List<BoardVo>) map.get("list");
//		
//		
//		for(BoardVo vo : map.get("list")) {
//			
//		}
//		
//		BoardVo boardVo01 = new BoardVo();
//		boardVo01.setUserNo(1L);
//		boardVo01.setTitle("안녕");
//		boardVo01.setContents("우리");
//		boardVo01.setHit(0);
//		boardVo01.setGroupNo(1);
//		boardVo01.setOrderNo(1);
//		boardVo01.setDepth(1);
//		boardVo01.setRegDate("2022-01-24");
//		boardVo01.setUserNo(1L);
//		boardVo01.setUserName("의진");
//		System.out.println(boardVo01);
//		map.put("boardVo", boardVo01);
//	}
//	
//	
//	
//}
