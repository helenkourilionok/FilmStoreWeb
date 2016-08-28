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
<fmt:message bundle="${locale}" key="locale.filmpage.putInBasket"
	var="putInBasket" />
<fmt:message bundle="${locale}" key="locale.index.noneFilmWasFound"
	var="noneFilmWasFound" />
</head>
<body>
	<div class="wrapper container">
		<c:import url="jsp/notContent/header.jsp" />
		<c:import url="jsp/notContent/adminmenu.jsp" />
		<c:import url="jsp/notContent/carousel.jsp" />
		<div class="wrapper row">
			<c:import url="jsp/notContent/aside.jsp" />
			<!-- content -->
			<section class="col-md-9">
				<h1>${allFilms}</h1>
				<c:if test="${requestScope.notFoundFilmForRequest == 'true'}">
					<h2>${noneFilmWasFound}</h2>
				</c:if>
				<!--Bar-->
				<c:forEach var="film" items="${requestScope.listFilm}">
					<article class="row">
						<div class="col-md-4 row-offset">
							<div class="mosaic-block bar">
									<div class="mosaic-overlay">
										<form method="post">
											<button title="${putInBasket}" type="submit"
												class="btn btn-default btn-default-custom"
												formaction="Controller?command=put_in_basket&id=${film.id}">
												<span class="glyphicon glyphicon-shopping-cart"></span>
											</button>
											<button title="${makeComment}" type="submit"
												class="btn btn-default btn-default-custom"
												formaction="Controller?command=show_comment_page&id=${film.id}">
												<span class="glyphicon glyphicon-star"></span>
											</button>
											<button title="${makeComment}" type="submit"
												class="btn btn-default btn-default-custom"
												formaction="Controller?command=show_comment_page&id=${film.id}">
												<span class="glyphicon glyphicon-film"></span>
											</button>
										</form>
									</div>
								<div class="mosaic-backdrop">
								<img src="${film.image}"
											alt="${film.name}" width="200" height="250" />
								</div>
							</div>
						</div>
						<div class="col-md-8 row-offset">
							<div class="pull-right text-right">
								<ul class="list-unstyled text-right">
									<li><b>${countFilms}:</b><span>${film.countFilms}</span></li>
									<li><b>${quality}:</b> ${film.quality.getNameQuality()}</li>
									<li><b>${price}:</b> <span>${film.price}</span></li>
								</ul>
							</div>
							<b><a title="${film.name}"
								href="Controller?command=show_comment_page&id=${film.id}">${film.name}</a></b>
							<ul class="list-unstyled">
								<li><b>${genre}:</b>&nbsp;<span>${film.genre}</span>.</li>
								<li><b>${yearOfRelease}</b>&nbsp;<time>${film.yearOfRelease}</time>.
								</li>
								<li><b>${country}:</b>&nbsp;<span>${film.country}</span>.</li>
								<li><b>${filmDirector}:</b>&nbsp;<span>${film.filmDirector.fio}</span>.</li>
								<li class="row-offset">
									<p>${film.description}</p>
								</li>
								<li><b>${actors}:</b>&nbsp; <span> <c:forEach
											var="actor" items="${film.actors}" varStatus="loop">
											<c:choose>
												<c:when test="${loop.index != film.actors.size()-1}">
									        		${actor.fio},
									    		</c:when>
												<c:otherwise>
										    		${actor.fio}
									    		</c:otherwise>
											</c:choose>
										</c:forEach>
								</span> <a href="Controller?command=show_comment_page&id=${film.id}">...&nbsp;»</a></li>
							</ul>
						</div>
					</article>
				</c:forEach>
			</section>
			<div class="row col-md-6 col-md-offset-5">
				<ul class="pagination">
					<c:if test="${pageInfo.currentPage != 1}">
				    	<li><a href="Controller?command=show_list_film&page=${pageInfo.currentPage - 1}&start=${pageInfo.currentPage - 1}">Previous</a></li>
				    </c:if>
				    <c:set var="start" scope="request" value="${pageInfo.listIndex.get(0)}"/>
				    
			        <c:forEach var="index" items="${pageInfo.listIndex}" varStatus="loop">
			                <c:choose>
			                    <c:when test="${pageInfo.currentPage eq index}">
			                        <li class="active"><a href="Controller?command=show_list_film&page=${index}&start=${start}">${index}</a></li>
			                    </c:when>
			                    <c:otherwise>
			                        <li><a href="Controller?command=show_list_film&page=${index}&start=${start}">${index}</a></li>
			                    </c:otherwise>
			                </c:choose>
			        </c:forEach>
			        
			        <c:if test="${pageInfo.currentPage lt pageInfo.countPages}">
						<li><a href="Controller?command=show_list_film&page=${pageInfo.currentPage + 1}&start=${pageInfo.currentPage + 1}">Next</a></li>
					</c:if>
				</ul>
			</div>
			<!-- content -->
		</div>
	</div>
	<c:import url="jsp/notContent/footer.jsp" />
</body>
</html>