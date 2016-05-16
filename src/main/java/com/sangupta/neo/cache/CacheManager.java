package com.sangupta.neo.cache;

import java.io.File;
import java.io.IOException;

import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.jerry.util.FileUtils;
import com.sangupta.jerry.util.GsonUtils;
import com.sangupta.neo.domain.NeoTemplate;

/**
 * Service that manages the template cache on the user's machine. Templates are
 * usually downloaded from the internet and kept in local machine's cache so they
 * can be reused.
 * 
 * @author sangupta
 *
 */
public class CacheManager {
    
    /**
     * Our cache directory where all templates are stored
     */
    private static final File CACHE_DIR;
    
    private static final File TEMPLATE_CACHE_DIR;
    
    static {
        File file = new File(FileUtils.getUsersHomeDirectory(), ".neo");
        if(!file.exists()) {
            file.mkdirs();
        }
        
        if(!file.isDirectory()) {
            file.mkdirs();
        }
        
        CACHE_DIR = file;
        
        TEMPLATE_CACHE_DIR = new File(CACHE_DIR, "templates");
        TEMPLATE_CACHE_DIR.mkdirs();
    }
    
    /**
     * Return the size of the cache.
     * 
     * @return
     */
    public long size() {
        return 0;
    }
    
    /**
     * Check if the template exists with the given name in our local
     * cache or not.
     * 
     * @param name
     * @return
     */
    public boolean exists(String name) {
        if(AssertUtils.isEmpty(name)) {
            return false;
        }
        
        File repo = new File(TEMPLATE_CACHE_DIR, name);
        if(repo.exists() && repo.isDirectory()) {
            return true;
        }
        
        return false;
    }

    public static boolean installTemplate(File templateDir) throws IOException {
        File neo = new File(templateDir, "neo.json");
        if(!neo.exists()) {
            System.out.println("No template found in folder (no neo.json found).");
            return false;
        }
        
        if(!neo.isFile()) {
            System.out.println("No template found in folder (no neo.json found).");
            return false;
        }
        
        String contents = org.apache.commons.io.FileUtils.readFileToString(neo);
        if(AssertUtils.isEmpty(contents)) {
            System.out.println("Template configuration neo.json is empty");
            return false;
        }
        
        NeoTemplate template = GsonUtils.getGson().fromJson(contents, NeoTemplate.class);
        template.validate();
        
        // start installing the template
        File repo = new File(TEMPLATE_CACHE_DIR, template.name);
        repo.mkdirs();
        
        // copy the entire folder contents to this repo folder
        org.apache.commons.io.FileUtils.copyDirectory(templateDir, repo);
        return true;
    }
}
