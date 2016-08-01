package by.training.filmstore.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.filmstore.command.Command;
import by.training.filmstore.entity.Comment;
import by.training.filmstore.entity.Film;
import by.training.filmstore.service.FilmService;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectIdException;
import by.training.filmstore.service.exception.FilmStoreServiceParamIncorrectException;

public class CommentShowPageCommand implements Command {

	private final static String FILM_ID = "id";
	private final static String FILM_ATTR = "film";
	private final static String LIST_COMMENT_ATTR = "listComment";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		short filmId = Short.parseShort(request.getParameter(FILM_ID));
		
		FilmStoreServiceFactory filmStoreServiceFactory = FilmStoreServiceFactory.getServiceFactory();
		FilmService filmService = filmStoreServiceFactory.getFilmService();
		
		List<Comment> listComment = null;
		Film film = null;
		
		try {
			film = filmService.find(filmId);
		} catch (FilmStoreServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (FilmStoreServiceIncorrectIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.getRequestDispatcher(CommandParamName.PATH_FILM_WITH_COMMENT_PAGE).forward(request, response);

	}

}
