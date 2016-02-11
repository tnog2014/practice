package practice02_q;

import java.io.File;

/**
 * 検索結果.
 *
 * 例）行
 * abcdefg
 * に対して、"c.e"という正規表現で検索を行った場合、開始インデックスは2、終了インデックスは4になる。
 */
public class GrepResult {

	/** ファイル. */
	private File file;

	/** 行番号. */
	private int lineNo;

	/** 検索結果文字列開始インデックス. */
	private int start;

	/** 検索結果文字列終了インデックス. */
	private int end;

	/** 行内容. */
	private String line;

	/**
	 * コンストラクタ.
	 *
	 * @param file ファイル
	 * @param lineNo 行番号
	 * @param start 開始インデックス
	 * @param end 終了インデックス
	 * @param line 行内容
	 */
	public GrepResult(File file, int lineNo, int start, int end, String line) {
		this.file = file;
		this.lineNo = lineNo;
		this.start = start;
		this.end = end;
		this.line = line;
	}

	/**
	 * ファイルを取得.
	 *
	 * @return ファイル
	 */
	public File getFile() {
		return file;
	}

	/**
	 * 行番号を取得.
	 *
	 * @return 行番号
	 */
	public int getLineNo() {
		return lineNo;
	}

	/**
	 * 開始インデックスを取得.
	 *
	 * @return 開始インデックス
	 */
	public int getStart() {
		return start;
	}

	/**
	 * 終了インデックスを取得.
	 *
	 * @return 終了インデックス
	 */
	public int getEnd() {
		return end;
	}

	/**
	 * 行内容を取得.
	 *
	 * @return 行内容
	 */
	public String getLine() {
		return line;
	}

	/**
	 * 文字列表現に変換.
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(file.getAbsolutePath()).append(":")
				.append(lineNo).append(",")
				.append(start).append(",")
				.append(end).append(":")
				.append(line);
		return sb.toString();
	}

}
