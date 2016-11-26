<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"
	type="text/javascript"></script>
<script src="<c:url value="/webjars/jquery/2.1.3/dist/jquery.min.js"/>"></script>

<link rel="stylesheet" href="../resources/css/basic.css" />
<script type="text/javascript">
	

	function join_proc() {
		$('#login-view').hide();
		$('#join-view').show();
	}

	function join() {
		var param = {
				email : $('#join_email').val(),
				pwd : $('#join_pwd').val(),
				name : $('#join_name').val(),
				phone : $('#join_phone').val(),
				mode : $(':radio[name="readStaffoption"]:checked').val()
			}
		$.ajax({
			type : 'POST',
			url  : '/users/create',
			data : JSON.stringify(param),
			headers : {
				"Content-Type" : "application/json"
			},
			success : function(data) {
				$('#login-view').show();
				$('#join-view').hide();
				$('#email').val($('#join_email').val());
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
	
	function login() {
		var email = $('#email').val();
		var pwd = $('#pwd').val();
		var loginData = {};
		loginData["email"] = email;
		loginData["pwd"] = pwd;
		// email : id_1@naver.com pwd : pw1
		$.ajax({
			type : 'POST',
			url : '/users/authentication',
			contentType : 'application/json;charset=UTF-8', // 서버로 전송할 데이터가 JSON 객체임을 명시
			data : JSON.stringify(loginData), // javascript 객체를 JSON 객체로 변환해서 서버로 전송 
			dataType : 'json', // 서버로부터 응답 받을 데이터가 JSON입을 명시
			success : function(response) {
				var msg = response.result;
				if(msg != null){
					window.location = "/main";
				} else{
					alert("로그인 실패");
				}

			},
			error : function(jqXHR, textStatus, errorThrown) {
				var errorMsg = 'status(code): ' + jqXHR.status + '\n';
				errorMsg += 's tatusText: ' + jqXHR.statusText + '\n';
				errorMsg += 'responseText: ' + jqXHR.responseText + '\n';
				errorMsg += 'textStatus: ' + textStatus + '\n';
				errorMsg += 'errorT	hrown: ' + errorThrown;

				alert(errorMsg);
			}

		});
	}
</script>
<title>Home</title>
</head>
<body>
	<div class="container" id="login-view">
		<img class="main-img1" src="resources/images/main1.png" />
		<div class="card card-container">
			<img id="profile-img" class="profile-img-card"
				src="//ssl.gstatic.com/accounts/ui/avatar_2x.png" />
			<p id="profile-name" class="profile-name-card"></p>
			<form class="form-signin" id="loginFrom">
				<span id="reauth-email" class="reauth-email"></span> <input
					type="email" id="email" class="form-control"
					placeholder="Email address" required autofocus> <input
					type="password" id="pwd" class="form-control"
					placeholder="Password" required>
				<div id="remember" class="checkbox">
					<label> <input type="checkbox" value="remember-me">
						Remember me
					</label>
				</div>
				<button class="btn btn-lg btn-primary btn-block btn-signin"
					type = button onclick="login()">Sign in</button>
				<button class="btn btn-lg btn-primary btn-block btn-signup"
					type = button onclick="join_proc();">Sign up</button>
			</form>
			<!-- /form -->
			<a href="#" class="forgot-password"> Forgot the password? </a>
		</div>
		<!-- /card-container -->
	</div>
	<!-- /container -->
	
	<!-- 회원가입 -->
	<div class="container" id="join-view" style="display: none;">

		<img class="main-img1" src="../resources/images/main1.png" />
		<div class="card card-container">
			<!-- <img class="profile-img-card" src="//lh3.googleusercontent.com/-6V8xOA6M7BA/AAAAAAAAAAI/AAAAAAAAAAA/rzlHcD0KYwo/photo.jpg?sz=120" alt="" /> -->
			<img id="profile-img" class="profile-img-card"
				src="//ssl.gstatic.com/accounts/ui/avatar_2x.png" />
			<p id="profile-name" class="profile-name-card"></p>
			<form class="form-signin" action="/user/regist" method="post">
				<span id="reauth-email" class="reauth-email"></span> 
				
				이메일 : <input type="text" name="join_email" id ="join_email" placeholder="email" /><br />
				비밀번호 : <input type="password" id="join_pwd" name="join_pwd" placeholder="password"/><br />
				이름 : <input type="text" id="join_name" name="join_name" class="form-control" placeholder="name"/><br />
				휴대폰 번호 : <input type="text" name="join_phone" id="join_phone" class="form-control" placeholder="phone"/> <br />

		       <input type="radio" name="cb1" id="cb1" value="0"> 사장님
               <input type="radio" name="cb1" id="cb2" value="1"> 알바생 <br />		
				<button type="button" onclick="join()" class="btn btn-lg btn-primary btn-block btn-signup" >
				Sign up</button>
			</form>
			<!-- /form -->

		</div>
		<!-- /card-container -->
	</div>

</body>
</html>
