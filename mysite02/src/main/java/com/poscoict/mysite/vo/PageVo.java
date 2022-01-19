package com.poscoict.mysite.vo;

public class PageVo {

	private int pageDivide = 5;
	private int pageCount = -1;
	private int pageDevideCount = 1;
	private int currentPage = -1;
	private int nextPage = -1; // 없으면 -1
	private int startPage = 1;
	private int prePage = -1;
	
	private String keyword = "";
	
	public int getPageDivide() {
		return pageDivide;
	}
	public void setPageDivide(int pageDivide) {
		this.pageDivide = pageDivide;
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
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
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
		return "PageVo [pageDivide=" + pageDivide + ", pageCount=" + pageCount + ", pageDevideCount=" + pageDevideCount
				+ ", currentPage=" + currentPage + ", nextPage=" + nextPage + ", startPage=" + startPage + ", prePage="
				+ prePage + ", keyword=" + keyword + "]";
	}
}
