package by.training.filmstore.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.filmstore.command.Command;

public class Controller extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String COMMAND = "command";
	
    public Controller() {
        super();    
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String commandName = request.getParameter(COMMAND);
		System.out.println("Command"+commandName);
		Command command  = CommandHelper.getInstance().getCommand(commandName);
		command.execute(request, response);
//		request.getSession(true).setAttribute("local", request.getParameter("language"));
//		System.out.println(request.getParameter("language"));
//		request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
	}

}
