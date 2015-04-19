package handlers;

import java.io.FileNotFoundException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import utils.utilities;

public class handler extends AbstractHandler {

	public Object execute (ExecutionEvent event) throws ExecutionException
	{
		utilities utils = new utilities();
		String filepath = "file-not-found";
		
		try {
			filepath = utils.GetFileAbsolutePath(); 
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.printf("GetFileDirectory() : %s\n", filepath);
		
		return null;
	}
}
