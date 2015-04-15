package handlers;


import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import utils.utilities;

public class handler extends AbstractHandler {

	public Object execute (ExecutionEvent event) throws ExecutionException {
		
		utilities utils = new utilities();
		
		String workspacedir = utils.GetWorkspaceDirectory();
		System.out.printf("workspace dir: %s\n", workspacedir);
		
		return null;
	}
}
