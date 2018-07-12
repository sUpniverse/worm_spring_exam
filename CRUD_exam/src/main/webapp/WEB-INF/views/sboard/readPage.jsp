<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file ="../include/header.jsp" %>
	
	<div class="box-body">
		<div class="form-group">
			<label for="exampleInputEmail1">Title</label>
			<input type="text" name="title" class="form-control" value="${board.title}" readonly="readonly">
		</div>
		<div class="form-group">
			<label for="examplePassword1">Content</label>
			<textarea rows="3" class="form-control" name="content" readonly="readonly">${board.content}</textarea>
		</div>	
		<div class="form-group">
			<label for="exampleInputEmail1">Writer</label>
			<input type="text" name="writer" class="form-control" value="${board.writer}" readonly="readonly">
		</div>
	</div>
	
	<div class="box-footer">
		<button type="submit" class="btn btn-warning modifyBtn">Modify</button>
		<button type="submit" class="btn btn-danger removeBtn">REMOVE</button>
		<button type="submit" class="btn btn-primary goListBtn">Go LIST</button>	
	</div>
	
	<form role="form" action="modifyPage" method="post">
		<input type="hidden" name="bno" value="${board.bno}" >
		<input type="hidden" name="page" value="${cri.page}" >
		<input type="hidden" name="perPageNum" value="${cri.perPageNum}" >
		<input type="hidden" name="searchType" value="${cri.searchType}" >
		<input type="hidden" name="keyword" value="${cri.keyword}" >	
	</form>
	
<%@include file="../include/footer.jsp" %>

<script>
	$(document).ready(function() {
		var formObj = $("form[role='form']");
		
		console.log(formObj);	
		
		$(".goListBtn").on("click",function() {
			formObj.attr("method","get");
			formObj.attr("action","/sboard/list");
			formObj.submit();
		});
		
		$(".removeBtn").on("click",function(){
			formObj.attr("action","/sboard/removePage");
			formObj.submit();
		})
		
		$(".modifyBtn").on("click",function(){
			formObj.attr("action","/sboard/modifyPage");
			formObj.attr("method","get");
			formObj.submit();
		})
	});

</script>