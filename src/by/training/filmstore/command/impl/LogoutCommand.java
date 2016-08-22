package by.training.filmstore.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.filmstore.command.Command;

public class LogoutCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession(false);
		if((session!=null)&&(!session.getAttribute(CommandParamName.USER_ROLE).toString().equals("ROLE_GUEST"))){
			session.invalidate();
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_INDEX_REDIRECT).forward(request, response);
		}else{
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_LOGIN).forward(request, response);
		}
	}

}
