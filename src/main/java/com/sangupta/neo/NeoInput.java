package com.sangupta.neo;

import java.util.HashMap;
import java.util.Map;

import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.jerry.util.ConsoleUtils;
import com.sangupta.jerry.util.StringUtils;

public class NeoInput {
    
    public static Map<String, Object> getUserInput(UserInputParam[] params) {
        Map<String, Object> result = new HashMap<>();
        
        if(AssertUtils.isEmpty(params)) {
            // nothing to do
            return result;
        }
        
        for(UserInputParam param : params) {
            Object paramValue = readParam(param);
            result.put(param.name, paramValue);
        }
        
        return result;
    }

    private static Object readParam(UserInputParam param) {
        String prompt = param.prompt;
        if(AssertUtils.isEmpty(prompt)) {
            prompt = param.name;
        }
        
        if(!param.required && AssertUtils.isNotEmpty(param.defaultValue)) {
            prompt = prompt + " [" + param.defaultValue + "]: ";
        } else {
            prompt = prompt + ": ";
        }
        
        String input = ConsoleUtils.readLine(prompt, !param.required);
        if(!param.required) {
            if(AssertUtils.isBlank(input)) {
                input = param.defaultValue;
            }
        }
        
        // convert the value to proper type
        switch(param.type) {
            case "string":
                return input;
        
            case "int":
                return StringUtils.getIntValue(input, 0);
                
            default:
                return input;
        }
    }

}
