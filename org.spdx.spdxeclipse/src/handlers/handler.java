package handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import utils.utilities;

public class handler extends AbstractHandler {

	public Object execute (ExecutionEvent event) throws ExecutionException {
		String str = "whatever";
		utilities utils = new utilities();
		
		String filedir = utils.GetFileDirectory(str);
		System.out.printf("GetFileDirectory() : %s\n", filedir);
		
		return null;
	}
}
