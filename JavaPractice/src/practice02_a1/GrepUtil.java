  package practice02_a1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
		// TODO: この部分を実装する。メソッドは適宜分割のこと。

        // ファイル一覧取得（フォルダ除去）
		List<File> fileList = new ArrayList<File>();
		getFileList(dir, fileList);

		// Grepメイン処理の呼び出し
		return readFile(fileList, encoding, regex);
	}

	/**
	 * Grepメイン処理
	 * 
	 * @param fileList ファイルリスト
	 * @param encoding　
	 * @param regex
	 * @return
	 */
	public static List<GrepResult> readFile(List<File> fileList, String encoding, String regex) {

		// 返却用Grep結果リスト
		List<GrepResult> ret = new ArrayList<GrepResult>();

		// ファイルの数だけ、以下処理を行う
		for (File fileName : fileList) {

			// Grepの実施
			execGrep(fileName, encoding, regex, ret);
		}

		return ret;
	}
	
	/**
	 * パターン検索処理
	 * 
	 * 引数に与えられたファイルを先頭からEOFまで1行ずつ読み込む。<BR>
	 * 読み込んだ行に、検索文字と一致する行があるかチェックする。<BR>
	 * あればオブジェクトをnewし、必要な情報を設定したうえ、引数のリストへ追加する。<BR>
	 * なければ次の行を読み込む。<BR>
	 * 
	 * @param fileName Grep対象ファイル名
	 * @param encoding エンコーディング
	 * @param regex 検索文字列
	 * @param ret 返却用リスト
	 */
	public static void execGrep(File fileName, String encoding, String regex, List<GrepResult> ret) {

		try{

			FileInputStream fIs = new FileInputStream(fileName);
			InputStreamReader iSr = new InputStreamReader(fIs, encoding);
			BufferedReader iBr = new BufferedReader(iSr);

			// 入力ファイルから一行読み込む
			String oneLine = iBr.readLine();

			// EOFまで入力ファイルを1行ずつ読み込む
			for (int cnt = 0; oneLine != null; cnt++) {

				// matcherを生成
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(oneLine);

				while (m.find()) {
					
					// 一致条件がみつかった場合
					int start = m.start();
					int end = m.end();

					ret.add(new GrepResult(fileName, cnt+1, start, end, oneLine));
				}
				oneLine = iBr.readLine();
			}

			iBr.close();

		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	/**
	 * ファイル一覧取得<BR>
	 * 
	 * 引数に指定したパス配下に存在するファイルオブジェクト一覧を取得する。<BR>
	 * 取得したオブジェクトのうち種類がファイルのものだけ、引数のファイルリストへ追加する。<BR>
	 * 
	 * @param dir 検索開始ディレクトリ
	 * @param fileList ファイルリスト
	 */
	public static void getFileList(File dir, List<File> fileList) {

		// Fileオブジエクトを取得
		File[] files = dir.listFiles();

		// Fileオブジェクト存在チェック
		if(files == null) {

			// Fileオブジェクトが存在しない場合
			return;
		}

		// Fileオジェクトの数だけ、以下処理を実施
		for(File file : files) {

			if(file.isDirectory()) {

				// フォルダの場合、サブフォルダを検索
				getFileList(file , fileList);
			} else if(file.isFile()) {

				// ファイルの場合、リストへ追加
				fileList.add(file);
			}
		}
	}
}
