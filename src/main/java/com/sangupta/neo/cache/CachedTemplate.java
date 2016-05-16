package com.sangupta.neo.cache;

public class CachedTemplate {
    
    public String name;
    
    public String version = "";
    
    public String user = "";
    
    public TemplateProvider provider;
    
    public String url;
    
    public long installed;
    
    public CachedTemplate(String name) {
        this.name = name;
    }

}
