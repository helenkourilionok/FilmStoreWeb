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
	function look(type, type1) {
		param = document.getElementById(type);
		if (param.style.display == "none")
			param.style.display = "block";
		else
			param.style.display = "none"
		param1 = document.getElementById(type1);
		if (param1.style.display == "none")
			param1.style.display = "block";
		else
			param1.style.display = "none"
	}
</script>
<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
			<script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
		<![endif]-->
</head>
<body>
	<div class="wrapper container">
		<c:import url="header.jsp" />
		<c:import url="adminmenu.jsp" />
		<c:import url="carousel.jsp" />
		<div class="wrapper row">
			<c:import url="aside.jsp" />
			<section class="col-md-9">
				<h1>Mother's Day</h1>
				<article class="row">
					<div class="col-md-4 row-offset">
						<div>
							<img src="images/2.jpg" alt="Mother's Day" width="200"
								height="250" />
						</div>
						<div style="margin: 10px 30px">
							<button type="submit" class="btn btn-primary" id="basket"
								onClick="look('order','basket'); return false;">Put in
								basket</button>
						</div>
						<div style="margin: 10px 30px">
							<button type="submit" class="btn btn-primary" id="order"
								style="display: none">Make order</button>
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
								href="/filmAllInf">...&nbsp;Â»</a></li>
						</ul>
					</div>
				</article>
				<article class="row">
					<div class="col-md-12">
						<h3>COMMENTS</h3>
						<article class="commennts clearfix">
							<div class="comment-avatar">
								<img src="images/avatar.png" alt="avatar">
							</div>
							<div class="comment-box avatar-indent">
								<span class="comment-author">EmailUser</span>
								<time datetime="1914-12-20 08:30" class="comment-date">1914-12-20
									08:30</time>
								<div class="comment-body" id="comment-body-1">
									Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras
									accumsan sed mauris a sollicitudin. Phasellus orci lectus,
									tincidunt in velit nec, convallis pretium arcu. Phasellus at
									convallis metus. Mauris vitae felis nec orci facilisis iaculis.<a
										class="comments-buttons" href="#">Answer</a>
								</div>
							</div>
						</article>
						<article class="commennts clearfix">
							<div class="comment-avatar">
								<img src="images/avatar.png" alt="avatar">
							</div>
							<div class="comment-box avatar-indent">
								<span class="comment-author">EmailUser</span>
								<time datetime="1914-12-20 08:30" class="comment-date">1914-12-20
									08:30</time>
								<div class="comment-body" id="comment-body-2">
									Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras
									accumsan sed mauris a sollicitudin. Phasellus orci lectus,
									tincidunt in velit nec, convallis pretium arcu. Phasellus at
									convallis metus. Mauris vitae felis nec orci facilisis iaculis.<a
										class="comments-buttons" href="#">Answer</a>
								</div>
							</div>
						</article>
					</div>
				</article>
				<c:choose>
					<c:when test="${sessionScope.userRole =='ROLE_USER' or sessionScope.userRole == 'ROLE_ADMIN'}">
						<article class="row col-md-12">
							<h3>MAKE COMMENT</h3>
							<form action="/makeComment" method="post">
								<p>
									<textarea name="description" placeholder="Your comment" cols="38"
										rows="8" maxlength="300"></textarea>
								</p>
								<p>
									<button type="submit" class="btn btn-primary">Send</button>
								</p>
							</form>
						</article>
					</c:when>
					<c:otherwise>
						<article class="row">
							<div class="col-md-12">
								<div class="alert alert-info">Для добавления отзывов,
									необходимо зарегистрироваться и войти на сайт.</div>
							</div>
						</article>
					</c:otherwise>
				</c:choose>
			</section>
		</div>
	</div>
	<c:import url="footer.jsp" />
</body>
</html>