/**
 * Copyright (c) 2015 University of Nebraska at Omaha
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
*/

package handlers;

import java.io.FileNotFoundException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import utils.utilities;

public class menuHandler extends AbstractHandler {

	public Object execute (ExecutionEvent event) throws ExecutionException
	{
		String SPDXDocumentType = null;
		
		try 
		{
			SPDXDocumentType = event.getCommand().getName();
			
			if (SPDXDocumentType != null)
			{
				SPDXDocumentType = SPDXDocumentType.substring(SPDXDocumentType.lastIndexOf('.') + 1).trim().toUpperCase();
			}
			
		} 
		catch (NotDefinedException e1) 
		{
			e1.printStackTrace();
		}
								
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
			if( utils.CreateSPDX(directory, filename, SPDXDocumentType) )
			{
				// refresh the Eclipse for the SPDX folder and/or 
				// updated spdx documents appear in the Package Explorer
				try {
					
					utils.RefreshInstance();
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
