package utils;

import java.io.FileNotFoundException;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public class utilities {
	
	// Finds the directory path for the file given as a parameter
	public String GetFileAbsolutePath( ) throws FileNotFoundException
	{
		String filepath = null;
		IWorkbenchPart workbenchpart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
		IFile ifile = (IFile) workbenchpart.getSite().getPage().getActiveEditor().getEditorInput().getAdapter(IFile.class);
		
		if(ifile == null) {
			throw new FileNotFoundException(); }
		
		filepath = ifile.getRawLocation().makeAbsolute().toOSString();
		
		return filepath;
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
