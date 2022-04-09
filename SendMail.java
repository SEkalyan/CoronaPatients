package coronatracker;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class SendMail {

	public static void main(String[] args) throws IOException, InterruptedException {
		Tracker tracker = new Tracker();
		// 日付を取得
		LocalDate date = LocalDate.now().minusDays(2);
		// 前日の日付を取得
		LocalDate dateBefore = LocalDate.now().minusDays(3);
		
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
		String formatDateToday = myFormat.format(date);
		String formatDateYesterday = myFormat.format(dateBefore);
		String formatedDate = myFormatObj.format(date);

		String response = tracker.getData(formatDateToday, "東京都");
		HashMap<String, String> data = tracker.getMapDate(response);

		String responseYesterday = tracker.getData(formatDateYesterday, "東京都");
		HashMap<String, String> dataYesterday = tracker.getMapDate(responseYesterday);
		int increase = Integer.parseInt(data.get("number")) -Integer.parseInt(dataYesterday.get("number"));

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

		     //from,password,to,subject,message  
			System.out.println(msg);
		     Mailer.send("kalyanbc12345@gmail.com","vwdbwscwdsxtznae","kalyanbc12345@gmail.com","Babe!! ", msg);  
		
	}

}
