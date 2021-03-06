package by.training.filmstore.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.filmstore.command.Command;
import by.training.filmstore.service.impl.PoolConnectionServiceInitializer;

public class PoolConnectionDestroyCommand implements Command {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try{
			PoolConnectionServiceInitializer.destroyPoolConnection();
		}catch(RuntimeException e){
			throw new RuntimeException(e);
		}
	}

}
