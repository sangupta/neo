package com.sangupta.neo.cache;

import com.sangupta.jerry.util.AssertUtils;

public enum TemplateProvider {
    
    LOCAL,
    
    CACHE,
    
    GITHUB,
    
    BITBUCKET,
    
    GITLAB,
    
    UNKNOWN;

    public static TemplateProvider from(String provider) {
        if(AssertUtils.isEmpty(provider)) {
            return null;
        }
        
        provider = provider.toLowerCase();
        
        switch(provider) {
            case "github":
            case "github.com":
                return GITHUB;
                
            case "bitbucket":
            case "bitbucket.org":
                return BITBUCKET;
                
            case "gitlab":
            case "gitlab.com":
                return GITLAB;
            
            default:
                return UNKNOWN;
        }
    }

}
