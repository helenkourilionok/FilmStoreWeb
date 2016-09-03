<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.locale" var="locale" />
<fmt:message bundle="${locale}" key="locale.header.basket" var="basket" />
<fmt:message bundle="${locale}" key="locale.header.logout" var="logout" />
<fmt:message bundle="${locale}" key="locale.header.search" var="search" />
<fmt:message bundle="${locale}" key="locale.nav.home" var="home" />
<fmt:message bundle="${locale}" key="locale.nav.aboutUs" var="aboutUs" />
<fmt:message bundle="${locale}" key="locale.nav.services" var="services" />
<fmt:message bundle="${locale}" key="locale.nav.films" var="films" />
<fmt:message bundle="${locale}" key="locale.nav.films.triller"
	var="triller" />
<fmt:message bundle="${locale}" key="locale.nav.films.horror"
	var="horror" />
<fmt:message bundle="${locale}" key="locale.nav.films.comedy"
	var="comedy" />
<fmt:message bundle="${locale}" key="locale.nav.films.adventures"
	var="adventures" />
<fmt:message bundle="${locale}" key="locale.nav.films.fantastic"
	var="fantastic" />
<fmt:message bundle="${locale}" key="locale.nav.payment" var="payment" />
<fmt:message bundle="${locale}" key="locale.nav.delivery" var="delivery" />
<fmt:message bundle="${locale}" key="locale.nav.help" var="help" />
<fmt:message bundle="${locale}" key="locale.nav.contacts" var="contacts" />
<fmt:message bundle="${locale}" key="locale.nav.signUp" var="signUp" />
<fmt:message bundle="${locale}" key="locale.nav.login" var="login" />
<header class="row">
	<div class="col-md-12">
		<a href="Controller?command=show_list_film"><img src="images/logo.png" alt="FilmStore logo"></a>
		<a href="Controller?command=make_order_show_page" class="basket">${basket}
		<span class="badge" id="basketFilm">${sessionScope.countFilmInBasket}</span></a>
		<ul class="language">
			<li><a href="Controller?command=change_language&language=ru"
				id="ru"><img src="images/russia.png" alt="Russian" /></a></li>
			<li><a href="Controller?command=change_language&language=en"
				id="en"><img src="images/usa.png" alt="English" /></a></li>
			<li><a href="Controller?command=personal_info_show">${sessionScope.userEmail}</a></li>
			<c:if test="${sessionScope.userRole !='ROLE_GUEST'}">
				<li><a href="Controller?command=logout">${logout}</a></li>
			</c:if>
		</ul>
		<form name="search" action="#" method="get"
			class="form-inline form-search pull-right">
			<div class="input-group">
				<input class="form-control" type="text" id="search" name="search"
					placeholder="${search}">
				<div class="input-group-btn">
					<button type="submit" class="btn btn-primary">
						<span class="glyphicon glyphicon-search"></span>
					</button>
				</div>
			</div>
		</form>
	</div>
</header>
<nav class="navbar navbar-default navbar-inverse">
	<ul class="nav navbar-nav">
		<li><a href="Controller?command=show_list_film">${home}</a></li>
		<li><a href="/about/">${aboutUs}</a></li>
		<li><a href="/services/">${services}</a></li>
		<li class="dropdown"><a href="#">${films}<span></span></a>
			<ul class="dropdown-menu">
				<li><a href="#">${triller}</a></li>
				<li><a href="#">${horror}</a></li>
				<li><a href="#">${comedy}</a></li>
				<li><a href="#">${adventures}</a></li>
				<li><a href="#">${fantastic}</a></li>
			</ul></li>
		<li><a href="/payment/">${payment}</a></li>
		<li><a href="/delivery/">${delivery}</a></li>
		<li><a href="/help/">${help}</a></li>
	</ul>
	<c:if test="${sessionScope.userRole == 'ROLE_GUEST'}">
		<ul class="nav navbar-nav" style="float: right">
			<li><a href="Controller?command=sign_up_show_page"> <span
					class="glyphicon glyphicon-user"></span>${signUp}</a></li>
			<li><a href="Controller?command=login_show_page"> <span
					class="glyphicon glyphicon-log-in"></span>${login}</a></li>
		</ul>
	</c:if>
</nav>