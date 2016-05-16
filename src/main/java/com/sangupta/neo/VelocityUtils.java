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

package com.sangupta.neo;

import java.io.StringWriter;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.directive.Directive;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sangupta.jerry.util.AssertUtils;

/**
 * Utility class that helps us run content through Apache {@link Velocity}
 * and get the results.
 * 
 * @author sangupta
 *
 */
public class VelocityUtils {
    
    /**
     * My private logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(VelocityUtils.class);
    
    /**
     * Load all custom Velocity directives
     */
    static {
        // load all velocity tags
        Reflections reflections = new Reflections("com.sangupta.neo.velocity");
        Set<Class<? extends Directive>> directives = reflections.getSubTypesOf(Directive.class);
        if(AssertUtils.isNotEmpty(directives)) {
            for(Class<? extends Directive> directive : directives) {
                if(Modifier.isAbstract(directive.getModifiers())) {
                    continue;
                }

                RuntimeSingleton.loadDirective(directive.getName());
            }
        }
    }
    
    /**
     * Run the contents through {@link Velocity}.
     * 
     * @param contents
     * @return
     */
    public static String processWithVelocity(String contents) {
        if(AssertUtils.isEmpty(contents)) {
            return contents;
        }
        
        try {
            StringWriter writer = new StringWriter();
            Map<String, Object> properties = NeoGenerator.getInstance().getProperties();
            VelocityContext context = new VelocityContext(properties);
            Velocity.evaluate(context, writer, "test", contents);
            
            return writer.toString();
        } catch(Exception e) {
            LOGGER.error("Error evaluating content: {}", contents);
            throw e;
        }
    }

}