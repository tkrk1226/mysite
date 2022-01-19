package com.poscoict.mysite.vo;

public class PageVo {

	private int pageDivide = 5; // 한 페이지 내에서 보여주는 게시글 개수
	private int pageShow = 5;   // 한 번에 보여주는 페이지 개수
	private int boardCount = -1; // 총 게시글 개수  
	private int pageCount = -1; // 총 페이지 개수
	private int pageDevideCount = 0; // 현재 속한 페이지를 pageShow로 나눈 값 , 1 - 5 : 1 , 6 - 10 : 2 , currentpage에서 - 1을 하고 show로 나눈 몫을 가지게...
	private int currentPage = -1; // 현재 페이지 
	private int nextPage = -1;  // 다음 페이지
	private int prePage = -1;   // 전 페이지
	
	private String keyword = "";

	public int getPageDivide() {
		return pageDivide;
	}

	public void setPageDivide(int pageDivide) {
		this.pageDivide = pageDivide;
	}

	public int getPageShow() {
		return pageShow;
	}

	public void setPageShow(int pageShow) {
		this.pageShow = pageShow;
	}

	public int getBoardCount() {
		return boardCount;
	}

	public void setBoardCount(int boardCount) {
		this.boardCount = boardCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageDevideCount() {
		return pageDevideCount;
	}

	public void setPageDevideCount(int pageDevideCount) {
		this.pageDevideCount = pageDevideCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "PageVo [pageDivide=" + pageDivide + ", pageShow=" + pageShow + ", boardCount=" + boardCount
				+ ", pageCount=" + pageCount + ", pageDevideCount=" + pageDevideCount + ", currentPage=" + currentPage
				+ ", nextPage=" + nextPage + ", prePage=" + prePage + ", keyword=" + keyword + "]";
	}
}
