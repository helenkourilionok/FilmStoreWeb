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
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.7.5/css/bootstrap-select.min.css">
<link href="css/datepicker.css" rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.7.5/js/bootstrap-select.min.js"></script>
<script src="js/jquery.carouFredSel-5.2.3-packed.js"></script>
<script src="js/mosaic.1.0.1.js"></script>
<script type="text/javascript" src="js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="js/bootstrap-datepicker.min.js"></script>
<script src="js/makeDiscountValidation.js"></script>
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
		$('#myInput').datepicker( {format: 'dd/mm/yyyy',defaultDate: "+5y"});
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
<fmt:message bundle="${locale}" key="locale.makeDiscount.makeDiscount" var="makeDiscount" />
<fmt:message bundle="${locale}" key="locale.makeDiscount.countOrders" var="countOrders_loc" />
<fmt:message bundle="${locale}" key="locale.makeDiscount.enterCountOrders" var="enterCountOrders" />
<fmt:message bundle="${locale}" key="locale.makeDiscount.errorCountOrders" var="errorCountOrders" />
<fmt:message bundle="${locale}" key="locale.makeDiscount.sizeOfDiscount" var="sizeOfDiscount" />
<fmt:message bundle="${locale}" key="locale.makeDiscount.monthYear" var="monthYear" />
<fmt:message bundle="${locale}" key="locale.makeDiscount.noneFilm" var="noneFilm" />
</head>
<body>
	<div class="wrapper container">
		<c:import url="notContent/header.jsp" />
		<c:import url="notContent/adminmenu.jsp" />
		<c:import url="notContent/carousel.jsp" />
		<div class="wrapper row">
			<c:import url="notContent/aside.jsp" />
			<section class="col-md-9">
				<h1>${makeDiscount}</h1>
				<article class="row">
						<form method="post" onsubmit="return validationForDiscount();">
							<div class="col-md-12">
								<div class="col-md-4">
									<div class="form-group">
									    <label for="countOrders">${countOrders_loc}:</label>
										<input type="text" class="form-control" id="countOrders" name="countOrders" value="${countOrders}" placeholder="${enterCountOrders}" />
										<span id="countOrd_error" style="color:#eb6a5a;" hidden="true">${errorCountOrders}</span>
									</div>
								</div>							
								<div class="col-md-4">
									<c:if test="${fn:length(requestScope.listUserForDiscount) > 0}">
										<div class="form-group">
										    <label for="sizeofDiscount">${sizeOfDiscount}:</label>
											<select name="sizeofDiscount" class="selectpicker" data-style="btn-primary" >
												<option value='20' selected>20</option>
												<option value='30' >30</option>
												<option value='50' >50</option>
												<option value='70' >70</option>
											</select>
										</div>
									</c:if>
								</div>
								<fmt:formatDate pattern="dd/MM/yyyy" value="${requestScope.currentDate}" var="dateNow" />
								<div class="col-md-4">
									<div class="form-group">
											<label for="monthYear">${monthYear}:</label>
											<div class="input-group date" id="myInput" data-date="01/2016" data-date-format="mm/yyyy" data-date-viewmode="years" data-date-minviewmode="months">
											    <input type="text" class="form-control" name="date" value="${dateNow}" readonly/>
											    <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
											</div>
									</div> 
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 col-md-offset-4">
									<c:choose>
									    <c:when test="${fn:length(requestScope.listUserForDiscount) > 0}">
											<button type="submit" class="btn btn-primary btn-sm" formaction="Controller?command=a_make_discount" style="margin-bottom:10px">Make discount for user</button>
										</c:when>
									    <c:otherwise>
											<button type="submit" class="btn btn-primary btn-sm" formaction="Controller?command=a_make_discount_show_user" style="margin-bottom:10px">Show user for discount</button>
									    </c:otherwise>
									</c:choose>
								</div>
							</div>
						</form>
					<div class="col-md-12">
						<c:if test="${ (requestScope.listUserForDiscount!=null) && !(fn:length(requestScope.listUserForDiscount) > 0)}">
							<span>${noneFilm}</span>
						</c:if>
						<table class="table table-bordered">
							<tbody>
								<c:forEach var="user" items="${requestScope.listUserForDiscount}">
									      <tr>
									        <td>${user.lastName}</td>
									        <td>${user.firstName}</td>
									        <td>${user.patronymic}</td>
									        <td>${user.balance}</td>
									        <td>${user.discount}</td>
									      </tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</article>
			</section>
		</div>
	</div>
	<c:import url="notContent/footer.jsp" />
</body>
</html>