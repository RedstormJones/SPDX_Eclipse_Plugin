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

		if (utils.ValidateFOSSology() && utils.ValidateDoSOCS())
		{
			String filepath = null;
			String filename = null;
			
			try {
				filepath = utils.GetFileAbsolutePath();
				filename = utils.GetOpenFilename();
			}
			catch (FileNotFoundException e) 
			{ 
				e.printStackTrace();
			}
									
			String directory = utils.CreateSPDXDirectory();
			
			utils.CreateTarball(directory, filename, filepath);		
			
			System.out.printf("\ndirectory: %s\nfilename: %s\nfilepath: %s", directory, filename, filepath);
		}
		
		return null;
	}
}
