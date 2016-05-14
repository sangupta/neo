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

public class LogDirective extends Directive {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LogDirective.class);

	@Override
	public String getName() {
		return "log";
	}

	@Override
	public int getType() {
		return LINE;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		Object text = null;

		if(node.jjtGetChild(0) != null) {
			text = node.jjtGetChild(0).value(context);
		}

		if(text == null) {
			return true;
		}

		LOGGER.info(text.toString());
		return true;
	}

}
