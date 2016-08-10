package by.training.filmstore.command.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.command.Command;
import by.training.filmstore.entity.Film;
import by.training.filmstore.service.FilmService;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidOperException;

public class CreateFilmCommand implements Command {
	
	private final static Logger logger = LogManager.getLogger(CreateFilmCommand.class);
	
	private final static String IMAGE_UPLOAD_PATH = "imageUploadPath";
	private final static String ENCODING = "UTF-8";

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
		if ((sessionCheckRole == null)
				|| (sessionCheckRole.getAttribute(CommandParamName.USER_ROLE).equals("ROLE_GUEST"))) {
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_LOGIN).forward(request, response);
		}
		
		FilmStoreServiceFactory filmStoreServiceFactory = FilmStoreServiceFactory.getServiceFactory();
		FilmService filmService = filmStoreServiceFactory.getFilmService();
		
		String prev_query = (String)sessionCheckRole.getAttribute(CommandParamName.PREV_QUERY);
		
		Map<String, String> listParamValue = null;
		List<Short> idActors = null;
		Film film = null;
		try {
			listParamValue = parseMultipartRequest(request);
			if(listParamValue == null){
				request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
			}

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
			
			idActors = strToListShort(listActors);
			film = filmService.create(name, genres, countries, yearOfRel, quality, filmDirId, description, price, countFilms, image);
			filmService.createFilmActor(film, idActors);
			response.sendRedirect(prev_query+"&"+CREATION_FAILED+"=false");
		}catch(FilmStoreServiceInvalidOperException e){
			logger.error("Film creation failed!",e);
			response.sendRedirect(prev_query+"&"+CREATION_FAILED+"=true");
		}catch(FilmStoreServiceIncorrectParamException e){
			logger.error("Can't create film because of incorrect parametrs!",e);
			response.sendRedirect(prev_query+"&"+CREATION_FAILED+"=true");
		}
		catch(FilmStoreServiceException e){
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		}
	}

	private Map<String,String> parseMultipartRequest(HttpServletRequest request){
		String pathImage = "images/";
		Map<String, String> listParamValue = new HashMap<>();
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		try{
			if (isMultipart) {
	
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				List<FileItem> multiparts = upload.parseRequest(request);
				for (FileItem item : multiparts) {
					if (item.isFormField()) {
						processFormField(item, listParamValue);
					} else {
						String imageUploadPath = request.getServletContext().getInitParameter(IMAGE_UPLOAD_PATH);
						processUploadedFile(item,imageUploadPath);
						listParamValue.put(IMAGE, pathImage+item.getName());
						//!!!!!!!!!!!!!!!!imageUploadPath - path for save image in images folder
						//!!!!!!!!!!!!!!!!pathImage - path for DB
					}
				}
			}
		}catch(Exception e){
			listParamValue = null;
		}
		
		return listParamValue;
	}

	private void processUploadedFile(FileItem item, String uploadPath) throws Exception {
		File uploadedFile = new File(uploadPath + File.separator + item.getName());
		item.write(uploadedFile);
	}

	private void processFormField(FileItem item, Map<String, String> listParamValue)
			throws UnsupportedEncodingException {

		String key = item.getFieldName();
		String value = item.getString(ENCODING);

		if (listParamValue.containsKey(key)) {
			String oldValue = listParamValue.get(key);
			listParamValue.put(key, oldValue + "," + value);
		} else {
			listParamValue.put(key, value);
		}
	}
	
	private List<Short> strToListShort(String listActors){
		if(listActors==null){
			return null;
		}
		List<Short> idActors = new ArrayList<>();
		String[] listId = listActors.split(",");
		short idDefault = 0;
		for(String strId : listId){
			try{
				idActors.add(Short.parseShort(strId));
			}catch(NumberFormatException e){
				idActors.add(idDefault);
			}
		}
		return idActors;
	}
}
