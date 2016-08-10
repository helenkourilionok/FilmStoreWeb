package by.training.filmstore.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.filmstore.command.Command;
import by.training.filmstore.command.util.QueryUtil;

public class SignUpShowPageCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession session = request.getSession(true);

		String query = QueryUtil.createHttpQueryString(request);
		session.setAttribute(CommandParamName.PREV_QUERY, query);
		
		request.getRequestDispatcher(CommandParamName.PATH_PAGE_SIGNUP).forward(request, response);;

	}

}
