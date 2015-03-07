package models;

import javax.persistence.*;

import models.base.BaseModel;
import play.db.jpa.JPA;

@Entity
@NamedQueries({
		@NamedQuery(name = Media.QUERY_FETCH_ALL, query = "SELECT m FROM Media m ORDER BY m.name"),
		@NamedQuery(name = Media.QUERY_FIND_BY_NAME, query = "SELECT m FROM Media m WHERE m.name = :"
				+ Media.PARAM_NAME + " ORDER BY m.name"),

})
public class Media extends BaseModel {

	public static final String QUERY_FETCH_ALL = "Media.fetchAll";
	public static final String QUERY_FIND_BY_NAME = "Media.findByName";
	public static final String PARAM_NAME = "param_name";


	@Column(name = "name")
	public String name;

	@ManyToOne
	public Folder inFolder;

	public static Media findById(long id) {
		return JPA.em().find(Media.class, id);
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
		sb.append("media { ").append("id :").append(id).append(", ")
				.append("name :").append(name).append(" }");
		return sb.toString();
	}

}
