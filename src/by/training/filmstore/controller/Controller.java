package by.training.filmstore.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.command.Command;

public class Controller extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = LogManager.getLogger(Controller.class);
	
	private static final String PATH_ERROR_PAGE = "error-page.jsp";
	private static final String COMMAND = "command";
	
    public Controller() {
        super();    
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String commandName = request.getParameter(COMMAND);
			Command command  = CommandHelper.getInstance().getCommand(commandName);
			command.execute(request, response);
		}catch(RuntimeException e){
			logger.error("Error creating/destroy connection with database!",e);
			request.getRequestDispatcher(PATH_ERROR_PAGE).forward(request, response);
		}
	}

}
