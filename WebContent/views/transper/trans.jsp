<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>OSF 번역</title>
</head>
<body>
<script>
	function check(){
		var selectObj1 = document.querySelector('#source');
		var selectObj2 = document.querySelector('#target');
		if(selectObj1.value==selectObj2.value){
			alert('번역 하는 언어와 번역 되는 언어가 같습니다.\n다시 선택해주세요.');
			return false;
		} 
		var textObj = document.querySelector('#text');
		if(textObj.value.length>=100){
			alert('번역 할 내용은 100글자 이상일 수 없습니다.');
			return false;
		}
		return true;
	}
</script>
<c:if test="${rMap.isError=='true'}">
	<script>
		alert("${rMap.msg}");
	</script>				
</c:if>
<form method="post" action="/trans" onsubmit="return check()">
<table border="1">
	<tr>
		<th>번역 하는 언어</th>
		<td>
			<select name="source" id="source">
				<c:forEach items="${ccList}" var="cc">
					<option value="${cc.ccCode}"
					<c:if test="${cc.ccCode==param.source}">
					selected
					</c:if>
					>${cc.ccName}</option>
				</c:forEach>
			</select>
		</td>
		<td rowspan="2"><button>번역하기</button></td>
		<th>번역 되는 언어</th>
		<td>
			<select name="target" id="target">
				<c:forEach items="${ccList}" var="cc">
					<option value="${cc.ccCode}"
					<c:if test="${cc.ccCode==param.target}">
					selected
					</c:if>
					>${cc.ccName}</option>
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<textarea name="text" id="text">${param.text}</textarea>
		</td>
		<td colspan="2">
			<textarea>
				<c:if test="${rMap.isError!='true'}">
					${rMap.msg}
				</c:if>
			</textarea>
		</td>
	</tr>
</table>
</form>
</body>
</html>