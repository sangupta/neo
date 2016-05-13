package com.sangupta.neo;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.sangupta.jerry.util.AssertUtils;

public class VelocityUtils {
    
    public static VelocityContext getContext(Map<String, Object> properties) {
        // TODO: add caching
        return new VelocityContext(properties);
    }

    public static String processWithVelocity(String contents, Map<String, Object> properties) {
        if(AssertUtils.isEmpty(contents)) {
            return contents;
        }
        
        StringWriter writer = new StringWriter();
        Velocity.evaluate(getContext(properties), writer, "test", contents);
        
        return writer.toString();
    }

}
