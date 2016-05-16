package com.sangupta.neo.download;

import java.io.File;
import java.io.IOException;

import org.apache.http.client.HttpResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sangupta.jerry.http.WebRequest;
import com.sangupta.jerry.util.UriUtils;

public class DownloadManager {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadManager.class);

    public static File downloadToTempFile(String url) throws IOException {
        String name = UriUtils.extractFileName(url);
        String extension = UriUtils.extractExtension(url);

        File tempFile = File.createTempFile(name + "-", "." + extension);
        
        LOGGER.debug("Downloading {} to {}", url, tempFile.getAbsolutePath());
        
        try {
            WebRequest.get(url).execute().writeToFile(tempFile);
            return tempFile;
        } catch(HttpResponseException e) {
            LOGGER.error("HTTP response did not yield an OK status", e);
        } catch(IOException e) {
            LOGGER.error("Unable to download url to temp file", e);
        }
        
        return null;
    }
}
