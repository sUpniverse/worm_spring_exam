<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file ="../include/header.jsp" %>

	<div class="box-body">
		<select name="searchType">
			<option value="n"
			<c:out value="${cri.searchType == null? 'selected' :''}" />>
			---</option>
			<option value="t" 
			<c:out value="${cri.searchType eq 't' ? 'selected' : ''}" />>
			Title</option>
			<option value="c" 
			<c:out value="${cri.searchType eq 'c' ? 'selected' : ''}" />>
			Content</option>
			<option value="w" 
			<c:out value="${cri.searchType eq 'w' ? 'selected' : ''}" />>
			Writer</option>
			<option value="tc" 
			<c:out value="${cri.searchType eq 'tc' ? 'selected' : ''}" />>
			Title OR Content</option> 
			<option value="cw" 
			<c:out value="${cri.searchType eq 'cw' ? 'selected' : ''}" />>
			Content OR Writer</option>
			<option value="tcw" 
			<c:out value="${cri.searchType eq 'tcw' ? 'selected' : ''}" />>
			Title OR Content OR Writer </option>
		</select>	
		
		<input type="text" name="keyword" id="keywordInput" value="${cri.keyword}">
		<button id="searchBtn">Search</button>
		<button id="newBtn">New Board</button>
	</div>
	
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
			<td><a href='/sboard/readPage${pageMaker.makeSearch(pageMaker.cri.page)}&bno=${board.bno}'>
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
				<li><a href="${pageMaker.makeSearch(pageMaker.startPage - 1)}">&laquo;</a></li>				
			</c:if>
			
			<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="idx">
				<li <c:out value="${pageMaker.cri.page == idx?'class = active':''}" />>
				<a href="list${pageMaker.makeSearch(idx)}">${idx}</a>
				</li>				
			</c:forEach>
			
			<c:if test="${pageMaker.next && pageMaker.endPage > 0 }">
				<li><a href="${pageMaker.makeSearch(pageMaker.endPage +1)}">&raquo;</a></li>
			</c:if>
		</ul>	
	</div>
	
	
<%@include file="../include/footer.jsp" %>

<script>
	var result = '${msg}'
	
	if(result == "success") {
		alert("처리가 완료되었습니다.");
	}
	
/* 	$(".pagination li a").on("click",function(event) {
		event.preventDefault();
		
		var targetPage = $(this).attr("href");
		
		var jobForm = $("#jobForm");
		jobForm.find("[name='page']").val(targetPage);
		jobForm.attr("action","/board/listPage").attr("method","get");
		jobForm.submit();
	}); */
	
	$(document).ready(function(){
		$('#searchBtn').on("click",function(event){
			self.location = "list"
			+ '${pageMaker.makeQuery(1)}'
			+ "&searchType="
			+ $("select option:selected").val()
			+ "&keyword=" + encodeURIComponent($('#keywordInput').val());
		});
		
		$('#newBtn').on("click",function(evt){
			self.location = "register";
		});
	});
	
</script>
