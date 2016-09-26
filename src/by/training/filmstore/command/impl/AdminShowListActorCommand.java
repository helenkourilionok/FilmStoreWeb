package by.training.filmstore.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.command.Command;
import by.training.filmstore.command.util.CustomAttrTagUtil;
import by.training.filmstore.command.util.QueryUtil;
import by.training.filmstore.entity.Actor;
import by.training.filmstore.service.ActorService;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.exception.FilmStoreServiceException;

public final class AdminShowListActorCommand implements Command {
	
	private final static Logger logger = LogManager.getLogger(AdminShowListActorCommand.class);
	
	private final static String LIST_ACTOR = "listActor"; 
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	
		HttpSession sessionCheckRole = request.getSession(false);
		if ((sessionCheckRole == null)||
		(!sessionCheckRole.getAttribute(CommandParamName.USER_ROLE).toString().equals("ROLE_ADMIN"))) {
			request.getRequestDispatcher(CommandParamName.PATH_ACESS_DENIED_PAGE).forward(request, response);
			return;
		}
		
		String query = QueryUtil.createHttpQueryString(request);
		sessionCheckRole.setAttribute(CommandParamName.PREV_QUERY, query);
		
		FilmStoreServiceFactory filmStoreServiceFactory = FilmStoreServiceFactory.getServiceFactory();
		ActorService actorService = filmStoreServiceFactory.getActorService();
		
		CustomAttrTagUtil customAttrTagUtil = new CustomAttrTagUtil();
		List<Actor> listActor = null;
		List<String> listFio = null;
		
		try {
			listActor = actorService.findAllActors();
			listFio = getFIOActorsFromList(listActor);
			customAttrTagUtil.setListStr(listFio);
			request.setAttribute(LIST_ACTOR, customAttrTagUtil);
			request.getRequestDispatcher(CommandParamName.PATH_LIST_ACTOR).forward(request, response);
		} catch (FilmStoreServiceException e) {
			logger.error("Can't find actors in database!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		}

	}

	
	private List<String> getFIOActorsFromList(List<Actor> listActor){
		List<String> fioActor = new ArrayList<>();
		for(Actor actor:listActor){
			fioActor.add(actor.getFio());
		}
		return fioActor; 
	}
}
