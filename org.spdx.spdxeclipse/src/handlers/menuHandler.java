/**
 * Copyright © 2015 University of Nebraska at Omaha
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

import org.eclipse.core.commands.*;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;

import classes.ExceptionUtilities;
import classes.Utilities;

public class menuHandler extends AbstractHandler {
	
	public Object execute (ExecutionEvent event) throws ExecutionException
	{							
		Utilities utils = new Utilities();
		ExceptionUtilities exceptionUtils = new ExceptionUtilities();
		
		String SPDXDocumentType = null;
		
		try 
		{
			SPDXDocumentType = event.getCommand().getName();
		} 
		catch (NotDefinedException e1) 
		{
			exceptionUtils.Error(e1);
		}
		
		if (SPDXDocumentType != null)
		{
			SPDXDocumentType = SPDXDocumentType.substring(SPDXDocumentType.lastIndexOf('.') + 1).trim().toUpperCase();
		}
		else
		{
			exceptionUtils.Error();
		}
		
		String filepath = null;
		
		filepath = utils.GetFileAbsolutePath();
		
		if (filepath == null)
		{
			exceptionUtils.Error("An error occured while finding the directory of the currently open file.  Please try your request again.");
		}
		
		String filename = null;
		
		filename = utils.GetOpenFilename();
		
		// Create the SPDX/ directory within the project
		String directory = utils.CreateSPDXDirectory();
		
		// Create a .tar file from the source file to be scanned
		utils.CreateTarball(directory, filename, filepath);		
		
		// Create the .spdx document from the .tar file and store
		// in the SPDX/ directory. Remove the .tar file will as well.
		if( utils.CreateSPDX(directory, filename, SPDXDocumentType) )
		{
			// refresh the Eclipse for the SPDX folder and/or 
			utils.RefreshInstance();
		}
			
		return null;
	}
}