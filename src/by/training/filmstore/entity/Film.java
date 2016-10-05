package by.training.filmstore.entity;

import java.util.List;

import java.io.Serializable;
import java.math.BigDecimal;

public class Film  implements Serializable{
	


	/**
	 * 
	 */
	private static final long serialVersionUID = -7897225574119150872L;
	private short id;
	private String name;
	private String genre;
	private String country;
	private short yearOfRelease;
	private Quality quality;
	private FilmDirector filmDirector;
	private String description;
	private BigDecimal price;
	private short countFilm;
	private String image;
	private List<Actor> listActor;
	public short getId() {
		return id;
	}
	public void setId(short id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public short getYearOfRelease() {
		return yearOfRelease;
	}
	public void setYearOfRelease(short yearOfRelease) {
		this.yearOfRelease = yearOfRelease;
	}
	public Quality getQuality() {
		return quality;
	}
	public void setQuality(Quality quality) {
		this.quality = quality;
	}
	public FilmDirector getFilmDirector() {
		return filmDirector;
	}
	public void setFilmDirector(FilmDirector filmDirector) {
		this.filmDirector = filmDirector;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public short getCountFilm() {
		return countFilm;
	}
	public void setCountFilm(short countFilm) {
		this.countFilm = countFilm;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public List<Actor> getListActor() {
		return listActor;
	}
	public void setListActor(List<Actor> listActor) {
		this.listActor = listActor;
	}
	
	public Film() {
		super();
	}
	

	public Film(String name, String genre, String country, short yearOfRelease, Quality quality, String description,
			BigDecimal price, short countFilm, String image) {
		super();
		this.name = name;
		this.genre = genre;
		this.country = country;
		this.yearOfRelease = yearOfRelease;
		this.quality = quality;
		this.description = description;
		this.price = price;
		this.countFilm = countFilm;
		this.image = image;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + countFilm;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((filmDirector == null) ? 0 : filmDirector.hashCode());
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + id;
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((quality == null) ? 0 : quality.hashCode());
		result = prime * result + yearOfRelease;
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
		Film other = (Film) obj;
		if (countFilm != other.countFilm)
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (filmDirector == null) {
			if (other.filmDirector != null)
				return false;
		} else if (!filmDirector.equals(other.filmDirector))
			return false;
		if (genre == null) {
			if (other.genre != null)
				return false;
		} else if (!genre.equals(other.genre))
			return false;
		if (id != other.id)
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (quality != other.quality)
			return false;
		if (yearOfRelease != other.yearOfRelease)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Film [id=" + id + ", name=" + name + ", genre=" + genre + ", country=" + country + ", yearOfRelease="
				+ yearOfRelease + ", quality=" + quality + ", filmDirector=" + filmDirector + ", description="
				+ description + ", price=" + price + ", countFilms=" + countFilm + ", image=" + image + "]";
	}
	
}
