<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file ="../include/header.jsp" %>
	<form role="form" method="post">	
		<input type="hidden" name="bno" value="${board.bno}">
	</form>
	
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
		<button type="submit" class="btn btn-warning">Modify</button>
		<button type="submit" class="btn btn-danger">REMOVE</button>
		<button type="submit" class="btn btn-primary">LIST ALL</button>	
	</div>
	
<%@include file="../include/footer.jsp" %>

<script>
	$(document).ready(function() {
		var formObj = $("form[role='form']");
		
		console.log(formObj);
		
		$(".btn-warning").on("click", function() {
			formObj.attr("action","/board/modify");
			formObj.attr("method","get");
			formObj.submit();
		});
		
		$(".btn-danger").on("click",function() {
			formObj.attr("action","/board/remove");
			formObj.submit();
		});
		
		$(".btn-primary").on("click",function() {
			formObj.attr("action","/board/listAll");
			self.location ="/board/listAll";
		});	
	});

</script>