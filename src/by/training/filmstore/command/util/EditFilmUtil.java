package by.training.filmstore.command.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public final class EditFilmUtil {
	
	private final static String IMAGE_FOLDER = "/images/";
	private final static String ENCODING = "UTF-8";
	private final static String IMAGE = "image";
	
	public static List<Short> strToListShort(String listActors){
		if(listActors==null){
			return null;
		}
		List<Short> idActors = new ArrayList<>();
		String[] listId = listActors.split(",");
		short idDefault = 0;
		for(String strId : listId){
			try{
				idActors.add(Short.parseShort(strId));
			}catch(NumberFormatException e){
				idActors.add(idDefault);
			}
		}
		return idActors;
	}
	
	public static Map<String,String> parseMultipartRequest(HttpServletRequest request){
		Map<String, String> listParamValue = new HashMap<>();
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		try{
			if (isMultipart) {

				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				List<FileItem> multiparts = upload.parseRequest(request);
				for (FileItem item : multiparts) {
						if (item.isFormField()) {
							processFormField(item, listParamValue);
						} else {
							processFileField(request,item,listParamValue);
						}
				}
			}
		}catch(Exception e){
			listParamValue = null;
		}
		
		return listParamValue;
	}
	
	private static void processFormField(FileItem item, Map<String, String> listParamValue)
			throws UnsupportedEncodingException {

		String key = item.getFieldName();
		String value = item.getString(ENCODING);
		if (listParamValue.containsKey(key)) {
			String oldValue = listParamValue.get(key);
			listParamValue.put(key, oldValue + "," + value);
		} else {
			listParamValue.put(key, value);
		}
	}
	
	private static void processFileField(HttpServletRequest request,FileItem item,Map<String,String> listParamValue) throws Exception{
		if(!item.getName().isEmpty()){
			UUID id = UUID.randomUUID();
			String fileUID = id.toString().replaceAll("-","");
			String fileUID10 = fileUID.substring(0, 10)+"_";
			String imageUploadPath = request.getServletContext().getRealPath(IMAGE_FOLDER+ fileUID10+item.getName());
				if(processUploadedFile(request,item,imageUploadPath)){
					listParamValue.put(IMAGE, "images/"+ fileUID10 + item.getName());
				}
		}
	}
	
	private static boolean processUploadedFile(HttpServletRequest request,FileItem item, String uploadPath) throws Exception {
		if(isFileExist(request, item.getName())){
			return false;//because file isn't uploaded
		}
		File uploadedFile = new File(uploadPath);
		item.write(uploadedFile);
		return true;//if file is uploaded
	}

	private static boolean isFileExist(HttpServletRequest request,String filename){
		String imageUploadPath = request.getServletContext().getRealPath(IMAGE_FOLDER+filename);
		File uploadedFile = new File(imageUploadPath);
		return uploadedFile.exists();
	}

	
}
