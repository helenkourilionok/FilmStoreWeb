package by.training.filmstore.controller;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import by.training.filmstore.entity.Role;

/**
 * Application Lifecycle Listener implementation class FilmStoreSessionListener
 *
 */
public final class FilmStoreSessionListener implements HttpSessionListener {

	private final static String USER_ROLE = "userRole";
	
    public FilmStoreSessionListener() {
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent sessionEvent)  { 
         HttpSession httpSession = sessionEvent.getSession();
         httpSession.setAttribute(USER_ROLE,Role.ROLE_GUEST);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent sessionEvent)  { 
    }
	
}
