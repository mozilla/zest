/**
/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * @author Alessandro Secco: seccoale@gmail.com
 */
package mozilla.zest.core.v1;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.mozilla.zest.core.v1.ZestLoopFile;

/**
 */
public class ZestLoopFileUnitTest {
	public static final File file=new File("test/org/TestLoopFile.txt");
	/**
	 * Method testOpenFile.
	 * @throws IOException
	 */
	@Test
	public void testOpenFile() throws IOException {
		new ZestLoopFile(file);
	}
	
}
