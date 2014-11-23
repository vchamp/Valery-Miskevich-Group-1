<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	<form action="${pageContext.request.contextPath}/main.jsp"
		method="post">
		<div class="logout">
			<p class="field">
				Добрый день, ${userName}
				<button type="submit">Logout</button>
			</p>
		</div>
	</form>

	<form action="ExchangeServlet" method="post">
		<div class="converter">
			${balance}
			<p>Конвертировать валюту</p>
			<br>

			<p>
				Из какой <select size="1" class="from" name="from">
					<option value="USD" selected="selected">USD</option>
					<option value="EUR">EUR</option>
					<option value="BYR">BYR</option>
				</select>
			</p>
			<br>

			<p>
				В какую <select name="to" size="1" class="to">
					<option value="USD">USD</option>
					<option value="EUR">EUR</option>
					<option value="BYR" selected="selected">BYR</option>
				</select>
			</p>
			<br> <input type="text" class="amount" name="amount"
				placeholder="Сколько?" onkeyup="exchangeMoney(event)"
				onkeydown="isValidNumber(event)"> <br> <br>

			<div class="exchange">
				<button type="submit">Перевести</button>
			</div>
			<p>Результат</p>

			<p class="calculate_result">Введите сумму</p>

		</div>
	</form>

</body>
</html>