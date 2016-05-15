package com.sangupta.neo.velocity;

import java.io.IOException;
import java.io.Writer;

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

public class AddPropertyDirective extends Directive {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AddPropertyDirective.class);
    
	@Override
	public String getName() {
		return "addProperty";
	}

	@Override
	public int getType() {
		return LINE;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		String property = null;
		String value = null;
		
		if(node.jjtGetChild(0) != null) {
		    property = String.valueOf(node.jjtGetChild(0).value(context));
		}
        if(node.jjtGetChild(1) != null) {
            value = String.valueOf(node.jjtGetChild(1).value(context));
        }
        
        if(AssertUtils.isEmpty(property)) {
            LOGGER.error("Property name cannot be empty/null");
        }

        if(value == null) {
            value = "";
        }

		NeoGenerator.getInstance().addProperty(property, value);
		return true;
	}

}
