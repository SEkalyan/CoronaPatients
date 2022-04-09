package sendreport;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import org.json.JSONObject;

/**
 * データを取得するクラス
 * @author kalyan_cr7
 *20220321
 */
public class Tracker {

	/**
	 * APIから取ってきたデータを整形する
	 * @param str　APIから取ってきたデータ
	 * @return　感染者数を場所別に取得しやすくしたデータ
	 */
	public HashMap<String, String> getMapDate(String str) {

		HashMap<String, String> map = new HashMap<>();

		JSONObject obj = new JSONObject(str).getJSONArray("itemList").getJSONObject(0);
		// 感染者数
		map.put("number", obj.getString("npatients"));
		// 場所
		map.put("place", obj.getString("name_jp"));

		return map;
	}

	/**
	 * @param date 取得日付
	 * @param place　取得場所
	 * @return　APIから引っ張ってきたデータ（JSONで取得する）
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
