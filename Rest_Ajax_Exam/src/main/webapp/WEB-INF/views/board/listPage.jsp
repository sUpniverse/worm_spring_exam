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
			<td><a href='/board/readPage${pageMaker.makeQuery(pageMaker.cri.page)}&bno=${board.bno}'>
			       ${board.title}</a></td>
			<td>${board.writer}</td>
			<td><fmt:formatDate pattern="yyyy-mm-dd HH:mm" value="${board.regdate}" /> </td>
			<td><span class="badge bg-red">${board.viewcnt}</span></td>
		</tr>	`
		</c:forEach>
	</table>
	
	<div class="text-center">	
		<ul class="pagination">
			<form id="jobForm">
				<input type="hidden" name="page" value="${pageMaker.cri.page}">
				<input type="hidden" name="perPageNum" value="${pageMaker.cri.perPageNum}">
			</form>
			<c:if test="${pageMaker.prev}">
				<li><a href="${pageMaker.startPage - 1}">&laquo;</a></li>				
			</c:if>
			
			<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="idx">
				<li <c:out value="${pageMaker.cri.page == idx?'class = active':''}" />>
				<a href="${idx}">${idx}</a>
				</li>				
			</c:forEach>
			
			<c:if test="${pageMaker.next && pageMaker.endPage > 0 }">
				<li><a href="${pageMaker.endPage +1}">&raquo;</a></li>
			</c:if>
		</ul>	
	</div>
	
	
<%@include file="../include/footer.jsp" %>

<script>
	var result = '${msg}'
	
	if(result == "success") {
		alert("처리가 완료되었습니다.");
	}
	
	$(".pagination li a").on("click",function(event) {
		event.preventDefault();
		
		var targetPage = $(this).attr("href");
		
		var jobForm = $("#jobForm");
		jobForm.find("[name='page']").val(targetPage);
		jobForm.attr("action","/board/listPage").attr("method","get");
		jobForm.submit();
	});
</script>
