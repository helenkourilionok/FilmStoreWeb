package by.training.filmstore.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.command.Command;
import by.training.filmstore.command.util.QueryUtil;
import by.training.filmstore.entity.Comment;
import by.training.filmstore.entity.Film;
import by.training.filmstore.service.CommentService;
import by.training.filmstore.service.FilmService;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectCommentParamException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectFilmParamException;
import by.training.filmstore.service.exception.FilmStoreServiceListCommentNotFoundException;
import by.training.filmstore.service.exception.FilmStoreServiceListFilmNotFoundException;

public final class FindFilmByNameCommand implements Command {

	private final static Logger logger = LogManager.getLogger(FindFilmByNameCommand.class);

	private final static String FILM_NAME = "filmName";
	private final static String FILM_INFO = "filmInfo";
	private final static String LIST_COMMENT_ATTR = "listComment";
	private final static String FILM_NOT_FOUND_ATTR = "filmNotFound";
	private final static String LIST_COMMENT_NOT_FOUND = "listComNotFound";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String prev_query = QueryUtil.createHttpQueryString(request);
		HttpSession session = request.getSession(true);
		session.setAttribute(CommandParamName.PREV_QUERY, prev_query);
		
		String filmName = request.getParameter(FILM_NAME);
		List<Comment> listComment = null;
		Film film = null;
		
		FilmStoreServiceFactory filmStoreServiceFactory = FilmStoreServiceFactory.getServiceFactory();
		FilmService filmService = filmStoreServiceFactory.getFilmService();
		CommentService commentService = filmStoreServiceFactory.getCommentService();
		
		try {
	
			film = filmService.findFilmByName(filmName).get(0);
			session.setAttribute(FILM_INFO, film);
			
			listComment = commentService.findCommentByIdFilm(String.valueOf(film.getId()));
			session.setAttribute(LIST_COMMENT_ATTR, listComment);
			
			request.getRequestDispatcher(CommandParamName.PATH_FILM_WITH_COMMENT_PAGE).forward(request, response);
		} catch (FilmStoreServiceException e) {
			logger.error("Can't find film by this name!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch (FilmStoreServiceListFilmNotFoundException|	
				FilmStoreServiceIncorrectFilmParamException |
				FilmStoreServiceIncorrectCommentParamException e) {
			logger.error("Incorrect film id or can't find film by this id!",e);
			request.setAttribute(FILM_NOT_FOUND_ATTR,"true");
			request.getRequestDispatcher(CommandParamName.PATH_FILM_WITH_COMMENT_PAGE).forward(request, response);
		} catch (FilmStoreServiceListCommentNotFoundException e) {
			logger.error("This film hasn't comments!",e);
			request.setAttribute(LIST_COMMENT_NOT_FOUND, "true");
		}
	}

}
