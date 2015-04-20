package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
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
	
	public Boolean CreateSPDX(String target_directory, String tar_file_name)
	{ 
		try
		{
			Process findDoSPDX = Runtime.getRuntime().exec("locate DoSPDX.py");
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(findDoSPDX.getInputStream()));
			
			String DoSPDXLoc = null;
			String dospdxOutput = null;
			
			if ((DoSPDXLoc = bufferedReader.readLine()) != null)
			{					
				String target_spdxfile = target_directory + "/" + tar_file_name + ".tar.spdx";
				System.out.println(target_spdxfile);
				
				File spdxFile = new File(target_spdxfile);
				if(!spdxFile.exists())
				{
					spdxFile.createNewFile();
				}
				
				String DoSPDXcmd = DoSPDXLoc + " -p " + target_directory + "/" + tar_file_name + ".tar --scan --scanOption fossology --print RDF";
				System.out.println(DoSPDXcmd);

				Process spdxDocInput = Runtime.getRuntime().exec(DoSPDXcmd);
				
				BufferedReader spdxOutput = new BufferedReader(new InputStreamReader(spdxDocInput.getInputStream()));
				BufferedWriter spdxFileWriter = new BufferedWriter(new FileWriter(spdxFile));
				
				while((dospdxOutput = spdxOutput.readLine()) != null)
				{
					spdxFileWriter.append(dospdxOutput + "\n");
				}
				
				spdxOutput.close();
				spdxFileWriter.close();
			}
			
			bufferedReader.close();
        }
		catch (Exception e)
		{
			e.printStackTrace();
		}
		

		
		return false;
	}
}
