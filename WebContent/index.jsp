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
<fmt:setBundle basename="localization.locale" var="loc" />
<fmt:message bundle="${loc}" key="locale.message" var="message" />
</head>
<body>
	<div class="wrapper container">
		<c:import url="jsp/header.jsp" />
		<c:import url="jsp/adminmenu.jsp" />
		<c:import url="jsp/carousel.jsp" />
		<div class="wrapper row">
			<c:import url="jsp/aside.jsp" />
			<!-- content -->
			<section class="col-md-9">
				<h1>New films</h1>
				<c:out value="Hellomessage:${message}" />
				<c:out value="Hellomessage:${sessionScope.locale}" />
				<a href="Controller?command=show_list_film">show_films</a>
				<c:if test="${sessionScope.userRole == 'ROLE_ADMIN'}">
					<c:out value="Hellomessageadmin:${message}" />
				</c:if>
				<!--Bar-->
				<article class="row">
					<div class="col-md-4 row-offset">
						<div class="mosaic-block bar">
							<div class="mosaic-overlay">
								<button type="button" class="btn btn-default btn-default-custom">
									<span class="glyphicon glyphicon-shopping-cart"></span>
								</button>
								<button type="button" class="btn btn-default btn-default-custom">
									<span class="glyphicon glyphicon-star"></span>
								</button>
								<button type="button" class="btn btn-default btn-default-custom">
									<span class="glyphicon glyphicon-film"></span>
								</button>
							</div>
							<div class="mosaic-backdrop">
								<img src="images/2.jpg" alt="Mother's Day" width="200"
									height="250" />
							</div>
						</div>
					</div>
					<div class="col-md-8 row-offset">
						<div class="pull-right text-right">
							<ul class="list-unstyled text-right">
								<li><b title="Count films in storehouse">Count films:</b><span>120</span>
								</li>
								<li><b>Quality:</b> WEB-DL</li>
								<li><b title="One film price">Price:</b> <span>150$</span>
								</li>
							</ul>
						</div>
						<b><a title="Mother's Day (2016) - buy film"
							href="/film/419443/">Mother's Day</a></b>
						<ul class="list-unstyled">
							<li><b>Genre:</b>&nbsp;<span>Drama,Comedy</span>.</li>
							<li><b>Year of release:</b>&nbsp;<time datetime="2016">2016</time>.
							</li>
							<li><b>Country:</b>&nbsp;<span>USA</span>.</li>
							<li><b>Film director:</b>&nbsp;<span>Garry Marshal</span>.</li>
							<li class="row-offset">
								<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.
									Cras accumsan sed mauris a sollicitudin. Phasellus orci lectus,
									tincidunt in velit nec, convallis pretium arcu. Phasellus at
									convallis metus. Mauris vitae felis nec orci facilisis iaculis.
									Fusce sed cursus nibh. Donec accumsan quam sit amet pulvinar
									varius. Maecenas posuere vulputate suscipit. Duis tristique
									pellentesque sem id scelerisque. Quisque varius imperdiet
									placerat. Donec rhoncus lectus eu nisl congue auctor. Nunc
									egestas in lorem a suscipit. Aliquam aliquet aliquam est ut
									posuere. Proin magna ipsum, efficitur in pharetra sed, finibus
									pharetra eros.</p>
							</li>
							<li><b>Actors:</b>&nbsp; <span>Julia Roberts,Kate
									Hadson,Kaleb Braun,Timoti Olifent,Shey Mitchel</span> <a
								href="/filmAllInf">...&nbsp;»</a></li>
						</ul>
					</div>
				</article>
				<table border="1">
					<c:forEach var="room" items="${requestScope.listFilm}">
						<tr>
							<td><c:out value="${room.name}" /></td>
							<td><c:out value="${room.genre}" /></td>
							<td><c:out value="${room.description}" /></td>
							<td><button type="submit" class="btn btn-primary">Update</button></td>
							<td><button type="submit" class="btn btn-primary">Delete</button></td>
						</tr>
					</c:forEach>
				</table>
			</section>
			<div class="row col-md-6 col-md-offset-5">
				<ul class="pagination">
					<li><a href="#">«</a></li>
					<li><a href="#">1</a></li>
					<li><a href="#">2</a></li>
					<li><a href="#">3</a></li>
					<li><a href="#">4</a></li>
					<li><a href="#">5</a></li>
					<li><a href="#">»</a></li>
				</ul>
			</div>
			<!-- content -->
		</div>
	</div>
	<c:import url="jsp/footer.jsp" />
</body>
</html>