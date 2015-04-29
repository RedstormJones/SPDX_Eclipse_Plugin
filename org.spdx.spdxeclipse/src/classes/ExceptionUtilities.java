/**
 * Copyright © 2015 University of Nebraska at Omaha
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

package classes;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ExceptionUtilities 
{	
	public void Error()
	{
		Display display = Display.getDefault();
        Shell shell = new Shell(display);
		
		MessageDialog.openError(shell, "ERROR", "There was an error while generating your SPDX Document.  Please try your request again.");
	}
	
	public void Error(String message)
	{
		Display display = Display.getDefault();
        Shell shell = new Shell(display);
        
		MessageDialog.openError(shell, "ERROR", message);
	}
	
	public void Error(Throwable e)
	{
		Display display = Display.getDefault();
        Shell shell = new Shell(display);
		
		Status status = new Status(IStatus.ERROR, "org.spdx.spdxeclipse", 0, "There was an error while generating your SPDX Document.  Please try your request again.", e);
		ErrorDialog dlg = new ErrorDialog(shell, "ERROR", "There was an error while generating your SPDX Document.  Please try your request again.", status, IStatus.ERROR);
		
		dlg.open();
	}
	
	public void Error(String message, Throwable e)
	{
		Display display = Display.getDefault();
        Shell shell = new Shell(display);
        
		Status status = new Status(IStatus.ERROR, "org.spdx.spdxeclipse", 0, message, e);
		ErrorDialog dlg = new ErrorDialog(shell, "ERROR", message, status, IStatus.ERROR);
		
		dlg.open();
	}
	
	public void Warning()
	{
		Display display = Display.getDefault();
        Shell shell = new Shell(display);
        
		MessageDialog.openWarning(shell, "WARNING", "There was a warning while generating your SPDX Document.  Please try your request again.");
	}
	
	public void Warning(String message)
	{
		Display display = Display.getDefault();
        Shell shell = new Shell(display);
        
		MessageDialog.openWarning(shell, "WARNING", message);
	}
}
