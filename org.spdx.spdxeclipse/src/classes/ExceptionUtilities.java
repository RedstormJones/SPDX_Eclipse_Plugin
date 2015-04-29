package classes;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

public class ExceptionUtilities {
	
	public void Error()
	{
		MessageDialog.openError(Display.getCurrent().getActiveShell(), "ERROR", "There was an error while generating your SPDX Document.  Please try your request again.");
	}
	
	public void Error(String message)
	{
		MessageDialog.openError(Display.getCurrent().getActiveShell(), "ERROR", message);
	}
	
	public void Error(String message, Throwable e)
	{
		
	}
}
