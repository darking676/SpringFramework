<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

<script type="text/javascript">
	function detail() {
		$('.detailForm input').hide();
		$('.modal-title').html('상세페이지');
		$('.modal-footer').show();		
		$('.well-input').show();		
	}
	function edit() {
		$('.detailForm input').show();
		$('.modal-title').html('수정페이지');
		$('.modal-footer').hide();		
		$('.well-input').hide();		
	}
	$(function() {
		detail();
		
		// 리스트 페이지 시작하자 팝업되는 ${sabun}의 상세 페이지
		$('#myModal-${sabun}').modal({show:true}); 
		
		// 리스트페이지에서 눌렀을 때 -> detail
		$('.yourModal').on('hide.bs.modal', function(e) {
			detail();
		});
		
		// 수정 페이지에서 수정을 클릭 했을 때
		$('.edit').click(function() {
			edit();
		});
	});
</script>

</head>
<body>
<!-- Navbar 이용 -->
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">
        <p>비트교육센터</p>
      </a>
    </div>
    <ul class="nav navbar-nav">
    	<li><a href="./">Home</a></li>
    	<li><a href="#">List</a></li>
    	<li><a href="/guest/" data-toggle="modal" data-target=".bs-example-modal-lg">Add</a></li>
    	<!-- Modal을 이용한 add -->
		<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
		  <div class="modal-dialog modal-lg" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		      	<h4>Add PAge</h4>
		      </div>
		      <div class="modal-body">
		      	 <form method="post">
			      	 <div class="form-group">
			      	 	<label for="sabun">sabun</label>
			      	 	<input type="text" class="form-control" name="sabun" id="sabun" placeholder="enter sabun" />
			      	 </div>
			      	 <div class="form-group">
			      	 	<label for="name">name</label>
			      	 	<input type="text" class="form-control" name="name" id="name" placeholder="enter name" />
			      	 </div>
			      	 <div class="form-group">
			      	 	<label for="pay">pay</label>
			      	 	<input type="text" class="form-control" name="pay" id="pay" placeholder="enter pay" />
			      	 </div>
			      	 <button type="submit" class="btn btn-primary">입력</button>
			      	 <button type="button" class="btn btn-default">취소</button>
		      	 </form>
		      </div>
		      <div class="modal-footer">
		      </div>
		    </div>
		  </div>
		</div>
    </ul>
  </div>
</nav>

<!-- ListGroup 이용 -->
<div class="list-group">
	<c:forEach items="${alist }" var="bean">
		<button type="button" class="list-group-item btn btn-default" data-toggle="modal" data-target="#myModal-${bean.sabun }">
			<span class="badge">${bean.pay }</span>
			<h4 class="list-group-item-heading">${bean.sabun }</h4>
		    <p class="list-group-item-text">[${bean.nalja }]${bean.name }</p>
		</button>
		
		<!-- Modal을 이용한 detail page -->
		<div class="modal fade yourModal" id="myModal-${bean.sabun }" tabindex="-1" role="dialog" aria-labelledby="myModalLabel-${bean.sabun }">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="myModalLabel-${bean.sabun }">${bean.sabun }님 상세 페이지</h4>
		      </div>
		      <div class="modal-body">
				<form action="./${bean.sabun }" method="post" class="detailForm">
					<input type="hidden" name="_method" value="put" />
			      	<div class="form-group">
			      		<label for="sabun">sabun</label>
			      		<div class="well well-sm">${bean.sabun }</div>
			      		<input type="hidden" value="${bean.sabun }" class="form-control" name="sabun" id="sabun" />
			      	</div>
			      	<div class="form-group">
			      		<label for="name">name</label>
			      		<div class="well well-sm well-input">${bean.name }</div>
			      		<input type="text" value="${bena.name }" class="form-control" name="name" id="name" placeholder="enter name" />
			      	</div>
			      	<div class="form-group">
			      	 	<label>nalja</label>
			      	 	<div class="well well-sm">${bean.nalja }</div>
		      	 	</div>
			      	 <div class="form-group">
			      	 	<label for="pay">pay</label>
			      	 	<div class="well well-sm well-input">${bean.pay }</div>
			      	 	<input type="text" value="${bean.pay }" class="form-control" name="pay" id="pay" placeholder="enter pay" />
			      	</div>
			      	<input type="submit" class="btn btn-primary" value="수정" />
			      	<input type="button" class="btn btn-default" data-dismiss="modal" value="취소" />
		      	 </form>
		      </div>
		      <div class="modal-footer">
		      	<form action="./${bean.sabun }" method="post">
		      		<input type="hidden" name="_method" value="delete" />
			        <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
			        <button type="button" class="btn btn-primary edit">수정</button>
			        <button type="submit" class="btn btn-danger delete">삭제</button>
		        </form>
		      </div>
		    </div>
		  </div>
		</div>
	</c:forEach>
</div>


</body>
</html>