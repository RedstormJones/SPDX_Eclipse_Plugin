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

import org.eclipse.core.commands.*;
import classes.*;

public class menuHandler extends AbstractHandler {
	
	// This execute method handles the clickable actions tied to each menu contribution in the plugin.xml file.
	public Object execute (ExecutionEvent event) throws ExecutionException
	{			
		Utilities utils = new Utilities();
		ExceptionUtilities exceptionUtils = new ExceptionUtilities();
		
		String SPDXDocumentType = null;
		
		try 
		{
			// Get the name of the commandId passed to the execute function.
			SPDXDocumentType = event.getCommand().getName();
		} 
		catch (Exception e1) 
		{   
			exceptionUtils.Error(e1);

			return null;
		}
		
		// If a commandId was successfully passed to the execute function...
		if (SPDXDocumentType != null)
		{
			// Parse the commandId for the string after the last occurrence of a period and store that.
			// This should store either JSON, TAG, or RDF as the SPDXDocumentType requested by the user.
			SPDXDocumentType = SPDXDocumentType.substring(SPDXDocumentType.lastIndexOf('.') + 1).trim().toUpperCase();
		}
		else
		{   
	        exceptionUtils.Error();

	        return null;
		}
			
		String filepath = null;
		String filename = null;

		try 
		{
			// Get the name of the currently open file.
			filename = utils.GetOpenFilename();
			
			// Get the directory of the currently open file.
			filepath = utils.GetFileAbsolutePath();
		} 
		catch (Exception e2) 
		{
			exceptionUtils.Warning("To generate an SPDX document please open a file and try your request again.");

			return null;
		}	
				
		// Create or find the /SPDX directory relative to the project of the currently open file.
		String directory = utils.CreateSPDXDirectory();
		
		// Create a .tar within the /SPDX directory using the passed source file.
		if (utils.CreateTarball(directory, filename, filepath))
		{
			// Create an .spdx document using the users specified document type passing it the directory of 
			// the .tar file and finally placing it in the /SPDX directory.  Once the .spdx document has been
			// successfully created, remove the .tar file from the /SPDX directory.
			if( utils.CreateSPDX(directory, filename, SPDXDocumentType) )
			{
				// Refresh the users workspace to reflect the previous changes.
				utils.RefreshInstance();
			}
		}
		
		return null;
	}
}