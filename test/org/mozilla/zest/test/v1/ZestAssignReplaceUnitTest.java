/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.zest.test.v1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.mozilla.zest.core.v1.ZestAssignReplace;
import org.mozilla.zest.core.v1.ZestJSON;
import org.mozilla.zest.core.v1.ZestResponse;
import org.mozilla.zest.core.v1.ZestVariables;


/**
 */
@RunWith(MockitoJUnitRunner.class)
public class ZestAssignReplaceUnitTest {


	/**
	 * Method testSimpleCase.
	 * @throws Exception
	 */
	@Test
	public void testSimpleCase() throws Exception {
		ZestAssignReplace ast = new ZestAssignReplace();
		ZestResponse resp = new ZestResponse(null, "Header prefix12345postfix", "Body Prefix54321Postfix", 200, 0);
		TestRuntime rt = new TestRuntime(resp);

		ast.setVariableName(ZestVariables.RESPONSE_HEADER);
		ast.setReplace("12345");
		ast.setReplacement("ABC");
		assertEquals ("Header prefixABCpostfix", ast.assign(resp, rt));
		
		ast.setVariableName(ZestVariables.RESPONSE_BODY);
		ast.setReplace("54321");
		ast.setReplacement("ZZZZ");
		assertEquals ("Body PrefixZZZZPostfix", ast.assign(resp, rt));

		
		ast.setVariableName(ZestVariables.RESPONSE_HEADER);
		ast.setReplace("1.*5");
		ast.setReplacement("ABC");
		ast.setRegex(true);
		assertEquals ("Header prefixABCpostfix", ast.assign(resp, rt));

	}

	@Test
	public void testSerialization() {
		ZestAssignReplace assign = new ZestAssignReplace("var", "aaa", "bbb", false, true);
		
		String str = ZestJSON.toString(assign);
		
		ZestAssignReplace assign2 = (ZestAssignReplace) ZestJSON.fromString(str);
		
		assertEquals(assign.getElementType(), assign2.getElementType());
		assertEquals(assign.getVariableName(), assign2.getVariableName());
		assertEquals(assign.getReplace(), assign2.getReplace());
		assertEquals(assign.getReplacement(), assign2.getReplacement());
		assertEquals(assign.isCaseExact(), assign2.isCaseExact());
		assertEquals(assign.isRegex(), assign2.isRegex());
	}

}
