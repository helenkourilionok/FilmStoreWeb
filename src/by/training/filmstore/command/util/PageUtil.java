package by.training.filmstore.command.util;

import java.io.Serializable;
import java.util.List;

public final class PageUtil implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8384732852202957003L;
	private int currentPage;
	private int countPages;
	private List<Integer> listIndex;
	
	public PageUtil() {
		super();
	}

	public PageUtil(int currentPage, int countPages, List<Integer> listIndex) {
		super();
		this.currentPage = currentPage;
		this.countPages = countPages;
		this.listIndex = listIndex;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCountPages() {
		return countPages;
	}

	public void setCountPages(int countPages) {
		this.countPages = countPages;
	}
	public Integer getListIndex(int index){
		return listIndex.get(index);
	}
	public List<Integer> getListIndex() {
		return listIndex;
	}
	public void setListIndex(int index,int value){
		listIndex.add(index, value);
	}
	public void setListIndex(List<Integer> listIndex) {
		this.listIndex = listIndex;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + countPages;
		result = prime * result + currentPage;
		result = prime * result + ((listIndex == null) ? 0 : listIndex.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageUtil other = (PageUtil) obj;
		if (countPages != other.countPages)
			return false;
		if (currentPage != other.currentPage)
			return false;
		if (listIndex == null) {
			if (other.listIndex != null)
				return false;
		} else if (!listIndex.equals(other.listIndex))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Page [currentPage=" + currentPage + ", countPages=" + countPages + ", listIndex=" + listIndex + "]";
	}
	
	
}
