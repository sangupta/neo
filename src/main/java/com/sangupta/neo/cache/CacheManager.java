package com.sangupta.neo.cache;

/**
 * Service that manages the template cache on the user's machine. Templates are
 * usually downloaded from the internet and kept in local machine's cache so they
 * can be reused.
 * 
 * @author sangupta
 *
 */
public class CacheManager {
    
    /**
     * Return the size of the cache.
     * 
     * @return
     */
    public long size() {
        return 0;
    }
    
    /**
     * Check if the template exists with the given name in our local
     * cache or not.
     * 
     * @param name
     * @return
     */
    public boolean exists(String name) {
        return false;
    }

}
