function formValidation(language) {
	var email = document.signup.email.value;
	var password = document.signup.password.value;
	var passcopy = document.signup.copypassword.value;
	var last_name = document.signup.last_name;
	var first_name = document.signup.first_name;
	var patronymic = document.signup.patronymic;
	var phone = document.signup.phone.value;
	var balance = document.signup.balance.value;
	if (!validateEmail(email, language)) {
		return false;
	}
	if (!validatePassword(password, language)) {
		return false;
	}
	if (!validatePassCopyPass(password, passcopy, language)) {
		return false;
	}
	if (!validateTextField(last_name, language, "#last_name", "lastname_error",
			"Last name", "Фамилия")) {
		return false;
	}
	if (!validateTextField(first_name, language, "#first_name",
			"firstname_error", "First name", "Имя")) {
		return false;
	}
	if (!validateTextField(patronymic, language, "#patronymic",
			"patronymic_error", "Patronymic", "Отчество")) {
		return false;
	}
	if (!validatePhone(phone, language, "phone_error")) {
		return false;
	}
	if (!validateBalance(balance, language, "balance_error")) {
		return false;
	}
	return true;
}

function changePasswordValidation(language) {
	var email = document.signup.email.value;
	var password = document.signup.password.value;
	var passcopy = document.signup.copypassword.value;
	if (!validateEmail(email, language)) {
		return false;
	}
	if (!validatePassword(password, language)) {
		return false;
	}
	if (!validatePassCopyPass(password, passcopy, language)) {
		return false;
	}
	return true;
}


function createActorValidation() {
	var fio = document.getElementById("fio").value;
	var textRegex = /^[a-zA-Z\u0410-\u044F\u0401\u0451\-\s]{4,45}$/;
	if((fio == "")||(!textRegex.test(fio)) ){
		$("#fio").css('border', '#eb6a5a 1px solid');
		document.getElementById("fio_error").hidden = false;
		return false;
	}
	$("#fio").css('border', '#ccc 1px solid');
	document.getElementById("fio_error").hidden = true;
	return true;
}

function createFilmDirectorValidation(){
	var fio = document.getElementById("fio").value;
	var textRegex = /^[a-zA-Z\u0410-\u044F\u0401\u0451\-\s]{4,70}$/;
	if((fio == "")||(!textRegex.test(fio)) ){
		$("#fio").css('border', '#eb6a5a 1px solid');
		document.getElementById("fio_error").hidden = false;
		return false;
	}
	$("#fio").css('border', '#ccc 1px solid');
	document.getElementById("fio_error").hidden = true;
	return true;
}

function updateUserValidation(language){
	var last_Name = document.getElementById("last_name");
	var first_Name = document.getElementById("first_name");
	var patronymic = document.getElementById("patronymic");
	var phone = document.getElementById("phone").value;
	var balance = document.getElementById("balance").value;
	if (!validateTextField(last_name, language, "#last_name", "lastname_error",
			"Last name", "Фамилия")) {
		return false;
	}
	if (!validateTextField(first_name, language, "#first_name",
			"firstname_error", "First name", "Имя")) {
		return false;
	}
	if (!validateTextField(patronymic, language, "#patronymic",
			"patronimic_error", "Patronimic", "Отчество")) {
		return false;
	}
	if (!validatePhonePrs(phone, language, "phone_error")) {
		return false;
	}
	if (!validateBalance(balance, language, "balance_error")) {
		return false;
	}
	return true;
}

function validateEmail(email, language) {
	var emailRegex = /^[A-Za-z0-9._\-]*\@[A-Za-z]*\.[A-Za-z]{2,5}$/;
	if (email == "") {
		document.signup.email.focus();
		$("#email").css('border', '#eb6a5a 1px solid');
		if (language == 'en') {
			document.getElementById("email_error").innerHTML = "Enter the email";
		} else {
			document.getElementById("email_error").innerHTML = "\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u0430\u0434\u0440\u0435\u0441 \u044D\u043B\u0435\u043A\u0442\u0440\u043E\u043D\u043D\u043E\u0439 \u043F\u043E\u0447\u0442\u044B";
		}
		return false;
	} else if (!emailRegex.test(email)) {
		document.signup.email.focus();
		$("#email").css('border', '#eb6a5a 1px solid');
		if (language == 'en') {
			document.getElementById("email_error").innerHTML = "Email format is invalid";
		} else {
			document.getElementById("email_error").innerHTML = "\u0410\u0434\u0440\u0435\u0441 \u044D\u043B\u0435\u043A\u0442\u0440\u043E\u043D\u043D\u043E\u0439 \u043F\u043E\u0447\u0442\u044B \u0438\u043C\u0435\u0435\u0442 \u043D\u0435\u0432\u0435\u0440\u043D\u044B\u0439 \u0444\u043E\u0440\u043C\u0430\u0442";
		}
		return false;
	}
	$("#email").css('border', '#ccc 1px solid');
	document.getElementById("email_error").innerHTML = "";
	return true;
}

function validatePassword(password, language) {

	var passRegex = /^[0-9a-zA-Z\#\&\?\/\+\=\!\@\%]{8,}$/;

	if (password == "") {
		document.signup.password.focus();
		$("#password").css('border', '#eb6a5a 1px solid');
		if (language == 'en') {
			document.getElementById("pass_error").innerHTML = "Enter the password";
		} else {
			document.getElementById("pass_error").innerHTML = "\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u043F\u0430\u0440\u043E\u043B\u044C";
		}
		return false;

	} else if ((password.length < 8)) {

		document.signup.password.focus();
		$("#password").css('border', '#eb6a5a 1px solid');
		if (language == 'en') {
			document.getElementById("pass_error").innerHTML = "The password is the wrong length";
		} else {
			document.getElementById("pass_error").innerHTML = "\u0414\u043B\u0438\u043D\u0430 \u043F\u0430\u0440\u043E\u043B\u044F \u0434\u043E\u043B\u0436\u043D\u0430 \u0431\u044B\u0442\u044C \u043E\u0442 8 \u0434\u043E 40 \u0441\u0438\u043C\u0432\u043E\u043B\u043E\u0432";
		}
		return false;

	} else if (!passRegex.test(password)) {

		document.signup.password.focus();
		$("#password").css('border', '#eb6a5a 1px solid');
		if (language == 'en') {
			document.getElementById("pass_error").innerHTML = "The password contains illegal characters";
		} else {
			document.getElementById("pass_error").innerHTML = "\u041F\u0430\u0440\u043E\u043B\u044C \u0441\u043E\u0434\u0435\u0440\u0436\u0438\u0442 \u043D\u0435\u0432\u0435\u0440\u043D\u044B\u0435 \u0441\u0438\u043C\u0432\u043E\u043B\u044B";
		}
		return false;

	} else if ((password.search(/[a-zA-Z]+/) == -1)
			|| (password.search(/[0-9]+/) == -1)) {
		document.signup.password.focus();
		$("#password").css('border', '#eb6a5a 1px solid');
		if (language == 'en') {
			document.getElementById("pass_error").innerHTML = "The password must contain at least one numeral or one character";
		} else {
			document.getElementById("pass_error").innerHTML = "        	\u041F\u0430\u0440\u043E\u043B\u044C \u0434\u043E\u043B\u0436\u0435\u043D \u0441\u043E\u0434\u0435\u0440\u0436\u0430\u0442\u044C \u043A\u0430\u043A \u0441\u0438\u043C\u0432\u043E\u043B\u044B, \u0442\u0430\u043A \u0438 \u0446\u0438\u0444\u0440\u044B";
		}
		return false;
	}
	$("#password").css('border', '#ccc 1px solid');
	document.getElementById("pass_error").innerHTML = "";
	return true;
}

function validatePassCopyPass(password, passcopy, language) {
	if (passcopy == "") {
		$("#copypassword").css('border', '#eb6a5a 1px solid');
		document.getElementById("copypass_error").hidden = false;
		return false;
	}
	if (password != passcopy) {
		document.signup.copypassword.focus();
		$("#password").css('border', '#eb6a5a 1px solid');
		$("#copypassword").css('border', '#eb6a5a 1px solid');
		document.getElementById("copypass_error").hidden = false;
		return false;
	}
	$("#password").css('border', '#ccc 1px solid');
	$("#copypassword").css('border', '#ccc 1px solid');
	document.getElementById("copypass_error").hidden = true;
	return true;
}

function validateTextField(field, language, id_field, id_error_el,
		field_name_en, field_name_ru) {
	var textRegex = /^[a-zA-Z\u0410-\u044F\u0401\u0451\-]+$/;
	if (field.value == "") {
		field.focus();
		$(id_field).css('border', '#eb6a5a 1px solid');
		if (language == 'en') {
			document.getElementById(id_error_el).innerHTML = "Enter the "
					+ field_name_en;
		} else {
			document.getElementById(id_error_el).innerHTML = "\u0417\u0430\u043F\u043E\u043B\u043D\u0438\u0442\u0435 \u043F\u043E\u043B\u0435 "
					+ field_name_ru;
		}
		return false;
	}
	if (!textRegex.test(field.value)) {
		field.focus();
		$(id_field).css('border', '#eb6a5a 1px solid');
		if (language == 'en') {
			document.getElementById(id_error_el).innerHTML = field_name_en
					+ "must contain just alphabetic letters";
		} else {
			document.getElementById(id_error_el).innerHTML = "\u041F\u043E\u043B\u0435 "
					+ field_name_ru
					+ " \u0434\u043E\u043B\u0436\u043D\u043E \u0441\u043E\u0434\u0435\u0440\u0436\u0430\u0442\u044C \u0442\u043E\u043B\u044C\u043A\u043E \u0441\u0438\u043C\u0432\u043E\u043B\u044B";
		}
		return false;
	}
	$(id_field).css('border', '#ccc 1px solid');
	document.getElementById(id_error_el).innerHTML = "";
	return true;
}

function validatePhonePrs(phone, language, id_error_el){
	var phoneRegex = /^\+375\d{2}\d{3}\d{2}\d{2}$/;
	if (phone == "") {
		$("#phone").css('border', '#eb6a5a 1px solid');
		if (language == 'en') {
			document.getElementById(id_error_el).innerHTML = "Enter the phone";
		} else {
			document.getElementById(id_error_el).innerHTML = "\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u043F\u043E\u043B\u0435 \u0442\u0435\u043B\u0435\u0444\u043E\u043D"
		}
		return false;
	}
	if (!phoneRegex.test(phone)) {
		$("#phone").css('border', '#eb6a5a 1px solid');
		if (language == 'en') {
			document.getElementById(id_error_el).innerHTML = "Phone format is invalid";
		} else {
			document.getElementById(id_error_el).innerHTML = "\u041F\u043E\u043B\u0435 \u0422\u0435\u043B\u0435\u0444\u043E\u043D \u0438\u043C\u0435\u0435\u0442 \u043D\u0435\u0432\u0435\u0440\u043D\u044B\u0439 \u0444\u043E\u0440\u043C\u0430\u0442"
		}
		return false;
	}
	$("#phone").css('border', '#ccc 1px solid');
	document.getElementById(id_error_el).innerHTML = "";
	return true;	
}


function validatePhone(phone, language, id_error_el) {
	var phoneRegex = /^\+375\-\d{2}\-\d{3}\-\d{2}\-\d{2}$/;
	if (phone == "") {
		$("#phone").css('border', '#eb6a5a 1px solid');
		if (language == 'en') {
			document.getElementById(id_error_el).innerHTML = "Enter the phone";
		} else {
			document.getElementById(id_error_el).innerHTML = "\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u043F\u043E\u043B\u0435 \u0442\u0435\u043B\u0435\u0444\u043E\u043D"
		}
		return false;
	}
	if (!phoneRegex.test(phone)) {
		$("#phone").css('border', '#eb6a5a 1px solid');
		if (language == 'en') {
			document.getElementById(id_error_el).innerHTML = "Phone format is invalid";
		} else {
			document.getElementById(id_error_el).innerHTML = "\u041F\u043E\u043B\u0435 \u0422\u0435\u043B\u0435\u0444\u043E\u043D \u0438\u043C\u0435\u0435\u0442 \u043D\u0435\u0432\u0435\u0440\u043D\u044B\u0439 \u0444\u043E\u0440\u043C\u0430\u0442"
		}
		return false;
	}
	$("#phone").css('border', '#ccc 1px solid');
	document.getElementById(id_error_el).innerHTML = "";
	return true;
}

function validateBalance(balance, language, id_error_el) {
	var numbers = /^[0-9]+\.?[0-9]+$/;
	if (balance == "") {
		$("#balance").css('border', '#eb6a5a 1px solid');
		if (language == 'en') {
			document.getElementById(id_error_el).innerHTML = "Enter the balance";
		} else {
			document.getElementById(id_error_el).innerHTML = "\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u0431\u0430\u043B\u0430\u043D\u0441";
		}
		return false;
	}
	if (!numbers.test(balance)) {
		$("#balance").css('border', '#eb6a5a 1px solid');
		if (language == 'en') {
			document.getElementById(id_error_el).innerHTML = "Balance must have numeric characters and/or just one point ";
		} else {
			document.getElementById(id_error_el).innerHTML = "\u0411\u0430\u043B\u0430\u043D\u0441 \u0434\u043E\u043B\u0436\u0435\u043D \u0441\u043E\u0434\u0435\u0440\u0436\u0430\u0442\u044C \u0446\u0438\u0444\u0440\u044B \u0438/\u0438\u043B\u0438 \u043E\u0434\u043D\u0443 \u0442\u043E\u0447\u043A\u0443";
		}
		return false;
	}
	$("#balance").css('border', '#ccc 1px solid');
	document.getElementById(id_error_el).innerHTML = "";
	return true;
}