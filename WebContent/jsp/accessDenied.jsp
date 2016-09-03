<%@ page errorPage="error-page.jsp" language="java"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
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
<fmt:message bundle="${locale}" key="locale.operSuccess.operSuccess" var="operSuccess" />
<fmt:message bundle="${locale}" key="locale.nav.login" var="login" />
<fmt:message bundle="${locale}" key="locale.header.logout" var="logout" />
<fmt:message bundle="${locale}" key="locale.accessDenied.andOr" var="andOr" />
<fmt:message bundle="${locale}" key="locale.accessDenied.asAdministrator" var="asAdministrator" />
<fmt:message bundle="${locale}" key="locale.accessDenied.accessDenied" var="accessDenied" />
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
							<img src="images/accessDenied.jpg" alt="${accessDenied}" style="width:85%;height:85%;"/>
						</div>
					</div>
					<div class="row">
						<div class="col-md-9 col-md-offset-4">
							<h3>${accessDenied}!</h3>
						</div>
						<div class="col-md-9 col-md-offset-3">
							<h3><a href="Controller?command=logout">${logout}</a> ${andOr} <a href="Controller?command=login_show_page">${login}</a> ${asAdministrator}!</h3>
						</div>
					</div>
			</section>
		</div>
	</div>
	<c:import url="notContent/footer.jsp" />
</body>
</html>