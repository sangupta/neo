package com.sangupta.neo;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.jerry.util.GsonUtils;

/**
 * {@link NeoGenerator} takes care of reading the project template and
 * creation of project artifacts as per the user's choices.
 * 
 * @author sangupta
 *
 */
public class NeoGenerator {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(NeoGenerator.class);
    
    private final File templateDir;
    
    private final NeoTemplate template;

    private Map<String, Object> properties;
    
    /**
     * Constructor
     * 
     * @param templateDir
     *            the folder from where the project template has to be read.
     *            Eventually we will be able to pass the url to a repository
     *            like github or someplace so that we can read templates
     *            directly from there.
     */
    public NeoGenerator(File templateDir) {
        this.templateDir = templateDir;
        this.template = readNeoProjectTemplate();
    }

    /**
     * Read the project template from the template folder. Templates
     * are usually named <code>neo.json</code>
     * 
     */
    private NeoTemplate readNeoProjectTemplate() {
        String json = this.getResourceContents("neo.json");
        if(AssertUtils.isEmpty(json)) {
            throw new NeoRuntimeException("Cannot read template contents from neo.json");
        }
        
        NeoTemplate template = GsonUtils.getGson().fromJson(json, NeoTemplate.class);
        if(template == null) {
            throw new NeoRuntimeException("Unable to parse template contents from neo.json");
        }
        
        // run integrity check on template
        template.checkIntegrity();
        
        // return the template
        return template;
    }

    /**
     * Generate the project in the specified folder.
     * 
     * @param projectFolder
     */
    public void generateIn(File projectFolder) {
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Properties from user input are as under:");
            for(Entry<String, Object> entry : this.properties.entrySet()) {
                LOGGER.debug(entry.getKey() + ": " + entry.getValue());
            }
        }
        
        projectFolder.mkdirs();
        
        // start copying each file from data directory to the project folder
        File dataFolder = this.getResource("data");
        if(dataFolder != null && dataFolder.exists() && dataFolder.isDirectory()) {
            try {
                LOGGER.info("Processing data folder");
                processDataFolder(dataFolder, projectFolder);
            } catch (IOException e) {
                throw new NeoRuntimeException("Unable to copy file from data folder to project folder", e);
            }
        } else {
            LOGGER.info("Skipping data folder as it is non-existent");
        }
    }
    
    /**
     * Copy all files from data folder to the project folder
     * @param dataFolder
     * @throws IOException 
     */
    private void processDataFolder(File dataFolder, File projectFolder) throws IOException {
        Collection<File> files = FileUtils.listFiles(dataFolder, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        
        if(AssertUtils.isEmpty(files)) {
            // nothing to do in data folder
            return;
        }
        
        // let's process each file
        for(File file : files) {
            LOGGER.info("Processing file: {}", file.getAbsolutePath());
            String filePath = getProjectFolderPath(dataFolder, file);
            File projectFile = new File(projectFolder, filePath);
            
            if(file.length() == 0) {
                // create an empty 
                FileUtils.writeStringToFile(projectFile, "");
                continue;
            }
            
            // the file has contents
            String contents = FileUtils.readFileToString(file);
            
            // update contents via velocity
            contents = VelocityUtils.processWithVelocity(contents, this.properties);
            
            // write back to project folder
            FileUtils.writeStringToFile(projectFile, contents);
        }
    }

    private String getProjectFolderPath(File dataFolder, File file) {
        String basePath = dataFolder.getAbsolutePath();
        String path = file.getAbsolutePath();
        
        if(!path.startsWith(basePath)) {
            return file.getName(); 
        }
        
        path = path.substring(basePath.length());
        if(path.startsWith("/") || path.startsWith("\\")) {
            return path.substring(1);
        }
        
        return path;
    }

    /**
     * Read a resource from the template dir/url for the given relative
     * path.
     * 
     * @param relativePath
     */
    protected File getResource(String relativePath) {
        File file = new File(this.templateDir, relativePath);
        if(file.exists() && file.canRead()) {
            return file;
        }
        
        return null;
    }
    
    protected String getResourceContents(String relativePath) {
        File file = this.getResource(relativePath);
        if(file == null) {
            return null;
        }
        
        try {
            return FileUtils.readFileToString(file);
        } catch (IOException e) {
            throw new NeoRuntimeException("Unable to read resource " + relativePath + " from repository", e);
        }
    }

    /**
     * Initialize this generator by reading and processing all contents
     * from the {@link NeoTemplate} that has been read as part of the
     * construction of this object.
     * 
     */
    public void initialize() {
        this.properties = new NeoInput().getUserInput(this.template.params);
    }
    
}
