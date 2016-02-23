package practice02_a2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * フォルダ・ファイルに対するGrep処理.
 */
public class GrepUtil {

	/**
	 * 正規表現Grep処理.
	 *
	 * 指定したフォルダ配下のすべてのファイル（サブフォルダ内のファイルなども含む）に対して、
	 * 正規表現で検索を行い、検索結果リストとして結果を返す。
	 *
	 * @param dir Grep対象フォルダ
	 * @param encoding エンコーディング
	 * @param regex 正規表現文字列
	 * @return Grep結果リスト
	 *
	 * @throws IOException 例外
	 */
	public static List<GrepResult> grep(File dir, String encoding, String regex) throws IOException {

		// 指定フォルダ配下のファイルリストを取得
		List<File> fileList = new ArrayList<File>();
		List<GrepResult> resultList = new ArrayList<GrepResult>();

		// grep対象ファイル検索
		find(dir, fileList);

		// 対象のファイル数分grepを行う
		for (int i = 0; i < fileList.size(); i++) {
			grepCheck(fileList.get(i), encoding, regex, resultList);
		}

		return resultList;
	}

	/**
	 * grep対象ファイル検索
	 *
	 * @param targetDir 検索対象フォルダ
	 * @param fileList 検索結果格納用リスト
	 */
	private static void find(File targetDir, List<File> fileList) {
		File[] files = targetDir.listFiles();

		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.isDirectory()) {
				find(file, fileList);
			} else {
				fileList.add(file);
			}
		}
	}

	/**
	 * grep処理
	 *
	 * @param file grep対象ファイル
	 * @param encoding 文字コード
	 * @param regex 正規表現パターン
	 * @param resultList grep結果格納用リスト
	 * @throws IOException
	 */
	public static void grepCheck(
			File file, String encoding, String regex, List<GrepResult> resultList) throws IOException {

		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis, encoding);
		BufferedReader br = new BufferedReader(new FileReader(file));
		Pattern pattern = Pattern.compile(regex);
		String line = "";
		int row = 1;

		try {

			while ((line = br.readLine()) != null) {
				Matcher matcher = pattern.matcher(line);
				// hitしなくなるまで繰り返し
				while (matcher.find()) {
					GrepResult grepResult =
							new GrepResult(file, row, matcher.start(), matcher.end(), line);
					resultList.add(grepResult);
				}

				row++;
			}


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			br.close();
			isr.close();
			fis.close();
		}

	}

}
