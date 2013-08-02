/**
/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * @author Alessandro Secco: seccoale@gmail.com
 */
package mozilla.zest.core.v1;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.mozilla.zest.core.v1.ZestExpressionResponseTime;
import org.mozilla.zest.core.v1.ZestResponse;
/**
 */
@RunWith(MockitoJUnitRunner.class)
public class ZestExpressionResponseTimeUnitTest {

	@Test
	public void testSetTimeInMs() {
		long time=1000;
		ZestExpressionResponseTime timeExpr=new ZestExpressionResponseTime(10);
		timeExpr.setTimeInMs(time);
		assertTrue(time==timeExpr.getTimeInMs());
	}

	@Test
	public void testDeepCopy() {
		ZestExpressionResponseTime timeExpr=new ZestExpressionResponseTime(1000);
		ZestExpressionResponseTime copy=timeExpr.deepCopy();
		assertTrue(copy.getTimeInMs()==timeExpr.getTimeInMs());
	}
	@Test
	public void testDeepCopyNoPointers() {
		ZestExpressionResponseTime timeExpr=new ZestExpressionResponseTime(1000);
		ZestExpressionResponseTime copy=timeExpr.deepCopy();
		timeExpr.setGreaterThan(false);
		timeExpr.setTimeInMs(5);
		assertFalse(copy.getTimeInMs()==timeExpr.getTimeInMs() || copy.isGreaterThan()==timeExpr.isGreaterThan());
	}
	@Test
	public void testEvaluate(){
		ZestExpressionResponseTime timeExpr=new ZestExpressionResponseTime(1000);
		timeExpr.setGreaterThan(false);
		ZestResponse response=new ZestResponse(null, "", "", 100, 10);
		assertTrue(timeExpr.evaluate(response));
	}
	@Test
	public void testIsTrueGreaterThan(){
		ZestExpressionResponseTime timeExpr=new ZestExpressionResponseTime(0);
		timeExpr.setGreaterThan(true);
		ZestResponse response=new ZestResponse(null, "", "", 200, 100);
		assertTrue(timeExpr.isTrue(response));
	}
	
	@Test
	public void testEvaluateGreaterThanIsInverse(){
		ZestExpressionResponseTime timeExpTime=new ZestExpressionResponseTime(1000);
		ZestResponse response=new ZestResponse(null, "", "", 200, 1000);
		timeExpTime.setInverse(true);
		timeExpTime.setGreaterThan(true);
		assertTrue(timeExpTime.evaluate(response));
	}
	@Test
	public void testDeepCopyNoPointer(){
		ZestExpressionResponseTime timeExpr=new ZestExpressionResponseTime(100);
		ZestExpressionResponseTime copy=timeExpr.deepCopy();
		timeExpr.setTimeInMs(50);
		assertTrue(timeExpr.getTimeInMs()!=copy.getTimeInMs());
	}
	public void testIsTrueDeepCopyDifferentGreaterThan(){
		ZestExpressionResponseTime timeExpr=new ZestExpressionResponseTime(1000);
		ZestExpressionResponseTime copy=timeExpr.deepCopy();
		timeExpr.setGreaterThan(false);
		ZestResponse  response=new ZestResponse(null, "", "", 100, 10);
		assertTrue(timeExpr.isTrue(response) && ! copy.isTrue(response));
	}
}
