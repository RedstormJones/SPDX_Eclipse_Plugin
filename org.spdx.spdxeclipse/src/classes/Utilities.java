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

package classes;

import java.io.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.ui.*;

import classes.ExceptionUtilities;

public class Utilities {
		
	/**
	 *  Using the Runtime.getRuntime().exec() method to perform command line operations, this method runs the
	 *  DoSPDX.py file via command line on the users local machine, creating an SPDX document for the passed 
	 *  parameters.  This method also deletes the tarball file for which the SPDX document was created for.
	 * <p>
	 * @param target_directory : String (directory where the SPDX document should be created)
	 * @param tar_file_name : String (name of the tarball)
	 * @param spdx_document_type : String (type of SPDX document to be produced)
	 * <p>
	 * @return true or false depending if creation of the SPDX document failed.
	*/
	public Boolean CreateSPDX(String target_directory, String tar_file_name, String spdx_document_type)
	{ 
		try
		{
			String DoSPDXLoc = null;
			String dospdxOutput = null;
			
			// Create a string directory of where the SPDX document should be placed.
			String target_spdxfile = target_directory + "/" + tar_file_name + ".spdx";
			
			// Create a string directory for the tarball file.
			tar_file_name += ".tar";
			
			// Run the locate DoSPDX.py command to find the location of the DoSPDX.py file on the users local machine.
			Process findDoSPDX = Runtime.getRuntime().exec("locate DoSPDX.py");
			
			// Read the output from the execution of the command.
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(findDoSPDX.getInputStream()));
			
			// If the command found the DoSPDX.py file...
			if ((DoSPDXLoc = bufferedReader.readLine()) != null)
			{
				// Create a file object for the .spdx.
				File spdxFile = new File(target_spdxfile);
				
				// If the file doesn't exist...
				if(!spdxFile.exists())
				{
					// Create a new .spdx file.
					spdxFile.createNewFile();
				}
				
				// Append all proper statements to create the command.
				String DoSPDXcmd = DoSPDXLoc + " -p " + target_directory + "/" + tar_file_name + " --scan --scanOption fossology --print " + spdx_document_type;

				// Run the command created above to create the .spdx document on the users local machine.
				Process spdxDocInput = Runtime.getRuntime().exec(DoSPDXcmd);
				
				// Create a buffered reader and writer for capturing the output of the process running the DoSPDX
				// command the for writing the captured output to the .spdx file.
				BufferedReader spdxOutput = new BufferedReader(new InputStreamReader(spdxDocInput.getInputStream()));
				BufferedWriter spdxFileWriter = new BufferedWriter(new FileWriter(spdxFile));
				
				// For each line of the output append it.
				while((dospdxOutput = spdxOutput.readLine()) != null)
				{
					spdxFileWriter.append(dospdxOutput + "\n");
				}
				
				// Remove the .tar file used by the DoSPDX command to clean up the directory. 
				String rmtarcmd = "rm -f " + target_directory + "/" + tar_file_name;
				@SuppressWarnings("unused")

				// Execute the following command to remove the tar file.
				Process RemoveTarFile = Runtime.getRuntime().exec(rmtarcmd);
				
				spdxOutput.close();
				spdxFileWriter.close();
			}
			else
			{
				ExceptionUtilities exceptionUtils = new ExceptionUtilities();
				
				exceptionUtils.Error("An error occured while finding the DoSPDX.py file on your local file system.  Please try your request again.");
				
				return false;
			}
			
			bufferedReader.close();
        }
		catch (Exception e)
		{			
			ExceptionUtilities exceptionUtils = new ExceptionUtilities();
			
			exceptionUtils.Error("An error occured while calling the CreateSPDX method to create an SPDX Document.  Please try your request again.", e);
			
			return false;
		}
		
		return true;
	}
	
	/**
	 * Using the GetProjectDirectory() method, this method creates a new SPDX directory if it doesn't current exist at
	 * the following location: { Project Directory/SPDX }.
	 * <p>
	 * @return String of the new or existing SPDX Directory
	*/
	public String CreateSPDXDirectory() 
	{
		// Get the directory of the current project.
		IPath proj_dir = GetProjectDirectory();
		
		// Append /SPDX to the project directory.
		String spdx_fp = proj_dir.append("/SPDX").toOSString();
		
		// Create file for the new directory.
		File file = new File(spdx_fp);
			
		// If the SPDX directory doesn't exist...
		if (!file.exists())
		{			
			// Create it.
			file.mkdirs();
		}
		
		return spdx_fp;
	}
	
	/**
	 *  Using the Runtime.getRuntime().exec() method to perform command line operations, this method runs the
	 *  tar command on the users local machine.
	 * <p>
	 * @param target_directory : String (directory where the tarball file should be created)
	 * @param tar_file_name : String (what the tarball file should be named)
	 * @param file_directory : String (directory of the file to create a tarball from)
	 * <p>
	 * @return true or false depending if creation of the tarball failed.
	*/
	public Boolean CreateTarball(String target_directory, String tar_file_name, String file_directory)
	{
		try 
		{
			// Run the tar command.
			Process creatTar = Runtime.getRuntime().exec("tar -zcPf " + target_directory + "/" + tar_file_name + ".tar " + file_directory);
		
			// Read the output from the execution of the command.
			BufferedReader tarOutput = new BufferedReader(new InputStreamReader(creatTar.getInputStream()));
			
			// If an error occured while executing command...
			if((tarOutput.readLine()) != null)
			{
				ExceptionUtilities exceptionUtils = new ExceptionUtilities();
				
				exceptionUtils.Error("An error occured while creating the tarball for the currently open file.  Please try your request again.");
			
				return false;
			}
		} 
		catch (Exception e) 
		{
			ExceptionUtilities exceptionUtils = new ExceptionUtilities();
			
			exceptionUtils.Error("An error occured while creating the tarball for the currently open file.  Please try your request again.", e);
		
			return false;
		}
		
		return true;
	}
	
	/**
	 *  This method grabs first uses the users current workspace and then grabs the file currently open.  Using
	 *  the .getRawLocation() IFile method, we obtain the directory of the currently open file.
	 * <p>
	 * @return Directory of the currently open file in the form of a String.
	*/
	public String GetFileAbsolutePath() throws FileNotFoundException
	{		
		String filepath = null;
		
		// Grab the users current workspace.
		IWorkbenchPart workbenchpart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
		
		// Grab the currently open file if one exists.
		IFile ifile = (IFile) workbenchpart.getSite().getPage().getActiveEditor().getEditorInput().getAdapter(IFile.class);
		
		// If a file was open...
		if(ifile != null) 
		{
			// Grab its directory location.
			filepath = ifile.getRawLocation().makeAbsolute().toOSString();
		}
		else
		{
			throw new FileNotFoundException();
		}
		
		return filepath;
	}
	
	/**
	 *  This method first gets the users current workspace and then grabs the file currently open.  Using the
	 *  IFile method .getName() we obtain the name of the currently open file.
	 * <p>
	 * @return Name of the currently open file in the form of a String.
	*/
	public String GetOpenFilename() throws FileNotFoundException
	{		
		String filename = null;

		// Grab the users current workspace.
		IWorkbenchPart workbenchpart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
		
		// Grab the currently open file if one exists.
		IFile ifile = (IFile) workbenchpart.getSite().getPage().getActiveEditor().getEditorInput().getAdapter(IFile.class);
		
		// If a file was open...
		if (ifile != null)
		{
			// Grab the name.
			filename = ifile.getName();
		}
		// If no file was open...
		else
		{
			throw new FileNotFoundException();
		}
		
		return filename;
	}
	
	/**
	 * Using the root workspace directory for a users eclipse instance and the directory for the 
	 * currently open file, this method parses each location and finds the project directory for the open file.
	 * <p>
	 * @return IPath of the Project Directory
	*/
	public IPath GetProjectDirectory()
	{
		// Get the Workspace Directory.
		IPath wksp_dir = GetWorkspaceDirectory();

		int forwardSlashes = 0;
		
		// Count the number of directories for Workspace Directory.
		for(int i = 0; i < wksp_dir.toOSString().length(); i++) 
		{
		    if(wksp_dir.toOSString().charAt(i) == '/')
		    {
		    	forwardSlashes++;
		    }
		}
			
		String filepath = null;
		
		try 
		{
			// Get the location of the currently open file.
			filepath = GetFileAbsolutePath();
		} 
		catch (Exception e) 
		{
			ExceptionUtilities exceptionUtils = new ExceptionUtilities();
			
			exceptionUtils.Error("An error occured while getting the absolute path of the currently open file.  Please try your request again.", e);
		}
		
		// Parse the directories.
		String[] directories = filepath.split("/");
		
		// Get the project name.
		String proj_name = directories[forwardSlashes+1];
		
		// Append the project directory to the workspace directory.
		IPath proj_dir = wksp_dir.append("/" + proj_name + "");
			
		return proj_dir;
	}
	
	/**
	 * This method uses the IWorkspace method ResourcesPlugin.getWorkspace() to obtain the root workspace
	 * directory for a users eclipse instance.
	 * <p>
	 * @return IPath of the Workspace Directory
	*/
	public IPath GetWorkspaceDirectory()
	{
		// Get the current workspace directory.
		IPath wksp_dir = ResourcesPlugin.getWorkspace().getRoot().getLocation();
				
		return wksp_dir;
	}
	
	/**
	 * This method refreshes a users workspace manually using the IWorkspaceRoot's refreshLocal() method.
	*/
	public void RefreshInstance()
	{
		// Get the current workspace directory.
		IWorkspaceRoot workspaceroot = ResourcesPlugin.getWorkspace().getRoot();
		
		try 
		{
			// Try to refresh the workspace.
			workspaceroot.refreshLocal(IResource.DEPTH_INFINITE, null);
		} 
		catch (Exception e) 
		{
			ExceptionUtilities exceptionUtils = new ExceptionUtilities();
			
			exceptionUtils.Error("An error occured while refreshing your workspace.  Please manually refresh your workspace using the right clickable menu.", e);
		}
	}
}