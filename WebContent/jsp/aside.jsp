<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.locale" var="locale" />
<fmt:message bundle="${locale}" key="locale.aside.top10" var="top10" />
<fmt:message bundle="${locale}" key="locale.aside.popularFilms" var="popFilms" />
<fmt:message bundle="${locale}" key="locale.aside.filmCollections" var="filmCollection" />
<fmt:message bundle="${locale}" key="locale.aside.newFilms" var="newFilms" />
<fmt:message bundle="${locale}" key="locale.aside.allFilms" var="allFilms" />
<fmt:message bundle="${locale}" key="locale.aside.lastFilms" var="lastFilms" />
<fmt:message bundle="${locale}" key="locale.aside.ourOffices" var="ourOffices" />
<aside class="col-md-3">
	<ul class="list-group submenu">
		<li class="list-group-item"><a href="/top10/">${top10}</a></li>
		<!--Топ 10 фильмов -->
		<li class="list-group-item"><a href="/popularFilms/">${popFilms}</a></li>
		<!--Популярные фильмы-->
		<li class="list-group-item"><a href="/filmCollection/">${filmCollection}</a></li>
		<!--Сборки фильмов -->
		<li class="list-group-item"><a href="/newFilms/">${newFilms}</a></li>
		<!--Поступления -->
		<li class="list-group-item"><a href="/allFilms/">${allFilms}</a></li>
		<!--Просмотр всех фильмов -->
		<li class="list-group-item"><a href="/lastFilms/">${lastFilms}</a></li>
		<!--Последние просмотренные фильмы-->
	</ul>
	<div class="panel panel-primary">
		<div class="panel-heading">${ourOffices}</div>
		<div class="panel-body">
			<img src="images/map.png" class="img-responsive" alt="${ourOffices}">
		</div>
	</div>
</aside>