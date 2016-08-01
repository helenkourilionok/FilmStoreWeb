package by.training.filmstore.command.impl;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.command.Command;
import by.training.filmstore.service.FilmService;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceImageNotFoundException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectIdException;


public class ShowImageCommand implements Command {

	private final static Logger logger = LogManager.getLogger(ShowImageCommand.class);
	private final static String ID_PARAM = "id";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		short id = Short.parseShort(request.getParameter(ID_PARAM));

		FilmStoreServiceFactory filmStoreServiceFactoryImpl = FilmStoreServiceFactory.getServiceFactory();
		FilmService filmService = filmStoreServiceFactoryImpl.getFilmService();
		byte[] image = null;

		try {
			image = filmService.findFilmImageByFilmId(id);
			response.setContentType("image/jpeg");
			response.setContentLength(image.length);
			ServletOutputStream out = response.getOutputStream();
			out.write(image);
			out.flush();
		} catch (FilmStoreServiceException e) {
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE);
		} catch (FilmStoreServiceIncorrectIdException e) {
			logger.error("Incorrect film id!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch (FilmStoreServiceImageNotFoundException e) {
			logger.error("Image not found!",e);
			downloadDefaultImage(request, response);
		}
	}

	private void downloadDefaultImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext servletContext = request.getServletContext();
		InputStream inputStream = servletContext.getResourceAsStream("images/default.jpg");
		byte[] defaultImage = null;
		try {
			defaultImage = new byte[inputStream.available()];
			inputStream.read(defaultImage, 0, defaultImage.length);
			inputStream.close();
		
			response.setContentType("image/jpeg");
			response.setContentLength(defaultImage.length);

			ServletOutputStream out = response.getOutputStream();
			out.write(defaultImage);
			out.flush();
		} catch (IOException e) {
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		}
	}
}
