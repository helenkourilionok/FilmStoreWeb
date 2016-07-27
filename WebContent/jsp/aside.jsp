<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.locale" var="locale" />
<fmt:message bundle="${locale}" key="locale.aside.top_10" var="top_10" />
<fmt:message bundle="${locale}" key="locale.aside.popular_films" var="pop_films" />
<fmt:message bundle="${locale}" key="locale.aside.film_collections" var="film_collection" />
<fmt:message bundle="${locale}" key="locale.aside.new_films" var="new_films" />
<fmt:message bundle="${locale}" key="locale.aside.all_films" var="all_films" />
<fmt:message bundle="${locale}" key="locale.aside.last_films" var="last_films" />
<fmt:message bundle="${locale}" key="locale.aside.our_offices" var="our_offices" />
<aside class="col-md-3">
	<ul class="list-group submenu">
		<li class="list-group-item"><a href="/top10/">${top_10}</a></li>
		<!--Топ 10 фильмов -->
		<li class="list-group-item"><a href="/popularfilms/">${pop_films}</a></li>
		<!--Популярные фильмы-->
		<li class="list-group-item"><a href="/filmcollection/">${film_collection}</a></li>
		<!--Сборки фильмов -->
		<li class="list-group-item"><a href="/newfilms/">${new_films}</a></li>
		<!--Поступления -->
		<li class="list-group-item"><a href="/allfilms/">${all_films}</a></li>
		<!--Просмотр всех фильмов -->
		<li class="list-group-item"><a href="/lastfilms/">${last_films}</a></li>
		<!--Последние просмотренные фильмы-->
	</ul>
	<div class="panel panel-primary">
		<div class="panel-heading">${our_offices}</div>
		<div class="panel-body">
			<img src="images/map.png" class="img-responsive" alt="${our_offices}">
		</div>
	</div>
</aside>