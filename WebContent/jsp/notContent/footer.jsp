<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.locale" var="locale" />
<fmt:message bundle="${locale}" key="locale.footer.contacts" var="contacts" />
<fmt:message bundle="${locale}" key="locale.footer.address" var="address" />
<fmt:message bundle="${locale}" key="locale.footer.sitemap" var="sitemap" />
<fmt:message bundle="${locale}" key="locale.footer.socialNet" var="socialNet" />
<fmt:message bundle="${locale}" key="locale.nav.home" var="home" />
<fmt:message bundle="${locale}" key="locale.nav.aboutUs" var="aboutUs" />
<fmt:message bundle="${locale}" key="locale.nav.services" var="services" />
<fmt:message bundle="${locale}" key="locale.nav.films" var="films" />
<fmt:message bundle="${locale}" key="locale.nav.films.triller" var="triller" />
<fmt:message bundle="${locale}" key="locale.nav.films.horror" var="horror" />
<fmt:message bundle="${locale}" key="locale.nav.films.comedy" var="comedy" />
<fmt:message bundle="${locale}" key="locale.nav.films.adventures" var="adventures" />
<fmt:message bundle="${locale}" key="locale.nav.films.fantastic" var="fantastic" />
<fmt:message bundle="${locale}" key="locale.nav.payment" var="payment" />
<fmt:message bundle="${locale}" key="locale.nav.delivery" var="delivery" />
<fmt:message bundle="${locale}" key="locale.nav.help" var="help" />
<footer>
	<div class="container">
		<div class="row">
			<div class="col-md-2 col-md-offset-2 contact">
				<h3>
					<span>${contacts}</span>
				</h3>
				<p>
					<span>+375 (29) 1234567&nbsp;</span>
				</p>
				<p>
					<span>+375 (29) 2588808&nbsp;</span>
				</p>
				<p>
					<span>+375 (29) 2722828&nbsp;</span>
				</p>
				<address>
					<p>${address}</p>
					<p>
						<a href="mailto:book-shop@interpress.kz">film-shop@interpress.ru</a>
					</p>
				</address>
			</div>
			<div class="col-md-2 sitemap">
				<h3>${sitemap}</h3>
				<div class="row">
					<div class="col-md-6">
						<a href="index.html">${home}</a> 
						<a href="/about/">${aboutUs}</a>
						<a href="/services/">${services}</a>
					</div>
					<div class="col-md-6">
						<a href="/partners/">${payment}</a> 
						<a href="/customers/">${delivery}</a>
						<a href="/contact/">${help}</a>
					</div>
				</div>
			</div>
			<div class="col-md-2">
				<h3>${socialNet}</h3>
				<a href="http://twitter.com/" class="social-icon twitter"></a> <a
					href="http://facebook.com/" class="social-icon facebook"></a> <a
					href="http://plus.google.com/" class="social-icon google-plus"></a>
				<a href="http://vimeo.com/" class="social-icon-small vimeo"></a> <a
					href="http://youtube.com/" class="social-icon-small youtube"></a> <a
					href="http://flickr.com/" class="social-icon-small flickr"></a> <a
					href="http://instagram.com/" class="social-icon-small instagram"></a>
				<a href="/rss/" class="social-icon-small rss"></a>
			</div>
			<div class="col-md-2 sitemap">
				<h3>${films}</h3>
				<div class="row">
					<div class="col-md-6">
						<a href="#">${triller}</a> 
						<a href="#">${horror}</a> 
						<a href="#">${comedy}</a>
					</div>
					<div class="col-md-6">
						<a href="#">${adventures}</a> 
						<a href="#">${fantastic}</a>
					</div>
				</div>
				<p>Copyright © 2016.</p>
			</div>
		</div>
	</div>
</footer>