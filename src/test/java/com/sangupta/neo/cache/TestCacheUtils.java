package com.sangupta.neo.cache;

import org.junit.Assert;
import org.junit.Test;

public class TestCacheUtils {

    @Test
    public void testResovleTemplatePath() {
        Assert.assertNull(CacheUtils.resovleTemplatePath(null));
        Assert.assertNull(CacheUtils.resovleTemplatePath(""));
        
        TemplatePath path = CacheUtils.resovleTemplatePath("aem");
        Assert.assertNotNull(path);
        Assert.assertEquals(TemplateProvider.CACHE, path.provider);
        Assert.assertNull(path.user);
        Assert.assertNotNull(path.repository);
        Assert.assertNull(path.path);
        
        path = CacheUtils.resovleTemplatePath("c:/aem");
        Assert.assertNotNull(path);
        Assert.assertEquals(TemplateProvider.LOCAL, path.provider);
        Assert.assertNull(path.user);
        Assert.assertNotNull(path.repository);
        Assert.assertNull(path.path);
        
        path = CacheUtils.resovleTemplatePath("/aem");
        Assert.assertNotNull(path);
        Assert.assertEquals(TemplateProvider.LOCAL, path.provider);
        Assert.assertNull(path.user);
        Assert.assertNotNull(path.repository);
        Assert.assertNull(path.path);
        
        path = CacheUtils.resovleTemplatePath("~/.m2");
        Assert.assertNotNull(path);
        Assert.assertEquals(TemplateProvider.LOCAL, path.provider);
        Assert.assertNull(path.user);
        Assert.assertNotNull(path.repository);
        Assert.assertNull(path.path);
        
        path = CacheUtils.resovleTemplatePath("./src");
        Assert.assertNotNull(path);
        Assert.assertEquals(TemplateProvider.LOCAL, path.provider);
        Assert.assertNull(path.user);
        Assert.assertNotNull(path.repository);
        Assert.assertNull(path.path);
        
        path = CacheUtils.resovleTemplatePath("github:sangupta/aem");
        Assert.assertNotNull(path);
        Assert.assertEquals(TemplateProvider.GITHUB, path.provider);
        Assert.assertEquals("sangupta", path.user);
        Assert.assertNotNull("aem", path.repository);
        Assert.assertNull(path.path);
        
        path = CacheUtils.resovleTemplatePath("github.com:sangupta/aem");
        Assert.assertNotNull(path);
        Assert.assertEquals(TemplateProvider.GITHUB, path.provider);
        Assert.assertEquals("sangupta", path.user);
        Assert.assertNotNull("aem", path.repository);
        Assert.assertNull(path.path);
        
        path = CacheUtils.resovleTemplatePath("github.com/sangupta/aem");
        Assert.assertNotNull(path);
        Assert.assertEquals(TemplateProvider.GITHUB, path.provider);
        Assert.assertEquals("sangupta", path.user);
        Assert.assertNotNull("aem", path.repository);
        Assert.assertNull(path.path);
        
        path = CacheUtils.resovleTemplatePath("http://github.com/sangupta/aem");
        Assert.assertNotNull(path);
        Assert.assertEquals(TemplateProvider.GITHUB, path.provider);
        Assert.assertEquals("sangupta", path.user);
        Assert.assertNotNull("aem", path.repository);
        Assert.assertNull(path.path);

        path = CacheUtils.resovleTemplatePath("https://github.com/sangupta/aem");
        Assert.assertNotNull(path);
        Assert.assertEquals(TemplateProvider.GITHUB, path.provider);
        Assert.assertEquals("sangupta", path.user);
        Assert.assertNotNull("aem", path.repository);
        Assert.assertNull(path.path);

        path = CacheUtils.resovleTemplatePath("git@github.com/sangupta/aem");
        Assert.assertNotNull(path);
        Assert.assertEquals(TemplateProvider.GITHUB, path.provider);
        Assert.assertEquals("sangupta", path.user);
        Assert.assertNotNull("aem", path.repository);
        Assert.assertNull(path.path);
    }
}
