package by.training.filmstore.command.util;

import java.util.Iterator;
import java.util.List;

public class CustomAttrTagUtil {

	private List<String> listStr;
	private Iterator<String> strIterator;
	
	public void setListStr(List<String> listStr){
		this.listStr = listStr;
		this.strIterator = listStr.iterator();
	}
	
	public int getSize(){
		return listStr.size();
	}
	
	public String getValue(){
		if(strIterator.hasNext()){
			return  strIterator.next();
		}else{
			return null;
		}
	}
}
