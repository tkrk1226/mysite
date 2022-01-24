<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% pageContext.setAttribute("newline", "\n"); %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.servletContext.contextPath}/board" method="post">
					<input type="text" id="keyword" name="keyword" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>				
					<c:set var="count" value="${boardCount}" />
					<c:forEach items="${list}" var="vo" varStatus="status">
					<tr>       
						<td>${count - status.index - pageDevide * (currentPage - 1)}</td>
						<td style="text-align:left; padding-left:${(vo.depth - 1) * 20}px">
							<c:if test="${vo.orderNo > 1}">
								<img src="${pageContext.servletContext.contextPath }/assets/images/reply.png" />
							</c:if>
							<a href="${pageContext.servletContext.contextPath }/board/view?no=${vo.no}">${vo.title }</a>
						</td>
						<td>${vo.userName}</td>
						<td>${vo.hit }</td>
						<td>${vo.regDate }</td>
						<c:if test="${vo.userNo eq authUser.no }">
						<td><a href="${pageContext.servletContext.contextPath }/board/delete?no=${vo.no}" class="del" style="background-image:url('${pageContext.servletContext.contextPath }/assets/images/recycle.png')">삭제</a></td>
						</c:if>
					</tr>
					</c:forEach> 

				</table>
				
				<!-- pager 추가 -->
				
				<c:choose>
					<c:when test="${pageCount <= (pageDevideCount * pageShow + pageShow)}">
						<c:set var="end" value="${pageCount}" />
					</c:when>
					<c:otherwise>
						<c:set var="end" value="${pageDevideCount * pageShow + pageShow}" />
					</c:otherwise>
				</c:choose>
								
				<div class="pager">
					<ul>
						<c:if test="${prePage ne -1}">
						<li><a href="${pageContext.servletContext.contextPath }/board?currentPage=${prePage}&keyword=${keyword}">◀</a></li>
						</c:if>
						<c:forEach var="var" begin="${1 + pageDevideCount * pageShow}" end="${end}" step="1" varStatus="status">
							<c:choose>
								<c:when test="${ var == currentPage}">
								<li class="selected">
								</c:when>
								<c:otherwise>
									<li>
								</c:otherwise>
							</c:choose>
								<a href="${pageContext.servletContext.contextPath }/board?currentPage=${status.count + pageDevideCount * pageShow}&keyword=${keyword}">${status.count + pageDevideCount * pageShow}</a>
							</li>
						</c:forEach>
						<c:if test="${nextPage ne -1}">
						<li><a href="${pageContext.servletContext.contextPath }/board?currentPage=${nextPage}&keyword=${keyword}">▶</a></li>
						</c:if>
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<div class="bottom">
					<c:if test="${not empty authUser}">
					<a href="${pageContext.servletContext.contextPath }/board/write" id="new-book">글쓰기</a>
					</c:if>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>