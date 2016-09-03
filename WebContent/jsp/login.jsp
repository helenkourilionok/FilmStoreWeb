<%@ page errorPage="error-page.jsp" language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
	<head>
		<title>FilmStore</title>
	  	<!-- <meta charset="utf-8"> -->
	  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	  	<meta name="description" content="FilmStore permits you buy and see new films, make comments of films">
	  	<meta name="keywords" content="buy new films,watch new films ">
	  	<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
		<link href="css/styles.css" rel="stylesheet">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
	    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/jquery.carouFredSel-5.2.3-packed.js"></script>
		<script type="text/javascript" src="js/mosaic.1.0.1.js"></script>
		<script type="text/javascript">
			$(function() {

				$('#carousel ul').carouFredSel({
					prev: '#prev',
					next: '#next',
					pagination: "#pager",
					auto: true,
					scroll: 1000,
					pauseOnHover: true
				});
			});
			jQuery(function($){
				$('.bar').mosaic({
					animation	:	'slide'
				});
		    });
		</script>
		<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
			<script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
		<![endif]--> 
		<fmt:setLocale value="${sessionScope.locale}" />
		<fmt:setBundle basename="localization.locale" var="locale" />
		<fmt:message bundle="${locale}" key="locale.login.login" var="login" />
		<fmt:message bundle="${locale}" key="locale.login.email" var="email" />
		<fmt:message bundle="${locale}" key="locale.login.password" var="password" />
		<fmt:message bundle="${locale}" key="locale.login.remember" var="remember" />
		<fmt:message bundle="${locale}" key="locale.login.forgotPass" var="forgotPass" />
		<fmt:message bundle="${locale}" key="locale.login.enterPassword" var="enterPassword" />
		<fmt:message bundle="${locale}" key="locale.login.enterEmail" var="enterEmail" />
		<fmt:message bundle="${locale}" key="locale.login.authFailed" var="authFailed" />		
	</head>
	<body>
		<div class="wrapper container">
            <c:import url="notContent/header.jsp"/>
            <c:import url="notContent/adminmenu.jsp" />
			<c:import url="notContent/carousel.jsp"/>
			<div class="wrapper row">
				<c:import url="notContent/aside.jsp"/>
				<section class="col-md-9">
					<div class="col-md-6 col-md-offset-3">				
						<h2>${login}</h2>
						<form role="form" action="Controller" method="post">
						<input type="hidden" name="command" value="logination" /> 
						    <div class="form-group">
						      <label for="email">${email}:</label>
						      <input type="email" class="form-control" id="email" name="email" placeholder="${enterEmail}"/>
						    </div>
						    <div class="form-group">
						      <label for="password">${password}:</label>
						      <input type="password" class="form-control" id="password" name="password" placeholder="${enterPassword}" />
						      <br/>
						      <c:if test="${incorrectEmailOrPassword == 'true'}">
						      	<span style="color:red">${authFailed}</span>
						      </c:if>
						      <br/>
						    </div>
						    <div class="form-group" style="margin-bottom:30px">
						    	<button type="submit" class="btn btn-primary">${login}</button>
						    </div>
						    <div style="margin-bottom:10px">
						    	<a href="Controller?command=change_password_show_page">${forgotPass}</a>
						    </div>
						</form>
					</div>
				</section>
			</div>
			</div>
		<c:import url="notContent/footer.jsp"/>
	</body>
</html>