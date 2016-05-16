package com.sangupta.neo.helper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.gson.FieldNamingPolicy;
import com.sangupta.jerry.http.WebInvoker;
import com.sangupta.jerry.http.WebResponse;
import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.jerry.util.GsonUtils;
import com.sangupta.neo.cache.ProjectTemplate;
import com.sangupta.neo.download.DownloadManager;

public class GithubRipper {
    
    
    public static File downloadGithubTemplate(ProjectTemplate path) {
        if(AssertUtils.isEmpty(path.path)) {
            // download the entire repository in one shot
            String url = "https://github.com/" + path.user + "/" + path.repository + "/archive/master.zip";
            try {
                return DownloadManager.downloadToTempFile(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            return null;
        }
        
        // we need to crawl the site and download the folder - this happens using the API
        final String base = "https://api.github.com/repos/" + path.user + "/" + path.repository + "/contents/";
        String uri = base + path.path;
        Queue<String> urls = new LinkedBlockingQueue<>();
        urls.add(uri);
        
        System.out.println("Retrieving a list of all files in the folder...");
        List<String> filesToDownload = new ArrayList<>();
        do {
            if(urls.isEmpty()) {
                break;
            }
            
            String url = urls.poll();
            WebResponse response = WebInvoker.getResponse(url);
            if(response == null || !response.isSuccess()) {
                System.out.println("Unable to fetch valid response from github: " + url);
                return null;
            }
            
            parseResponse(response.getContent(), filesToDownload, urls, base);
        } while(true);
        
        // start downloading all the files
        for(String s : filesToDownload) {
            System.out.println("Downloading file: " + s);
        }
        return null;
    }
    
    private static void parseResponse(String json, List<String> filesToDownload, Queue<String> apiUrls, String base) {
        if(AssertUtils.isEmpty(json)) {
            return;
        }
        
        GithubEntry[] entries = GsonUtils.getGson(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).fromJson(json, GithubEntry[].class);
        if(AssertUtils.isEmpty(entries)) {
            return;
        }
        
        for(GithubEntry e : entries) {
            if(AssertUtils.isEmpty(e.downloadUrl)) {
                // this is a folder
                apiUrls.add(base + e.path);
                continue;
            }
            
            filesToDownload.add(e.downloadUrl);
        }
    }

    private static class GithubEntry {
        
        public String path;
        
        public String downloadUrl;

    }

}
