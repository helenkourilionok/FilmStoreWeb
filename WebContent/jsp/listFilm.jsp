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
<fmt:message bundle="${locale}" key="locale.showfilm.countFilms"
	var="countFilms" />
<fmt:message bundle="${locale}" key="locale.showfilm.quality"
	var="quality" />
<fmt:message bundle="${locale}" key="locale.showfilm.price" var="price" />
<fmt:message bundle="${locale}" key="locale.showfilm.allFilms"
	var="allFilms" />
<fmt:message bundle="${locale}" key="locale.showfilm.yearOfRelease"
	var="yearOfRelease" />
<fmt:message bundle="${locale}" key="locale.showfilm.genre" var="genre" />
<fmt:message bundle="${locale}" key="locale.showfilm.filmDirector"
	var="filmDirector" />
<fmt:message bundle="${locale}" key="locale.showfilm.country"
	var="country" />
<fmt:message bundle="${locale}" key="locale.showfilm.actors"
	var="actors" />
<fmt:message bundle="${locale}" key="locale.showfilm.makeComment"
	var="makeComment" />
<fmt:message bundle="${locale}" key="locale.index.noneFilmWasFound"
	var="noneFilmWasFound" />
<fmt:message bundle="${locale}" key="locale.filmedit.filmName"
	var="filmName" />
<fmt:message bundle="${locale}" key="locale.filmedit.price"
	var="filmPrice" />
<fmt:message bundle="${locale}" key="locale.showfilm.quality"
	var="filmQuality" />
<fmt:message bundle="${locale}" key="locale.listFilm.operation"
	var="filmOperation" />
<fmt:message bundle="${locale}" key="locale.adminmenu.update"
	var="update" />
<fmt:message bundle="${locale}" key="locale.adminmenu.delete"
	var="delete" />
</head>
<body>
	<div class="wrapper container">
		<c:import url="notContent/header.jsp" />
		<c:import url="notContent/adminmenu.jsp" />
		<c:import url="notContent/carousel.jsp" />
		<div class="wrapper row">
			<c:import url="notContent/aside.jsp" />
			<!-- content -->
			<section class="col-md-9">
				<h1>${allFilms}</h1>
				<c:if test="${requestScope.notFoundFilmForRequest == 'true'}">
					<h2>${noneFilmWasFound}</h2>
				</c:if>
				<!--Bar-->
				<article class="row">
					<div class="col-md-12">
					<table class="table table-bordered">
						<thead>
						      <tr>
						       <th>${filmName}</th>
						       <th>${filmPrice}</th>
						       <th>${filmQuality}</th>
						       <th>${filmOperation}</th>
						     </tr>
						</thead>
						<tbody>
							<c:forEach var="film" items="${requestScope.listFilm}">
								      <tr>
								        <td>${film.name}(${film.yearOfRelease})</td>
								        <td>${film.price}</td>
								        <td>${film.quality.getNameQuality()}</td>
								        <td>  <a href="Controller?command=a_update_film_show_page&id=${film.id}">${update}</a>
								        	  <a href="Controller?command=a_delete_film&id=${film.id}">${delete}</a>
								        </td>
								      </tr>
							</c:forEach>
							</tbody>
						  </table>
					</div>
				</article>
			<div class="row col-md-8 col-md-offset-1">
				<ul class="pagination">
					<c:if test="${pageInfo.currentPage != 1}">
				    	<li><a href="Controller?command=a_show_list_film&page=${pageInfo.currentPage - 1}&start=${pageInfo.currentPage - 1}">Previous</a></li>
				    </c:if>
				    <c:set var="start" scope="request" value="${pageInfo.listIndex.get(0)}"/>
				    
			        <c:forEach var="index" items="${pageInfo.listIndex}" varStatus="loop">
			                <c:choose>
			                    <c:when test="${pageInfo.currentPage eq index}">
			                        <li class="active"><a href="Controller?command=a_show_list_film&page=${index}&start=${start}">${index}</a></li>
			                    </c:when>
			                    <c:otherwise>
			                        <li><a href="Controller?command=a_show_list_film&page=${index}&start=${start}">${index}</a></li>
			                    </c:otherwise>
			                </c:choose>
			        </c:forEach>
			        
			        <c:if test="${pageInfo.currentPage lt pageInfo.countPages}">
						<li><a href="Controller?command=a_show_list_film&page=${pageInfo.currentPage + 1}&start=${pageInfo.currentPage + 1}">Next</a></li>
					</c:if>
				</ul>
			</div>
			</section>
			<!-- content -->
		</div>
	</div>
	<c:import url="notContent/footer.jsp" />
</body>
</html>