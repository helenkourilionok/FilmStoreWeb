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
import by.training.filmstore.entity.Comment;
import by.training.filmstore.service.CommentService;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectCommentParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidCommentOperException;
import by.training.filmstore.service.exception.FilmStoreServiceListCommentNotFoundException;

public class MakeCommentCommand implements Command {

	private final static Logger logger = LogManager.getLogger(MakeCommentCommand.class);
	
	private final static String FILM_ID = "filmId";
	private final static String CONTENT = "content";
	private final static String LIST_COMMENT_ATTR = "listComment";
	private final static String COMMENT_CREATION_FAILED = "creationFailed";
	private final static String COMMENT_PARAM_FAILED = "incorrectContent";
		
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession session = request.getSession(false);
		if((session == null)||(session.getAttribute(CommandParamName.USER_ROLE).toString().equals("ROLE_GUEST"))){
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_LOGIN).forward(request, response);
			return;
		}
	
		
		String filmId = request.getParameter(FILM_ID);
		String content = request.getParameter(CONTENT);
		String userEmail = request.getParameter(CommandParamName.USER_EMAIL);
		
		List<Comment> listComment = null;
		
		FilmStoreServiceFactory filmStoreServiceFactory = FilmStoreServiceFactory.getServiceFactory();
		CommentService commentService = filmStoreServiceFactory.getCommentService();
		
		try {
			
			commentService.create(userEmail, filmId, content);
			
			listComment = commentService.findCommentByIdFilm(filmId);
			session.setAttribute(LIST_COMMENT_ATTR, listComment);
			request.getRequestDispatcher(CommandParamName.PATH_FILM_WITH_COMMENT_PAGE).forward(request, response);
		} catch (FilmStoreServiceException e) {
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		}catch (FilmStoreServiceIncorrectCommentParamException | 
				FilmStoreServiceListCommentNotFoundException e) {
			logger.error("Incorrect comment parametrs!",e);
			request.setAttribute(COMMENT_PARAM_FAILED,"true");
			request.getRequestDispatcher(CommandParamName.PATH_FILM_WITH_COMMENT_PAGE).forward(request, response);
		} catch (FilmStoreServiceInvalidCommentOperException e) {
			logger.error("Can't create comment!",e);
			request.setAttribute(COMMENT_CREATION_FAILED,"true");
			request.getRequestDispatcher(CommandParamName.PATH_FILM_WITH_COMMENT_PAGE).forward(request, response);
		}
		
	}
}
