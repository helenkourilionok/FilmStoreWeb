package by.training.filmstore.command.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	public static int getCountGoodsInCookie(HttpServletRequest httpServletRequest,String prefix){
		Cookie[] listCookies = httpServletRequest.getCookies();
		int count = 0;
		if(prefix == null){
			return count;
		}
		for (Cookie cookie : listCookies) {
			if (cookie.getName().contains(prefix)) {
			    count = count + ConvertStringToIntUtil.getIntFromString(cookie.getValue());
			}
		}
		return count;
	}

	public static Cookie getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }
	
	public static void getArrayOrderCookies(HttpServletRequest httpServletRequest,String prefix){
		Cookie[] listCookies = httpServletRequest.getCookies();
		for (Cookie cookie : listCookies) {
			if (cookie.getName().contains(prefix)) {
			   System.out.println(cookie.getName()+"-"+cookie.getValue());;
			}
		}
	}

	public static void removeOrderCookies(HttpServletRequest request,
			HttpServletResponse response,String prefix){
		Cookie[] cookies = request.getCookies();
		Cookie tempCookie = null;
		
		for(int i = 0;i<cookies.length;i++){
			if(cookies[i].getName().contains(prefix)){
				tempCookie = new Cookie(cookies[i].getName(),"");
				tempCookie.setMaxAge(0);
				
				response.addCookie(tempCookie);
			}
		}
	}
	
	public static void removeOrderCookies(HttpServletRequest request,
			HttpServletResponse response,String prefix,String filmId){
		Cookie[] cookies = request.getCookies();
		Cookie tempCookie = null;
		for(int i = 0;i<cookies.length;i++){
			if(cookies[i].getName().equals(prefix+filmId)){
				tempCookie = new Cookie(cookies[i].getName(),"");
				tempCookie.setMaxAge(0);
				response.addCookie(tempCookie);
				break;
			}
		}
	}
	
	public static Map<Short,Short> getMapIdCountFromCookies(HttpServletRequest request,String prefix){
		Map<Short,Short> mapIdCountFilm = new HashMap<>();
		Cookie[] cookies = request.getCookies();
		String replacement = "";
		for(Cookie cookie:cookies){
			if (cookie.getName().contains(prefix)) {
				String cookieName = cookie.getName();
				String filmId = cookieName.replaceAll(prefix,replacement);
				
				short id = (short)ConvertStringToIntUtil.getIntFromString(filmId);
				short countFilm = (short)ConvertStringToIntUtil.getIntFromString(cookie.getValue());
				
				mapIdCountFilm.put(id,countFilm);
			}
		}
		return mapIdCountFilm;
	}
}
