package coronatracker;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import org.json.JSONObject;

public class Tracker {

	/*
	 * 1.データの取得
	 * 　※APIから日付と場所を指定しデータを取得する
	 * 2.データの加工
	 * 　Mapへ変換
	 * 　前日と当日の感染者数を取り出し、当日何人増えたか計算する
	 * 3.メール送信処理
	 * 4.タスクスケジュールで毎日実行するように設定
	 */
//	public static void main(String[] args) throws IOException, InterruptedException {
//		LocalDate date = LocalDate.now().minusDays(2);
//		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyyMMdd");
//		String formatedDate = myFormatObj.format(date);
//		System.out.println(formatedDate);
//
//		
//		Tracker tracker = new Tracker();
//		System.out.println(tracker.getData(formatedDate, "神奈川県"));
//		System.out.println(tracker.getMapDate(tracker.getData(formatedDate, "神奈川県")));
//		String test = "こんにちは \r\n世界";
//		System.out.println(test);
//
//		
//	}

	/**
	 * @param str
	 * @return
	 */
	public HashMap<String, String> getMapDate(String str) {
		HashMap<String, String> map = new HashMap<>();
		JSONObject obj = new JSONObject(str).getJSONArray("itemList").getJSONObject(0);
		map.put("number", obj.getString("npatients"));
		map.put("place", obj.getString("name_jp"));
		return map;
	}

//	public HashMap<String, String> getNumber(HttpResponse<String> response) throws JsonMappingException, JsonProcessingException {
//		Map<String, String> map = new HashMap<>();
//		JSONObject obj = new JSONObject(response.body()).getJSONArray("itemList").getJSONObject(0);
//		ObjectMapper mapper = new ObjectMapper();
//		System.out.println(obj.getString("npatients"));
//		
//		//map = mapper.readValue(response.body(), new TypeReference<Map<String,Object>>(){});
//		//mapper.(obj, new TypeReference<Map<String,Object>>(){});
//		mapper.writeValueAsString(obj);
//		System.out.println(obj);
//		map = mapper.readValue(obj, new TypeReference<Map<String,String>>(){});
//			return (HashMap<String, String>) map;
//	}

	/**
	 * @param date
	 * @param place
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public String getData(String date, String place) throws IOException, InterruptedException {
		var url = "https://opendata.corona.go.jp/api/Covid19JapanAll?date=" + date + "&dataName=" + place;
		System.out.println(url);

		var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();

		var client = HttpClient.newBuilder().build();

		var response = client.send(request, HttpResponse.BodyHandlers.ofString());
		return response.body();
	}

}
