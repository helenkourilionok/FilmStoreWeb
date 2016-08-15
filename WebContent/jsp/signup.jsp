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
<fmt:message bundle="${locale}" key="locale.nav.signUp" var="signUp" />
<fmt:message bundle="${locale}" key="locale.login.email" var="email" />
<fmt:message bundle="${locale}" key="locale.login.enterEmail" var="enterEmail" />
<fmt:message bundle="${locale}" key="locale.login.password" var="password" />
<fmt:message bundle="${locale}" key="locale.login.enterPassword" var="enterPassword" />
<fmt:message bundle="${locale}" key="locale.signup.copyPass" var="copyPass" />
<fmt:message bundle="${locale}" key="locale.signup.lastName" var="lastName" />
<fmt:message bundle="${locale}" key="locale.signup.firstName" var="firstName" />
<fmt:message bundle="${locale}" key="locale.signup.patronimic" var="patronimic" />
<fmt:message bundle="${locale}" key="locale.signup.phone" var="phone" />
<fmt:message bundle="${locale}" key="locale.signup.balance" var="balance" />
<fmt:message bundle="${locale}" key="locale.signup.enterCopyPass" var="enterCopyPass" />
<fmt:message bundle="${locale}" key="locale.signup.enterLastName" var="enterLastName" />
<fmt:message bundle="${locale}" key="locale.signup.enterFirstName" var="enterFirstName" />
<fmt:message bundle="${locale}" key="locale.signup.enterPatronimic" var="enterPatronimic" />
<fmt:message bundle="${locale}" key="locale.signup.enterBalance" var="enterBalance" />
<fmt:message bundle="${locale}" key="locale.signup.createAccount" var="createAccount" />
<fmt:message bundle="${locale}" key="locale.signup.signUpFailed" var="mesSignUpFailed"/>
</head>
<body>
	<div class="wrapper container">
		<c:import url="notContent/header.jsp" />
		<c:import url="notContent/adminmenu.jsp" />
		<c:import url="notContent/carousel.jsp" />
		<div class="wrapper row">
			<c:import url="notContent/aside.jsp" />
			<section class="col-md-9">
				<div class="row">
					<div class="col-md-6 col-md-offset-3">
						<h1>${signup}</h1>
						<c:if test="${requestScope.signUpFailed == 'true'}">
							<h1>${mesSignUpFailed}</h1>
						</c:if>
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
						<form method="post" action="Controller" name="signup" onsubmit="${language}">
							<input type="hidden" name="command" value="sign_up" /> 
							<div class="form-group">
								<label for="email">${email}:</label> <input type="text"
									class="form-control" id="email" name="email"
									placeholder="${enterEmail} : name@email.ru" maxlength="39"/>
									<span id="email_error" class="error"></span>
							</div>
							<div class="form-group">
								<label for="password">${password}:</label> <input type="password"
									class="form-control" id="password" name="password"
									placeholder="${enterPassword}" maxlength="40"/>
								<span id="pass_error" class="error"></span>
							</div>
							<div class="form-group">
								<label for="copypassword">${copyPass}:</label> <input
									type="password" class="form-control" id="copypassword"
									name="copypassword" placeholder="${enterCopyPass}"
									maxlength="40"/>
								<span id="copypass_error" class="error"></span>
							</div>
							<div class="form-group">
								<label for="last_name">${lastName}:</label> <input type="text"
									class="form-control" id="last_name" name="last_name"
									placeholder="${enterLastName}" maxlength="15"/>
								<span id="lastname_error" class="error"></span>
							</div>
							<div class="form-group">
								<label for="first_name">${firstName}:</label> <input type="text"
									class="form-control" id="first_name" name="first_name"
									placeholder="${enterFirstName}" maxlength="10"/>
								<span id="firstname_error" class="error"></span>
							</div>
							<div class="form-group">
								<label for="patronimic">${patronimic}:</label> <input type="text"
									class="form-control" id="patronimic" name="patronimic"
									placeholder="${enterPatronimic}" maxlength="15"/>
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
									placeholder="${enterBalance}:10 900.90"/>
									<span id="balance_error" class="error"></span>
							</div>
							<div class="row-offset">
								<button type="submit" class="btn btn-primary">${createAccount}</button>
							</div>
						</form>
					</div>
				</div>
			</section>
		</div>
	</div>	
	<c:import url="notContent/footer.jsp" />
</body>
</html>