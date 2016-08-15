package by.training.filmstore.command.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public final class CookieUtil {
	
	public static String getValueFromCookies(HttpServletRequest httpServletRequest, String cookieName) {
		Cookie[] listCookies = httpServletRequest.getCookies();
		String value = null;
		if (listCookies == null) {
			return value;
		}
		for (Cookie cookie : listCookies) {
			if (cookie.getName().equals(cookieName)) {
				value = cookie.getValue();
			}
		}
		return value;
	}
	
	public static int getCountValuesInCookie(HttpServletRequest httpServletRequest,String prefix){
		Cookie[] listCookies = httpServletRequest.getCookies();
		int count = 0;
		if(prefix == null){
			return count;
		}
		for (Cookie cookie : listCookies) {
			if (cookie.getName().contains(prefix)) {
				count++;
			}
		}
		return count;
	}
}
