package handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class handler extends AbstractHandler {

	public Object execute (ExecutionEvent event) throws ExecutionException {
		System.out.println("Hello world");
		
		return null;
	}
}
