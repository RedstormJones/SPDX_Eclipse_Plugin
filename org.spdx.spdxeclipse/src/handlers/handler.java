package handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import utils.utilities;

public class handler extends AbstractHandler {

	public Object execute (ExecutionEvent event) throws ExecutionException {
		
		utilities utils = new utilities();

		String fileName = utils.GetOpenFileName();
		
		String filedir = utils.GetFileDirectory(fileName);
		
		System.out.printf("GetFileDirectory() : %s\n", filedir);
		
		return null;
	}
}
