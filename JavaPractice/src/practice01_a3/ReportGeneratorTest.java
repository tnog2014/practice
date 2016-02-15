package practice01_a3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class ReportGeneratorTest {

	@Test
	public void test_レポートファイルの作成() throws IOException {
		ReportGenerator reportGenerator = new ReportGenerator();
		reportGenerator.create("data/practice01/input.csv", "data/practice01/report.txt");
		assertFiles("作成したレポートの比較",
				new File("data/practice01/expectedReport.txt"),
				new File("data/practice01/report.txt"));
	}

	@Test
	public void test_レポートファイルの作成に失敗() throws IOException {
		ReportGenerator reportGenerator = new ReportGenerator();
		try {
			reportGenerator.create("data/practice01/inputNG.csv", "data/practice01/report.txt");
			fail("想定する例外が発生しませんでした。");
		} catch (IOException ioe) {
			assertEquals("例外クラスの比較", IOException.class, ioe.getClass());
			assertEquals("エラーメッセージの比較", "レポート作成に失敗しました[data/practice01/inputNG.csv]",
					ioe.getMessage());
		}
	}

	/**
	 * ファイル内容比較.
	 *
	 * @param message メッセージ
	 * @param expected 想定ファイル
	 * @param actual 結果ファイル
	 * @throws IOException 例外
	 */
	private void assertFiles(String message, File expected, File actual) throws IOException {
		String actualReport = FileUtils.readFileToString(actual);
		String expectedReport = FileUtils.readFileToString(expected);
		assertEquals(message, expectedReport, actualReport);
	}
}
