package models;

import javax.persistence.*;

import models.base.BaseModel;
import play.db.jpa.JPA;

@Entity
@NamedQueries({
		@NamedQuery(name = Spiel.QUERY_FETCH_ALL, query = "SELECT s FROM Spiel s ORDER BY s.name"),
		@NamedQuery(name = Spiel.QUERY_FIND_BY_NAME, query = "SELECT s FROM Spiel s WHERE s.name = :"
				+ Spiel.PARAM_NAME + " ORDER BY s.name"),

})
public class Spiel extends BaseModel {

	public static final String QUERY_FETCH_ALL = "Spiel.fetchAll";
	public static final String QUERY_FIND_BY_NAME = "Spiel.findByName";
	public static final String PARAM_NAME = "param_name";


	@Column(name = "name")
	public String name;

	public String player1;

	public String player2;

	public String uhr;

	public String datum;

	@Column(name = "score1")
    public int score1;

    @Column(name = "score2")
    public int score2;

	public String status;

	public String beschreibung;

	public static Spiel findById(long id) {
		return JPA.em().find(Spiel.class, id);
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
		sb.append("spiel { ").append("id :").append(id).append(", ")
				.append("name :").append(name).append(", ")
				.append("player1 :").append(player1).append(", ")
				.append("player2 :").append(player2).append(" }");
		return sb.toString();
	}

}
