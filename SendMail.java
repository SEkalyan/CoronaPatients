package sendreport;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * メールを送信するクラス
 * @author kalyan_cr7
 *
 */
public class SendMail {

	public static void main(String[] args) throws IOException, InterruptedException {
		Tracker tracker = new Tracker();
		// 日付を取得
		LocalDate date = LocalDate.now().minusDays(3);
		// 前日の日付を取得
		LocalDate dateBefore = LocalDate.now().minusDays(4);

		// メール本文用
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
		String formatedDate = myFormatObj.format(date);

		// データ取得用にフォーマットを変換
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
		// 三日前
		String formatDateToday = myFormat.format(date);
		// 四日前
		String formatDateYesterday = myFormat.format(dateBefore);

		// 東京の感染者数を取得（三日前）
		String response = tracker.getData(formatDateToday, "東京都");
		HashMap<String, String> data = tracker.getMapDate(response);

		// 東京の感染者数を取得（四日前）
		String responseYesterday = tracker.getData(formatDateYesterday, "東京都");
		HashMap<String, String> dataYesterday = tracker.getMapDate(responseYesterday);

		// 増えた感染者数
		int increase = Integer.parseInt(data.get("number")) -Integer.parseInt(dataYesterday.get("number"));

		// メール本文の作成
		String msg = "おはようございます。\r\n"
				+ formatedDate + "のコロナ感染者数は以下になります。\r\n"
				+ "------------------------------ \r\n"
				+ "場所：" + data.get("place") + "\r\n"
				+ "感染者数：" + data.get("number") + "人\r\n"
				+ "前日：" + dataYesterday.get("number") + "\r\n"
				+ "増加人数：" + increase + "\r\n"
				+ "------------------------------"
				+ "\r\n"
				+ "ありがとうございます。";

		// 確認用
		System.out.println(msg);

		// メールを送信
		Mailer.sendEmail("送信元アドレス","パスワード","送信先アドレス","件名", "メッセージ本文");
		
	}

}
