package com.sangupta.neo;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.sangupta.jerry.util.AssertUtils;

/**
 * Main entry point for the Neo project creation tool.
 * 
 * @author sangupta
 *
 */
public class NeoMain {
    
    public static void main(String[] args) {
//        String projectTemplate = ConsoleUtils.readLine("Project template: ", true);
        
        String projectTemplate = "c:/git/lifeline/neo-aem62";
        if(AssertUtils.isEmpty(projectTemplate)) {
            System.out.println("Project template is required.");
            return;
        }
        
//        String projectDir = ConsoleUtils.readLine("Project folder: ", true);
        String projectDir = "c:/aem-work/neo-test";
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
            if(start > end) {
                System.out.println("\n\nGeneration complete in " + (end - start) + " milliseconds.");
            }
        }
        
    }

}
