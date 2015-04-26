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

package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public class utilities {
	
	// Returns the absolute directory path of the currently open file in the editor
	public String GetFileAbsolutePath() throws FileNotFoundException
	{
		String filepath = null;
		
		IWorkbenchPart workbenchpart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
		IFile ifile = (IFile) workbenchpart.getSite().getPage().getActiveEditor().getEditorInput().getAdapter(IFile.class);
		
		if(ifile == null) 
		{ 
			throw new FileNotFoundException();
		}
		else
		{
			filepath = ifile.getRawLocation().makeAbsolute().toOSString();
			
			if(filepath == null) 
			{ 
				throw new FileNotFoundException();
			}
			else
			{
				return filepath;
			}
		}
	}
		
	// Returns the workspace directory
	public IPath GetWorkspaceDirectory()
	{
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IPath wksp_dir = workspace.getRoot().getLocation();
		
		return wksp_dir;
	}
	
	public IPath GetProjectDirectory()
	{
		IPath wksp_dir = GetWorkspaceDirectory();

		int forwardSlashes = 0;
		
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
			filepath = GetFileAbsolutePath();
						
		}
		catch (FileNotFoundException e) 
		{ 
			e.printStackTrace();
		}
		
		String[] directories = filepath.split("/");
		
		String proj_name = directories[forwardSlashes+1];
		
		IPath proj_dir = wksp_dir.append("/" + proj_name + "");
				
		return proj_dir;
	}
	
	// Returns the filename of the currently open file in the editor
	public String GetOpenFilename() throws FileNotFoundException
	{		
		IWorkbenchPart workbenchpart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
		IFile ifile = (IFile) workbenchpart.getSite().getPage().getActiveEditor().getEditorInput().getAdapter(IFile.class);
		String filename = ifile.getName();
		
		if (filename ==  null)
		{
			throw new FileNotFoundException();
		}
		else
		{
			return filename;
		}
	}
	
	public String CreateSPDXDirectory() 
	{
		IPath proj_dir = GetProjectDirectory();
		
		String tar_fp = proj_dir.append("/SPDX").toOSString();
		
		File file = new File(tar_fp);
				
		if (!file.exists())
		{			
			file.mkdirs();
		}
		
		return tar_fp;
	}
	
	// This method executes library functions for creating .tar
	// file of the file specified in the filepath parameter.
	public Boolean CreateTarball(String target_directory, String tar_file_name, String file_directory)
	{
		try 
		{
			Runtime.getRuntime().exec("tar -zcPf " + target_directory + "/" + tar_file_name + ".tar " + file_directory);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return true;
	}
	
	// validates that FOSSology is installed
	public Boolean ValidateFOSSology() 
	{
		return true;
	}
	
	// validates that the DoSPDX.py ran successfully
	public Boolean ValidateDoSOCS()
	{ 
		try
		{
			Process findDoSPDX = Runtime.getRuntime().exec("locate DoSPDX.py");
		
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(findDoSPDX.getInputStream()));
			
			if (bufferedReader.readLine() != null)
			{	
				return true;
			}
			
			bufferedReader.close();
        }
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	// Takes a target directory and a filename as parameters and runs the DoSPDX.py 
	// command on the combined target directory and file. The output of the command is
	// written to a .spdx file that is named the same as the tar file name.
	public Boolean CreateSPDX(String target_directory, String tar_file_name, String spdx_document_type)
	{ 
		try
		{
			String DoSPDXLoc = null;
			String dospdxOutput = null;
			String target_spdxfile = target_directory + "/" + tar_file_name + ".spdx";
			
			tar_file_name += ".tar";
			
			Process findDoSPDX = Runtime.getRuntime().exec("locate DoSPDX.py");
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(findDoSPDX.getInputStream()));
			
			if ((DoSPDXLoc = bufferedReader.readLine()) != null)
			{
				File spdxFile = new File(target_spdxfile);
				if(!spdxFile.exists())
				{
					spdxFile.createNewFile();
				}
				
				// Put all the pieces of the DoSPDX command together and run it
				String DoSPDXcmd = DoSPDXLoc + " -p " + target_directory + "/" + tar_file_name + " --scan --scanOption fossology --print " + spdx_document_type;

				Process spdxDocInput = Runtime.getRuntime().exec(DoSPDXcmd);
				
				// Create a buffered reader and writer for capturing the output of the process running the DoSPDX
				// command the for writing the captured output to the .spdx file.
				BufferedReader spdxOutput = new BufferedReader(new InputStreamReader(spdxDocInput.getInputStream()));
				BufferedWriter spdxFileWriter = new BufferedWriter(new FileWriter(spdxFile));
				
				while((dospdxOutput = spdxOutput.readLine()) != null)
				{
					spdxFileWriter.append(dospdxOutput + "\n");
				}
				
				// Remove the .tar file used by the DoSPDX command to clean up the directory. 
				// Then close the buffered reader and writer.
				String rmtarcmd = "rm -f " + target_directory + "/" + tar_file_name;
				@SuppressWarnings("unused")
				Process RemoveTarFile = Runtime.getRuntime().exec(rmtarcmd);
				
				spdxOutput.close();
				spdxFileWriter.close();
			}
			
			bufferedReader.close();
        }
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public void RefreshInstance() throws CoreException
	{
		IWorkspaceRoot workspaceroot = ResourcesPlugin.getWorkspace().getRoot();
		workspaceroot.refreshLocal(IResource.DEPTH_INFINITE, null);
	}
}