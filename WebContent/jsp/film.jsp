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
<fmt:message bundle="${locale}" key="locale.filmpage.comments"
	var="comments" />
<fmt:message bundle="${locale}" key="locale.showfilm.makeComment"
	var="makeComment" />
<fmt:message bundle="${locale}"
	key="locale.filmpage.infoForUnregistrUserP1" var="infoMessage1" />
<fmt:message bundle="${locale}"
	key="locale.filmpage.infoForUnregistrUserP2" var="infoMessage2" />
<fmt:message bundle="${locale}"
	key="locale.filmpage.infoForUnregistrUserP3" var="infoMessage3" />
<fmt:message bundle="${locale}" key="locale.filmpage.send" var="send" />
<fmt:message bundle="${locale}" key="locale.filmpage.yourComment"
	var="yourComment" />
<fmt:message bundle="${locale}" key="locale.filmpage.noComments"
	var="noComments" />
<fmt:message bundle="${locale}"
	key="locale.filmpage.commentCreationFailed" var="commentCreationFailed" />
<fmt:message bundle="${locale}"
	key="locale.filmpage.commentIncorrectContent"
	var="commentIncorrectContent" />
<fmt:message bundle="${locale}" key="locale.filmpage.filmNotFound"
	var="filmNotFoundMessage" />
<fmt:message bundle="${locale}" key="locale.filmpage.putInBasket"
	var="putInBasket" />
<fmt:message bundle="${locale}" key="locale.filmpage.makeOrder"
	var="makeOrder" />
</head>
<body>
	<div class="wrapper container">
		<c:import url="notContent/header.jsp" />
		<c:import url="notContent/adminmenu.jsp" />
		<c:import url="notContent/carousel.jsp" />>
		<div class="wrapper row">
			<c:import url="notContent/aside.jsp" />
			<section class="col-md-9">
				<c:choose>
					<c:when test="${requestScope.filmNotFound == null}">
						<h1>${sessionScope.filmInfo.name}</h1>
						<article class="row">
							<!-- Film image -->
							<div class="col-md-4 row-offset">
								<div class="mosaic-block" style="height: 290px">
									<div>
										<img src="${sessionScope.filmInfo.image}"
											alt="${sessionScope.filmInfo.name}" width="200" height="250" />
									</div>
									<form method="post">
										<div style="margin: 10px 30px">
											<button type="submit" class="btn btn-primary"
											formaction="Controller?command=make_order" 
											id="order">${makeOrder}</button>
										</div>
									</form>
								</div>
							</div>
							<!-- Film image -->
							<!-- Film description -->
							<div class="col-md-8 row-offset">
								<div class="pull-right text-right">
									<ul class="list-unstyled text-right">
										<li><b>${countFilms}:</b><span>${sessionScope.filmInfo.countFilms}</span></li>
										<li><b>${quality}:</b>
											${sessionScope.filmInfo.quality.getNameQuality()}</li>
										<li><b>${price}:</b> <span>${sessionScope.filmInfo.price}</span></li>
									</ul>
								</div>
								<ul class="list-unstyled">
									<li><b>${genre}:</b>&nbsp;<span>${sessionScope.filmInfo.genre}</span>.</li>
									<li><b>${yearOfRelease}</b>&nbsp;<time>${sessionScope.filmInfo.yearOfRelease}</time>.
									</li>
									<li><b>${country}:</b>&nbsp;<span>${sessionScope.filmInfo.country}</span>.</li>
									<li><b>${filmDirector}:</b>&nbsp;<span>${sessionScope.filmInfo.filmDirector.fio}</span>.</li>
									<li class="row-offset">
										<p>${sessionScope.filmInfo.description}</p>
									</li>
									<li><b>${actors}:</b>&nbsp; <span> <c:forEach
												var="actor" items="${sessionScope.filmInfo.actors}"
												varStatus="loop">
												<c:choose>
													<c:when test="${loop.index != filmInfo.actors.size()-1}">
									        ${actor.fio},
									    </c:when>
													<c:otherwise>
										    ${actor.fio}
									    </c:otherwise>
												</c:choose>
											</c:forEach>
									</span></li>
								</ul>
							</div>
							<!-- Film description -->
						</article>
						<article class="row">
							<!-- List comments -->
							<div class="col-md-12">
								<c:choose>
									<c:when test="${requestScope.listComNotFound == null}">
										<h3>${comments}</h3>
									</c:when>
									<c:otherwise>
										<h3>${noComments}</h3>
									</c:otherwise>
								</c:choose>
								<c:forEach var="comment" items="${sessionScope.listComment}">
									<article class="commennts clearfix">
										<div class="comment-avatar">
											<img src="images/avatar.png" alt="avatar">
										</div>
										<div class="comment-box avatar-indent">
											<span class="comment-author">${comment.id.userEmail}</span>
											<time datetime="${comment.date}" class="comment-date">
												<fmt:formatDate pattern="dd/MM/yyyy HH:mm"
													value="${comment.date}" />
											</time>
											<div class="comment-body" id="comment-body-1">
												${comment.content}<a class="comments-buttons" href="#">Answer</a>
											</div>
										</div>
									</article>
								</c:forEach>
							</div>
							<!-- List comments -->
						</article>
						<c:choose>
							<c:when test="${sessionScope.userRole != 'ROLE_GUEST'}">
								<article class="row col-md-12">
									<!-- Form for creating comment -->
									<h3>${makeComment}</h3>
									<c:if test="${requestScope.creationFailed == 'true'}">
										<span style="color: red">${commentCreationFailed}</span>
									</c:if>
									<form action="Controller" method="post">
										<input type="hidden" name="command" value="make_comment" /> 
										<input type="hidden" name="userEmail"
											   value="${sessionScope.userEmail}" /> 
										<input type="hidden" name="filmId" value="${sessionScope.film.id}" />
										<p>
											<textarea name="content" placeholder="${yourComment}"
												cols="38" rows="8" maxlength="300"></textarea>
										</p>
										<p>
											<button type="submit" class="btn btn-primary">${send}</button>
										</p>
										<c:if test="${requestScope.incorrectContent == 'true'}">
											<p style="color: red">${commentIncorrectContent}</p>
										</c:if>
									</form>
									<!-- Form for creating comment -->
								</article>
							</c:when>
							<c:otherwise>
								<article class="row">
									<div class="col-md-12">
										<div class="alert alert-info">${infoMessage1}${infoMessage2}${infoMessage3}</div>
									</div>
								</article>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<h2>${filmNotFoundMessage}</h2>
					</c:otherwise>
				</c:choose>
			</section>
		</div>
	</div>
	<c:import url="notContent/footer.jsp" />
</body>
</html>