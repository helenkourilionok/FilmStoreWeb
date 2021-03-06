package by.training.filmstore.command.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.command.Command;
import by.training.filmstore.command.util.CheckUserRoleUtil;
import by.training.filmstore.command.util.EditFilmUtil;
import by.training.filmstore.entity.Actor;
import by.training.filmstore.entity.Film;
import by.training.filmstore.service.FilmService;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectFilmParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidFilmOperException;

public class AdminUpdateFilmCommand implements Command {

	private final static Logger logger = LogManager.getLogger(AdminUpdateFilmCommand.class);

	private final static String UPDATING_FAILED = "updatingFilmFailed";
	private final static String INCORRECT_PARAMS = "incorrectParams";

	private final static String FILM = "film";
	private final static String FILM_ID = "id";
	private final static String NAME = "name";
	private final static String YEAR_OF_REL = "yearOfRelease";
	private final static String PRICE = "price";
	private final static String COUNT_FILMS = "countFilms";
	private final static String QUALITY = "quality";
	private final static String LIST_COUNTRIES = "list_countries";
	private final static String LIST_GENRES = "genres";
	private final static String FILM_DIRECTOR = "film_director";
	private final static String LIST_ACTORS = "list_actors";
	private final static String DESCRIPTION = "description";
	private final static String IMAGE = "image";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession sessionCheckRole = request.getSession(false);
		if(!CheckUserRoleUtil.isAdmin(sessionCheckRole)){
			request.getRequestDispatcher(CommandParamName.PATH_ACESS_DENIED_PAGE).forward(request, response);
			return;
		}
		
		String prev_query = (String)sessionCheckRole.getAttribute(CommandParamName.PREV_QUERY);
		
		FilmStoreServiceFactory filmStoreServiceFactory = FilmStoreServiceFactory.getServiceFactory();
		FilmService filmService = filmStoreServiceFactory.getFilmService();
		
		Map<String, String> listParamValue = null;
		List<Actor> listNewActors = null;
		List<Short> idNewActors = null;
		
		try {
			listParamValue = EditFilmUtil.parseMultipartRequest(request);
			if(listParamValue == null){
				request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
			}
			
			String filmId = request.getParameter(FILM_ID);
			String name = listParamValue.get(NAME);
			String yearOfRel = listParamValue.get(YEAR_OF_REL);
			String price = listParamValue.get(PRICE);
			String countFilms = listParamValue.get(COUNT_FILMS);
			String quality = listParamValue.get(QUALITY);
			String countries = listParamValue.get(LIST_COUNTRIES);
			String genres = listParamValue.get(LIST_GENRES);
			String filmDirId = listParamValue.get(FILM_DIRECTOR);
			String listActors = listParamValue.get(LIST_ACTORS);
			String description = listParamValue.get(DESCRIPTION);
			String image = listParamValue.get(IMAGE);
			image = checkImage(image);
			
			Film film = (Film)sessionCheckRole.getAttribute(FILM);
			idNewActors = EditFilmUtil.strToListId(listActors);
			listNewActors = EditFilmUtil.createListActorFromList(idNewActors);
			
			filmService.update(filmId,name, genres, countries, yearOfRel, quality,
					filmDirId, description, price, countFilms, image,film.getListActor(),listNewActors);
			
			sessionCheckRole.setAttribute(FILM,null);
			request.getRequestDispatcher(CommandParamName.PATH_SUCCESS_PAGE).forward(request, response);
		}catch(FilmStoreServiceInvalidFilmOperException e){
			logger.error("Film updating failed!",e);
			response.sendRedirect(prev_query+"&"+UPDATING_FAILED+"=true");
			request.getRequestDispatcher(CommandParamName.PATH_UPDATE_FILM_PAGE).forward(request, response);
		}catch(FilmStoreServiceIncorrectFilmParamException e){
			logger.error("Can't updating film because of incorrect parametrs!",e);
			response.sendRedirect(prev_query+"&"+INCORRECT_PARAMS+"=true");
		}
		catch(FilmStoreServiceException e){
			logger.error("Operation failed!Can't update film!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		}

	}
	
	private String checkImage(String image){
		String pathToImageFolder = "images/";
		String result = image;
		if(!image.contains(pathToImageFolder)){
			result = pathToImageFolder+image;
		}
		return result;
	}
}
