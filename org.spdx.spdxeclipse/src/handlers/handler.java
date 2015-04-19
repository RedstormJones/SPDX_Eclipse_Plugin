package handlers;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import utils.utilities;

public class handler extends AbstractHandler {

	public Object execute (ExecutionEvent event) throws ExecutionException
	{
		utilities utils = new utilities();
		String filepath = null;
		String tarpath = null;
		
		try { filepath = utils.GetFileAbsolutePath(); }
		catch (FileNotFoundException e) { e.printStackTrace(); }
		
		try { tarpath = utils.PackageFile(filepath); }
		catch (IOException e) {	e.printStackTrace(); }
		
		
		System.out.printf("\nfilepath: %s\ntarpath: %s\n", filepath.toString(), tarpath);

		
		return null;
	}
}
