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

public class VelocityUtils {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(VelocityUtils.class);
    
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
    
    public static VelocityContext getContext(Map<String, Object> properties) {
        // TODO: add caching
        return new VelocityContext(properties);
    }

    public static String processWithVelocity(String contents, Map<String, Object> properties) {
        try {
            if(AssertUtils.isEmpty(contents)) {
                return contents;
            }
            
            StringWriter writer = new StringWriter();
            Velocity.evaluate(getContext(properties), writer, "test", contents);
            
            return writer.toString();
        } catch(Exception e) {
            LOGGER.error("Error evaluating content: {}", contents);
            throw e;
        }
    }

}
