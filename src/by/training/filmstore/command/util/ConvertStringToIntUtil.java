package by.training.filmstore.command.util;

import javax.servlet.http.HttpServletRequest;

public final class ConvertStringToIntUtil {
	
	public static int getIntFromString(String value) {
		if ((value == null)||(value.isEmpty())) {
			return -1;
		}
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	
	public static int getIntFromString(HttpServletRequest request, String nameParam) {
		String strParam = request.getParameter(nameParam);
		if ((strParam == null)||(nameParam.isEmpty())) {
			return -1;
		}
		try {
			return Integer.parseInt(strParam);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
}
