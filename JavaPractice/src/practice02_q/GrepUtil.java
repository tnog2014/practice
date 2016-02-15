package practice02_q;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
		// TODO: この部分を実装する。メソッドは適宜分割のこと。
		return null;
	}

}
