package classes;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
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
		Status status = new Status(IStatus.ERROR, "org.spdx.spdxeclipse", 0, message, e);
		ErrorDialog dlg = new ErrorDialog(Display.getCurrent().getActiveShell(), "ERROR", message, status, IStatus.ERROR);
		
		dlg.open();
	}
	
	public void Warning()
	{
		MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "WARNING", "There was a warning while generating your SPDX Document.  Please try your request again.");
	}
	
	public void Warning(String message)
	{
		MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "WARNING", message);
	}
}
