package by.training.filmstore.entity;

import java.io.Serializable;

public class CommentPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7225595530210508482L;
	private String userEmail;
	private short filmId;
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public short getFilmId() {
		return filmId;
	}
	public void setFilmId(short filmId) {
		this.filmId = filmId;
	}
	public CommentPK() {
		super();
	}
	public CommentPK(String userEmail, short filmId) {
		super();
		this.userEmail = userEmail;
		this.filmId = filmId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + filmId;
		result = prime * result + ((userEmail == null) ? 0 : userEmail.hashCode());
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
		CommentPK other = (CommentPK) obj;
		if (filmId != other.filmId)
			return false;
		if (userEmail == null) {
			if (other.userEmail != null)
				return false;
		} else if (!userEmail.equals(other.userEmail))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CommentPK [userEmail=" + userEmail + ", filmId=" + filmId + "]";
	}
	
}
