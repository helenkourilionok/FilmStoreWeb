<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.locale" var="locale" />
<fmt:message bundle="${locale}" key="locale.adminmenu.admin_tools" var="admin_tools" />
<fmt:message bundle="${locale}" key="locale.adminmenu.user" var="user" />
<fmt:message bundle="${locale}" key="locale.adminmenu.film_director" var="film_director" />
<fmt:message bundle="${locale}" key="locale.adminmenu.actor" var="actor" />
<fmt:message bundle="${locale}" key="locale.adminmenu.order" var="order" />
<fmt:message bundle="${locale}" key="locale.adminmenu.film" var="film" />
<fmt:message bundle="${locale}" key="locale.adminmenu.update" var="update" />
<fmt:message bundle="${locale}" key="locale.adminmenu.delete" var="delete" />
<fmt:message bundle="${locale}" key="locale.adminmenu.create" var="create" />
<fmt:message bundle="${locale}" key="locale.adminmenu.make_discount" var="make_discount" />
<fmt:message bundle="${locale}" key="locale.adminmenu.take_away_discount" var="take_away_discount" />
<fmt:message bundle="${locale}" key="locale.adminmenu.list_users" var="list_users" />
<fmt:message bundle="${locale}" key="locale.adminmenu.list_orders" var="list_orders" />
<fmt:message bundle="${locale}" key="locale.adminmenu.list_films" var="list_films" />
<fmt:message bundle="${locale}" key="locale.adminmenu.list_actors" var="list_actors" />
<fmt:message bundle="${locale}" key="locale.adminmenu.list_film_directors" var="list_film_directors" />
<c:if test="${sessionScope.userRole == 'ROLE_ADMIN'}">
<nav class="navbar navbar-default navbar-inverse">
	<ul class="nav navbar-nav">
		<li class="admin-panel">${admin_tools}</li>
		<li class="dropdown"><a href="#">${user}</a>
			<ul class="dropdown-menu">
				<li><a href="#">${update}</a></li>
				<li><a href="#">${delete}</a></li>
				<li><a href="#">${make_discount}</a></li>
				<li><a href="#">${take_away_discount}</a></li>
				<li><a href="#">${list_users}</a></li>
			</ul></li>
		<li class="dropdown"><a href="/about/">${order}</a>
			<ul class="dropdown-menu">
				<li><a href="#">${update}</a></li>
				<li><a href="#">${delete}</a></li>
				<li><a href="#">${list_orders}</a></li>
			</ul></li>
		<li class="dropdown"><a href="#">${film}<span></span></a>
			<ul class="dropdown-menu">
				<li><a href="#">${create}</a></li>
				<li><a href="#">${update}</a></li>
				<li><a href="#">${delete}</a></li>
				<li><a href="#">${list_films}</a></li>
			</ul></li>
		<li class="dropdown"><a href="/payment/">${actor}</a>
			<ul class="dropdown-menu">
				<li><a href="#">${create}</a></li>
				<li><a href="#">${update}</a></li>
				<li><a href="#">${delete}</a></li>
				<li><a href="#">${list_actors}</a></li>
			</ul></li>
		<li class="dropdown"><a href="/delivery/">${film_director}</a>
			<ul class="dropdown-menu">
				<li><a href="#">${create}</a></li>
				<li><a href="#">${update}</a></li>
				<li><a href="#">${delete}</a></li>
				<li><a href="#">${list_film_directors}</a></li>
			</ul></li>
	</ul>
</nav>
</c:if>