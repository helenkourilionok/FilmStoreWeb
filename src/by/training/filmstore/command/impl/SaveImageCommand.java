package by.training.filmstore.command.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import by.training.filmstore.command.Command;

public class SaveImageCommand implements Command {

	private final static String PATH_IMAGE_FOR_DB = "imageDBPath";
	private final static String IMAGE_PARAM = "image";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession session = request.getSession(false);
		if((session == null) || (session.getAttribute(CommandParamName.USER_ROLE).equals("ROLE_GUEST"))){
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_LOGIN).forward(request, response);
		}
		
		String imagePath = "/image";
		String image = request.getParameter(IMAGE_PARAM);
		String pathImageForDB = imagePath+File.separator+image;
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			FileItemFactory factory = new DiskFileItemFactory();

			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				List<FileItem> multiparts = upload.parseRequest(request);
				for (FileItem item : multiparts) {
					if (!item.isFormField()) {
						item.write(new File(imagePath + File.separator + image));
					}
				}
			} catch (Exception e) {
				pathImageForDB = null;
			}
		}
		
		request.setAttribute(PATH_IMAGE_FOR_DB, pathImageForDB);
	}

}
