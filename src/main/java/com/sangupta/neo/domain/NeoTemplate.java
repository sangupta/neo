/**
 *
 * neo - project scaffolding tool
 * Copyright (c) 2016, Sandeep Gupta
 * 
 * http://sangupta.com/projects/neo
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.sangupta.neo.domain;

import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.neo.NeoRuntimeException;

public class NeoTemplate {
    
    public String name;
    
    public String version;

    public String description;
    
    public NeoInputParam[] params;
    
    public NeoProcess[] process;

    /**
     * Run integrity check for this template.
     * 
     */
    public void validate() {
        if(AssertUtils.isEmpty(name) || AssertUtils.isEmpty(version)) {
            throw new NeoRuntimeException("Template name is required");
        }
        
        for(NeoInputParam param : this.params) {
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