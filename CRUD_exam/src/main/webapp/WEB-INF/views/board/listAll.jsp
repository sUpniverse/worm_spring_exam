<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file ="../include/header.jsp" %>
	
	<table class="table table-bordered">
		<tr>
			<th style="width: 10px">BON</th>
			<th>TITLE</th>
			<th>WRITER</th>
			<th>REGDATE</th>
			<th style="width: 40px">VIEWCNT</th>
		</tr>
		
		<c:forEach items="${list}" var="board">
		<tr>
			<td>${board.bno}</td>
			<td><a href='/board/read?bno=${board.bno}'>${board.title}</a></td>
			<td>${board.writer}</td>
			<td><fmt:formatDate pattern="yyyy-mm-dd HH:mm" value="${board.regdate}" /> </td>
			<td><span class="badge bg-red">${board.viewcnt}</span></td>
		</tr>	
		</c:forEach>
	</table>
	
<%@include file="../include/footer.jsp" %>

<script>
	var result = '${msg}'
	
	if(result == "success") {
		alert("처리가 완료되었습니다.");
	}
</script>
