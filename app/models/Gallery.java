package models;

import javax.persistence.*;

import models.base.BaseModel;
import play.db.jpa.JPA;

@Entity
@NamedQueries({
		@NamedQuery(name = Gallery.QUERY_FETCH_ALL, query = "SELECT gal FROM Gallery gal ORDER BY gal.name"),
		@NamedQuery(name = Gallery.QUERY_FIND_BY_NAME, query = "SELECT gal FROM Gallery gal WHERE gal.name = :"
				+ Gallery.PARAM_NAME + " ORDER BY gal.name"),

})
public class Gallery extends BaseModel {

	public static final String QUERY_FETCH_ALL = "Gallery.fetchAll";
	public static final String QUERY_FIND_BY_NAME = "Gallery.findByName";
	public static final String PARAM_NAME = "param_name";


	@Column(name = "name")
	public String name;

	@ManyToOne
	public Folder inFolder;

	public static Gallery findById(long id) {
		return JPA.em().find(Gallery.class, id);
	}

	@Override
	public void create() {
		try {
			JPA.em().persist(this);
		} catch (Exception e) {
			//Blabla
		}
	}

	@Override
	public void update() {
		JPA.em().merge(this);
	}

	@Override
	public void delete() {
			JPA.em().remove(this);
	}

	public String toAlternateString() {
		StringBuilder sb = new StringBuilder();
		sb.append("gallery { ").append("id :").append(id).append(", ")
				.append("name :").append(name).append(" }");
		return sb.toString();
	}

}
