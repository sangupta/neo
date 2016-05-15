package com.sangupta.neo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.jerry.util.ConsoleUtils;
import com.sangupta.jerry.util.StringUtils;

public class NeoInput {
    
    private Map<String, Object> properties = new HashMap<>();
    
    public NeoInput(Map<String, Object> properties) {
        this.properties = properties;
    }
    
    /**
     * Read user input for all parameters that are defined.
     * 
     * @param params
     * @return
     */
    public void getUserInput(UserInputParam[] params) {
        if(AssertUtils.isEmpty(params)) {
            // nothing to do
            return;
        }
        
        for(UserInputParam param : params) {
            Object paramValue = readParam(param);
            if(paramValue != null) {
                this.properties.put(param.name, paramValue);
            }
        }
        
        return;
    }

    /**
     * Return the value of the user-input converting it to proper type into an {@link Object}.
     * 
     * @param param
     * @return
     */
    private Object readParam(UserInputParam param) {
        if(!paramShouldBeRead(param)) {
            return null;
        }
        
        String prompt = param.prompt;
        if(AssertUtils.isEmpty(prompt)) {
            prompt = param.name;
        }
        
        if(!param.required && AssertUtils.isNotEmpty(param.value)) {
            prompt = prompt + " [" + param.value + "]: ";
        } else {
            prompt = prompt + ": ";
        }
        
        // process with velocity only if it has a velocity variable in it
        if(prompt.contains("$")) {
            prompt = VelocityUtils.processWithVelocity(prompt);
        }
        
        String input = ConsoleUtils.readLine(prompt, !param.required);
        if(!param.required) {
            if(AssertUtils.isBlank(input)) {
                input = VelocityUtils.processWithVelocity(param.value);
            }
        }
        
        return convertInputToType(param, input);
    }

    /**
     * Convert the given user-input to the type defined in template.
     * 
     * @param param
     * @param input
     * @return
     */
    private Object convertInputToType(UserInputParam param, String input) {
        // convert the value to proper type
        switch(param.type) {
            case "string":
                return input;
        
            case "int":
            case "integer":
                return StringUtils.getIntValue(input, 0);
                
            case "bool":
            case "boolean":
                return StringUtils.getBoolean(input, false);
                
            case "array":
                return splitToArray(input);
                
            case "list":
                return splitToList(input);
                
            default:
                throw new NeoRuntimeException("Unknown param type detected as: " + param.type);
        }
    }
    
    /**
     * Convert user input into a {@link List} of {@link String} considering values
     * are comma-separated.
     *  
     * @param input
     * @return
     */
    private static List<String> splitToList(String input) {
        List<String> list = new ArrayList<>();
        
        if(AssertUtils.isEmpty(input)) {
            return list;
        }
        
        String[] tokens = input.split(",");
        for(int index = 0; index < tokens.length; index++) {
            list.add(tokens[index].trim());
        }
        
        return list;
    }

    /**
     * Convert user input into a {@link String}-array considering values
     * are comma-separated.
     * 
     * @param input
     * @return
     */
    private static String[] splitToArray(String input) {
        if(AssertUtils.isEmpty(input)) {
            return StringUtils.EMPTY_STRING_LIST;
        }
        
        String[] tokens = input.split(",");
        for(int index = 0; index < tokens.length; index++) {
            tokens[index] = tokens[index].trim();
        }
        
        return tokens;
    }

    /**
     * Decide if the parameter should be read from user or not, checking if the
     * conditions specified in the param are met.
     *  
     * @param param
     * @return
     */
    private boolean paramShouldBeRead(UserInputParam param) {
        if(AssertUtils.areEmpty(param.ifTrue, param.ifFalse)) {
            return true;
        }
        
        // by default all variables should be read
        boolean result = true;
        
        // if true
        if(AssertUtils.isNotEmpty(param.ifTrue)) {
            result = getBoolean(param.ifTrue, false);
            if(!result) {
                // variable should not be read
                return false;
            }
        }
        
        // if false
        if(AssertUtils.isNotEmpty(param.ifFalse)) {
            result = result & !getBoolean(param.ifFalse, false);
            if(!result) {
                return false;
            }
        }
        
        return result;
    }
    
    /**
     * Convert the value to a {@link Boolean} value.
     * 
     * @param property
     * @param defaultValue
     * @return
     */
    private boolean getBoolean(String property, boolean defaultValue) {
        Object bool = this.properties.get(property);
        if(bool == null) {
            return defaultValue;
        }
        
        if(bool instanceof Boolean) {
            return (Boolean) bool;
        }
        
        return StringUtils.getBoolean(bool.toString(), defaultValue);
    }

}
