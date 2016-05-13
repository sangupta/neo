package com.sangupta.neo;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

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
        // TODO: for now let's display the collected properties
        for(Entry<String, Object> entry : this.properties.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    /**
     * Read a resource from the template dir/url for the given relative
     * path.
     * 
     * @param relativePath
     */
    protected File getResource(String relativePath) {
        File file = new File(this.templateDir, relativePath);
        if(file.exists() && file.isFile() && file.canRead()) {
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
