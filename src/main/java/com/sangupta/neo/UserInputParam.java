package com.sangupta.neo;

public class UserInputParam {
    
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
