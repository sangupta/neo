package com.sangupta.neo;

import java.io.File;

import org.zeroturnaround.zip.ZipUtil;

import com.sangupta.jerry.constants.SystemPropertyNames;

public class NeoUtils {

    public static File extractToFolderIfNeeded(File zip, String repositoryName) {
        if(zip.isDirectory()) {
            return zip;
        }
        
        if(!zip.getName().endsWith(".zip")) {
            // not a zip file
            return null;
        }
        
        File folder = NeoUtils.createTempFolder();
        
        // extract
        ZipUtil.unpack(zip, folder);
        
        // check for sub-folder
        File sub = new File(folder, repositoryName + "-master");
        if(sub.exists() && sub.isDirectory()) {
            return sub;
        }
        
        // return the main folder
        return folder;
    }

    public static File createTempFolder() {
        // create a temporary directory
        File tempFolder = new File(System.getProperty(SystemPropertyNames.JAVA_TMPDIR));
        File folder = new File(tempFolder, "neo-" + System.currentTimeMillis());
        folder.mkdirs();
        
        return folder;
    }
}