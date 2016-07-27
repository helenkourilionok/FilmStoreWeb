<%@ page errorPage="error-page.jsp" language="java"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>FilmStore</title>
<meta charset="utf-8">
<meta name="description"
	content="FilmStore permits you buy and see new films, make comments of films">
<meta name="keywords" content="buy new films,watch new films ">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link href="css/styles.css" rel="stylesheet">
<link href="css/commentstyle.css" rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="js/jquery.carouFredSel-5.2.3-packed.js"></script>
<script type="text/javascript" src="js/mosaic.1.0.1.js"></script>
<script type="text/javascript" src="js/validation.js"></script>
<script type="text/javascript">
	$(function() {

		$('#carousel ul').carouFredSel({
			prev : '#prev',
			next : '#next',
			pagination : "#pager",
			auto : true,
			scroll : 1000,
			pauseOnHover : true
		});
	});
	jQuery(function($) {
		$('.bar').mosaic({
			animation : 'slide'
		});
	});
</script>
<style type="text/css">
				.error{
				display: inline-block;
				padding-top:15px;
				font-size: 14px;
				color:#eb6a5a;
			}
</style>
<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
			<script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
		<![endif]-->
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.locale" var="locale" />
<fmt:message bundle="${locale}" key="locale.signup.signup" var="signup" />
<fmt:message bundle="${locale}" key="locale.login.email" var="email" />
<fmt:message bundle="${locale}" key="locale.login.enter_email" var="enter_email" />
<fmt:message bundle="${locale}" key="locale.login.password" var="password" />
<fmt:message bundle="${locale}" key="locale.login.enter_password" var="enter_password" />
<fmt:message bundle="${locale}" key="locale.signup.copypass" var="copypass" />
<fmt:message bundle="${locale}" key="locale.signup.lastname" var="lastname" />
<fmt:message bundle="${locale}" key="locale.signup.firstname" var="firstname" />
<fmt:message bundle="${locale}" key="locale.signup.patronimic" var="patronimic" />
<fmt:message bundle="${locale}" key="locale.signup.phone" var="phone" />
<fmt:message bundle="${locale}" key="locale.signup.balance" var="balance" />
<fmt:message bundle="${locale}" key="locale.signup.enter_copypass" var="enter_copypass" />
<fmt:message bundle="${locale}" key="locale.signup.enter_lastname" var="enter_lastname" />
<fmt:message bundle="${locale}" key="locale.signup.enter_firstname" var="enter_firstname" />
<fmt:message bundle="${locale}" key="locale.signup.enter_patronimic" var="enter_patronimic" />
<fmt:message bundle="${locale}" key="locale.signup.enter_phone" var="enter_phone" />
<fmt:message bundle="${locale}" key="locale.signup.enter_balance" var="enter_balance" />
<fmt:message bundle="${locale}" key="locale.signup.create_account" var="create_account" />
</head>
<body>
	<div class="wrapper container">
		<c:import url="header.jsp" />
		<c:import url="adminmenu.jsp" />
		<c:import url="carousel.jsp" />
		<div class="wrapper row">
			<c:import url="aside.jsp" />
			<section class="col-md-9">
				<div class="row">
					<div class="col-md-6 col-md-offset-3">
						<h1>${signup}</h1>
						<c:choose>
						    <c:when test="${sessionScope.locale == 'ru'}">
						       <c:set var="language" scope="session" value="return formValidation('ru');"/>
						    </c:when>
						    <c:when test="${sessionScope.locale == 'en'}">
						        <c:set var="language" scope="session" value="return formValidation('en');"/>
						    </c:when>
					       <c:otherwise>
							    <c:set var="language" scope="session" value="return formValidation('ru');"/>
						    </c:otherwise>
						</c:choose>						
						<form method="get" name="signup" onsubmit="${language}">
							<input type="hidden" name="command" value="sign_up" /> <input
								type="hidden" name="redirect" value="false" />
							<div class="form-group">
								<label for="email">${email}:</label> <input type="text"
									class="form-control" id="email" name="email"
									placeholder="${enter_email} : name@email.ru" maxlength="39"/>
									<span id="email_error" class="error"></span>
							</div>
							<div class="form-group">
								<label for="password">${password}:</label> <input type="password"
									class="form-control" id="password" name="password"
									placeholder="${enter_password}" maxlength="40"/>
								<span id="pass_error" class="error"></span>
							</div>
							<div class="form-group">
								<label for="copypassword">${copypass}:</label> <input
									type="password" class="form-control" id="copypassword"
									name="copypassword" placeholder="${enter_copypass}"
									maxlength="40"/>
								<span id="copypass_error" class="error"></span>
							</div>
							<div class="form-group">
								<label for="last_name">${lastname}:</label> <input type="text"
									class="form-control" id="last_name" name="last_name"
									placeholder="${enter_lastname}" maxlength="15"/>
								<span id="lastname_error" class="error"></span>
							</div>
							<div class="form-group">
								<label for="first_name">${firstname}:</label> <input type="text"
									class="form-control" id="first_name" name="first_name"
									placeholder="${enter_firstname}" maxlength="10"/>
								<span id="firstname_error" class="error"></span>
							</div>
							<div class="form-group">
								<label for="patronimic">${patronimic}:</label> <input type="text"
									class="form-control" id="patronimic" name="patronimic"
									placeholder="${enter_patronimic}" maxlength="15"/>
								<span id="patronimic_error" class="error"></span>
							</div>
							<div class="form-group">
								<label for="phone">${phone}:</label> <input type="text"
									class="form-control" id="phone" name="phone"
									placeholder="+375-29-123-45-67"/>
								<span id="phone_error" class="error"></span>
							</div>
							<div class="form-group">
								<label for="balance">${balance}:</label> <input type="text"
									class="form-control" id="balance" name="balance"
									placeholder="${enter_balance}:10 900.90"/>
									<span id="balance_error" class="error"></span>
							</div>
							<div class="row-offset">
								<button type="submit" class="btn btn-primary">${create_account}</button>
							</div>
						</form>
					</div>
				</div>
			</section>
		</div>
	</div>
	
	
	
	
	<c:import url="footer.jsp" />
</body>
</html>