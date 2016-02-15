package practice01_a4;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;

/**
 * レポート作成クラス.
 *
 * CSVファイルを読み込んで、顧客ＩＤごとの利用金額合計を計算する。
 * レポートファイルに、顧客ＩＤと利用金額の一覧を表示する。尚、顧客ＩＤはアルファベット順にソートした状態とする。
 *
 * <pre>
 * 一時利用するツールとして実装する場合の例
 *  ・ファイルアクセスにはOSSのライブラリ（Apache Commons IO）を利用して、手で書くソースを削減。
 * 
 * 以下の潜在的な問題点があるが、利用目的によっては問題ない：
 *  ・入力データをList<String>に格納するため、大量データの処理時に利用メモリ大
 *  ・ファイル出力前にStringBufferに格納するため、大量データの処理時に利用メモリ大
 * </pre>
 *
 * @author tnoguchi
 *
 */
public class ReportGenerator {

	/** エラーメッセージ（レポート作成失敗）. */
	private static final String ERROR_FAILURE_IN_MAKING_REPORT = "レポート作成に失敗しました[%s]";

	/**
	 * レポートを作成する.
	 *
	 * @param inputCsvPath 入力ファイルパス
	 * @param reportFilePath レポートファイルパス
	 */
	public void create(String inputCsvPath, String reportFilePath) throws IOException {
		try {
			List<String> lines = FileUtils.readLines(new File(inputCsvPath), "UTF8");
			Map<String, Integer> amountMap = new TreeMap<String, Integer>();

			for (String line : lines) {
				if (line.trim().startsWith("#")) {
					continue;
				}
				String[] items = line.split("\t");
				String customerId = items[0];
				int amount = Integer.parseInt(items[2]);
				if (!amountMap.containsKey(customerId)) {
					amountMap.put(customerId, 0);
				}
				amountMap.put(customerId, amountMap.get(customerId) + amount);
			}
			StringBuffer sb = new StringBuffer();
			for (Map.Entry<String, Integer> entry : amountMap.entrySet()) {
				sb.append(entry.getKey()).append("\t").append(entry.getValue()).append("\r\n");
			}

			FileUtils.writeStringToFile(new File(reportFilePath), sb.toString(), "UTF8");
		} catch (IOException ioe) {
			throw new IOException(String.format(ERROR_FAILURE_IN_MAKING_REPORT, inputCsvPath));
		}
	}

}
