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
		document.getElementById("uploadFile").value = 
			str.replace(/C:\\fakepath\\/, "");
	}
</script>
<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
			<script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
		<![endif]-->
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.locale" var="locale" />
<fmt:message bundle="${locale}" key="locale.filmedit.createFilm"
	var="createFilm" />
<fmt:message bundle="${locale}" key="locale.filmedit.filmName"
	var="filmName" />
<fmt:message bundle="${locale}" key="locale.filmedit.enterFilmName"
	var="enterFilmName" />
<fmt:message bundle="${locale}" key="locale.filmedit.yearOfRelease"
	var="yearOfRelease" />
<fmt:message bundle="${locale}" key="locale.filmedit.enterYearOfRel"
	var="enterYearOfRel" />
<fmt:message bundle="${locale}" key="locale.filmedit.price"
	var="price" />
<fmt:message bundle="${locale}" key="locale.filmedit.enterPrice"
	var="enterPrice" />
<fmt:message bundle="${locale}" key="locale.filmedit.countFilms"
	var="countFilms" />
<fmt:message bundle="${locale}" key="locale.filmedit.enterCountFilms"
	var="enterCountFilms" />
<fmt:message bundle="${locale}" key="locale.filmedit.chooseQuality"
	var="chooseQuality" />
<fmt:message bundle="${locale}" key="locale.filmedit.chooseCounties"
	var="chooseCounties" />
<fmt:message bundle="${locale}" key="locale.filmedit.chooseGenres"
	var="chooseGenres" />
<fmt:message bundle="${locale}" key="locale.filmedit.chooseFilmDirector"
	var="chooseFilmDirector" />
<fmt:message bundle="${locale}" key="locale.filmedit.chooseActors"
	var="chooseActors" />
<fmt:message bundle="${locale}" key="locale.filmedit.filmDescription"
	var="filmDescription" />
<fmt:message bundle="${locale}" key="locale.filmedit.filmDescriptionPlace"
	var="filmDescriptionPlace" />
<fmt:message bundle="${locale}" key="locale.filmedit.chooseImage"
	var="chooseImage" />	
</head>
<body>
	<div class="wrapper container">
		<c:import url="header.jsp" />
		<c:import url="adminmenu.jsp" />
		<c:import url="carousel.jsp" />
		<div class="wrapper row">
			<c:import url="aside.jsp" />
			<section class="col-md-9">
				<h1>${createFilm}</h1>
				<article class="row">
					<div class="col-md-8">
						<form name="filmCreating" >
							<div class="row-offset">
								<label for="name">${filmName}:</label> <input type="text"
									class="form-control" id="name" name="name"
									placeholder="${enterFilmName}" />
							</div>
							<div class="row-offset">
								<label for="yearOfRelease">${yearOfRelease}:</label> <input
									type="text" class="form-control" id="yearOfRelease"
									name="yearOfRelease" placeholder="${enterYearOfRel}" />
							</div>
							<div class="row-offset">
								<label for="price">${price}:</label> <input type="text"
									class="form-control" id="price" name="price"
									placeholder="${enterPrice}" />
							</div>
							<div class="row-offset">
								<label for="countFilms">${countFilms}:</label> <input type="text"
									class="form-control" id="countFilms" name="countFilms"
									placeholder="${enterCountFilms}" />
							</div>
							<div class="row-offset">
								<select name="quality" class="selectpicker"
									data-style="btn-primary" data-live-search="true"
									title="${chooseQuality}">
									<option value="DVDRip">DVDRip</option>
									<option value="CAMPRip">CAMPRip</option>
									<option value="WEB-DL">WEB-DL</option>
									<option value="WEB-DLRip">WEB-DLRip</option>
								</select>
							</div>
							<div class="row-offset">
									<select name="list_countries" multiple class="selectpicker" data-style="btn-primary" 
										data-live-search="true" title="${chooseCounties}">
									  <option value="США">США</option>
									  <option value="Англия">Англия</option>
									  <option value="Япония">Япония</option>
									  <option value="Китай">Китай</option>
									  <option value="Италия">Италия</option>
									  <option value="Франция">Франция</option>
									  <option value="Россия">Россия</option>
									  <option value="Германия">Германия</option>
									  <option value="Канада">Канада</option>
									  <option value="Испания">Испания</option>
									</select>
							</div>
							<div class="row-offset">
									<select name="genres" multiple class="selectpicker" 
									data-style="btn-primary" 
										data-live-search="true" title="${chooseGenres}">
									  <option value="Драма">Драма</option>
									  <option value="Комедия">Комедия</option>
									  <option value="Мелодрама">Мелодрама</option>
									  <option value="Триллер">Триллер</option>
									  <option value="Криминал">Криминал</option>
									  <option value="Детктив">Детктив</option>
									  <option value="Фантастика">Фантастика</option>
									  <option value="Боевик">Боевик</option>
									  <option value="Ужасы">Ужасы</option>
									  <option value="Биография">Биография</option>
									  <option value="Мистика">Мистика</option>
									  <option value="Мультфильм">Мультфильм</option>
									  <option value="Исторический">Исторический</option>
									  <option value="Документальный">Документальный</option>
									</select>
							</div>
							<div class="row-offset">
								<select name="film_director" class="selectpicker"
									data-style="btn-primary" data-live-search="true"
									title="${chooseFilmDirector}">
									<c:forEach var="filmDir" items="${requestScope.listFilmDir}">
										<option value="${filmDir.id}">${filmDir.fio}</option>
									</c:forEach>
								</select>
							</div>
							<div class="row-offset">
								<select name="list_actors" multiple multiple
									class="selectpicker" data-style="btn-primary"
									data-live-search="true" title="${chooseActors}">
									<c:forEach var="actor" items="${requestScope.listActors}">
										<option value="${actor.id}">${actor.fio}</option>
									</c:forEach>
								</select>
							</div>
							<div class="row col-md-12">
								<h3>${filmDescription}</h3>
								<p>
									<textarea name="description" placeholder="${filmDescriptionPlace}"
										cols="38" rows="8" maxlength="500"></textarea>
								</p>
							</div>
							<div>
								<input id="uploadFile" name="image" placeholder="Choose File"
									disabled="disabled" />
								<div class="fileUpload btn btn-primary">
									<span><span
										class="icon-span-filestyle glyphicon glyphicon-folder-open">
										</span>${chooseImage}</span> 
										<input id="uploadBtn" type="file" class="upload"
										onchange="showFileName(this.value);" />
								</div>
								</div>
								<div class="row-offset">
									<button type="submit" class="btn btn-primary">${createFilm}</button>
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