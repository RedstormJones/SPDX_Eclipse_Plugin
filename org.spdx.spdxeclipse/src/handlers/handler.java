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

		if (utils.ValidateFOSSology() && utils.ValidateDoSOCS())
		{
			String filepath = null;
			String filename = null;
			
			try {
				filepath = utils.GetFileAbsolutePath();
				filename = utils.GetOpenFilename();
			}
			catch (FileNotFoundException e) { e.printStackTrace(); }
			
			String directory = utils.CreateSPDXDirectory();
			
			String tarpath = utils.PackageFile(directory, filename);		
			
			System.out.printf("\nfilename: %s\nfilepath: %s\ntarpath: %s\n", filename, filepath.toString(), tarpath);
		}
		else
		{
			
		}
		
		return null;
	}
}
