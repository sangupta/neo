package com.sangupta.neo.velocity;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.neo.NeoGenerator;

public class PrintContextDirective extends Directive {

	@Override
	public String getName() {
		return "printContext";
	}

	@Override
	public int getType() {
		return LINE;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
	    Map<String, Object> map = NeoGenerator.getInstance().getProperties();
	    if(AssertUtils.isEmpty(map)) {
	        return true;
	    }
	    
	    for(Entry<String, Object> entry : map.entrySet()) {
	        System.out.println("Property: " + entry.getKey() + " = " + entry.getValue());
	    }
	    
		return true;
	}

}
