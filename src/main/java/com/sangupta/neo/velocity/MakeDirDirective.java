package com.sangupta.neo.velocity;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import com.sangupta.neo.NeoGenerator;

public class MakeDirDirective extends Directive {
    
	@Override
	public String getName() {
		return "mkdir";
	}

	@Override
	public int getType() {
		return LINE;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		String param = null;

		if(node.jjtGetChild(0) != null) {
			param = String.valueOf(node.jjtGetChild(0).value(context));
		}

		if(param == null) {
			return true;
		}

		File baseFolder = NeoGenerator.getInstance().getProjectFolder();
		
		// TODO: check if we are not going out of parent folder
		File file = new File(baseFolder, param);
		boolean success = file.mkdirs();
		
		System.out.println("Directory created at: " + file.getAbsolutePath() + " :: " + success);
		return true;
	}

}
