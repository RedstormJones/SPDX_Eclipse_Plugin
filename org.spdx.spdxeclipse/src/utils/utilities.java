package utils;

import java.io.FileNotFoundException;
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
		else { return wksp_dir; }
	}
	
	// Returns the filename of the currently open file in the editor
	public String GetFilename()
	{		
		IWorkbenchPart workbenchpart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
		IFile ifile = (IFile) workbenchpart.getSite().getPage().getActiveEditor().getEditorInput().getAdapter(IFile.class);
		String filename = ifile.getName();
		
		return filename;
	}
	
	
	// Gets the workspace directory and creates a spdx/ directory for storing
	// the spdx documents. Then executes library functions for creating a .tar
	// file of the file specified in the filepath parameter.
	public String PackageFile( String filepath )
	{
		IPath wksp_dir = null;
		String tarfilename = GetFilename();
		
		// get the workspace directory path
		try { wksp_dir = GetWorkspaceDirectory(); }
		catch (FileNotFoundException e) { e.printStackTrace(); }
		
		// Create the SPDX/ directory in the workspace directory
		String tar_fp = wksp_dir.append("/SPDX").toOSString();
		File spdx = new File(tar_fp);
		if(!spdx.mkdir()) {
			System.out.println("creating <workspace>/spdx directory failed");
			return null;
		}
		
		// Piece together the path for where to put .tar files and the filename
		String path_to_tar = tar_fp + "/" + tarfilename + ".tar";
		
		
		
		return path_to_tar;
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
