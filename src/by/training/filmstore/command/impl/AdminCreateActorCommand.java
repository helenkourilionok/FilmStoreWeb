package by.training.filmstore.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.command.Command;
import by.training.filmstore.service.ActorService;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectActorParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidActorOperException;

public final class AdminCreateActorCommand implements Command {
	
	private final static Logger logger = LogManager.getLogger(AdminCreateActorCommand.class);
	
	private final static String FIO = "fio";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession sessionCheckRole = request.getSession(false);
		if ((sessionCheckRole == null)||(!sessionCheckRole.getAttribute(CommandParamName.USER_ROLE).toString().equals("ROLE_ADMIN"))) {
			request.getRequestDispatcher(CommandParamName.PATH_ACESS_DENIED_PAGE).forward(request, response);
			return;
		}
		
		FilmStoreServiceFactory filmStoreServiceFactory = FilmStoreServiceFactory.getServiceFactory();
		ActorService actorService = filmStoreServiceFactory.getActorService();
		
		String fio = request.getParameter(FIO);
		
		try {
			actorService.create(fio);
			request.getRequestDispatcher(CommandParamName.PATH_SUCCESS_PAGE).forward(request, response);
		} catch (FilmStoreServiceException e) {
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch (FilmStoreServiceInvalidActorOperException e) {
			logger.error("Operation failed!Can't create actor!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch (FilmStoreServiceIncorrectActorParamException e) {
			logger.error("Operation failed!Can't create actor!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		}
	}

}
