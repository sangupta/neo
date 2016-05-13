package com.sangupta.neo;

public class NeoTemplate {
    
    public String name;
    
    public String version;

    public String description;
    
    public UserInputParam[] params;

    /**
     * Run integrity check for this template.
     * 
     */
    public void checkIntegrity() {
        System.out.println("Checks passed");
    }
    
}
