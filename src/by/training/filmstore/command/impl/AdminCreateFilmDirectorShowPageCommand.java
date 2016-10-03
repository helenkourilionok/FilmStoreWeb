package by.training.filmstore.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.filmstore.command.Command;
import by.training.filmstore.command.util.CheckUserRoleUtil;
import by.training.filmstore.command.util.QueryUtil;

public class AdminCreateFilmDirectorShowPageCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession sessionCheckRole = request.getSession(false);
		if(!CheckUserRoleUtil.isAdmin(sessionCheckRole)){
			request.getRequestDispatcher(CommandParamName.PATH_ACESS_DENIED_PAGE).forward(request, response);
			return;
		}
		
		String query = QueryUtil.createHttpQueryString(request);
		sessionCheckRole.setAttribute(CommandParamName.PREV_QUERY, query);
		
		request.getRequestDispatcher(CommandParamName.PATH_CREATE_FILMDIR_PAGE).forward(request, response);


	}

}
