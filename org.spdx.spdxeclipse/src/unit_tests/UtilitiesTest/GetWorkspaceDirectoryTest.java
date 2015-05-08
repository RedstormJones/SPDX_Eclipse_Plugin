package unit_tests.UtilitiesTest;

import org.eclipse.core.runtime.*;
import org.junit.Test;

import classes.Utilities;

public class GetWorkspaceDirectoryTest {

	/**
	 * Create a valid account.
	 * @result Account will be persisted without any errors,
	 *         and Account.getId() will no longer be <code>null</code>
	 */
	@Test
	public void ValidWorkspace_Pass() 
	{
		Utilities utils = new Utilities();

		IPath workspace_directory = null;
		
		workspace_directory = utils.GetWorkspaceDirectory();
		
		Assert.isNotNull(workspace_directory);
	}
}