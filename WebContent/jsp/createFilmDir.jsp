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
		<script type="text/javascript" src="js/validation.js"></script>
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
		<fmt:message bundle="${locale}" key="locale.createActor.fio" var="fio" />
		<fmt:message bundle="${locale}" key="locale.createActor.enterFio" var="enterfio" />
		<fmt:message bundle="${locale}" key="locale.createActor.createActor" var="createActor" />
		<fmt:message bundle="${locale}" key="locale.createActor.errorFio" var="errorFio" />
		<fmt:message bundle="${locale}" key="locale.createFilmDir.createFilmDir" var="createFilmDir" />
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
						<h2>${createFilmDir}</h2>
						<form role="form" action="Controller" method="post" onsubmit="return createFilmDirectorValidation();">
						<input type="hidden" name="command" value="a_create_filmdir" /> 
						    <div class="form-group">
						      <label for="fio">${fio}:</label>
						      <input type="text" class="form-control" id="fio" name="fio" placeholder="${enterfio}"/>
						      <span id="fio_error" style="color:#eb6a5a;" hidden="true">${errorFio}</span>
						    </div>
						    <div class="form-group" style="margin-bottom:30px">
						    	<button type="submit" class="btn btn-primary">${createFilmDir}</button>
						    </div>
						</form>
					</div>
				</section>
			</div>
			</div>
		<c:import url="notContent/footer.jsp"/>
	</body>
</html>