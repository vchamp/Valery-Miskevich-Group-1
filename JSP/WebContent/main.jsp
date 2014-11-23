<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8" />
<title>Login</title>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<script src="js/modernizr.custom.63321.js"></script>
<script src="js/bank.js"></script>
<script src="js/jquery-1.11.1.js"></script>
</head>
<body>
	<form action="CreateNewUserServlet" method="post">
		<div class="login">
		<p>Create New User
			<p class="field">
				<input type="text" name="userName" class="login_field"
					placeholder="userName" /> <i class="icon-user icon-large"></i>
			</p>

			<p class="submit">
				<button type="submit" name="login" >
					<i class="icon-arrow-right icon-large"></i>
				</button>
			</p>
		</div>
	</form>
	
	<form action="LoginServlet" method="post">
		<div class="login">
		<p>Login
			<p class="field">
				<input type="text" name="userName" class="login_field"
					placeholder="Login" /> <i class="icon-user icon-large"></i>
			</p>

			<p class="submit">
				<button type="submit" name="login" >
					<i class="icon-arrow-right icon-large"></i>
				</button>
			</p>
		</div>
	</form>
</body>
</html>
