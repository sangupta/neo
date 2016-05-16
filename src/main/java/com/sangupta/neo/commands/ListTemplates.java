package com.sangupta.neo.commands;

import java.util.Collection;

import com.sangupta.jerry.print.ConsoleTable;
import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.neo.cache.CacheManager;
import com.sangupta.neo.cache.CachedTemplate;

import io.airlift.airline.Command;

@Command(name = "list", description = "List all the templates that exist within the cache")
public class ListTemplates implements Runnable {

    @Override
    public void run() {
        Collection<CachedTemplate> templates = CacheManager.getTemplatesInCache();
        if(AssertUtils.isEmpty(templates)) {
            System.out.println("No templates are currently installed in the cache.");
            return;
        }
        
        ConsoleTable table = new ConsoleTable();
        table.addHeaderRow("Name", "User", "Version");
        for(CachedTemplate template : templates) {
            table.addRow(template.name, template.user, template.version);
        }
        
        table.write(System.out);
    }

}
