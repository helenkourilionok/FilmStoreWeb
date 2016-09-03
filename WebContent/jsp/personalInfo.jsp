<%@ page errorPage="error-page.jsp" language="java"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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

<script src="js/jquery.carouFredSel-5.2.3-packed.js"></script>
<script src="js/mosaic.1.0.1.js"></script>
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
<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
			<script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
		<![endif]-->
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.locale" var="locale" />
<fmt:message bundle="${locale}" key="locale.signup.lastName"
	var="lastName" />
<fmt:message bundle="${locale}" key="locale.signup.firstName"
	var="firstName" />
<fmt:message bundle="${locale}" key="locale.signup.patronimic"
	var="patronimic" />
<fmt:message bundle="${locale}" key="locale.signup.phone" var="phone" />
<fmt:message bundle="${locale}" key="locale.signup.balance"
	var="balance" />
<fmt:message bundle="${locale}" key="locale.signup.createAccount"
	var="createAccount" />
<fmt:message bundle="${locale}" key="locale.signup.signUpFailed"
	var="mesSignUpFailed" />
<fmt:message bundle="${locale}" key="locale.personalInfo.personalInfo"
	var="personalInfo" />
<fmt:message bundle="${locale}" key="locale.personalInfo.discount"
	var="discount" />
<fmt:message bundle="${locale}" key="locale.personalInfo.noneOrder"
	var="noneOrder" />
<fmt:message bundle="${locale}" key="locale.order.payment" var="payment" />
<fmt:message bundle="${locale}" key="locale.order.address" var="address" />
<fmt:message bundle="${locale}" key="locale.order.commonPrice"
	var="commonPrice" />
<fmt:message bundle="${locale}" key="locale.order.status" var="status" />
<fmt:message bundle="${locale}" key="locale.personalInfo.dateOfDelivery"
	var="dateOfDelivery" />
<fmt:message bundle="${locale}" key="locale.personalInfo.dateOfOrder"
	var="dateOfOrder" />
<fmt:message bundle="${locale}" key="locale.personalInfo.kindOfDelivery"
	var="kindOfDelivery" />
<fmt:message bundle="${locale}" key="locale.adminmenu.listOrders"
	var="listOrders" />
<fmt:message bundle="${locale}" key="locale.order.paid" var="paid" />
<fmt:message bundle="${locale}" key="locale.order.unpaid" var="unpaid" />
<fmt:message bundle="${locale}" key="locale.order.annuled" var="annuled" />
<fmt:message bundle="${locale}" key="locale.listFilm.operation" var="operation" />
<fmt:message bundle="${locale}" key="locale.personalInfo.changePassword" var="changePassword" />
<fmt:message bundle="${locale}" key="locale.order.annul" var="annul" />
<fmt:message bundle="${locale}" key="locale.order.pay" var="pay" />
</head>
<body>
	<div class="wrapper container">
		<c:import url="notContent/header.jsp" />
		<c:import url="notContent/adminmenu.jsp" />
		<c:import url="notContent/carousel.jsp" />
		<div class="wrapper row">
			<c:import url="notContent/aside.jsp" />
			<section class="col-md-9">
				<c:if test="${not empty requestScope.user}">
					<div class="row">
						<h1>${personalInfo}</h1>
						<div class="col-md-6">
							<div class="form-group">
								<label for="last_name">${lastName}:</label> <input type="text"
									class="form-control" id="last_name" name="last_name"
									maxlength="15" value="${requestScope.user.lastName}" readonly />
							</div>
							<div class="form-group">
								<label for="first_name">${firstName}:</label> <input type="text"
									class="form-control" id="first_name" name="first_name"
									value="${requestScope.user.firstName}" maxlength="10" readonly />
							</div>
							<div class="form-group">
								<label for="patronimic">${patronimic}:</label> <input
									type="text" class="form-control" id="patronimic"
									name="patronimic" value="${requestScope.user.patronymic}"
									maxlength="15" readonly />
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label for="phone">${phone}:</label> <input type="text"
									class="form-control" id="phone" name="phone"
									value="${requestScope.user.phone}" readonly />
							</div>
							<div class="form-group">
								<label for="balance">${balance}:</label> <input type="text"
									class="form-control" id="balance" name="balance"
									value="${requestScope.user.balance}" readonly />
							</div>
							<div class="form-group">
								<label for="balance">${discount}:</label> <input type="text"
									class="form-control" id="balance" name="balance"
									value="${requestScope.user.discount}" readonly />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<form method="get">
								<input type="hidden" name="command" value="order_for_user_show" />
								<div class="form-group">
									<select name="status" class="selectpicker"
										data-style="btn-primary">
										<option value='PAID' selected>${paid}</option>
										<option value='UNPAID'>${unpaid}</option>
										<option value='ANNULED'>${annuled}</option>
									</select>
								</div>
								<button type="submit" class="btn btn-primary"> ${listOrders}</button>
							</form>
						</div>
						<div class="col-md-6">
							<form method="get">
								<input type="hidden" name="command" value="change_password_show_page" />
								<button type="submit" class="btn btn-primary"> ${changePassword}</button>
							</form>
						</div>
					</div>
				</c:if>
				<c:if test="${requestScope.listOrder != null}">
					<div class="col-md-12">
						<c:if test="${!(fn:length(requestScope.listOrder) > 0)}">
							<span>${noneOrder}</span>
						</c:if>
						<span>${notEnoughMoney}</span>
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>${commonPrice}</th>
									<th>${status}</th>
									<th>${kindOfDelivery}</th>
									<th>${payment}</th>
									<th>${dateOfOrder}</th>
									<th>${dateOfDelivery}</th>
									<th>${address}</th>
									<c:if test="${requestScope.status.getNameStatus().equals('не оплачено')}">
									<th>${operation}</th>
									</c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="order" items="${requestScope.listOrder}">
									<tr>
										<td>${order.commonPrice}</td>
										<td>${order.status.getNameStatus()}</td>
										<td>${order.kindOfDelivery.getNameKindOfDelivery()}</td>
										<td>${order.kindOfPayment.getNameKindOfPayment()}</td>
										<td>${order.dateOfOrder}</td>
										<td>${order.dateOfDelivery}</td>
										<td>${order.address}</td>
										<c:if test="${requestScope.status.getNameStatus().equals('не оплачено')}">
											<th>
												<c:if test="${order.kindOfPayment.getNameKindOfPayment().equals('банковская карта')}">
													<a href="Controller?command=pay_order&id=${order.id}">${pay}</a>
												</c:if>
												<a href="Controller?command=annul_order&id=${order.id}">${annul}</a>
											</th>
										</c:if>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
			</section>
		</div>
	</div>
	<c:import url="notContent/footer.jsp" />
</body>
</html>