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

import utils.utilities;

public class menuHandler extends AbstractHandler {
	
	public Object execute (ExecutionEvent event) throws ExecutionException
	{							
		utilities utils = new utilities();

		if (utils.ValidateFOSSology() && utils.ValidateDoSOCS())
		{
			String SPDXDocumentType = null;
			
			try 
			{
				SPDXDocumentType = event.getCommand().getName();
			} 
			catch (NotDefinedException e1) 
			{
				Status status = new Status(IStatus.ERROR, "org.spdx.spdxeclipse", 0, "Error", e1);
				ErrorDialog dlg = new ErrorDialog(Display.getCurrent().getActiveShell(), "Error", "There was an error while generating your SPDX Document.  Please try your request again.", status, IStatus.ERROR);
				dlg.open();
			}
			
			if (SPDXDocumentType != null)
			{
				SPDXDocumentType = SPDXDocumentType.substring(SPDXDocumentType.lastIndexOf('.') + 1).trim().toUpperCase();
			}
			
			String filepath = null;
			String filename = null;
			
			filepath = utils.GetFileAbsolutePath();
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
		}
		
		return null;
	}
}