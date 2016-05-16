package com.sangupta.neo;

import com.sangupta.jerry.ApplicationContext;
import com.sangupta.neo.commands.CreateProject;

import io.airlift.airline.Cli;
import io.airlift.airline.Cli.CliBuilder;
import io.airlift.airline.Help;
import io.airlift.airline.ParseArgumentsUnexpectedException;

/**
 * Main entry point for the Neo project creation tool. Parses
 * the command line arguments, and executes the tool as needed.
 * 
 * Also helps display the usage information for the tool.
 * 
 * @author sangupta
 *
 */
public class NeoMain {
    
    public static void main(String[] args) {
        System.out.println("\nNeo Project Scaffolding Tool, Version " + ApplicationContext.PROJECT_VERSION);
        System.out.println("Copyright (c) 2016, Sandeep Gupta.\n");
        @SuppressWarnings("unchecked")
        CliBuilder<Runnable> builder = Cli.<Runnable>builder("neo")
                .withDescription("Project scaffolding tool")
                .withDefaultCommand(Help.class)
                .withCommands(Help.class, CreateProject.class);
        
        Cli<Runnable> cliParser = builder.build();
        
        try {
            cliParser.parse(args).run();
        } catch(ParseArgumentsUnexpectedException e) {
            System.out.println("Invalid command, use '$ neo help' for usage instructions!");
        }
    }
    
}
