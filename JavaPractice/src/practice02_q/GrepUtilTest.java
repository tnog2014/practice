package practice02_q;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

/**
 * フォルダ・ファイルに対するGrep処理テスト.
 */
public class GrepUtilTest {

	@Test
	public void test() throws IOException {
		File dir = new File("data/practice02");
		List<GrepResult> result = GrepUtil.grep(dir, "UTF8", "\".*?\"");

		Object[][] expected = {
				{"Department.java", 5, 21, 23},
				{"Department.java", 7, 26, 28},
				{"Employee.java", 5, 21, 23},
				{"Employee.java", 7, 23, 25},
				{"Employee.java", 9, 26, 28},
				{"Employee.java", 11, 31, 35},
				{"Employee.java", 11, 46, 50},
				{"Main.java", 9, 13, 20},
				{"Main.java", 10, 16, 24},
				{"Main.java", 11, 21, 25}
		};

		assertEquals("結果件数", expected.length, result.size());
		for (int i = 0; i < result.size(); i++) {
			System.out.println(result.get(i));
			assertGrepResult(expected[i], result.get(i));
		}

	}

	/**
	 * 検索結果アサーション.
	 *
	 * @param expected 想定結果
	 * @param actual 検索結果
	 */
	private void assertGrepResult(Object[] expected, GrepResult actual) {
		String expectedPath = (String) expected[0];
		int expectedLineNo = (Integer) expected[1];
		int expectedStart = (Integer) expected[2];
		int expectedEnd = (Integer) expected[3];
		assertEquals(expectedPath, actual.getFile().getName());
		assertEquals(expectedLineNo, actual.getLineNo());
		assertEquals(expectedStart, actual.getStart());
		assertEquals(expectedEnd, actual.getEnd());
	}
}
