package by.training.filmstore.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.command.Command;
import by.training.filmstore.command.util.CheckUserRoleUtil;
import by.training.filmstore.service.FilmDirectorService;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectFilmDirParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidFilmDirOperException;

public class AdminCreateFilmDirectorCommand implements Command {

	private final static Logger logger = LogManager.getLogger(AdminCreateFilmDirectorCommand.class);
	
	private final static String FIO = "fio";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession sessionCheckRole = request.getSession(false);
		if(!CheckUserRoleUtil.isAdmin(sessionCheckRole)){
			request.getRequestDispatcher(CommandParamName.PATH_ACESS_DENIED_PAGE).forward(request, response);
			return;
		}
		
		FilmStoreServiceFactory filmStoreServiceFactory = FilmStoreServiceFactory.getServiceFactory();
		FilmDirectorService filmDirectorService = filmStoreServiceFactory.getFilmDirectorService();
		
		String fio = request.getParameter(FIO);
		
		try {
			filmDirectorService.create(fio);
			request.getRequestDispatcher(CommandParamName.PATH_SUCCESS_PAGE).forward(request, response);
		} catch (FilmStoreServiceException e) {
			logger.error("Operation failed!Can't film director!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch (FilmStoreServiceInvalidFilmDirOperException e) {
			logger.error("Operation failed!Can't create film director!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch (FilmStoreServiceIncorrectFilmDirParamException e) {
			logger.error("Operation failed!Can't create film director!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		}

	}

}
