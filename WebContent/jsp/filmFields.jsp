<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.locale" var="locale" />
<fmt:message bundle="${locale}" key="locale.filmedit.filmName"
	var="filmName" />
<fmt:message bundle="${locale}" key="locale.filmedit.enterFilmName"
	var="enterFilmName" />
<fmt:message bundle="${locale}" key="locale.filmedit.yearOfRelease"
	var="yearOfRelease" />
<fmt:message bundle="${locale}" key="locale.filmedit.enterYearOfRel"
	var="enterYearOfRel" />
<fmt:message bundle="${locale}" key="locale.filmedit.price" var="price" />
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
<fmt:message bundle="${locale}"
	key="locale.filmedit.filmDescriptionPlace" var="filmDescriptionPlace" />
<fmt:message bundle="${locale}" key="locale.filmedit.chooseImage"
	var="chooseImage" />
<div class="row-offset">
	<label for="name">${filmName}:</label> <input type="text"
		class="form-control" id="name" name="name"
		placeholder="${enterFilmName}" value="${sessionScope.film.name}" />
</div>
<div class="row-offset">
	<label for="yearOfRelease">${yearOfRelease}:</label> <input type="text"
		class="form-control" id="yearOfRelease" name="yearOfRelease"
		placeholder="${enterYearOfRel}" value="${sessionScope.film.yearOfRelease}" />
</div>
<div class="row-offset">
	<label for="price">${price}:</label> <input type="text"
		class="form-control" id="price" name="price"
		placeholder="${enterPrice}" value="${sessionScope.film.price}" />
</div>
<div class="row-offset">
	<label for="countFilms">${countFilms}:</label> <input type="text"
		class="form-control" id="countFilms" name="countFilms"
		placeholder="${enterCountFilms}" value="${sessionScope.film.countFilms}"/>
</div>
<div class="row-offset">
	<select name="quality" class="selectpicker" data-style="btn-primary"
	    title="${chooseQuality}">
		<c:forEach var="quality" items="${requestScope.listQuality}">
			<c:choose>
				<c:when test="${sessionScope.film.quality.getNameQuality().equals(quality)}">
					<option value="${quality}" selected>${quality}</option>
				</c:when>
				<c:otherwise>
					<option value="${quality}">${quality}</option>
				</c:otherwise>
			</c:choose>			
		</c:forEach>
	</select>
</div>
<div class="row-offset">
	<select name="list_countries" multiple class="selectpicker"
		data-style="btn-primary" data-live-search="true"
		title="${chooseCounties}">
		<c:forEach var="country" items="${requestScope.listCountries}">
			<c:choose>
				<c:when test="${sessionScope.film.country.contains(country)}">
					<option value="${country}" selected>${country}</option>
				</c:when>
				<c:otherwise>
					<option value="${country}">${country}</option>
				</c:otherwise>
			</c:choose>			
		</c:forEach>
	</select>
</div>
<div class="row-offset">
	<select name="genres" multiple class="selectpicker"
		data-style="btn-primary" data-live-search="true"
		title="${chooseGenres}">
		<c:forEach var="genre" items="${requestScope.listGenres}">
			<c:choose>
				<c:when test="${sessionScope.film.genre.contains(genre)}">
					<option value="${genre}" selected>${genre}</option>
				</c:when>
				<c:otherwise>
					<option value="${genre}">${genre}</option>
				</c:otherwise>
			</c:choose>			
		</c:forEach>
	</select>
</div>
<div class="row-offset">
	<select name="film_director" class="selectpicker"
		data-style="btn-primary" data-live-search="true"
		title="${chooseFilmDirector}">
		<c:forEach var="filmDir" items="${requestScope.listFilmDir}">
			<c:choose>
				<c:when test="${sessionScope.film.filmDirector.equals(filmDir)}">
					<option value="${filmDir.id}" selected>${filmDir.fio}</option>
				</c:when>
				<c:otherwise>
					<option value="${filmDir.id}">${filmDir.fio}</option>
				</c:otherwise>
			</c:choose>			
		</c:forEach>
	</select>
</div>
<div class="row-offset">
	<select name="list_actors" multiple class="selectpicker"
		data-style="btn-primary" data-live-search="true"
		title="${chooseActors}">
		<c:forEach var="actor" items="${requestScope.listActors}">
			<c:choose>
				<c:when test="${sessionScope.film.actors.contains(actor)}">
						<option value="${actor.id}" selected>${actor.fio}</option>
				</c:when>
				<c:otherwise>
						<option value="${actor.id}">${actor.fio}</option>
				</c:otherwise>
			</c:choose>	
		</c:forEach>
	</select>
</div>
<div class="row col-md-12">
	<h3>${filmDescription}</h3>
	<p>
		<textarea name="description" placeholder="${filmDescriptionPlace}"
			cols="38" rows="8" maxlength="500" >${sessionScope.film.description}</textarea>
	</p>
</div>
<div class="row-offset">
	<div>
		<div id="imageForFilm">
			<c:if test="${sessionScope.film != null}">
					<span>Current image</span>
					<img src="${sessionScope.film.image}" alt="${sessionScope.film.name}" width="200" height="250"/>
			</c:if>
		</div>
		<input id="uploadFile" name="image" placeholder="${chooseImage}" 
		value="${fn:replace(sessionScope.film.image,'images/', '')}" readonly />
		<div class="fileUpload btn btn-primary">
			<span> 
			<span class="icon-span-filestyle glyphicon glyphicon-folder-open">
			</span>${chooseImage}</span> 
			<input id="uploadBtn" name="file" type="file"
				class="upload" onchange="showFileName(this.value);" />
		</div>
	</div>
</div>