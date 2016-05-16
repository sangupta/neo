package com.sangupta.neo.domain;

/**
 * An input parameter that will be requested from the user after
 * the template is read, and generation process is initiated. User
 * parameters define how the project will be setup.
 * 
 * @author sangupta
 *
 */
public class NeoInputParam {
    
    public String name;
    
    public String type = "string";
    
    public String prompt;
    
    public String value = "";
    
    public boolean required = false;
    
    public String ifTrue;
    
    public String ifFalse;

    @Override
    public String toString() {
        return this.name + " (" + this.type + ")";
    }
}
