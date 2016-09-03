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
<fmt:message bundle="${locale}" key="locale.showfilm.allFilms"
	var="allFilms" />
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
		<c:import url="notContent/carousel.jsp" />
		<div class="wrapper row">
			<c:import url="notContent/aside.jsp" />
			<section class="col-md-9">
				<c:choose>
					<c:when test="${requestScope.filmNotFound == null}">
						<c:import url="filmDescription.jsp" />
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
										<input type="hidden" name="filmId" value="${sessionScope.filmInfo.id}" />
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