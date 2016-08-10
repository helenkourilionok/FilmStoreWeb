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
import by.training.filmstore.service.exception.FilmStoreServiceFilmNotFoundException;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectParamException;
import by.training.filmstore.service.exception.FilmStoreServiceListCommentNotFoundException;

public class CommentShowPageCommand implements Command {

	private final static Logger logger = LogManager.getLogger(CommentShowPageCommand.class);
	
	private final static String FILM_ID = "id";
	private final static String FILM_ATTR = "film";
	private final static String LIST_COMMENT_ATTR = "listComment";
	private final static String FILM_NOT_FOUND_ATTR = "filmNotFound";
	private final static String LIST_COMMENT_NOT_FOUND = "listComNotFound";
	private final static String COMMENT_CREATION_FAILED = "creationFailed";
	private final static String COMMENT_PARAM_FAILED = "incorrectContent";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession session = request.getSession(true);

		String query = QueryUtil.createHttpQueryString(request);
		session.setAttribute(CommandParamName.PREV_QUERY, query);
		
		String filmId = request.getParameter(FILM_ID);
		String creationFailed = request.getParameter(COMMENT_CREATION_FAILED);
		String incorrectParam = request.getParameter(COMMENT_PARAM_FAILED);
		
		FilmStoreServiceFactory filmStoreServiceFactory = FilmStoreServiceFactory.getServiceFactory();
		FilmService filmService = filmStoreServiceFactory.getFilmService();
		CommentService commentService = filmStoreServiceFactory.getCommentService(); 
		
		List<Comment> listComment = null;
	
		Film film = null;
		boolean lazyInit = false;
		
		try {
			
			request.setAttribute(COMMENT_CREATION_FAILED, creationFailed);
			request.setAttribute(COMMENT_PARAM_FAILED, incorrectParam);
			
			film = filmService.find(filmId,lazyInit);
			request.setAttribute(FILM_ATTR, film);
			
			listComment = commentService.findCommentByIdFilm(filmId);
			request.setAttribute(LIST_COMMENT_ATTR, listComment);

			request.getRequestDispatcher(CommandParamName.PATH_FILM_WITH_COMMENT_PAGE).forward(request, response);
		} catch (FilmStoreServiceException e) {
			
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
			
		}catch (FilmStoreServiceFilmNotFoundException |
				FilmStoreServiceIncorrectParamException e) {
			logger.error("Incorrect film id or can't find film by this id!",e);
			request.setAttribute(FILM_NOT_FOUND_ATTR,"true");
			request.getRequestDispatcher(CommandParamName.PATH_FILM_WITH_COMMENT_PAGE).forward(request, response);
		} catch (FilmStoreServiceListCommentNotFoundException e) {
			logger.error("This film hasn't comments!",e);
			request.setAttribute(LIST_COMMENT_NOT_FOUND, "true");
			request.getRequestDispatcher(CommandParamName.PATH_FILM_WITH_COMMENT_PAGE).forward(request, response);
		}
	}

}
