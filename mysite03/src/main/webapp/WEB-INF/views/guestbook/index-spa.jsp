<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-spa.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">

var scrollCheck = false;

var render = function(vo) { 
	
	var html = 				
	"<li data-no='"+ vo.no + "'>" + 
	"<strong>" + vo.name + "</strong>" + 
	"<p>" + vo.message + "</p>" + 
	"<strong></strong>" + 
	"<a href='' data-no='" + vo.no + "'>삭제</a>" +  
	"</li>";
	
	return html;	
}

var fetch = function(sn){	
	
	// 처음 계산을 위함
	var no = $("#list-guestbook li:last-child").length;
	
	if(no == 0){
		sn = -1;
	}
	
	$.ajax({
		url: '${pageContext.request.contextPath}/api/guestbook/list', //startNo
		type:'get',
		dataType:'json',
		data:"sn="+sn,
		success : function(response){
			if(response.result !== 'success'){
				console.error(response.message);
				return;
			}
			
			for(var i = 0; i < response.data.length; i++){
				var vo = response.data[i];
				var html = render(vo);
				$("#list-guestbook").append(html);
			}
			
			scrollCheck = false;
			
		}
	});
}

$(function(){
	
	$("#add-form").submit(function(event){
		event.preventDefault();
		
		var vo = {};
		vo.name = $("#input-name").val();
		vo.password = $("#input-password").val();
		vo.message = $("#tx-content").val();
				
		$.ajax({
			url: '${pageContext.request.contextPath}/api/guestbook/add',
			type:'post',
			dataType:'json',
			contentType:'application/json',
			data:JSON.stringify(vo), // 통신은 string으로 하지 json 객체로 하는 것이 아님
			success : function(response){
				if(response.result !== 'success'){
					console.error(response.message);
					return;
				}
			
				var html = render(response.data);
				$("#list-guestbook").prepend(html);
				
			},
			complete : function(response){
				$("#input-name").val('');
				$("#input-password").val('');
				$("#tx-content").val('');
			}
		});
	});
	
	var dialogDelete = $("#dialog-delete-form").dialog({
		autoOpen: false,
		modal: true,
		buttons: {
			"삭제": function(){
				var no = $("#hidden-no").val();
				var password = $("#password-delete").val(); 
				var url = "${pageContext.request.contextPath}/api/guestbook/delete/" + no; // + "?" + "password=" + password;
				
				$.ajax({
					url: url,
					type:'delete',
					dataType:'json',
					data:"password="+password,
					success: function(response){

						if(response.result !== 'success'){
							console.error(response.message);
							return;
						}
						
						console.log(response);
						
						// 삭제 안된 경우
						if(response.data == -1){
							// 하위가 아니라 종속임
							$(".validateTips.error").show();
							$("#password-delete").val("").focus();
							return;
						}
						
						// 삭제 된 경우
						$("#list-guestbook li[data-no='" + response.data + "']").remove();
						dialogDelete.dialog('close');
						
					}
				});
			},
			"취소": function(){
				$(this).dialog('close');	
			}
		},
		close: function(){
			$(".validateTips.error").hide();
			$("#password-delete").val("");
			$("#hidden-no").val("");
	
		}
	});
			
	// 글 삭제 버튼 Click 이벤트 처리 (Live Event)
	// click event가 Element에 생길거라는 것을 알려주고 위임
	$(document).on("click", "#list-guestbook li a", function(){
		event.preventDefault();
		var no = $(this).data("no");
		$("#hidden-no").val(no);
		dialogDelete.dialog('open');
	});
	
	// infinite scroll
	$(window).scroll(function(){

		var $window = $(this);
		var $document = $(document);

		var windowHeight = $window.height(); 
		var documentHeight = $document.height(); 
		var scrollTop = $window.scrollTop();

		if(scrollTop + windowHeight + 1 > documentHeight) { // + 10 만큼 피드를 더 불러온다.
			
			//li:last-child
			//$("#list-guestbook li:last-child")
			//console.log(no);
			
			if(scrollCheck){
				return;
			}
		
			var sn = $("#list-guestbook li:last-child").data("no");
			
			console.log("---------scroll-----------");
			console.log(sn);
			console.log("---------scroll-----------");
			
			fetch(sn);
			
		}

	});
	
	fetch();
	
});

</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook">

				</ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<div id="dialog-message" title="" style="display:none">
  				<p></p>
			</div>						
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-spa"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>