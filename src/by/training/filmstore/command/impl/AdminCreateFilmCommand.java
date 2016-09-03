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
import by.training.filmstore.command.util.EditFilmUtil;
import by.training.filmstore.entity.Film;
import by.training.filmstore.service.FilmService;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectFilmParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidFilmOperException;

public class AdminCreateFilmCommand implements Command {
	
	private final static Logger logger = LogManager.getLogger(AdminCreateFilmCommand.class);

	private final static String CREATION_FAILED = "filmCreationFailed";
	
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
		if ((sessionCheckRole == null)||
		(!sessionCheckRole.getAttribute(CommandParamName.USER_ROLE).toString().equals("ROLE_ADMIN"))) {
			request.getRequestDispatcher(CommandParamName.PATH_ACESS_DENIED_PAGE).forward(request, response);
			return;
		}
		
		FilmStoreServiceFactory filmStoreServiceFactory = FilmStoreServiceFactory.getServiceFactory();
		FilmService filmService = filmStoreServiceFactory.getFilmService();
		
		String prev_query = (String)sessionCheckRole.getAttribute(CommandParamName.PREV_QUERY);
		
		Map<String, String> listParamValue = null;
		List<Short> idActors = null;
		Film film = null;
		try {
			listParamValue = EditFilmUtil.parseMultipartRequest(request);
			if(listParamValue != null){
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
				
				idActors = EditFilmUtil.strToListShort(listActors);
				film = filmService.create(name, genres, countries, yearOfRel, quality, filmDirId, description, price, countFilms, image);
				filmService.createFilmActor(film.getId(), idActors);
				request.getRequestDispatcher(CommandParamName.PATH_SUCCESS_PAGE).forward(request, response);
			}
			else{
				request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
			}
		}catch(FilmStoreServiceInvalidFilmOperException e){
			logger.error("Film creation failed!",e);
			response.sendRedirect(prev_query+"&"+CREATION_FAILED+"=true");
		}catch(FilmStoreServiceIncorrectFilmParamException e){
			logger.error("Can't create film because of incorrect parametrs!",e);
			response.sendRedirect(prev_query+"&"+CREATION_FAILED+"=true");
		}
		catch(FilmStoreServiceException e){
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		}
	}
}
