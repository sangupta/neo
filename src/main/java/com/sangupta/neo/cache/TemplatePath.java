package com.sangupta.neo.cache;

public class TemplatePath {

    /**
     * The provider for this template
     */
    public final TemplateProvider provider;
    
    /**
     * The user who owns the repository
     */
    public final String user;

    /**
     * The repository where the template is stored
     */
    public final String repository;

    /**
     * The sub-path in the repository where template resides
     */
    public final String path;
    
    public TemplatePath(TemplateProvider provider, String user, String repository, String path) {
        this.provider = provider;
        this.user = user;
        this.repository = repository;
        this.path = path;
    }
    
}
