package controllers;

import models.Folder;
import models.Group_;
import play.db.jpa.JPA;

import java.util.List;

public class Group_Controller {

    public static void createGroup(String argGroupName) {
        Group_ g = new Group_();
        Folder f = new Folder();
        if (allowToCreate(argGroupName)) {
            g.name = argGroupName;
            g.create();
            f.name = argGroupName;
            f.parent = Folder.findById(1L);
            f.group = g;
            f.depth = 1;
            f.create();
        }
    }

    private static boolean allowToCreate(String argName) {
        boolean allow = true;
        List<Group_> groups = JPA.em().createNamedQuery(Group_.QUERY_FETCH_ALL).getResultList();
        if (!groups.isEmpty())
            for (Group_ g: groups)
                if (g.name.equals(argName))
                    allow =false;
        return allow;
    }
}
