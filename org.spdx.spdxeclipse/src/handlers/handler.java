package handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import utils.utilities;

public class handler extends AbstractHandler {

	public Object execute (ExecutionEvent event) throws ExecutionException {
		
		utilities utils = new utilities();

		String file = "helloworld.java";
		
		String filedir = utils.GetFileDirectory(file);
		
		System.out.printf("GetFileDirectory() : %s\n", filedir);
						
		return null;
	}
}
