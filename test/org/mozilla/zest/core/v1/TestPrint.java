/**
/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * @author Alessandro Secco: seccoale@gmail.com
 */
package mozilla.zest.core.v1;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import org.mozilla.zest.core.v1.ZestConditional;
import org.mozilla.zest.core.v1.ZestExpressionAnd;
import org.mozilla.zest.core.v1.ZestExpressionLength;
import org.mozilla.zest.core.v1.ZestExpressionOr;
import org.mozilla.zest.core.v1.ZestExpressionResponseTime;
import org.mozilla.zest.core.v1.ZestExpressionStatusCode;
import org.mozilla.zest.core.v1.ZestLoopFile;
import org.mozilla.zest.core.v1.ZestLoopInteger;
import org.mozilla.zest.core.v1.ZestLoopString;
import org.mozilla.zest.core.v1.ZestStatement;
import org.mozilla.zest.impl.ZestPrinter;

/**
 */
public class TestPrint {
	/**
	 * Method main.
	 * @param args String[]
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException{
		System.out.println("---------------------");
		System.out.println("ZestComplexExpression");
		System.out.println("---------------------");
		ZestExpressionOr or=new ZestExpressionOr();
		or.addChildCondition(new ZestExpressionLength(10, 20));
		or.addChildCondition(new ZestExpressionStatusCode(200));
		ZestExpressionAnd and=new ZestExpressionAnd();
		and.addChildCondition(new ZestExpressionLength(100,200));
		and.addChildCondition(new ZestExpressionResponseTime(1000));
		and.addChildCondition(or);
		and.addChildCondition(and.deepCopy());
		ZestPrinter.printExpression(and, -1);
		System.out.println("---------------------");
		System.out.println("    ZestLoopString");
		System.out.println("---------------------");
		String[] values={"a","b","c"};
		List<ZestStatement> statements=new LinkedList<>();
		statements.add(new ZestConditional(or));
		statements.add(new ZestConditional(and));
		ZestLoopString loopString=new ZestLoopString(values,statements);
		ZestPrinter.list(loopString, -1);
		System.out.println("---------------------");
		System.out.println("    ZestLoopFile");
		System.out.println("---------------------");
		ZestLoopFile loopFile=new ZestLoopFile(ZestLoopFileUnitTest.file, statements);
		ZestPrinter.list(loopFile, -1);
		System.out.println("---------------------");
		System.out.println("   ZestLoopInteger");
		System.out.println("---------------------");
		ZestLoopInteger loopInteger=new ZestLoopInteger(0, 1458, statements);
		ZestPrinter.list(loopInteger, -1);
	}
}
