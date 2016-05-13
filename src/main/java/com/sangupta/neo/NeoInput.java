package com.sangupta.neo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        
        if(!param.required && AssertUtils.isNotEmpty(param.value)) {
            prompt = prompt + " [" + param.value + "]: ";
        } else {
            prompt = prompt + ": ";
        }
        
        String input = ConsoleUtils.readLine(prompt, !param.required);
        if(!param.required) {
            if(AssertUtils.isBlank(input)) {
                input = param.value;
            }
        }
        
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

}
