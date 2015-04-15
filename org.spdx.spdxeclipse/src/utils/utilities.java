package utils;

import org.eclipse.core.resources.ResourcesPlugin;

public class utilities {
	
	// Finds the current workspace directory and returns it as a string; otherwise returns null
	public String GetWorkspaceDirectory() {
		
		String workspacepath = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
		
		return workspacepath;
	}
	
	// Finds the directory path for the file given as a parameter
	public String GetFileDirectory( String file ) {
		String workspacedir = GetWorkspaceDirectory();
		
		return workspacedir + "/" + file;
	}
	
	// validates that FOSSology is installed
	public Boolean ValidateFOSSology() {
		
		return true;
	}
	
	// validates that the DoSPDX.py ran successfully
	public Boolean ValidateDoSOCS() {
		
		return true;
	}
	
	// packages the current file to be sent to DoSOCS into a .tar file
	public String PackageFile( String file ) {
		
		return null;
	}
	
}
