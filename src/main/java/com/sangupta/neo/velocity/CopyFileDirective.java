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

public class CopyFileDirective extends Directive {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CopyFileDirective.class);
    
	@Override
	public String getName() {
		return "copyfile";
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
            destination = String.valueOf(node.jjtGetChild(0).value(context));
        }
        
        if(AssertUtils.isEmpty(source)) {
            LOGGER.error("Source dir cannot be empty/null");
        }

        if(AssertUtils.isEmpty(destination)) {
            LOGGER.error("Destination dir cannot be empty/null");
        }

		File src = NeoGenerator.getInstance().getResource(source);
		File dest = new File(NeoGenerator.getInstance().getProjectFolder(), destination);
		
		// TODO: check if we are not going out of parent folder
		FileUtils.copyFile(src, dest);
		
		System.out.println("File copied from: " + src.getAbsolutePath() + " to: " + dest.getAbsolutePath());
		return true;
	}

}
