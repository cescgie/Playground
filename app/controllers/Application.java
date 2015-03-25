package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import models.*;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.main;


import javax.persistence.PersistenceException;


/**
 * Manage a database of computers
 */
public class Application extends Controller {



	/**
	 * Handle default path requests, redirect to computers list
	 */
	@Transactional
	public static Result index() {

		if (JPA.em().createNamedQuery(Folder.QUERY_FETCH_ALL).getResultList().isEmpty()) {
			Folder f0 = new Folder(); //FolderController.createFolder("root", null);
			f0.name = "root";
			f0.depth = 0;
			f0.parent = null;
			f0.group = null;
			f0.create();

			Group_Controller.createGroup("Gruppe1");
			Group_Controller.createGroup("Gruppe2");
		}
		return printAll();
	}


    @Transactional
    public static Result createMedia(String name) {
        Media m = new Media();
        m.name = name;
        try {

            JPA.em().persist(m);

        } catch (Exception ex) {
            throw ex;
        }

        return printAll();
    }

	@Transactional
	public static Result createMedia(Long folderID) {
		MediaController.createMedia(UUID.randomUUID().toString(),folderID);
		return redirectFolder(folderID);
	}

	@Transactional
	public static Result createFolder(Long folderID) {

//		String uri = request().uri();
//		int index = uri.lastIndexOf("/");
//		Long folder = Long.valueOf(uri.substring(index));

        FolderController.createFolder("Ordner xyz", folderID);
        return redirectFolder(folderID);
    }
    /*
    @Transactional
	public static Result createSpiel(Long folderID) {
		//SpielController.createSpiel("Spiel1",folderID);
		return redirectFolder(folderID);
	}*/

    @Transactional
	public static Result printAll() {
		List<Media> media = JPA.em().createNamedQuery(Media.QUERY_FETCH_ALL).getResultList();
		List<Folder> folder = JPA.em().createNamedQuery(Folder.QUERY_FETCH_ALL).getResultList();
		List<Spiel> spiel = JPA.em().createNamedQuery(Spiel.QUERY_FETCH_ALL).getResultList();
		return ok(views.html.list.render(folder,media,spiel));
	}


	@Transactional
	public static Result listFolder(Long folderID) {
		Folder f = Folder.findById(folderID);
		Folder folder = f;
		List<Folder> path = new ArrayList<Folder>();
		List<Folder> pathTemp = new ArrayList<Folder>();

		if (f == null)
			return redirectFolderError("Ordner nicht vorhanden, wählen Sie einen anderen!");
		while (f.depth > 1) {
			pathTemp.add(f);
			f = f.parent;
		}
		for (int i = pathTemp.size()-1; i >= 0; i--)
			path.add(pathTemp.get(i));
		return ok(views.html.folder.render(path,folder));
	}
    

    public static Result redirectFolder(Long folderID) {
		return redirect("/folder/" + folderID);
	}

	public static Result redirectFolderError(String error) {
		return ok(views.html.test.render(error));
	}
}
