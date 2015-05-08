package unit_tests.UtilitiesTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@SuiteClasses({ CreateSPDXDirectoryTest.class, 
				CreateSPDXTest.class,
				CreateTarballTest.class, 
				GetFileAbsolutePathTest.class,
				GetOpenFilenameTest.class, 
				GetProjectDirectoryTest.class,
				GetWorkspaceDirectoryTest.class, 
				RefreshInstanceTest.class })

public class AllTests {

}
