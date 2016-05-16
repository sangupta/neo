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

public class RemoveFileDirective extends Directive {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RemoveFileDirective.class);
    
	@Override
	public String getName() {
		return "rmFile";
	}

	@Override
	public int getType() {
		return LINE;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		String file = null;
		
		if(node.jjtGetChild(0) != null) {
			file = String.valueOf(node.jjtGetChild(0).value(context));
		}
        
        if(AssertUtils.isEmpty(file)) {
            LOGGER.error("File to delete cannot be empty/null");
        }
        
		File dest = new File(NeoGenerator.getInstance().getProjectFolder(), file);
		
		// TODO: check if we are not going out of parent folder
		boolean deleted = FileUtils.deleteQuietly(dest);
		
		System.out.println("File deleted at: " + dest.getAbsolutePath() + ": " + deleted);
		return true;
	}

}
