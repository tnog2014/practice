package practice01_a1; 

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException; 
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
  
/** 
* レポート作成クラス. 
* 
* CSVファイルを読み込んで、顧客ＩＤごとの利用金額合計を計算する。 
* レポートファイルに、顧客ＩＤと利用金額の一覧を表示する。尚、顧客ＩＤはアルファベット順にソートした状態とする。  * 
* @author htsukakoshi
* 
*/ 
public class ReportGenerator { 

	/** 
	 * メソッド名：レポート作成<BR>
	 * メソッド概要：<BR>
	 * 入力ファイルを読み込み、コメント行を除いたデータを顧客ID順にソート（昇順）する。<BR>
	 * 返却用データ作成処理を呼び出し、顧客IDと金額のリストを取得する。<BR>
	 * 顧客IDと金額をセットに出力ファイルへ結果を出力する。<BR>
	 * <BR>
	 * 
	 * @param inputCsvPath 入力ファイルパス
	 * @param reportFilePath 出力ファイルパス
	 * @throws IOException 入出力ファイルエラーの場合
	 */
	public void create(String inputCsvPath, String reportFilePath) throws IOException { 

		List<String> kokyakuLst = new ArrayList<String>();
		List<Integer> kingakuLst = new ArrayList<Integer>();

		try{

			File inFile = new File(inputCsvPath);
			BufferedReader iBr = new BufferedReader(new FileReader(inFile));

			//　入力ファイル内容一時格納用リスト
			List<String> tmpLst = new ArrayList<String>();

			// コメント行対応
			String regex = "^#";
			Pattern p = Pattern.compile(regex);

			String oneLine = iBr.readLine();

			// EOFまで入力ファイルを1行ずつ読み込む
			while(oneLine != null){

				oneLine = iBr.readLine();

				// 読み込み行をチェック
				if(oneLine != null && !p.matcher(oneLine).find()) {

					// EOF、または、コメント行以外を一時格納用リストへ追加
					tmpLst.add(oneLine);
				}
			}

			iBr.close();

			// 一時格納用リストを顧客ＩＤでソート（昇順）
			Collections.sort(tmpLst);

			// 返却用データ作成
			kingaku(tmpLst, kokyakuLst, kingakuLst);

			File outFile = new File(reportFilePath);
			BufferedWriter oBw = new BufferedWriter(new FileWriter(outFile));

			// 結果ファイル出力
			for (int cnt = 0; cnt < kokyakuLst.size(); cnt++) {

				// 顧客ID＋タブ＋金額をファイル出力
				oBw.write(kokyakuLst.get(cnt) + "\t" + kingakuLst.get(cnt));

				if (cnt != kokyakuLst.size()-1) {

					// 改行ードをファイル出力（最終行は除く）コ
					oBw.newLine();
				}
			}

			oBw.close();
		} catch (FileNotFoundException e) {

			throw new IOException("レポート作成に失敗しました["+ inputCsvPath +"]");
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}
	} 

	/**
	 * メソッド名：返却データ作成<BR>
	 * メソッド概要：<BR>
	 * 対象リストから顧客IDと金額を抽出する。<BR>
	 * 顧客IDごとの合計金額を算出し、各出力リストへ設定する。<BR>
	 * <BR>
	 * 
	 * @param tmpLst 入力リスト
	 * @param kokyakuLst 出力顧客リスト
	 * @param kingakuLst 出力金額リスト
	 */
	private void kingaku(List<String> tmpLst, List<String> kokyakuLst, List<Integer> kingakuLst) {

		int dataCnt = -1;
		String kyakuTmp = "";
		int gakuTmp = 0;
		int tmpKingaku = 0;

		// ファイルから抽出した行数分、以下処理を行う。
		for (String lineData : tmpLst) {

			String[] lineTmp = lineData.split("\\t");
			kyakuTmp = lineTmp[0];
			gakuTmp = Integer.parseInt(lineTmp[2]);

			if (dataCnt == -1) {

				// 初期処理：顧客IDを追加
				kokyakuLst.add(kyakuTmp);
				dataCnt = 0;
			}

			// 前回の顧客IDと比較
			if (!kyakuTmp.equals(kokyakuLst.get(dataCnt))) {

				// これまでの合計金額を、返却用金額リストへ追加
				kingakuLst.add(tmpKingaku);

				// 金額一時領域をクリア
				tmpKingaku = 0;

				// 新規顧客IDを追加
				kokyakuLst.add(kyakuTmp);

				// データカウントをインクリメント
				dataCnt++;
			}

			// 金額一時領域へ金額を加算
			tmpKingaku += gakuTmp;
		}

		// 後処理：これまでの合計金額を、（最後の）返却用金額リストへ追加
		kingakuLst.add(tmpKingaku);
	}
} 
