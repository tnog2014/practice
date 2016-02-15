package practice01_a3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.TreeMap;

/**
 * レポート作成クラス.
 *
 * CSVファイルを読み込んで、顧客ＩＤごとの利用金額合計を計算する。
 * レポートファイルに、顧客ＩＤと利用金額の一覧を表示する。尚、顧客ＩＤはアルファベット順にソートした状態とする。
 *
 * <pre>
 * ポイント
 * ・リソースを閉じる（異常時にもきちんとクローズするように実装）
 * ・定数化
 * ・適切な処理単位でメソッドに分割
 * ・単純なロジックで処理を記述
 * ・わかりやすい変数名を付ける（機械的につけているところは除く。forループのインデックスなど）
 * ・共通処理のメソッド化
 * </pre>
 * 
 * @author tnoguchi
 *
 */
public class ReportGenerator {

	/** エラーメッセージ（レポート作成失敗）. */
	private static final String ERROR_FAILURE_IN_MAKING_REPORT = "レポート作成に失敗しました[%s]";

	/** コメント行開始文字. */
	private static final String LINE_COMMENT_CHAR = "#";

	/** タブ文字. */
	private static final String TAB = "\t";

	/** 入力ファイルエンコーディング. */
	private static final String INPUT_ENCODING = "UTF8";

	/** 出力ファイルエンコーディング. */
	private static final String OUTPUT_ENCODING = "UTF8";

	/**
	 * レポートを作成する.
	 *
	 * @param inputCsvPath 入力ファイルパス
	 * @param reportFilePath レポートファイルパス
	 */
	public void create(String inputCsvPath, String reportFilePath) throws IOException {

		try {
			// CSVから顧客ID-合計金額マップを作成
			Map<String, Integer> amountMap = readToAmountMap(inputCsvPath);

			// 顧客ID-合計金額マップをファイルに出力
			writeAmountMapToFile(amountMap, reportFilePath);
		} catch (IOException ioe) {
			throw new IOException(String.format(ERROR_FAILURE_IN_MAKING_REPORT, inputCsvPath), ioe);
		}
	}

	/**
	 * データCSVから顧客ID-合計金額マップを生成する.
	 *
	 * @param inputCsvPath 入力ファイルパス
	 * @return 顧客ID-合計金額マップ
	 * @throws IOException 例外
	 */
	private Map<String, Integer> readToAmountMap(String inputCsvPath) throws IOException {
		// 顧客ID-合計金額マップ（顧客IDで昇順ソートするため、TreeMapを利用）
		Map<String, Integer> amountMap = new TreeMap<String, Integer>();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(new File(inputCsvPath));
			isr = new InputStreamReader(fis, INPUT_ENCODING);
			br = new BufferedReader(isr);

			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.trim().startsWith(LINE_COMMENT_CHAR)) {
					continue;
				}
				String[] items = line.split(TAB);

				// 顧客ID
				String customerId = items[0];

				// 利用金額
				int amount = Integer.parseInt(items[2]);

				// 顧客IDが初出の場合は、顧客ID-合計金額マップに合計金額0円を追加。
				if (!amountMap.containsKey(customerId)) {
					amountMap.put(customerId, 0);
				}
				// 対象顧客IDの合計金額に利用金額を加算する。
				amountMap.put(customerId, amountMap.get(customerId) + amount);
			}
			return amountMap;
		} finally {
			closeQuietly(br);
			closeQuietly(isr);
			closeQuietly(fis);
		}
	}

	/**
	 * 顧客ID-合計金額マップをファイルに出力する.
	 *
	 * @param amountMap 顧客ID-合計金額マップ
	 * @param reportFilePath レポート出力パス
	 * @throws IOException 例外
	 */
	private void writeAmountMapToFile(Map<String, Integer> amountMap, String reportFilePath)
			throws IOException {
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			fos = new FileOutputStream(new File(reportFilePath));
			osw = new OutputStreamWriter(fos, OUTPUT_ENCODING);
			bw = new BufferedWriter(osw);
			for (Map.Entry<String, Integer> entry : amountMap.entrySet()) {
				bw.write(entry.getKey());
				bw.write(TAB);
				bw.write(Integer.toString(entry.getValue()));
				bw.newLine();
			}

		} finally {
			closeQuietly(bw);
			closeQuietly(osw);
			closeQuietly(fos);
		}
	}

	/**
	 * リソースを閉じる.
	 *
	 * @param closeable クローズ対象リソース
	 */
	private void closeQuietly(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException ioe) {
				// 処理を行わない。
			}
		}
	}
}
