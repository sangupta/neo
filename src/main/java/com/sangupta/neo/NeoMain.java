package com.sangupta.neo;

import com.sangupta.neo.commands.CreateProject;

import io.airlift.airline.Cli;
import io.airlift.airline.Cli.CliBuilder;
import io.airlift.airline.Help;
import io.airlift.airline.ParseArgumentsUnexpectedException;

/**
 * Main entry point for the Neo project creation tool.
 * 
 * @author sangupta
 *
 */
public class NeoMain {
    
    public static void main(String[] args) {
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
