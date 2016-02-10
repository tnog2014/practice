package practice01_a2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * レポート作成クラス.
 *
 * CSVファイルを読み込んで、顧客ＩＤごとの利用金額合計を計算する。
 * レポートファイルに、顧客ＩＤと利用金額の一覧を表示する。尚、顧客ＩＤはアルファベット順にソートした状態とする。
 *
 * @author hanashima
 *
 */
public class ReportGenerator {


	/**
	 * レポートを作成する.
	 *
	 * @param inputCsvPath 入力ファイルパス
	 * @param reportFilePath レポートファイルパス
	 */
	public void create(String inputCsvPath, String reportFilePath) throws IOException {

		try {
			// 入力ファイル読込み
			File inputfile = new File(inputCsvPath);
			BufferedReader br = new BufferedReader(new FileReader(inputfile));
			List<DataClass> inputDataList = new ArrayList<DataClass>();

			String line = "";
			while ((line = br.readLine()) != null) {

				if (line.substring(0, 1).equals("#")) {
					continue;
				}

				String array[] = line.split("\t");

				DataClass data = new DataClass();
				data.setCustomerId(array[0]);
				data.setStoreId(array[1]);
				data.setAmount(Integer.parseInt(array[2]));

				inputDataList.add(data);
			}

			// ソート処理
			Collections.sort(inputDataList);

			List<DataClass> outputDataList = new ArrayList<DataClass>();
			int tmpAmount = 0;
			String tmpCustomerId = "";
			boolean flg = false;

			for (int i = 0; i < inputDataList.size(); i++) {
				if (i == 0) {
					// 1行目
					tmpCustomerId = inputDataList.get(i).getCustomerId();
					tmpAmount = inputDataList.get(i).getAmount();
				} else if (i == (inputDataList.size() - 1)) {
					// 最終行
					tmpAmount = tmpAmount + inputDataList.get(i).getAmount();
					flg = true;
				} else {
					if (inputDataList.get(i).getCustomerId().equals(tmpCustomerId)) {
						tmpAmount = tmpAmount + inputDataList.get(i).getAmount();
					} else {
						flg = true;
					}
				}
				if (flg) {
					DataClass data = new DataClass();
					data.setCustomerId(tmpCustomerId);
					data.setAmount(tmpAmount);
					outputDataList.add(data);
					tmpCustomerId = inputDataList.get(i).getCustomerId();
					tmpAmount = inputDataList.get(i).getAmount();
					flg = false;
				}
			}

			// レポートファイル出力
			File outputfile = new File(reportFilePath);
			BufferedWriter bw = new BufferedWriter(new FileWriter(outputfile));

			for (int i = 0; i < outputDataList.size(); i++) {
				bw.write(outputDataList.get(i).getCustomerId()
						+ "\t" + String.valueOf(outputDataList.get(i).getAmount()));
				bw.newLine();
			}

			br.close();
			bw.close();

		} catch (Exception e) {
			throw new IOException("レポート作成に失敗しました[data/practice01/inputNG.csv]");
		}

	}

	private static class DataClass implements Comparable<DataClass> {

		private String customerId;	// 顧客ID
		private String storeId;		// 店舗ID
		private int amount;			// 利用金額

		@Override
		public int compareTo(DataClass other) {
			int result = this.customerId.compareTo(other.customerId);
			return result;
		}

		public String getCustomerId() {
			return customerId;
		}

		public void setCustomerId(String customerId) {
			this.customerId = customerId;
		}

		public String getStoreId() {
			return storeId;
		}

		public void setStoreId(String storeId) {
			this.storeId = storeId;
		}

		public int getAmount() {
			return amount;
		}

		public void setAmount(int amount) {
			this.amount = amount;
		}

	}

}
