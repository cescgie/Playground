package controllers;

import models.Folder;
import models.Spiel;
import org.omg.CORBA.Current;
import play.Logger;
import play.Play;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Call;
import play.mvc.Result;

import javax.persistence.NoResultException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static play.data.Form.form;


public class SpielController extends BaseController {
    final static Form<Spiel> spielForm = form(Spiel.class);

    public static Result createSpiel(Long argFolderID) {
        //Spiel parent = Spiel.findById(argFolderID);
        Form<Spiel> filledForm = spielForm.bindFromRequest();
        String name = filledForm.data().get("name");
        String player1 = filledForm.data().get("player1");
        String player2 = filledForm.data().get("player2");
        if (!name.isEmpty()) {
            if (allowToCreate(name)) {
                Spiel newSpiel = new Spiel();
                newSpiel.name = name;
                newSpiel.player1 = player1;
                newSpiel.player2 = player2;
                newSpiel.create();
            }
        }
        return redirectFolder(argFolderID);
    }

    private static boolean allowToCreate(String argName) {
        boolean allow = true;
        return allow;
    }

    public static Result redirectFolder(Long folderID) {
        return redirect("/folder/" + folderID);
    }

    @Transactional
    public static Result deleteSpiel(Long spielID) {
        Logger.debug("use deleteSpiel");
        Spiel spiel = Spiel.findById(spielID);
        if(spiel != null) {
            spiel.delete();
            flash("success", "Datei \"" + spiel.name + "\" wurde erfolgreich gelöscht!");
        }
        return redirect("/");
    }
}
