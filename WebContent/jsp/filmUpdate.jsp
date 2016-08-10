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
<link href="css/fileStyle.css" rel="stylesheet">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.7.5/css/bootstrap-select.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.7.5/js/bootstrap-select.min.js"></script>

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
	function showFileName(str) {
		document.getElementById("uploadFile").value = str.replace(
				/C:\\fakepath\\/, "");
	}
</script>
<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
			<script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
		<![endif]-->
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.locale" var="locale" />
<fmt:message bundle="${locale}" key="locale.filmedit.updateFilm"
	var="updateFilm" />
<fmt:message bundle="${locale}" key="locale.filmedit.findFilm"
	var="findFilm" />
<fmt:message bundle="${locale}" key="locale.filmedit.search"
	var="search" />
<fmt:message bundle="${locale}" key="locale.filmedit.filmCreationFailed"
	var="filmCreationFailedMes" />
</head>
<body>
	<div class="wrapper container">
		<c:import url="header.jsp" />
		<c:import url="adminmenu.jsp" />
		<c:import url="carousel.jsp" />
		<div class="wrapper row">
			<c:import url="aside.jsp" />
			<section class="col-md-9">
				<h1>${updateFilm}</h1>
				<article class="row">
					<div class="col-md-8" style="float: left">
						<form name="search" action="Controller" method="get"
							class="form-inline form-search pull-right" style="width: 265px">
							<input type="hidden" name="command" value="find_film_by_name"/>
							<div class="row-offset">
								<label for="name">${filmName}:</label> 
								<input type="text"
									class="form-control" id="name" name="name"
									placeholder="${enterFilmName}" />
							</div>
							<div class="row-offset">
								<label for="yearOfRelease">${yearOfRelease}:</label> 
								<input type="text"
									class="form-control" id="yearOfRelSearch" name="yearOfRelSearch"
									placeholder="${enterYearOfRel}" />
							</div>
							<div class="row-offset">
								<button type="submit" class="btn btn-primary">${findFilm}</button>
							</div>
						</form>
					</div>
				</article>
				<article class="row">
					<div class="col-md-8">
						<form action="Controller?command=update_film" method="post"
							enctype="multipart/form-data">
							<c:import url="filmFields.jsp" />
							<div class="row-offset">
								<button type="submit" class="btn btn-primary">${updateFilm}</button>
							</div>
						</form>
					</div>
				</article>
			</section>
		</div>
	</div>
	<c:import url="footer.jsp" />
</body>
</html>