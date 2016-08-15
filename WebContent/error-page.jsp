<%@ page isErrorPage="true" language="java"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>FilmStore</title>
<!-- <meta charset="utf-8">-->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="description"
	content="FilmStore permits you buy and see new films, make comments of films">
<meta name="keywords" content="buy new films,watch new films ">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link href="css/styles.css" rel="stylesheet">
<link href="css/errorpage.css" rel="stylesheet">
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
</head>
<body>
	<div class="wrapper container">
		<c:import url="jsp/notContent/header.jsp" />
		<c:import url="jsp/notContent/adminmenu.jsp" />
		<c:import url="jsp/notContent/carousel.jsp" />
		<div class="wrapper row">
			<c:import url="jsp/notContent/aside.jsp" />
			<section class="col-md-9">
				<div class="pagenot">
					<h2>404</h2>
					<div class="pagenot-text">
						<h3>Oops No template Found</h3>
					</div>
					<p>
						It looks like nothing was found at this location. Maybe try go to
						<a href="index.html">home</a> page or use the search form above
						this page?
					</p>
				</div>
			</section>
		</div>
	</div>
	<c:import url="jsp/notContent/footer.jsp" />
</body>
</html>