<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">

<title>ShowMeTheMoney</title>


<!-- Bootstrap core CSS -->
<link href="../resources/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="../resources/css/dashboard.css" rel="stylesheet">

<link href='../resources/css/fullcalendar.css' rel='stylesheet' />
<link href='../resources/css/fullcalendar.print.css' rel='stylesheet'
	media='print' />

<link href="../resources/css/modal-custom.css" rel="stylesheet">
	

</head>

<body>
	<!-- hidden data -->
	<input type="hidden" id="session-user-id" value="<c:out value='${sessionScope.user_id}'/>"/>
	<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">ShowMeTheMoney</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="/logout">Logout</a></li>
				<li id='btn-setting'><a href="#">Settings</a></li>
			</ul>
		</div>
	</div>
	</nav>

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<ul id="sidebar" class="nav nav-sidebar"></ul>
				<div class="btn-group-sm" role="group">
				  <button type="button" id="add-store" class="btn btn-warning" data-toggle="modal" data-target="#add-store-modal">상점추가</button>
				  <button type="button" id="delete-store" class="btn btn-warning">상점제거</button>
				  <button type="button" id="cancle-delete-store" class="btn btn-warning btn-sm hidden">제거취소</button>
				</div>
			</div>
			<div id = mainview class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h2 class="sub-header" >아르바이트생 리스트
					<div id='edit-staff-btn' class="btn-group hidden">
					  <button type="button" class="btn btn-warning btn-sm dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
					    	편집 <span class="caret"></span>
					  </button>
					  <ul class="dropdown-menu" role="menu">
					    <li id="add-staff" data-toggle="modal" data-target="#add-staff-modal"type="button"><a href="#">아르바이트생 추가</a></li>
					    <li id="delete-staff"><a href="#">아르바이트생 삭제</a></li>
					  </ul> 
				</div>
				<button id="btn-delete-staff" class="btn btn-warning btn-sm hidden" type="button">삭제완료</button>
				</h2>
				<div class="table-responsive">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>이름</th>
								<th>연락처</th>
								<th>이메일</th>
								<th>시급</th>
								<th id='title-delete' class='hidden'>삭제</th>
							</tr>
						</thead>
						<tbody id="tbl-staff-list">
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	
<!-- Modal -->
<!-- 달력 Modal -->
<div id="calendar-modal" class="modal hidden" tabindex="-1" role="dialog" aria-labelledby="calendatModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-custom">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" id="calendar-close"class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="calendatModalLabel">근무내역</h4>
			</div>
			<div class="modal-body clearfix">
				<div id='calendar'></div>
				<div id='daily-detail-view'>
					<!-- <input type="text" class="daily-time"> -->
					<div class="input-group input-group-sm">
					  <span class="input-group-addon">출근시간</span>
					  <input type="text" id='start_time' class="form-control" placeholder="00:00" aria-describedby="sizing-addon3">
					</div>
					<div class="input-group input-group-sm">
					  <span class="input-group-addon">퇴근시간</span>
					  <input type="text" id='end_time' class="form-control" placeholder="00:00" aria-describedby="sizing-addon3">
					</div>
					<div id='total_time'>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- 상점추가 Modaㅣ -->
<div class="modal fade" id="add-store-modal" tabindex="-1" role="dialog"
	aria-labelledby="addStoreModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="addStoreModalLabel">상점 추가</h4>
			</div>
			<div class="modal-body">
				<form class="form-inline">
					<div class="form-group">
						<label class="sr-only" for="inputStoreName"></label>
						<input type="text" class="form-control" id="inputStoreName">
					</div>
					<button type="button" id="btn-add-store" class="btn btn-warning">추가</button>
				</form>
			</div>
		</div>
	</div>
</div>

<!-- 알바생추가 Modal -->
<div class="modal fade" id="add-staff-modal" tabindex="-1" role="dialog"
	aria-labelledby="addStaffModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="addStaffModalLabel">알바생 추가</h4>
			</div>
			<div id=staff-info-list class="modal-body">
				<form class="form-inline">
					<div class="form-group">
						<label class="sr-only" for="selectStaff"></label>
						<input type="radio" name="readStaffoption" id="emailoption" value="emailoption">이메일검색
						<input type="radio" name="readStaffoption" id="phoneoption" value="phoneoption">연락처검색
						<input type="text" class="form-control" id="staffInfo" value="">
						<button type="button" id="btn-read-staff" class="btn btn-warning">검색</button>
						<div id=add-staff-view class="modal-body hidden">
							<form class="form-inline">
								<input type="text" id = add-staff-userid class="hidden" value="">
								<input type="text" id = add-staff-name class="form-control" value="">
								<input type="text" id = add-staff-email class="form-control" value="">
								<input type="text" id = add-staff-phone class="form-control" value="">
								<input type="text" id = add-satff-houlywage class="form-control" placeholder="시급입력">
								<!-- <input type="text" id = add-satff-overwage class="form-control" value="초과수당입력"> -->
								<button type="button" id= btn-add-staff class="btn btn-warning">추가</button>
							</form>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<!-- 세팅 Modaㅣ -->
<!-- <div class="modal fade" id="add-store-modal" tabindex="-1" role="dialog"
	aria-labelledby="addStoreModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="addStoreModalLabel">상점 추가</h4>
			</div>
			<div class="modal-body">
				<form class="form-inline">
					<div class="form-group">
						<label class="sr-only" for="inputStoreName"></label>
						<input type="text" class="form-control" id="inputStoreName">
					</div>
					<button type="button" id="btn-add-store" class="btn btn-primary">추가</button>
				</form>
			</div>
		</div>
	</div>
</div> -->

	<!-- Bootstrap core JavaScript
   ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="../resources/dist/js/bootstrap.min.js"></script>
	<script src="../resources/assets/js/docs.min.js"></script>
	
	<script src='../resources/js/moment.min.js'></script>
	<script src='../resources/js/fullcalendar.min.js'></script>
	<script src="../resources/js/locale-all.js"></script>
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="../resources/assets/js/ie10-viewport-bug-workaround.js"></script>
	<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
<script src="../resources/assets/js/ie-emulation-modes-warning.js"></script>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
<script src="../resources/js/dateFormat.js"></script>
<script type="text/javascript">
$(function() {
	$('#calendar-modal').removeClass('hidden');
	var store_delete_toggle = 0;
	var staff_delete_toggle = 0;
	getStoreList();
	$('#calander').fullCalendar();
	var store_id = "";
	$(document).on('click', '#sidebar li', function() {
		store_id = $(this).attr('alt');
		if(store_delete_toggle==0){
			getStaffList();
		}else if(store_delete_toggle==1){
			var data = {
					store_id : store_id
			};
			$.ajax({
				type : 'POST',
				url  : '/stores/delete',
				data : JSON.stringify(data),
				headers : {
					"Content-Type" : "application/json"
				},
				success : function(data) {
					alert("000");
					getStoreList();
					store_delete_toggle=0;
					$('#cancle-delete-store').addClass('hidden');
					$('#delete-store').removeClass('hidden');
					
				},
				error : function(jqXHR, textStatus, errorThrown) {
					var errorMsg = 'status(code): ' + jqXHR.status + '\n';
					errorMsg += 'statusText: ' + jqXHR.statusText + '\n';
					errorMsg += 'responseText: ' + jqXHR.responseText + '\n';
					errorMsg += 'textStatus: ' + textStatus + '\n';
					errorMsg += 'errorT   hrown: ' + errorThrown;
					//console.log(errorMsg);
					alert(errorMsg);
				}
			});
		}
	});

	$(document).on('click', '.modalDialog', function(){
		
		$.ajax({
			type : 'GET',
			url : '/checkdailytimelist?staff_id='+$(this).attr('value')+'&start_time='+"201608",
			headers : {
				"Content-Type" : "application/json"
			},
			success : function(data) {
				var eventSources = [];
				$(data).each(function(key, val) {
					var event = new Object();
					event.start = val.start_time;
					console.log(event.start);
					event.end = val.end_time;
					console.log(event.end);
					eventSources.push(event);
				});
				$('#calendar').fullCalendar({
					locale : 'ko',
					defaultDate : new Date(),
					events: eventSources,
					displayEventEnd : true,
					eventClick : function(calEvent, jsEvent, view){
						console.log("일한시간, 하루일급, 총월급");
						
						var t1 = moment(calEvent.start);
						var t2 = moment(calEvent.end);
						
						var sub_time = {
								  seconds: moment.duration(t2 - t1).asSeconds(), // 86400
								  minutes: moment.duration(t2 - t1).asMinutes(), // 1440
								  hours: moment.duration(t2 - t1).asHours() //24
								};
						$('#start_time').val(moment(calEvent.start, 'yyyy-mm-dd hh:mm:ss').format('hh:mm'));
						$('#end_time').val(moment(calEvent.end, 'yyyy-mm-dd hh:mm:ss').format('hh:mm'));
						$('#total_time').html("총  00 시간 "+sub_time.minutes+" 분");
					},
					timeFormat: 'hh:mm'
				});
			},
			error : function(jqXHR, textStatus, errorThrown) {
				var errorMsg = 'status(code): ' + jqXHR.status + '\n';
				errorMsg += 'statusText: ' + jqXHR.statusText + '\n';
				errorMsg += 'responseText: ' + jqXHR.responseText + '\n';
				errorMsg += 'textStatus: ' + textStatus + '\n';
				errorMsg += 'errorT   hrown: ' + errorThrown;
				//console.log(errorMsg);
				alert(errorMsg);
			}
		});

	});

	$('#calendar-modal').on('hidden.bs.modal', function (e) {
		$('#calendar').fullCalendar( 'destroy' );
		$('#start_time').val("");
		$('#end_time').val("");
		$('#total_time').html("");
	});

	$(document).on('click', '#btn-add-store', function(e) {
		
		e.preventDefault();
		var param = {
			user_id : $('#session-user-id').val(),
			store_name : $('#inputStoreName').val() 
		};
		
		console.log(param);
		
		$.ajax({
			type : 'POST',
			url  : '/stores/create',
			data : JSON.stringify(param),
			headers : {
				"Content-Type" : "application/json"
			},
			success : function(data) {
				getStoreList();
				store_name="";
				$('#add-store-modal').modal('hide');
			},
			error : function(jqXHR, textStatus, errorThrown) {
				var errorMsg = 'status(code): ' + jqXHR.status + '\n';
				errorMsg += 'statusText: ' + jqXHR.statusText + '\n';
				errorMsg += 'responseText: ' + jqXHR.responseText + '\n';
				errorMsg += 'textStatus: ' + textStatus + '\n';
				errorMsg += 'errorT   hrown: ' + errorThrown;
				//console.log(errorMsg);
				alert(errorMsg);
			}
		});
		$('#inputStoreName').val("");
	});

	/* 알바생추가하기 */
	$(document).on('click', '#btn-read-staff', function(e) {
		var temp = $(':radio[name="readStaffoption"]:checked').val();
		var staff_info = $('#staffInfo').val();

		if(temp=="emailoption" && staff_info!=""){
			readStaffList(staff_info, '/staffs/searchEmail?email=');
		}else if(temp=="phoneoption" && staff_info!=""){
			readStaffList(staff_info, '/staffs/searchPhone?phone=');
		}else{
			$('#readStafflist').html("정보를입력해주세염");
		}
	});
	
	function readStaffList(staff_info, url){
		console.log(staff_info);
		var searchUrl = url+staff_info;
		$.ajax({
			type : 'GET',
			url : searchUrl,
			dataType : 'json',
			headers : {
				"Content-Type" : "application/json"
			},
			success : function(data) {
				var search_staff_list = data.RESULT;
				/* $('#add-staff-modal').modal('hide'); */
				console.log(JSON.stringify(search_staff_list));
				$(search_staff_list).each(function(key, val) {
					console.log(JSON.stringify(search_staff_list));
					$('#add-staff-view').removeClass('hidden');
					$('#add-staff-userid').val(val.user_id);
					$('#add-staff-name').val(val.name);
					$('#add-staff-phone').val(val.phone);
					$('#add-staff-email').val(val.email);
				});
			},
			error : function(jqXHR, textStatus, errorThrown) {
				$('#readStafflist').html("해당회원이 존재안함");
				var errorMsg = 'status(code): ' + jqXHR.status + '\n';
				errorMsg += 'statusText: ' + jqXHR.statusText + '\n';
				errorMsg += 'responseText: ' + jqXHR.responseText + '\n';
				errorMsg += 'textStatus: ' + textStatus + '\n';
				errorMsg += 'errorT   hrown: ' + errorThrown;
				//console.log(errorMsg);
			}
		});
		$('#btn-next-staff-info').removeClass('hidden');
	}

	$(document).on('click', '#btn-add-staff', function(e) {
		e.preventDefault();
		var param = {
			store_id : store_id,
			user_id : $('#add-staff-userid').val(),
			grade : "0",
			hourly_wage : $('#add-satff-houlywage').val(),
			over_wage : "0"
		};
		console.log(param);
		$.ajax({
			type : 'POST',
			url  : '/staffs/create',
			data : JSON.stringify(param),
			headers : {
				"Content-Type" : "application/json"
			},
			success : function(data) {
				getStoreList();
				store_nmae="";
				$('#add-staff-modal').modal('hide');
				$('#add-staff-view').addClass('hidden');
				$('#add-staff-userid').val("");
				$('#add-staff-name').val("");
				$('#add-staff-phone').val("");
				$('#add-staff-email').val("");
				$('#staffInfo').val("");
				getStaffList();
			},
			error : function(jqXHR, textStatus, errorThrown) {
				var errorMsg = 'status(code): ' + jqXHR.status + '\n';
				errorMsg += 'statusText: ' + jqXHR.statusText + '\n';
				errorMsg += 'responseText: ' + jqXHR.responseText + '\n';
				errorMsg += 'textStatus: ' + textStatus + '\n';
				errorMsg += 'errorT   hrown: ' + errorThrown;
				//console.log(errorMsg);
				alert(errorMsg);
			}
		});
	});

	$(document).on('click', '#btn-setting', function(e) {
		
	});

	$(document).on('click', '#delete-store', function(e) {
		$('#delete-store').addClass('hidden');
		$('#cancle-delete-store').removeClass('hidden');
		
		if(store_delete_toggle == 0){
			$('#sidebar > li > a').animate({
				paddingLeft: 45
			}, 500);
			$('#sidebar div').hide().delay(1000).show();
			store_delete_toggle = 1;
		} 
		
	});
	$(document).on('click', '#cancle-delete-store', function(e) {
		$('#cancle-delete-store').addClass('hidden');
		$('#delete-store').removeClass('hidden');
		if(store_delete_toggle ==1) {
			$('#sidebar > li > a').animate({
				paddingLeft: 20
			}, 500);
			$('#sidebar div').show().delay(1000).hide();
			store_delete_toggle = 0;
		}
	});

	function getStoreList() {
		$.ajax({
			type : 'GET',
			url : '/stores/list?user_id='+$('#session-user-id').val(),
			headers : {
				"Content-Type" : "application/json"
			},
			success : function(data) {
				//console.log(JSON.stringify(data));
				var store_list = data;
				var list_html = "";

				$(store_list).each(function(key, val) {
					list_html += '<li alt="'+ val.store_id +'" >';
					list_html += '<div style="display:none;">';
					list_html += '<span class="glyphicon glyphicon-minus delete-icon" aria-hidden="true"></span>';
					list_html += '</div>';
					list_html += '<a style="padding-left: 20px;">' +  val.store_name + '</a>';
					list_html += '</li>';
				});
				$('#sidebar').html(list_html);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				var errorMsg = 'status(code): ' + jqXHR.status + '\n';
				errorMsg += 'statusText: ' + jqXHR.statusText + '\n';
				errorMsg += 'responseText: ' + jqXHR.responseText + '\n';
				errorMsg += 'textStatus: ' + textStatus + '\n';
				errorMsg += 'errorT   hrown: ' + errorThrown;
				//console.log(errorMsg);
			}
		});
	}
	/* 알바생삭제하기 */
	$(document).on('click', '#delete-staff', function(e) {
		$('#btn-delete-staff').removeClass('hidden');
		$('#calendar-modal').addClass('hidden');
		$('#title-delete').removeClass('hidden');
		$('.delete-check').removeClass('hidden');
		staff_delete_toggle = 1;
	});
	$(document).on('click', '#btn-delete-staff', function(e) {
		var staff_id_Arr = new Array();
		var num = $("input[name='delete']:checkbox:checked").length;
		if(num>0){
		$("input[name='delete']:checkbox:checked").each(function(){
			staff_id_Arr.push($(this).val());
		});
		for(i=0; i<num; i++){
			var staff_id=staff_id_Arr[i];
			var data = {
					staff_id : staff_id
			};
			$.ajax({
				type : 'POST',
				url  : '/staffs/delete',
				data : JSON.stringify(data),
				headers : {
					"Content-Type" : "application/json"
				},
				success : function(data) {
					getStaffList();
					$('#btn-delete-staff').addClass('hidden');
					//$('#calendar-modal').removeClass('hidden');
					$('#title-delete').addClass('hidden');
					$('.delete-check').addClass('hidden');
					staff_delete_toggle = 0;
				},
				error : function(jqXHR, textStatus, errorThrown) {
					var errorMsg = 'status(code): ' + jqXHR.status + '\n';
					errorMsg += 'statusText: ' + jqXHR.statusText + '\n';
					errorMsg += 'responseText: ' + jqXHR.responseText + '\n';
					errorMsg += 'textStatus: ' + textStatus + '\n';
					errorMsg += 'errorT   hrown: ' + errorThrown;
					//console.log(errorMsg);
					alert(errorMsg);
				}
			});
		}
		}
		$('#btn-delete-staff').addClass('hidden');
		//$('#calendar-modal').removeClass('hidden');
		$('#title-delete').addClass('hidden');
		$('.delete-check').addClass('hidden');
		staff_delete_toggle = 0;
	});
	
	function getStaffList(){
		$('#edit-staff-btn').removeClass('hidden');
		$('#sidebar li').removeClass('active');
		$(this).addClass('active');
		$.ajax({
			type : 'GET',
			url : '/staffs/list?store_id=' + store_id,
			headers : {
				"Content-Type" : "application/json"
			},
			success : function(data) {
				console.log(JSON.stringify(data));
				var staff_list = data;
				var list_html = "";
				console.log(store_id);
				$(staff_list).each(function(key, val) {
					list_html += '<tr class="modalDialog" value="' + val.staff_id + '" data-toggle="modal" data-target="#calendar-modal">';
					list_html += '<td>' + val.name + '</td>';
					list_html += '<td>' + val.phone + '</td>';
					list_html += '<td>' + val.email + '</td>';
					list_html += '<td>' + val.hourly_wage + '</td>';
					list_html += '<td class="delete-check hidden"><input type=checkbox name='+'"delete"'+'value='+val.staff_id+'></td>';
					list_html += '</tr>';
				})
				$('#tbl-staff-list').html(list_html);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				var errorMsg = 'status(code): ' + jqXHR.status + '\n';
				errorMsg += 'statusText: ' + jqXHR.statusText + '\n';
				errorMsg += 'responseText: ' + jqXHR.responseText + '\n';
				errorMsg += 'textStatus: ' + textStatus + '\n';
				errorMsg += 'errorT   hrown: ' + errorThrown;
				//console.log(errorMsg);
				alert(errorMsg);
			}
		});
	}
});
</script>
</body>
</html>