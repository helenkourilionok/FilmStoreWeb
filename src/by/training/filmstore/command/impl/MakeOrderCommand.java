package by.training.filmstore.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.filmstore.command.Command;

public class MakeOrderCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession sessionCheckRole = request.getSession(false);
		if ((sessionCheckRole == null)
				|| (!sessionCheckRole.getAttribute(CommandParamName.USER_ROLE).equals("ROLE_USER"))) {
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_LOGIN).forward(request, response);
		}
		
		
		String userEmail = (String)sessionCheckRole.getAttribute(CommandParamName.USER_EMAIL);
		//make in Cookie util method for get goodOfOrders using cookies
        //goodOfOredr for each cookie
	}

}
