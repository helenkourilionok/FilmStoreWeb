package by.training.filmstore.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.command.Command;
import by.training.filmstore.command.util.CheckUserRoleUtil;
import by.training.filmstore.entity.Film;
import by.training.filmstore.service.FilmService;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceFilmNotFoundException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectFilmParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidFilmOperException;

public final class AdminDeleteFilmCommand implements Command {
	
	private final static Logger logger = LogManager.getLogger(AdminDeleteFilmCommand.class);
	
	private final static String FILM_ID = "id";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession sessionCheckRole = request.getSession(false);
		if(!CheckUserRoleUtil.isAdmin(sessionCheckRole)){
			request.getRequestDispatcher(CommandParamName.PATH_ACESS_DENIED_PAGE).forward(request, response);
			return;
		}
		
		FilmStoreServiceFactory filmStoreServiceFactory = FilmStoreServiceFactory.getServiceFactory();
		FilmService filmService = filmStoreServiceFactory.getFilmService();
		
		String filmId = request.getParameter(FILM_ID);
		boolean lazyInit = false;
		Film film = null;
		
		try {
			
			film = filmService.find(filmId, lazyInit);
			filmService.delete(film.getId());
			
			String prev_query = (String) request.getSession(false).getAttribute(CommandParamName.PREV_QUERY);
			if (prev_query != null) {
				response.sendRedirect(prev_query);
			} else {
				request.getRequestDispatcher(CommandParamName.PATH_PAGE_INDEX_REDIRECT).forward(request, response);
			}
		} catch (FilmStoreServiceException e) {
			logger.error("Operation failed!Can't delete film!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch (FilmStoreServiceIncorrectFilmParamException e) {
			logger.error("Incorrect params!Can't delete film!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch (FilmStoreServiceFilmNotFoundException e) {
			logger.error("Incorrect film id!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch (FilmStoreServiceInvalidFilmOperException e) {
			logger.error("Invalid operation!Can't delete film or update filmactor table!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		}			
	}
}
