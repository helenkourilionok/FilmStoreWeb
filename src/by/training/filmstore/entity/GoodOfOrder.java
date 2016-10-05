package by.training.filmstore.entity;

import java.io.Serializable;

public class GoodOfOrder implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1452709168610741318L;
	private GoodOfOrderPK id;
	private byte countFilm;
	
	public GoodOfOrderPK getId() {
		return id;
	}
	public void setId(GoodOfOrderPK id) {
		this.id = id;
	}
	public byte getCountFilm() {
		return countFilm;
	}
	public void setCountFilm(byte countFilm) {
		this.countFilm = countFilm;
	}
	public GoodOfOrder() {
		super();
	}
	public GoodOfOrder(GoodOfOrderPK id, byte countFilm) {
		super();
		this.id = id;
		this.countFilm = countFilm;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + countFilm;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		GoodOfOrder other = (GoodOfOrder) obj;
		if (countFilm != other.countFilm)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "GoodOfOrder [id=" + id + ", countFilms=" + countFilm + "]";
	}
	
}
