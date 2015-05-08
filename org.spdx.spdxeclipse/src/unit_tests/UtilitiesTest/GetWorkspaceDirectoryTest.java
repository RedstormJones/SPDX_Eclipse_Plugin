/**
 * Copyright © 2015 Ryan Beasley, Tyler Filkins, University of Nebraska at Omaha
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