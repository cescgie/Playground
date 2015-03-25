package controllers;

import models.Folder;
import models.Media;

import java.util.List;

public class MediaController {

    public static Long createMedia(String argName, Long argFolderID) {
        Media newMedia = new Media();
        if (allowToCreate(argName,argFolderID)) {
            newMedia.name = argName;
            newMedia.inFolder = Folder.findById(argFolderID);
            newMedia.create();
        }
        return newMedia.id;
    }

    private static boolean allowToCreate(String argName, Long argfolderId) {
        boolean allow = true;
        Folder f = Folder.findById(argfolderId);
        List<Media> files = f.files;
        if (!files.isEmpty()) {
            for ( Media m: files)
                if (m.name.equals(argName))
                    allow = false;
        }
        return allow;
    }
}
