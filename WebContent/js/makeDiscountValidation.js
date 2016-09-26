function validationForDiscount() {
	var element = document.getElementById("countOrders");
	var countRegex = /^[0-9]{1,4}$/;
	if (!countRegex.test(element.value)) {
		$("#countOrders").css('border', 'red 1px solid');
		document.getElementById("countOrd_error").hidden = false;
		return false;
	}
	$("#countOrders").css('border', '1px solid #ccc');
	document.getElementById("countOrd_error").hidden = true;
	return true;

}