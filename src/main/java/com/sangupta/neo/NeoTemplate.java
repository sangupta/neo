package com.sangupta.neo;

import com.sangupta.jerry.util.AssertUtils;

public class NeoTemplate {
    
    public String name;
    
    public String version;

    public String description;
    
    public UserInputParam[] params;
    
    public NeoProcess[] process;

    /**
     * Run integrity check for this template.
     * 
     */
    public void checkIntegrity() {
        if(AssertUtils.isEmpty(this.params)) {
            return;
        }
        
        for(UserInputParam param : this.params) {
            if(AssertUtils.isEmpty(param.name)) {
                throw new NeoRuntimeException("Param name is empty in template");
            }
            
            param.name = param.name.trim();
            
            if(AssertUtils.isEmpty(param.type)) {
                param.type = "string";
            } else {
                param.type = param.type.toLowerCase();
            }
            
            // TODO: convert first letter to uppercase of param.description
        }
    }
    
}
