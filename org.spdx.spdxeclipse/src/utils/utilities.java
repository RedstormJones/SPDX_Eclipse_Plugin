package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public class utilities {
	
	// Finds the directory path for the file given as a parameter
	public String GetFileAbsolutePath() throws FileNotFoundException
	{
		String filepath = null;
		IWorkbenchPart workbenchpart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
		IFile ifile = (IFile) workbenchpart.getSite().getPage().getActiveEditor().getEditorInput().getAdapter(IFile.class);
		
		if(ifile == null) {
			throw new FileNotFoundException(); }
		else {
			filepath = ifile.getRawLocation().makeAbsolute().toOSString(); }
		
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
	public String PackageFile( String filepath ) throws IOException
	{
		String tarcmd = null;
		String tar_fp = null;
		String line;
		
		if(filepath.equals(null)) { return null; }
		else {
			tar_fp = filepath + ".tar";
			tarcmd = "gksudo 'tar -cf " + tar_fp + " " + filepath + "'";
			System.out.printf("\ntarcmd:  %s\n", tarcmd);
		}
		
		Process tarproc = Runtime.getRuntime().exec(tarcmd);
		
		//BufferedReader stdoutread = new BufferedReader (new InputStreamReader(tarproc.getInputStream()));
		//while ((line = stdoutread.readLine()) != null) {;}
		
		//BufferedReader stderrRead = new BufferedReader (new InputStreamReader(tarproc.getErrorStream()));
		//while ((line = stderrRead.readLine()) != null) {;}
		
		//int retval = tarproc.exitValue();
		//if(Runtime.getRuntime().exec(tarcmd) == null) { return "Failed to create .tar file"; }
		
		return tar_fp;
	}
	
}
