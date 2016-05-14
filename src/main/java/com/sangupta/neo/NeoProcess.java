package com.sangupta.neo;

/**
 * A process step that determines the actions we need to perform
 * other than just copying and processing the <code>data</code> folder.
 * 
 * For many of the projects it may be OK to have just <code>data</code>
 * folder processed, but many a times, projects will need to perform
 * many crucial steps based on user input, such as creating specific
 * files and folders as needed.
 * 
 * {@link NeoProcess} is essentially meant to execute such customized
 * process steps.
 * 
 * @author sangupta
 *
 */
public class NeoProcess {

    /**
     * The message to display before executing all commands in this process step
     */
    public String message;
    
    /**
     * List of commands to execute as part of this process step
     */
    public String[] commands;
    
    /**
     * The condition under which to bound all these steps
     */
    public String condition;
    
}
