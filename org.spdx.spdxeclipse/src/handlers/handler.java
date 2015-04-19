package handlers;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import utils.utilities;

public class handler extends AbstractHandler {

	public Object execute (ExecutionEvent event) throws ExecutionException
	{
		utilities utils = new utilities();
		String filepath = "file not found";
		String tar_fp = null;
		
		try { filepath = utils.GetFileAbsolutePath(); }
		catch (FileNotFoundException e) { e.printStackTrace(); }
		
		if(filepath.equals(null)) { return null; }
		else
		{	
			try { tar_fp = utils.PackageFile(filepath); }
			catch (IOException e) { e.printStackTrace(); }
			
			System.out.printf("tar_fp:  %s\n", tar_fp);
		}
		
		
		return null;
	}
}
