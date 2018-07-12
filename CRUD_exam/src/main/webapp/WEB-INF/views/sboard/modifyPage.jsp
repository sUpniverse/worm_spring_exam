<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<%@ include file ="../include/header.jsp" %>

	<form role="form" action="modifyPage" method="post">
		<input type="hidden" name="page" value="${cri.page}">
		<input type="hidden" name="perPageNum" value="${cri.perPageNum}">
		<input type="hidden" name="searchType" value="${cri.searchType}">
		<input type="hidden" name="keyword" value="${cri.keyword}">
		<div class="box-body">
			<div class="form-group">
				<label for="exampleInputEmail">BNO</label>
				<input type="text" name='bno' value="${board.bno}" class="form-control" placeholder="Enter Title" readonly="readonly">				
			</div>
			<div class="form-group">
				<label for="exampleInputEmail">Title</label>
				<input type="text" name='title' value="${board.title}" class="form-control" placeholder="Enter Title">				
			</div>
			<div class="form-group">
				<label for="exampleInputPassword1">Content</label>
				<textarea rows="3" class="form-control" name="content" placeholder="Enter content">${board.content}</textarea>
			</div>
			<div class="form-group">
				<label for="exampleInputEmail">Writer</label>
				<input type="text" name="writer" value="${board.writer}" class="form-control" placeholder="Enter Writer" readonly="readonly">
			</div>
		</div>	
		<div class="box-footer">
			<button type="submit" class="btn btn-primary">SAVE</button>
			<button type="submit" class="btn btn-warning">CANCLE</button>
		</div>
	</form>

<%@include file="../include/footer.jsp" %>

<script>
	$(document).ready(function() {
		var formObj = $.("form[role='form']");
		
		console.log(formObj);
		
		$(".btn-primary").on("click",function() {
			formObj.submit();			
		});
		
		$(".btn-warning").on("click",function() {
			self.location ="/sboard/list?page=${cri.page}&perPageNum=${cri.perPageNum}"
					+ "%searchType=${cri.searchType}&keyword=${cri.keyword}";
		});
	})
	
</script>
