package models;

import models.base.BaseModel;
import play.db.jpa.JPA;
import javax.persistence.*;
import java.util.List;


@Entity
@NamedQueries({
        @NamedQuery(name = Group_.QUERY_FETCH_ALL, query = "SELECT g FROM Group_ g ORDER BY g.name"),
        @NamedQuery(name = Group_.QUERY_FIND_BY_NAME, query = "SELECT g FROM Group_ g WHERE g.name = :"
                + Group_.PARAM_NAME + " ORDER BY g.name"),
})
public class Group_ extends BaseModel{

    public static final String QUERY_FETCH_ALL = "Group_.fetchAll";
    public static final String QUERY_FIND_BY_NAME = "Group_.findByName";
    public static final String PARAM_NAME = "param_name";


    @Column(name = "name")
    public String name;

    public static Group_ findById(long id) {
        return JPA.em().find(Group_.class, id);
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

    public static Group_ findByTitle(String argName) {
        @SuppressWarnings("unchecked")
        List<Group_> groups = (List<Group_>) JPA.em()
                .createQuery("FROM Group_ g WHERE g.name = ?1")
                .setParameter(1, argName).getResultList();

        if (groups.isEmpty()) {
            return null;
        } else {
            return groups.get(0);
        }
    }

}
