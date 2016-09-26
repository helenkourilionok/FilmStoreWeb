function validateAddress() {
	var address = document.order.address.value;
	var addrRegex_ru = /^[\u0410-\u042F\u0430-\u044F\.\,\-\s0-9]{7,40}$/;
	var numericRegex = /[0-9]+/;
	if (address == "") {
		alert("dfdgg");
		document.order.address.focus();
		$("#address").css('border', 'red 1px solid');
		document.getElementById("address_empty_error").hidden = false;
		return false;
	}
	if (!addrRegex_ru.test(address)) {
		document.order.address.focus();
		$("#address").css('border', 'red 1px solid');
		document.getElementById("address_illegal_error").hidden = false;
		return false;
	}
	if (address.search(numericRegex) == -1) {
		document.order.address.focus();
		$("#address").css('border', 'red 1px solid');
		document.getElementById("address_notnum_error").hidden = false;
		return false;
	}
	$("#address").css('border', 'black 1px solid');
	document.getElementById("address_empty_error").hidden = true;
	document.getElementById("address_illegal_error").hidden = true;
	document.getElementById("address_notnum_error").hidden = true;
	return true;
}
function calcPrice(sel, id, prefix) {
	var rows = document.getElementById("films").rows;
	var discount = document.getElementById("discount").value;
	var i;
	var result = 0;
	for (i = 0; i < rows.length; i++) {
		var count = document.getElementById(i + 1).value;
		var price = rows[i].cells[3].innerHTML;
		result += count * price;
	}
	result = result - (result*discount)/100;
	document.getElementById("commonPrice").value = result;
	/* cookies */
	var selCount = sel.value;
	var change = 0;
	var exdays = 30;
	var selCountForFilm = getCookie(prefix + id);
	if (selCountForFilm) {
		setCookie(prefix + id, selCount,exdays);
		change = selCount - selCountForFilm;
	} else {
		setCookie(prefix + id, selCount,exdays);
		change = selCount;
	}
	var countOrderedFilms = parseInt(document.getElementById("basketFilm").innerHTML);
	countOrderedFilms += change;
	document.getElementById("basketFilm").innerHTML = countOrderedFilms;
}
function setCookie(cname, cvalue, exdays) {
	var d = new Date();
	d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
	var expires = "expires=" + d.toUTCString();
	document.cookie = cname + "=" + cvalue + "; " + expires;
}
function getCookie(cname) {
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for (var i = 0; i < ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ') {
			c = c.substring(1);
		}
		if (c.indexOf(name) == 0) {
			return c.substring(name.length, c.length);
		}
	}
	return "";
}