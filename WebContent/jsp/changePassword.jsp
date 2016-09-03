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
<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
			<script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
		<![endif]-->
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.locale" var="locale" />
<fmt:message bundle="${locale}" key="locale.login.email" var="email" />
<fmt:message bundle="${locale}" key="locale.login.enterEmail" var="enterEmail" />
<fmt:message bundle="${locale}" key="locale.login.password" var="password" />
<fmt:message bundle="${locale}" key="locale.login.enterPassword" var="enterPassword" />
<fmt:message bundle="${locale}" key="locale.signup.copyPass" var="copyPass" />
<fmt:message bundle="${locale}" key="locale.signup.enterCopyPass" var="enterCopyPass" />
<fmt:message bundle="${locale}" key="locale.personalInfo.new" var="newPass" />
<fmt:message bundle="${locale}" key="locale.personalInfo.changePassword" var="changePassword" />
<fmt:message bundle="${locale}" key="locale.personalInfo.dontMatch" var="dontMatch" />
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
						<h1>${changePassword}</h1>
						<c:choose>
						    <c:when test="${sessionScope.locale == 'ru'}">
						       <c:set var="language" scope="session" value="return changePasswordValidation('ru');"/>
						    </c:when>
						    <c:when test="${sessionScope.locale == 'en'}">
						        <c:set var="language" scope="session" value="return changePasswordValidation('en');"/>
						    </c:when>
					       <c:otherwise>
							    <c:set var="language" scope="session" value="return changePasswordValidation('ru');"/>
						    </c:otherwise>
						</c:choose>						
						<form method="post" action="Controller" name="signup" onsubmit="${language}">
							<input type="hidden" name="command" value="change_password" /> 
							<span>${requestScope.userDoesntExist}</span>
							<div class="form-group">
								<label for="email">${email}:</label> <input type="text"
									class="form-control" id="email" name="email"
									placeholder="${enterEmail} : name@email.ru" value="${requestScope.email}" maxlength="39"/>
									<span id="email_error" class="error"></span>
							</div>
							<div class="form-group">
								<label for="password">${newPass}:</label> <input type="password"
									class="form-control" id="password" name="password"
									placeholder="${enterPassword}" maxlength="40"/>
								<span id="pass_error" class="error"></span>
							</div>
							<div class="form-group">
								<label for="copypassword">${copyPass}:</label> <input
									type="password" class="form-control" id="copypassword"
									name="copypassword" placeholder="${enterCopyPass}"
									maxlength="40"/>
								<span id="copypass_error" style="color:#eb6a5a;" hidden="true">${dontMatch}</span>
							</div>
							<div class="row-offset">
								<button type="submit" class="btn btn-primary">${changePassword}</button>
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