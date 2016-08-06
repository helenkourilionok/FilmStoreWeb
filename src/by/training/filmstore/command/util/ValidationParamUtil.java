package by.training.filmstore.command.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ValidationParamUtil {
	
	private static final String EMAIL_PATTERN = "^[A-Za-z0-9\\-]+@[A-Za-z0-9]+\\.[A-Za-z]{2,4}$";
	
	public static boolean validateEmail(String email, int length) {
		if (!ValidationParamUtil.notEmpty(email) || !ValidationParamUtil.checkLength(email, length)) {
			return false;
		}

		return checkField(EMAIL_PATTERN, email);
	}

	
	public static boolean validateId(Short id){
		if(id == null || id<=0){
			return false;
		}
		return true;
	}
	
	public static boolean checkLength(String value, int length) {
		if (value.length() > length) {
			return false;
		}
		return true;
	}
	
	public static boolean notEmpty(String value) {
		if (value == null || value.isEmpty()) {
			return false;
		}

		return true;
	}
	
	public static boolean checkField(String patternStr, String field) {
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(field);

		return matcher.matches();
	}

}
