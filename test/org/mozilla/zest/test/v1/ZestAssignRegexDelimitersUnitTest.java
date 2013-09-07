/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.zest.test.v1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.mozilla.zest.core.v1.ZestAssignFailException;
import org.mozilla.zest.core.v1.ZestAssignRegexDelimiters;
import org.mozilla.zest.core.v1.ZestResponse;


/**
 */
@RunWith(MockitoJUnitRunner.class)
public class ZestAssignRegexDelimitersUnitTest {
	
	private TestRuntime rt = new TestRuntime();

	/**
	 * Method testSimpleCase.
	 * @throws Exception
	 */
	@Test
	public void testSimpleCase() throws Exception {
		ZestAssignRegexDelimiters ast = new ZestAssignRegexDelimiters();
		ZestResponse resp = new ZestResponse(null, "Header prefix12345postfix", "Body Prefix54321Postfix", 200, 0);

		ast.setVariableName("aaa");
		ast.setPrefix("prefix");
		ast.setPostfix("postfix");
		assertEquals ("12345", ast.assign(resp, rt));

		ast.setVariableName("aaa");
		ast.setPrefix("Prefix");
		ast.setPostfix("Postfix");
		assertEquals ("54321", ast.assign(resp, rt));
	}

	/**
	 * Method testRegexes.
	 * @throws Exception
	 */
	@Test
	public void testRegexes() throws Exception {
		ZestAssignRegexDelimiters ast = new ZestAssignRegexDelimiters();
		ZestResponse resp = new ZestResponse(null, "Header prefix12345postfix", "Body Prefix54321Postfix", 200, 0);

		ast.setVariableName("aaa");
		ast.setPrefix("^");
		ast.setPostfix("$");
		ast.setLocation(ZestAssignRegexDelimiters.LOC_HEAD);
		assertEquals ("Header prefix12345postfix", ast.assign(resp, rt));

		ast.setVariableName("aaa");
		ast.setPrefix("^");
		ast.setPostfix("$");
		ast.setLocation(ZestAssignRegexDelimiters.LOC_BODY);
		assertEquals ("Body Prefix54321Postfix", ast.assign(resp, rt));
	}

	/**
	 * Method testExceptions.
	 * @throws Exception
	 */
	@Test
	public void testExceptions() throws Exception {
		ZestAssignRegexDelimiters ast = new ZestAssignRegexDelimiters();
		ZestResponse resp = new ZestResponse(null, "aaaa", "bbbb", 200, 0);

		ast.setVariableName("aaa");
		ast.setPrefix("bbb");
		ast.setPostfix("ccc");
		try {
			ast.assign(null, rt);
			fail("Should have caused an exception");
		} catch (ZestAssignFailException e) {
			// Expected
		}
		
		ast.setVariableName("aaa");
		ast.setPrefix(null);
		ast.setPostfix("ccc");
		try {
			ast.assign(resp, rt);
			fail("Should have caused an exception");
		} catch (ZestAssignFailException e) {
			// Expected
		}
		
		ast.setVariableName("aaa");
		ast.setPrefix("bbb");
		ast.setPostfix(null);
		try {
			ast.assign(resp, rt);
			fail("Should have caused an exception");
		} catch (ZestAssignFailException e) {
			// Expected
		}
		
		ast.setVariableName("aaa");
		ast.setPrefix("xxx");
		ast.setPostfix("yyy");
		try {
			ast.assign(resp, rt);
			fail("Should have caused an exception");
		} catch (ZestAssignFailException e) {
			// Expected
		}
		
	}

}
