package handlers;

import java.io.FileNotFoundException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;

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
			
			// Create the SPDX/ directory within the project
			String directory = utils.CreateSPDXDirectory();
			
			// Create a .tar file from the source file to be scanned
			utils.CreateTarball(directory, filename, filepath);		
			
			// Create the .spdx document from the .tar file and store
			// in the SPDX/ directory. Remove the .tar file will as well.
			if( utils.CreateSPDX(directory, filename) )
			{
				// refresh the Eclipse for the SPDX folder and/or 
				// updated spdx documents appear in the Package Explorer
				try { 
					utils.refreshInstance();
				}
				catch (CoreException e) 
				{
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
}
