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
		String filepath = null;
		String filename = null;
		
		try {
			filepath = utils.GetFileAbsolutePath();
			filename = utils.GetFilename();
		}
		catch (FileNotFoundException e) { e.printStackTrace(); }
		
		String tarpath = utils.PackageFile(filepath);		
		
		System.out.printf("\nfilename: %s\nfilepath: %s\ntarpath: %s\n", filename, filepath.toString(), tarpath);

		
		return null;
	}
}
