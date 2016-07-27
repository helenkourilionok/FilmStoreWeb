<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.locale" var="locale" />
<fmt:message bundle="${locale}" key="locale.message" var="message" />
<fmt:message bundle="${locale}" key="locale.header.basket" var="basket" />
<fmt:message bundle="${locale}" key="locale.header.logout" var="logout" />
<fmt:message bundle="${locale}" key="locale.header.search" var="search" />
<fmt:message bundle="${locale}" key="locale.nav.home" var="home" />
<fmt:message bundle="${locale}" key="locale.nav.about_us" var="about_us" />
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
<fmt:message bundle="${locale}" key="locale.nav.sign_up" var="sign_up" />
<fmt:message bundle="${locale}" key="locale.nav.login" var="login" />
<header class="row">
	<div class="col-md-12">
		<a href="#"><img src="images/logo.png" alt="FilmStore logo"></a>
		<a href="#" class="basket">${basket}<span class="badge">10</span></a>
		<ul class="language">
			<li><a href="Controller?command=change_language&language=ru"
				id="ru"><img src="images/russia.png" alt="Russian" /></a></li>
			<li><a href="Controller?command=change_language&language=en"
				id="en"><img src="images/usa.png" alt="English" /></a></li>
			<li><a>${sessionScope.userEmail}</a></li>
			<c:if
				test="${sessionScope.userRole =='ROLE_USER' or sessionScope.userRole == 'ROLE_ADMIN'}">
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
		<li><a href="Controller?command=home_page">${home}</a></li>
		<li><a href="/about/">${about_us}</a></li>
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
	<ul class="nav navbar-nav" style="float: right">
		<li><a href="Controller?command=sign_up&redirect=true"> <span
				class="glyphicon glyphicon-user"></span>${sign_up}</a></li>
		<li><a href="Controller?command=logination&redirect=true"> <span
				class="glyphicon glyphicon-log-in"></span>${login}</a></li>
	</ul>
</nav>