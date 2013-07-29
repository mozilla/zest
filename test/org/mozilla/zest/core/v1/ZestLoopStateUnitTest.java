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
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.mozilla.zest.core.v1.ZestLoopStateString;
import org.mozilla.zest.core.v1.ZestLoopTokenStringSet;

@RunWith(MockitoJUnitRunner.class)
public class ZestLoopStateUnitTest {
	String[] values = { "A", "B", "C", "D", "D" };
	ZestLoopTokenStringSet set = new ZestLoopTokenStringSet(values);

	@Test
	public void testZestLoopState() {
		ZestLoopStateString state = new ZestLoopStateString(set);
		assertFalse(state.isLastState());
	}

	// @Test(expected=IllegalArgumentException.class)
	// public void testZestLoopStateNull(){
	// new ZestLoopStateString(null);
	// fail();
	// }

	@Test
	public void testGetCurrentToken() {
		int index = 0;
		ZestLoopStateString state = new ZestLoopStateString(set);
		assertTrue(state.getCurrentToken().equals(values[index]));
	}

	@Test
	public void testGetCurrentIndex() {
		int index = 3;
		ZestLoopStateString state = new ZestLoopStateString(set);
		for (int i = 0; i < index; i++) {
			state.increase();
		}
		assertTrue(state.getCurrentIndex() == index);
	}

	@Test
	public void testIncrease() {
		ZestLoopStateString state = new ZestLoopStateString(set);
		int currentIndex = state.getCurrentIndex();
		boolean increasable = state.increase();
		if (!increasable) {
			fail();
		}
		int newIndex = state.getCurrentIndex();
		assertTrue(currentIndex + 1 == newIndex
				&& values[newIndex].equals(state.getCurrentToken()));
	}

	@Test
	public void testToLastState() {
		ZestLoopStateString state = new ZestLoopStateString(set);
		state.toLastState();
		assertTrue(state.isLastState());
	}

	@Test
	public void testDeepCopy() {
		ZestLoopStateString state = new ZestLoopStateString(set);
		ZestLoopStateString copy = (ZestLoopStateString) state.deepCopy();
		if (state.getCurrentIndex() != copy.getCurrentIndex()) {
			fail("Not same index!");
		}
		for (int i = 0; i < 0; i++) {
			String expected = state.getTokenSet().getToken(i);
			String obtained = copy.getTokenSet().getToken(i);
			String msg = i + " obtained " + obtained + " instead of " + expected;
			assertTrue(msg, expected.equals(obtained));
		}
	}

	@Test
	public void testIsLastState() {
		ZestLoopStateString state = new ZestLoopStateString(set);
		for (int i = 0; i < 13; i++) {
			state.increase();
			if (state.getCurrentIndex() == set.size()) {
				assertTrue(state.isLastState());
				break;
			}
		}
	}

}
