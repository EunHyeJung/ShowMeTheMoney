<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>

<head>

<link rel="stylesheet" href="../resources/js/main.js" />
<link rel="stylesheet" href="../resources/css/basic.css" />
<title>Home</title>
</head>
<body>
	<div class="container">

		<img class="main-img1" src="../resources/images/main1.png" />
		<div class="card card-container">
			<!-- <img class="profile-img-card" src="//lh3.googleusercontent.com/-6V8xOA6M7BA/AAAAAAAAAAI/AAAAAAAAAAA/rzlHcD0KYwo/photo.jpg?sz=120" alt="" /> -->
			<img id="profile-img" class="profile-img-card"
				src="//ssl.gstatic.com/accounts/ui/avatar_2x.png" />
			<p id="profile-name" class="profile-name-card"></p>
			<form class="form-signin" action="/user/regist" method="post">
				<span id="reauth-email" class="reauth-email"></span> 
				
				이메일 : <input type="text" name="email" id ="email" placeholder="email" /><br />
				비밀번호 : <input type="password" id="pwd" name="pwd" placeholder="password"/><br />
				이름 : <input type="text" id="name" name="name" class="form-control" placeholder="name"/><br />
				휴대폰 번호 : <input type="text" name="phone" id="phone" class="form-control" placeholder="phone"/> <br />

		        <input type="checkbox" name="cb1" value="1"> 사장님
               <input type="checkbox" name="cb2" value="2"> 알바생 <br />		
				<!-- <input
					type="email" id="inputEmail" class="form-control"
					placeholder="이름" required autofocus> <input
					type="email" id="inputEmail" class="form-control"
					placeholder="이메일 입력" required autofocus> <input
					type="password" id="inputPassword" class="form-control"
					placeholder="비밀번호" required>
					<input 	type="email" id="inputEmail" class="form-control"
					placeholder="휴대폰 번호" required autofocus> --> 
				<!-- <div id="remember" class="checkbox">
					<label> <input type="checkbox" value="remember-me">
						Remember me
					</label>
				</div> -->
		
				<button type="submit" class="btn btn-lg btn-primary btn-block btn-signup" >
				Sign up</button>
			</form>
			<!-- /form -->

		</div>
		<!-- /card-container -->
	</div>
	<!-- /container -->

</body>
</html>
