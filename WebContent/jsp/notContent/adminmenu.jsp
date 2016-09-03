<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.locale" var="locale" />
<fmt:message bundle="${locale}" key="locale.adminmenu.adminTools" var="adminTools" />
<fmt:message bundle="${locale}" key="locale.adminmenu.user" var="user" />
<fmt:message bundle="${locale}" key="locale.adminmenu.filmDirector" var="filmDirector" />
<fmt:message bundle="${locale}" key="locale.adminmenu.actor" var="actor" />
<fmt:message bundle="${locale}" key="locale.adminmenu.order" var="order" />
<fmt:message bundle="${locale}" key="locale.adminmenu.film" var="film" />
<fmt:message bundle="${locale}" key="locale.adminmenu.update" var="update" />
<fmt:message bundle="${locale}" key="locale.adminmenu.delete" var="delete" />
<fmt:message bundle="${locale}" key="locale.adminmenu.create" var="create" />
<fmt:message bundle="${locale}" key="locale.adminmenu.makeDiscount" var="makeDiscount" />
<fmt:message bundle="${locale}" key="locale.adminmenu.takeAwayDiscount" var="takeAwayDiscount" />
<fmt:message bundle="${locale}" key="locale.adminmenu.listUsers" var="listUsers" />
<fmt:message bundle="${locale}" key="locale.adminmenu.listOrders" var="listOrders" />
<fmt:message bundle="${locale}" key="locale.adminmenu.listFilms" var="listFilms" />
<fmt:message bundle="${locale}" key="locale.adminmenu.listActors" var="listActors" />
<fmt:message bundle="${locale}" key="locale.adminmenu.listFilmDirectors" var="listFilmDirectors" />
<c:if test="${sessionScope.userRole == 'ROLE_ADMIN'}">
<nav class="navbar navbar-default navbar-inverse">
	<ul class="nav navbar-nav">
		<li class="admin-panel">${adminTools}</li>
		<li class="dropdown"><a href="#">${user}</a>
			<ul class="dropdown-menu">
				<li><a href="#">${update}</a></li>
				<li><a href="#">${delete}</a></li>
				<li><a href="Controller?command=a_make_discount_show_page">${makeDiscount}</a></li>
				<li><a href="#">${takeAwayDiscount}</a></li>
				<li><a href="#">${listUsers}</a></li>
			</ul></li>
		<li class="dropdown"><a href="/about/">${order}</a>
			<ul class="dropdown-menu">
				<li><a href="#">${update}</a></li>
				<li><a href="#">${delete}</a></li>
				<li><a href="#">${listOrders}</a></li>
			</ul></li>
		<li class="dropdown"><a href="#">${film}<span></span></a>
			<ul class="dropdown-menu">
				<li><a href="Controller?command=a_create_film_show_page">${create}</a></li>
				<li><a href="Controller?command=a_update_film_show_page">${update}</a></li>
				<li><a href="#">${delete}</a></li>
				<li><a href="Controller?command=a_show_list_film">${listFilms}</a></li>
			</ul></li>
		<li class="dropdown"><a href="/payment/">${actor}</a>
			<ul class="dropdown-menu">
				<li><a href="#">${create}</a></li>
				<li><a href="#">${update}</a></li>
				<li><a href="#">${delete}</a></li>
				<li><a href="#">${listActors}</a></li>
			</ul></li>
		<li class="dropdown"><a href="/delivery/">${filmDirector}</a>
			<ul class="dropdown-menu">
				<li><a href="#">${create}</a></li>
				<li><a href="#">${update}</a></li>
				<li><a href="#">${delete}</a></li>
				<li><a href="#">${listFilmDirectors}</a></li>
			</ul></li>
	</ul>
</nav>
</c:if>