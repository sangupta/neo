package com.sangupta.neo.commands;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.neo.NeoGenerator;

import io.airlift.airline.Arguments;
import io.airlift.airline.Command;
import io.airlift.airline.Option;

/**
 * Allows creation of a new project using the given template name.
 * 
 * @author sangupta
 *
 */
@Command(name = "create", description = "Create a new project")
public class CreateProject implements Runnable {
    
    @Option(name = { "-t", "--template"}, description = "Template to be used for project creation", required = true)
    private String templateName;
    
    @Arguments(description = "the folder where new project will be created", required = true, title = "folder")
    private List<String> folder;

    @Override
    public void run() {
        if(folder.size() > 1) {
            System.out.println("A project can be created only in one folder at a time.");
            return;
        }
        
        String projectTemplate = "c:/git/lifeline/neo-aem62";
        
        execute(projectTemplate, folder.get(0));
    }

    private static void execute(String projectTemplate, String projectDir) {
      if(AssertUtils.isEmpty(projectTemplate)) {
          System.out.println("Project template is required.");
          return;
      }
      
      if(AssertUtils.isEmpty(projectDir)) {
          System.out.println("Project folder is required.");
          return;
      }
      
      // TODO: remove this - instead throw an error
      FileUtils.deleteQuietly(new File(projectDir));
      
      // start generation
      long start = 0;
      try {
          NeoGenerator generator = NeoGenerator.createInstance(new File(projectTemplate), new File(projectDir));
          generator.initialize();
          
          start = System.currentTimeMillis();
          generator.generate();
      } catch(Exception e) {
          e.printStackTrace();
      } finally {
          long end = System.currentTimeMillis();
          if(start > 0) {
              System.out.println("\n\nGeneration complete in " + (end - start) + " milliseconds.");
          }
      }
      
  }
}
