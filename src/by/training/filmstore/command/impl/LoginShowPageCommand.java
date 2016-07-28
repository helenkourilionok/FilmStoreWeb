package by.training.filmstore.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.filmstore.command.Command;

public class LoginShowPageCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	
		request.getRequestDispatcher(CommandParamName.PATH_PAGE_LOGIN).forward(request, response);
		
	}

}
