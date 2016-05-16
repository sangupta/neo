package com.sangupta.neo.velocity;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.neo.NeoGenerator;

public class MoveFileDirective extends Directive {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MoveFileDirective.class);
    
	@Override
	public String getName() {
		return "moveDir";
	}

	@Override
	public int getType() {
		return LINE;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		String source = null;
		String destination = null;
		
		if(node.jjtGetChild(0) != null) {
			source = String.valueOf(node.jjtGetChild(0).value(context));
		}
        if(node.jjtGetChild(1) != null) {
            destination = String.valueOf(node.jjtGetChild(1).value(context));
        }
        
        if(AssertUtils.isEmpty(source)) {
            LOGGER.error("Source file cannot be empty/null");
        }

        if(AssertUtils.isEmpty(destination)) {
            LOGGER.error("Destination file cannot be empty/null");
        }

		File srcFile = new File(NeoGenerator.getInstance().getProjectFolder(), source);
		File destFile = new File(NeoGenerator.getInstance().getProjectFolder(), destination);
		
		// TODO: check if we are not going out of parent folder
		FileUtils.moveDirectory(srcFile, destFile);
		
		System.out.println("Directory moved from: " + srcFile.getAbsolutePath() + " to: " + destFile.getAbsolutePath());
		return true;
	}

}
