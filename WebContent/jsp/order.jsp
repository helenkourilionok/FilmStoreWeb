<%@ page errorPage="error-page.jsp" language="java"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>FilmStore</title>
	  	<meta charset="utf-8">
	  	<meta name="description" content="FilmStore permits you buy and see new films, make comments of films">
	  	<meta name="keywords" content="buy new films,watch new films ">
	  	<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
		<link href="css/styles.css" rel="stylesheet">
		<link href="css/orderstyle.css" rel="stylesheet" >


	  	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.7.5/css/bootstrap-select.min.css">
	  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
	  	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.7.5/js/bootstrap-select.min.js"></script>

		<script type="text/javascript" src="js/jquery.carouFredSel-5.2.3-packed.js"></script>
		<script type="text/javascript" src="js/mosaic.1.0.1.js"></script>
		<script type="text/javascript" >
			$(function() {

				$('#carousel ul').carouFredSel({
					prev: '#prev',
					next: '#next',
					pagination: "#pager",
					auto: true,
					scroll: 1000,
					pauseOnHover: true
				});
			});
			jQuery(function($){
				$('.bar').mosaic({
					animation	:	'slide'
				});
		    });
		</script>
		<style type="text/css">
select {
    background: transparent;
    width: 268px;
    padding: 5px;
    border: 1px solid #CCC;
    font-size: 16px;
    height: 34px;
    -webkit-appearance: none;
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
						<form method="post" action="/makeOrder">
							<div class="col-md-8">
								<table class="table table-condensed">
								    <tbody>
								    <c:forEach var="film" items="${requestScope.listFilm}">
								      <tr>
								        <td style="width:20%"><img src="${film.image}" alt="${film.name}" width="100" height="150"></td>
								        <td style="width:45%">${film.name}(${film. yearOfRelease})</td>
								        <td style="width:15%">
									      <select class="form-control">
									      <c:forEach var="count" begin="1" end="${film.countFilms}">
									      	 <c:choose>
												<c:when test="${film.countFilms eq count}">
													<option value="${count}" selected>${count}</option>
												</c:when>
												<c:otherwise>
												 	<option value="${count}">${count}</option>
												</c:otherwise>
											</c:choose>	
									      </c:forEach>
									      </select>
								        </td>
								        <td style="width:15%">
								        ${film.price}
								        </td>
								        <td style="width:5%">
									        <a href="#">
									          <span class="glyphicon glyphicon-trash"></span>
									        </a>
								        </td>
								      </tr>
								    </c:forEach>
								    </tbody>
							 	 </table>
							</div>
							<div class="col-md-4">
								<div class="form-group">
								      <label for="userEmail">Customer:</label>
								      <input type="text" class="form-control" id="userEmail" name="userEmail" value="${requestScope.order.userEmail}" readonly/>
								</div>
								<div class="form-group">
								      <label for="commonPrice">Common price:</label>
								      <input type="text" class="form-control" id="commonPrice" name="commonPrice" value="${requestScope.order.commonPrice}" readonly/>
								</div>
								<div class="form-group">
								      <label for="status">Status:</label>
								      <input type="text" class="form-control" id="status" name="status" value="${requestScope.order.status}" readonly/>
								</div>
								<div class="form-group">
										<select name="kindOfDelivery" class="selectpicker" data-style="btn-primary" 
										 title="Choose kind of delivery">
										  <option value='MAILING'>MAILING</option>
										  <option value='COURIER'>COURIER</option>
										  <option value='SELFDELIVERY'>SELFDELIVERY</option>
										  <option value='ANOTHER'>ANOTHER</option>
										</select>
								</div>
								<div class="form-group">
	              				        <label for="payment">Payment:</label>
	              				        <br>
										<input type="radio" name="payment" value="PAY_IN_CASH"/>Pay in cash
										<input type="radio" name="payment" value="PAY_BY_CARD"/>Pay by card
								</div>
								<div class="form-group">
	              				        <label for="address">Address:</label>
	              				        <input type="text" class="form-control" id="address" name="address" value="${requestScope.order.address}" placeholder="Enter address of delivery" />
								</div>
								<div class="form-group">
	              				        <label for="address">Date of delivery:</label>
	              				        <input type="text" class="form-control" id="address" name="address" value="${requestScope.order.dateOfDelivery}" placeholder="Enter address of delivery" required/>
								</div>
			                    <div class="form-order form-order-commoninfo form-button-style">
			                        	<button class="i-button_primary " type="submit" name="makeOrder">Confirm&nbsp;order</button>
			                    </div>             
							</div>
						</form>
					</div>	
					<div class="row col-md-8 col-md-offset-1">
						<ul class="pagination">
						  <li><a href="#">«</a></li>
						  <li><a href="#">1</a></li>
						  <li><a href="#">2</a></li>
						  <li><a href="#">3</a></li>
						  <li><a href="#">4</a></li>
						  <li><a href="#">5</a></li>
						  <li><a href="#">»</a></li>
						</ul>
					</div>
				</section>
			</div>
			</div>
			<c:import url="notContent/footer.jsp" />
	</body>
</html>