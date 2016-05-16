package com.sangupta.neo.commands;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.sangupta.jerry.util.FileUtils;
import com.sangupta.neo.cache.CacheManager;

import io.airlift.airline.Arguments;
import io.airlift.airline.Command;

@Command(name = "install", description = "Install a new template from the given directory")
public class InstallTemplate implements Runnable {
    
    @Arguments(description = "the folder from where the template is installed", required = true, title = "folder")
    private List<String> folder;

    @Override
    public void run() {
        try {
            execute();
        } catch (IOException e) {
            System.out.println("Unable to install template: " + folder.get(0));
            e.printStackTrace();
        }
    }
    
    private void execute() throws IOException {
        if(folder.size() > 1) {
            System.out.println("A template can only installed from one folder at a time.");
            return;
        }
        
        // check if the folder exists or not
        File templateDir = FileUtils.resolveToFile(folder.get(0));
        if(!templateDir.exists()) {
            System.out.println("Template directory does not exists.");
            return;
        }
        
        if(!templateDir.isDirectory()) {
            System.out.println("Template folder is not a valid directory.");
            return;
        }
        
        boolean installed = CacheManager.installTemplate(templateDir);
        if(!installed) {
            System.out.println("Unable to install the template in cache.");
            return;
        }
        
        System.out.println("Template successfully installed in cache.");
    }

}
