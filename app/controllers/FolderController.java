package controllers;

import com.fasterxml.jackson.databind.deser.Deserializers;
import models.Folder;
import models.Group_;
import models.Media;
import play.db.jpa.JPA;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meichris on 06.12.14.
 */
public class FolderController {


    public static Long createFolder(String argName, Long argParentId) {
        Folder f = new Folder();
        if (allowToCreate(argName,argParentId)) {
            f.name = argName;
            f.group = Folder.findById(argParentId).group;
            if (argParentId != null) {
                f.parent = Folder.findById(argParentId);
                f.depth = f.parent.depth + 1;
            }
            f.create();
        }
        return f.id;
    }

    public static List<Media> getAllMediaInFolder(Long argId){
        List<Media> medias = new ArrayList<Media>();
        try {
            Folder f = Folder.findById(argId);
            medias = f.files;
        } catch (NullPointerException e) {
            //Blabla
        }
        return medias;
    }

    private static boolean allowToCreate(String argName, Long argParentId) {
        boolean allow = true;
        List<Folder> folders = Folder.findById(argParentId).childs;
        if (folders != null)
            for (Folder f: folders)
                if (f.name.equals(argName))
                    allow =false;
        return allow;
    }

    public static Long getGroupFolder(String argGroupName) {
        List<Folder> f = JPA.em().createNamedQuery(Folder.QUERY_FIND_ALL_GROUPFOLDER).getResultList();
        return f.get(0).id;
    }


}
