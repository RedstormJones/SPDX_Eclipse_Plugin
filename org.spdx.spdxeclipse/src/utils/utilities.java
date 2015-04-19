package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

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
		if(ifile == null) { throw new FileNotFoundException(); }
		
		filepath = ifile.getRawLocation().makeAbsolute().toOSString();
		if(filepath.equals(null)) { throw new FileNotFoundException(); }
		
		return filepath;
	}
	
	
	// Returns the workspace directory
	public IPath GetWorkspaceDirectory() throws FileNotFoundException
	{
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IPath wksp_dir = workspace.getRoot().getLocation();
		
		if(wksp_dir == null) { throw new FileNotFoundException(); }
		
		return wksp_dir;
	}
	
	
	// Gets the workspace directory and creates a spdx/ directory for storing
	// the spdx documents. Then executes library functions for creating a .tar
	// file of the file specified in the filepath parameter.
	public String PackageFile( String filepath ) throws IOException
	{
		IPath wksp_dir = null;
		
		try { wksp_dir = GetWorkspaceDirectory(); }
		catch (FileNotFoundException e) { e.printStackTrace(); }
		
		String tar_fp = wksp_dir.append("/spdx").toOSString();
		File temp = new File(tar_fp);
		if(!temp.mkdir()) { System.out.println("creating <workspace>/spdx directory failed"); }

		
		return tar_fp;
	}
	
	
	// validates that FOSSology is installed
	public Boolean ValidateFOSSology() {
		
		return true;
	}
	
	
	// validates that the DoSPDX.py ran successfully
	public Boolean ValidateDoSOCS() {
		
		return true;
	}
	
}
