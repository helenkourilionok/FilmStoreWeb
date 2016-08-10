package by.training.filmstore.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import by.training.filmstore.command.Command;
import by.training.filmstore.command.util.QueryUtil;
import by.training.filmstore.entity.Actor;
import by.training.filmstore.entity.FilmDirector;
import by.training.filmstore.service.ActorService;
import by.training.filmstore.service.FilmDirectorService;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.exception.FilmStoreServiceException;

public class CreateFilmShowPageCommand implements Command {
	
	private final static String FILM_CREATION_FAILED = "filmCreationFailed";
	
	private final static String LIST_ACTORS = "listActors";
	private final static String LIST_FILM_DIR = "listFilmDir";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession sessionCheckRole = request.getSession(false);
		if((sessionCheckRole == null) || (sessionCheckRole.getAttribute(CommandParamName.USER_ROLE).equals("ROLE_GUEST"))){
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_LOGIN).forward(request, response);
		}
		
		HttpSession session = request.getSession(true);
		String query = QueryUtil.createHttpQueryString(request);
		session.setAttribute(CommandParamName.PREV_QUERY, query);
		
		FilmStoreServiceFactory filmStoreServiceFactory = FilmStoreServiceFactory.getServiceFactory();
		ActorService actorService = filmStoreServiceFactory.getActorService();
		FilmDirectorService filmDirService = filmStoreServiceFactory.getFilmDirectorService();
		
		List<Actor> listActors = null;
		List<FilmDirector> listFilmDir = null;
		
		try {
			checkRequest(request);
		    listActors = actorService.findAllActors();
		    listFilmDir = filmDirService.findAllFilmDirectors();
		    request.setAttribute(LIST_ACTORS, listActors);
		    request.setAttribute(LIST_FILM_DIR, listFilmDir);
			request.getRequestDispatcher(CommandParamName.PATH_CREATE_FILM_PAGE).forward(request, response);
		} catch (FilmStoreServiceException e) {
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} 
		
	}

	private void checkRequest(HttpServletRequest request){
		String creationFailed = request.getParameter(FILM_CREATION_FAILED);
		request.setAttribute(FILM_CREATION_FAILED, creationFailed);
	}
}
