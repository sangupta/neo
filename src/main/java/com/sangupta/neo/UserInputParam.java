package com.sangupta.neo;

public class UserInputParam {
    
    public String name;
    
    public String type;
    
    public String prompt;
    
    public String defaultValue;
    
    public boolean required;

    @Override
    public String toString() {
        return this.name + " (" + this.type + ")";
    }
}
