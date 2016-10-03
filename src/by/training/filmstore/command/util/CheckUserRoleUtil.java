package by.training.filmstore.command.util;

import javax.servlet.http.HttpSession;

import by.training.filmstore.entity.Role;

public class CheckUserRoleUtil {

	final static String USER_ROLE = "userRole";
	
	public static boolean isAdmin(HttpSession session){
		
		if (session == null) {
			return false;
		}
		
		String role = session.getAttribute(USER_ROLE).toString();
		String roleAdmin = Role.ROLE_ADMIN.name();
		
		return role.equals(roleAdmin);
	}
	
	public static boolean isGuest(HttpSession session){
		if (session == null) {
			return true;
		}
		
		String role = session.getAttribute(USER_ROLE).toString();
		String roleGuest = Role.ROLE_GUEST.name();
		
		return role.equals(roleGuest);
	}
}
