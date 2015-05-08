package unit_tests.UtilitiesTest;

import org.eclipse.core.runtime.*;
import org.junit.Test;

import classes.Utilities;

public class GetWorkspaceDirectoryTest {

	@Test
	public void WorkspaceDirectoryTest_Pass() 
	{
		Utilities utils = new Utilities();
		
		IPath directory = utils.GetWorkspaceDirectory();
		
		Assert.isNotNull(directory);
	}
}