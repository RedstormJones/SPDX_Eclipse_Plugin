package unit_tests.UtilitiesTest;

import org.eclipse.core.runtime.*;
import org.junit.Test;

import classes.Utilities;

public class GetWorkspaceDirectoryTest {

	@Test
	public void ValidWorkspace_Pass() 
	{
		Utilities utils = new Utilities();
		
		IPath workspace_directory = utils.GetWorkspaceDirectory();
		
		Assert.isNotNull(workspace_directory);
	}
}