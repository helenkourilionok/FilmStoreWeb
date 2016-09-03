<%@ page errorPage="error-page.jsp" language="java"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
<link href="css/orderstyle.css" rel="stylesheet">


<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.7.5/css/bootstrap-select.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.7.5/js/bootstrap-select.min.js"></script>

<script	src="js/jquery.carouFredSel-5.2.3-packed.js"></script>
<script src="js/mosaic.1.0.1.js"></script>
<script src="js/order.js" ></script>
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
</script>
<style type="text/css">
			select {
				-webkit-appearance: none;
				  -moz-appearance: none;
				  -ms-appearance: none;
				  appearance: none;
				}
</style>
<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
			<script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
		<![endif]-->
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.locale" var="locale" />
<fmt:message bundle="${locale}" key="locale.filmedit.updateFilm"
	var="updateFilm" />
<fmt:message bundle="${locale}" key="locale.filmedit.filmCreationFailed"
	var="filmCreationFailedMes" />
<fmt:message bundle="${locale}" key="locale.filmedit.updatingFilmFailed"
	var="updatingFilmFailedMes" />
<fmt:message bundle="${locale}" key="locale.filmedie.incorrectParams"
	var="incorrectParamsMes" />
<fmt:message bundle="${locale}"
	key="locale.order.kindOfPayment.payInCash" var="payInCash" />
<fmt:message bundle="${locale}"
	key="locale.order.kindOfPayment.payByCard" var="payByCard" />
<fmt:message bundle="${locale}"
	key="locale.order.kindOfPayment.chooseKindOfDelivery"
	var="chooseKindOfDelivery" />
<fmt:message bundle="${locale}"
	key="locale.order.kindOfDelivery.mailing" var="mailing" />
<fmt:message bundle="${locale}"
	key="locale.order.kindOfDelivery.selfdelivery" var="selfDelivery" />
<fmt:message bundle="${locale}"
	key="locale.order.kindOfDelivery.courier" var="courier" />
<fmt:message bundle="${locale}"
	key="locale.order.kindOfDelivery.another" var="another" />
<fmt:message bundle="${locale}" key="locale.order.dateOfDelivery"
	var="dateOfDelivery" />
<fmt:message bundle="${locale}" key="locale.order.address" var="address" />
<fmt:message bundle="${locale}" key="locale.order.chooseAddress"
	var="chooseAddress" />
<fmt:message bundle="${locale}" key="locale.order.payment" var="payment" />
<fmt:message bundle="${locale}" key="locale.order.status" var="status" />
<fmt:message bundle="${locale}" key="locale.order.confirmOrder" var="confirmOrder" />
<fmt:message bundle="${locale}" key="locale.order.commonPrice" var="commonPrice" />
<fmt:message bundle="${locale}" key="locale.order.customer" var="customer" />
<fmt:message bundle="${locale}" key="locale.order.illegalAddress" var="illegalAddress" />
<fmt:message bundle="${locale}" key="locale.order.notNum" var="notNum" />
<fmt:message bundle="${locale}" key="locale.order.discount" var="discount" />
</head>
<body>
	<div class="wrapper container">
		<c:import url="notContent/header.jsp" />
		<c:import url="notContent/adminmenu.jsp" />
		<c:import url="notContent/carousel.jsp" />
		<div class="wrapper row">
			<c:import url="notContent/aside.jsp" />
			<section class="col-md-9">
				<div class="row">
					<c:choose>
						<c:when test="${(sessionScope.listFilm!=null)&&(fn:length(sessionScope.listFilm) > 0)}" >
							<form method="post" action="Controller" name="order" onsubmit="return validateAddress();">
							<input type="hidden" name="command" value="make_order" />
							<div class="col-md-8">
								<table class="table table-condensed" id="films">
									<tbody>
										<c:forEach var="film" items="${sessionScope.listFilm}"
											varStatus="loopIndex">
											<tr>
												<td style="width: 20%"><img src="${film.image}"
													alt="${film.name}" width="100" height="150"></td>
												<td style="width: 45%">${film.name}(${film. yearOfRelease})</td>
												<td style="width: 15%">
												<select class="form-control" id="${loopIndex.index+1}" onchange="calcPrice(this,${film.id},'orderFilm')">
														<c:forEach var="count" begin="1" end="${film.countFilms}">
															<c:choose>
																<c:when
																	test="${count == countOrderedFilm[loopIndex.index]}">
																	<option value="${count}" selected>${count}</option>
																</c:when>
																<c:otherwise>
																	<option value="${count}">${count}</option>
																</c:otherwise>
															</c:choose>
														</c:forEach>
												</select></td>
												<td style="width: 15%">${film.price}</td>
												<td style="width: 5%"><a href="Controller?command=remove_film_from_basket&id=${film.id}"> 
												<span
														class="glyphicon glyphicon-trash"></span>
												</a></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<label for="userEmail">${customer}:</label> 
									<input type="text"
										class="form-control" id="userEmail" name="userEmail"
										value="${requestScope.order.userEmail}" readonly />
								</div>
								<div class="form-group">
									<label for="discount">${discount}:</label> 
									<input type="text" class="form-control" id="discount" name="discount"
										value="${sessionScope.discount}" readonly />
								</div>
								<div class="form-group">
									<label for="commonPrice">${commonPrice}:</label> <input
										type="text" class="form-control" id="commonPrice"
										name="commonPrice" value="${requestScope.order.commonPrice}"
										readonly />
								</div>
								<div class="form-group">
									<label for="status">${status}:</label> 
									<input type="text" class="form-control" id="status" name="status"
										value="${requestScope.order.status.getNameStatus()}" readonly />
								</div>
								<c:set var="kindOfDelivery" value="${requestScope.order.kindOfDelivery.name()}" />
								<c:choose>
									<c:when test="${kindOfDelivery.equals('MAILING')}">
										<c:set var="mailing" value="selected"/>
									</c:when>
									<c:when test="${kindOfDelivery.equals('COURIER')}">
										<c:set var="courier" value="selected"/>
									</c:when>
									<c:when test="${kindOfDelivery.equals('SELFDELIVERY')}">
										<c:set var="selfdelivery" value="selected"/>
									</c:when>
									<c:when test="${kindOfDelivery.equals('ANOTHER')}">
										<c:set var="another" value="selected"/>
									</c:when>
								</c:choose>
								<div class="form-group">
									<select name="kindOfDelivery" class="selectpicker"
										data-style="btn-primary" title="${chooseKindOfDelivery}">
										<option value='MAILING' ${mailing}>${mailing}</option>
										<option value='COURIER' ${courier}>${courier}</option>
										<option value='SELFDELIVERY' ${selfdelivery}>${selfDelivery}</option>
										<option value='ANOTHER' ${another}>${another}</option>
									</select>
								</div>
								<div class="form-group">
									<label for="payment">${payment}:</label> <br>
									<c:choose>
										<c:when
											test="${requestScope.order.kindOfPayment.getNameKindOfPayment() == 'наличными'}">
											<input type="radio" name="payment" checked value="PAYMENT_IN_CASH" />${payInCash}
													<br>
											<input type="radio" name="payment" value="PAYMENT_BY_CARD" />${payByCard}
										</c:when>
										<c:otherwise>
											<input type="radio" name="payment" value="PAYMENT_IN_CASH" />${payInCash}
													<br>
											<input type="radio" name="payment" checked value="PAYMENT_BY_CARD" />${payByCard}
										</c:otherwise>
									</c:choose>
								</div>
								<div class="form-group">
									<label for="address">${address}:</label> <input type="text"
										class="form-control" id="address" name="address"
										value="${requestScope.order.address}"
										placeholder="${chooseAddress}" />
										<span id="address_empty_error" style="color:#eb6a5a;" hidden="true">${chooseAddress}</span>
										<span id="address_illegal_error" style="color:#eb6a5a;" hidden="true">${illegalAddress}</span>
										<span id="address_notnum_error" style="color:#eb6a5a;" hidden="true">${notNum}</span>
								</div>
								<fmt:formatDate pattern="dd/MM/yyyy" value="${requestScope.order.dateOfDelivery}" var="valDateOfDelivery" />
								<div class="form-group">
									<label for="dateOfDelivery">${dateOfDelivery}:</label> 
									<input type="text" class="form-control" id="dateOfDelivery" 
									name="dateOfDelivery" value="${valDateOfDelivery}" readonly />							
								</div>
								<div class="form-order form-order-commoninfo form-button-style" style="margin:2px">
									<button class="i-button_primary " type="submit" >${confirmOrder}</button>
								</div>
							</div>
						</form>
						</c:when>
						<c:otherwise>
							<span>Basket is empty!Go to the <a href="Controller?command=show_list_film">index</a> page and choose film!</span>
						</c:otherwise>
					</c:choose>
				</div>
			</section>
		</div>
	</div>
	<c:import url="notContent/footer.jsp" />
</body>
</html>