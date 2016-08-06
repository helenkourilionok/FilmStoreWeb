package by.training.filmstore.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.command.Command;
import by.training.filmstore.service.CommentService;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidOperException;

public class MakeCommentCommand implements Command {

	private final static Logger logger = LogManager.getLogger(MakeCommentCommand.class);
	
	private final static String FILM_ID = "filmId";
	private final static String CONTENT = "content";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String filmId = request.getParameter(FILM_ID);
		String content = request.getParameter(CONTENT);
		String userEmail = request.getParameter(CommandParamName.USER_EMAIL);
		
		FilmStoreServiceFactory filmStoreServiceFactory = FilmStoreServiceFactory.getServiceFactory();
		CommentService commentService = filmStoreServiceFactory.getCommentService();
		
		String prev_query = (String) request.getSession(false).getAttribute(CommandParamName.PREV_QUERY);
		
		try {
			commentService.create(userEmail, filmId, content);
			response.sendRedirect(prev_query);
		} catch (FilmStoreServiceException e) {
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch (FilmStoreServiceIncorrectParamException e) {
			logger.error("Incorrect comment parametrs!",e);
			response.sendRedirect(prev_query+"&incorrectContent=true");
		} catch (FilmStoreServiceInvalidOperException e) {
			logger.error("Can't create comment!",e);
			response.sendRedirect(prev_query+"&creationFailed=true");
		}
		
	}

}
