package com.sangupta.neo;

import java.io.File;

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
        
        String projectTemplate = "c:/aem-work/aem-neo";
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
        
        NeoGenerator generator = new NeoGenerator(new File(projectTemplate));
        generator.initialize();
        generator.generateIn(new File(projectDir));
    }

}
