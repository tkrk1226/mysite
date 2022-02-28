<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${ pageContext.request.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${ pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript">
$(function(){
	
	$("#join-form").submit(function(event){
		event.preventDefault(); // submit 안되게 방지
		
		// 이름 유효성 체크(empty) 체크
		if($("#name").val() === ''){
			alert('이름이 비어 있습니다.');
			$("#name").focus();
			return;
		}
		
		// 2. 이메일 유효성(empty) 체크
		if($("#email").val() === ''){
			alert('이메일이 비어 있습니다.');
			$("#email").focus();
			return;
		}
		
		// 3. 중복체크 유무 *** (hide, show 여부 확인)
		if($("#img-checkemail").css('display') == 'none'){
			alert('중복체크를 해주세요.');
		};
		
		// 4. 비밀번호 유효성 체크
		if($("#password").val() === ''){
			alert('비밀번호가 비어 있습니다.');
			$("#password").focus();
			return;
		}
		
		// 5. 유효성 OK
		console.log("OK!!!");
		//$("#join-form")[0].submit();
	});
	
	$("#email").change(function(){
		$("#img-checkemail").hide();
		$("#btn-checkemail").show();
	})
	
	
	$("#btn-checkemail").click(function(){
		
		var email = $("#email").val();
		
		if(email == ''){
			return;
		}
		
		$.ajax({
			url : "${pageContext.request.contextPath}/user/api/checkemail?email=" + email,
			type: "get",
			dataType: "json",
			success : function(response){
				if(response.result !== 'success'){
					console.error(response.message);
					return;
				}
				
				if(response.data){ // data가 true이므로...
					alert("존재하는 이메일입니다. 다른 이메일을 사용해주세요.");
					$("#email")
						.val('') // 중복이메일의 경우 날려버림
						.focus();
					return;
				}
				
				$("#img-checkemail").show();
				$("#btn-checkemail").hide();
				
			},
			error : function(xhr, status, e){
				console.error(status, e);
			}
		});
	});
});


</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="user">
				<form:form
				
					modelAttribute="userVo"
				
					id="join-form" 
					name="joinForm" 
					method="post" 
					action="${ pageContext.request.contextPath }/user/join">
					
					<label class="block-label" for="name">이름</label>
					<form:input path="name"/>
					
					<p style="text-align:left; padding-left:0; color:#f00">
					<spring:hasBindErrors name="userVo">
					   <c:if test="${errors.hasFieldErrors('name')}">
					   		<spring:message code="${errors.getFieldError('name').codes[0]}" />
					   </c:if>
					</spring:hasBindErrors>
					</p>
				
					<label class="block-label" for="email">이메일</label>
					<form:input path="email"/>
					<input type="button" id="btn-checkemail" value="중복체크">
					<img id="img-checkemail" src="${ pageContext.request.contextPath }/assets/images/check.png" style="width:16px; display:none">
					<p style="text-align:left; padding-left:0; color:#f00">
						<form:errors path="email" />
					</p>
					
					<label class="block-label"><spring:message code="user.join.label.password" /></label>
					<form:password path="password"/>
					<p style="text-align:left; padding-left:0; color:#f00">
						<form:errors path="password" />
					</p>
					
					
					<fieldset>
						<legend>성별</legend>
						<form:radiobutton path="gender" value="female" label="여" />
						<form:radiobutton path="gender" value="male" label="남" />
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">
					
				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>