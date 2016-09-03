<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.locale" var="locale" />
<fmt:message bundle="${locale}" key="locale.showfilm.countFilms"
	var="countFilms" />
<fmt:message bundle="${locale}" key="locale.showfilm.quality"
	var="quality" />
<fmt:message bundle="${locale}" key="locale.showfilm.price" var="price" />

<fmt:message bundle="${locale}" key="locale.showfilm.yearOfRelease"
	var="yearOfRelease" />
<fmt:message bundle="${locale}" key="locale.showfilm.genre" var="genre" />
<fmt:message bundle="${locale}" key="locale.showfilm.filmDirector"
	var="filmDirector" />
<fmt:message bundle="${locale}" key="locale.showfilm.country"
	var="country" />
<fmt:message bundle="${locale}" key="locale.showfilm.actors"
	var="actors" />
<fmt:message bundle="${locale}" key="locale.filmpage.makeOrder"
	var="makeOrder" />
		<h1>${sessionScope.filmInfo.name}</h1>
						<article class="row">
							<!-- Film image -->
							<div class="col-md-4 row-offset">
								<div class="mosaic-block" style="height: 290px">
									<div>
										<img src="${sessionScope.filmInfo.image}"
											alt="${sessionScope.filmInfo.name}" width="200" height="250" />
									</div>
									<form method="post">
										<div style="margin: 10px 30px">
											<button type="submit" class="btn btn-primary"
											formaction="Controller?command=make_order_show_page" 
											id="order">${makeOrder}</button>
										</div>
									</form>
								</div>
							</div>
							<!-- Film image -->
							<!-- Film description -->
							<div class="col-md-8 row-offset">
								<div class="pull-right text-right">
									<ul class="list-unstyled text-right">
										<li><b>${countFilms}:</b><span>${sessionScope.filmInfo.countFilms}</span></li>
										<li><b>${quality}:</b>
											${sessionScope.filmInfo.quality.getNameQuality()}</li>
										<li><b>${price}:</b> <span>${sessionScope.filmInfo.price}</span></li>
									</ul>
								</div>
								<ul class="list-unstyled">
									<li><b>${genre}:</b>&nbsp;<span>${sessionScope.filmInfo.genre}</span>.</li>
									<li><b>${yearOfRelease}</b>&nbsp;<time>${sessionScope.filmInfo.yearOfRelease}</time>.
									</li>
									<li><b>${country}:</b>&nbsp;<span>${sessionScope.filmInfo.country}</span>.</li>
									<li><b>${filmDirector}:</b>&nbsp;<span>${sessionScope.filmInfo.filmDirector.fio}</span>.</li>
									<li class="row-offset">
										<p>${sessionScope.filmInfo.description}</p>
									</li>
									<li><b>${actors}:</b>&nbsp; <span> <c:forEach
												var="actor" items="${sessionScope.filmInfo.actors}"
												varStatus="loop">
												<c:choose>
													<c:when test="${loop.index != filmInfo.actors.size()-1}">
									        ${actor.fio},
									    </c:when>
													<c:otherwise>
										    ${actor.fio}
									    </c:otherwise>
												</c:choose>
											</c:forEach>
									</span></li>
								</ul>
							</div>
							<!-- Film description -->
						</article>