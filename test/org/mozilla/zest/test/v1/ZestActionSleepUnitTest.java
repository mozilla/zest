/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.zest.test.v1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.mozilla.zest.core.v1.ZestActionInvoke;
import org.mozilla.zest.core.v1.ZestActionSleep;
import org.mozilla.zest.core.v1.ZestJSON;
import org.mozilla.zest.core.v1.ZestResponse;


/**
 */
@RunWith(MockitoJUnitRunner.class)
public class ZestActionSleepUnitTest {

	/**
	 * Method testSimpleJsScript.
	 * @throws Exception
	 */
	@Test
	public void testSimpleJsScript() throws Exception {
		ZestActionSleep inv = new ZestActionSleep();
		inv.setMilliseconds(1000);
		TestRuntime rt = new TestRuntime();
		
		ZestResponse resp = new ZestResponse(null, "Header prefix12345postfix", "Body Prefix54321Postfix", 200, 0);
		Date start = new Date();
		String result = inv.invoke(resp, rt);
		Date end = new Date();

		assertEquals ("1000", result);
		// Make sure its within 5% or expected time
		assertEquals ((end.getTime() - start.getTime())/5, 200);
	}

	@Test
	public void testSerialization() {
		ZestActionSleep inv = new ZestActionSleep();
		inv.setMilliseconds(1000);
		
		String str = ZestJSON.toString(inv);
		
		ZestActionSleep inv2 = (ZestActionSleep) ZestJSON.fromString(str);
		
		assertEquals(inv.getElementType(), inv2.getElementType());
		assertEquals(inv.getMilliseconds(), inv2.getMilliseconds());
	}

}
