/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package mozilla.zest.core.v1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.mozilla.zest.core.v1.ZestActionFailException;
import org.mozilla.zest.core.v1.ZestActionSetToken;
import org.mozilla.zest.core.v1.ZestResponse;


/**
 */
@RunWith(MockitoJUnitRunner.class)
public class ZestActionSetTokenUnitTest {

	/**
	 * Method testSimpleCase.
	 * @throws Exception
	 */
	@Test
	public void testSimpleCase() throws Exception {
		ZestActionSetToken ast = new ZestActionSetToken();
		ZestResponse resp = new ZestResponse(null, "Header prefix12345postfix", "Body Prefix54321Postfix", 200, 0);

		ast.setTokenName("aaa");
		ast.setPrefix("prefix");
		ast.setPostfix("postfix");
		assertEquals ("12345", ast.invoke(resp));

		ast.setTokenName("aaa");
		ast.setPrefix("Prefix");
		ast.setPostfix("Postfix");
		assertEquals ("54321", ast.invoke(resp));
	}

	/**
	 * Method testRegexes.
	 * @throws Exception
	 */
	@Test
	public void testRegexes() throws Exception {
		ZestActionSetToken ast = new ZestActionSetToken();
		ZestResponse resp = new ZestResponse(null, "Header prefix12345postfix", "Body Prefix54321Postfix", 200, 0);

		ast.setTokenName("aaa");
		ast.setPrefix("^");
		ast.setPostfix("$");
		ast.setLocation(ZestActionSetToken.LOC_HEAD);
		assertEquals ("Header prefix12345postfix", ast.invoke(resp));

		ast.setTokenName("aaa");
		ast.setPrefix("^");
		ast.setPostfix("$");
		ast.setLocation(ZestActionSetToken.LOC_BODY);
		assertEquals ("Body Prefix54321Postfix", ast.invoke(resp));
	}

	/**
	 * Method testExceptions.
	 * @throws Exception
	 */
	@Test
	public void testExceptions() throws Exception {
		ZestActionSetToken ast = new ZestActionSetToken();
		ZestResponse resp = new ZestResponse(null, "aaaa", "bbbb", 200, 0);

		ast.setTokenName("aaa");
		ast.setPrefix("bbb");
		ast.setPostfix("ccc");
		try {
			ast.invoke(null);
			fail("Should have caused an exception");
		} catch (ZestActionFailException e) {
			// Expected
		}
		
		ast.setTokenName("aaa");
		ast.setPrefix(null);
		ast.setPostfix("ccc");
		try {
			ast.invoke(resp);
			fail("Should have caused an exception");
		} catch (ZestActionFailException e) {
			// Expected
		}
		
		ast.setTokenName("aaa");
		ast.setPrefix("bbb");
		ast.setPostfix(null);
		try {
			ast.invoke(resp);
			fail("Should have caused an exception");
		} catch (ZestActionFailException e) {
			// Expected
		}
		
		ast.setTokenName("aaa");
		ast.setPrefix("xxx");
		ast.setPostfix("yyy");
		try {
			ast.invoke(resp);
			fail("Should have caused an exception");
		} catch (ZestActionFailException e) {
			// Expected
		}
		
	}

}
