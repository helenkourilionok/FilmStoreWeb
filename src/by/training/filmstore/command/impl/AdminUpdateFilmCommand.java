package by.training.filmstore.command.impl;

import java.io.IOException;
import java.util.ArrayList;
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
import by.training.filmstore.entity.Actor;
import by.training.filmstore.entity.Film;
import by.training.filmstore.service.FilmService;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectFilmParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidFilmOperException;

public class AdminUpdateFilmCommand implements Command {

	private final static Logger logger = LogManager.getLogger(AdminUpdateFilmCommand.class);

	private final static String PATH_TO_IMAGE_FOLDER = "images/";

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
		if ((sessionCheckRole == null)||(!sessionCheckRole.getAttribute(CommandParamName.USER_ROLE).toString().equals("ROLE_ADMIN"))) {
			request.getRequestDispatcher(CommandParamName.PATH_ACESS_DENIED_PAGE).forward(request, response);
			return;
		}
		
		
		FilmStoreServiceFactory filmStoreServiceFactory = FilmStoreServiceFactory.getServiceFactory();
		FilmService filmService = filmStoreServiceFactory.getFilmService();
		
		Map<String, String> listParamValue = null;
		List<Short> idNewActors = null;
		List<Short> idOldActors = null;
		
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
			String image = PATH_TO_IMAGE_FOLDER+listParamValue.get(IMAGE);
			idNewActors = EditFilmUtil.strToListShort(listActors);
			filmService.update(filmId,name, genres, countries, yearOfRel, quality,
								filmDirId, description, price, countFilms, image);
			Film film = (Film)sessionCheckRole.getAttribute(FILM);
			idOldActors = getListIdActorsFromListActors(film.getActors());
			removeCommonId(idNewActors, idOldActors);

			if(!idNewActors.isEmpty() || !idOldActors.isEmpty()){
				FilmActorOperation operation = defineTypeOfOperation(idNewActors, idOldActors);
				executeFilmActorOperation(operation, filmService, film.getId(), idNewActors, idOldActors);
			}

			sessionCheckRole.setAttribute(FILM,null);
			request.getRequestDispatcher(CommandParamName.PATH_SUCCESS_PAGE).forward(request, response);
		}catch(FilmStoreServiceInvalidFilmOperException e){
			logger.error("Film updating failed!",e);
			request.setAttribute(UPDATING_FAILED, "true");
			request.getRequestDispatcher(CommandParamName.PATH_UPDATE_FILM_PAGE).forward(request, response);
		}catch(FilmStoreServiceIncorrectFilmParamException e){
			logger.error("Can't updating film because of incorrect parametrs!",e);
			request.setAttribute(INCORRECT_PARAMS, "true");
			request.getRequestDispatcher(CommandParamName.PATH_UPDATE_FILM_PAGE).forward(request, response);
		}
		catch(FilmStoreServiceException e){
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		}

	}
	
	private List<Short> getListIdActorsFromListActors(List<Actor> listActor) {
		List<Short> result = new ArrayList<>();
		for (Actor actor : listActor) {
			result.add(actor.getId());
		}
		return result;
	}

	private FilmActorOperation defineTypeOfOperation(List<Short> idNewActors, List<Short> idOldActors) {
		if (idOldActors.size() == idNewActors.size()) {
			return FilmActorOperation.UPDATE;
		}
		if (idOldActors.size() > idNewActors.size()) {
			return FilmActorOperation.DELETE_UPDATE;
		}
		if (idOldActors.size() < idNewActors.size()) {
			return FilmActorOperation.CREATE_UPDATE;
		}
		return FilmActorOperation.UPDATE;
	}

	private void executeFilmActorOperation(FilmActorOperation operation,FilmService filmService,
			short filmId,List<Short> idNewActors,List<Short> idOldActors) throws FilmStoreServiceException, FilmStoreServiceIncorrectFilmParamException, FilmStoreServiceInvalidFilmOperException{
		int newSize = idNewActors.size();
		int oldSize = idOldActors.size();
		switch(operation){
			case UPDATE:
			{
				filmService.updateFilmActor(filmId,idNewActors,idOldActors);
			}break;
			case CREATE_UPDATE:{				
				List<Short> actorsForCreate = new ArrayList<Short>(idNewActors.subList(oldSize, newSize));
				idNewActors.removeAll(actorsForCreate);
				if(!idNewActors.isEmpty()){
					filmService.updateFilmActor(filmId, idNewActors, idOldActors);
				}
				filmService.createFilmActor(filmId, actorsForCreate);
			}break;
			case DELETE_UPDATE:{
				List<Short> actorsForDelete = new ArrayList<Short>(idOldActors.subList(newSize,oldSize));
				idOldActors.removeAll(actorsForDelete);
				if(!idOldActors.isEmpty()){
					filmService.updateFilmActor(filmId, idNewActors, idOldActors);
				}
				filmService.deleteFilmActor(filmId, actorsForDelete);
			}break;
		}
	}
	
	private void removeCommonId(List<Short> idNewActors, List<Short> idOldActors){
		List<Short> idForRemove = new ArrayList<>();
		for(Short id:idOldActors){
			if(idNewActors.contains(id)){
				idForRemove.add(id);
			}
		}
		idNewActors.removeAll(idForRemove);
		idOldActors.removeAll(idForRemove);
	}
	
	enum FilmActorOperation {
		CREATE_UPDATE, DELETE_UPDATE, UPDATE
	}

}
