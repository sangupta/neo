/**
 *
 * neo - project scaffolding tool
 * Copyright (c) 2016, Sandeep Gupta
 * 
 * http://sangupta.com/projects/neo
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.sangupta.neo.commands;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.zeroturnaround.zip.ZipUtil;

import com.sangupta.jerry.constants.SystemPropertyNames;
import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.neo.cache.CacheManager;
import com.sangupta.neo.cache.CacheUtils;
import com.sangupta.neo.cache.ProjectTemplate;
import com.sangupta.neo.cache.TemplateProvider;
import com.sangupta.neo.download.DownloadManager;

import io.airlift.airline.Arguments;
import io.airlift.airline.Command;

@Command(name = "download", description = "Download a template and install it in cache")
public class DownloadTemplate implements Runnable {

    @Arguments(title = "urls", required = true, description = "The URLs from where to download and install the template")
    private List<String> urls;
    
    @Override
    public void run() {
        for(String url : urls) {
            ProjectTemplate path = CacheUtils.resovleTemplatePath(url);
            if(path.provider == TemplateProvider.UNKNOWN) {
                System.out.println("Template installation not supported from the path: " + url);
                continue;
            }
            
            if(path.provider == TemplateProvider.LOCAL) {
                System.out.println("Use 'install' command to install local template: " + url);
                continue;
            }
            
            if(path.provider == TemplateProvider.CACHE) {
                System.out.println("Template is already installed in cache: " + url);
                continue;
            }
            
            try {
                downloadTemplate(path);
            } catch (IOException e) {
                System.out.println("Unable to install template from url: " + url);
                e.printStackTrace();
                continue;
            }
        }
    }

    private void downloadTemplate(ProjectTemplate path) throws IOException {
        System.out.println("Installing template from URL: " + path);
        
        File downloaded = null;
        switch(path.provider) {
            case GITHUB:
                downloaded = downloadGithubTemplate(path);
                break;
                
            default:
                System.out.println("Still to work this provider up");
        }
        
        if(downloaded == null) {
            System.out.println("Unable to download the template.");
            return;
        }
        
        // extract the ZIP to a temporary path
        System.out.println("Template downloaded to: " + downloaded.getAbsolutePath());
        
        File folder = extractToFolderIfNeeded(downloaded, path.repository);
        if(folder == null) {
            System.out.println("Don't know how to process downloaded file at: " + downloaded.getAbsolutePath());
            return;
        }
        
        // now install the folder into cache
        boolean installed = CacheManager.installTemplate(folder);
        if(installed) {
            System.out.println("Template successfully installed from url: " + downloaded.getAbsolutePath());
        }
    }

    private File extractToFolderIfNeeded(File zip, String repositoryName) {
        if(zip.isDirectory()) {
            return zip;
        }
        
        if(!zip.getName().endsWith(".zip")) {
            // not a zip file
            return null;
        }
        
        // create a temporary directory
        File tempFolder = new File(System.getProperty(SystemPropertyNames.JAVA_TMPDIR));
        File folder = new File(tempFolder, "neo-" + System.currentTimeMillis());
        folder.mkdirs();
        
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

    private File downloadGithubTemplate(ProjectTemplate path) {
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
        
        // we need to crawl the site and download the folder - this happens
        // using the API
        return null;
    }
    
}