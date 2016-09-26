package by.training.filmstore.entity;

import java.io.Serializable;

public class FilmDirector implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1700132050616355594L;
	private short id;
	private String fio;
	public short getId() {
		return id;
	}
	public void setId(short id) {
		this.id = id;
	}
	public String getFio() {
		return fio;
	}
	public void setFio(String fio) {
		this.fio = fio;
	}
	public FilmDirector() {
		super();
	}
	public FilmDirector(short id, String fio) {
		super();
		this.id = id;
		this.fio = fio;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fio == null) ? 0 : fio.hashCode());
		result = prime * result + id;
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
		FilmDirector other = (FilmDirector) obj;
		if (fio == null) {
			if (other.fio != null)
				return false;
		} else if (!fio.equals(other.fio))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "FilmDirector [id=" + id + ", fio=" + fio + "]";
	}
	
}
