import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

public class FreeBaseAPI {
	public static Properties properties = new Properties();

	public void infobox(String query) {
		try {
			// getFBKey();
			JSONArray topics = searchFB(query);
			for (int i = 0; i < topics.length(); i++) {
				String mid = topics.getJSONObject(i).getString("mid");
				int ret = topicFB(mid);
				if (ret == 1)
					break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void getFBKey() {
		try {
			properties.load(new FileInputStream(
					"../Properties/freebase.properties"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private JSONArray searchFB(String query) {
		JSONArray results = null;
		try {
			HttpTransport httpTransport = new NetHttpTransport();
			HttpRequestFactory requestFactory = httpTransport
					.createRequestFactory();
			GenericUrl url = new GenericUrl(
					"https://www.googleapis.com/freebase/v1/search");
			url.put("query", URLEncoder.encode(query, "UTF-8"));
			// url.put("key", properties.get("API_KEY"));
			HttpRequest request = requestFactory.buildGetRequest(url);
			HttpResponse httpResponse = request.execute();
			JSONObject response = new JSONObject(new JSONTokener(
					httpResponse.parseAsString()));
			//System.out.println(response.toString());
			results = response.getJSONArray("result");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return results;
	}

	private int topicFB(String mid) {
		try {
			HttpTransport httpTransport = new NetHttpTransport();
			HttpRequestFactory requestFactory = httpTransport
					.createRequestFactory();
			GenericUrl url = new GenericUrl(
					"https://www.googleapis.com/freebase/v1/topic" + mid);
			HttpRequest request = requestFactory.buildGetRequest(url);
			HttpResponse httpResponse = request.execute();
			JSONObject response = new JSONObject(new JSONTokener(
					httpResponse.parseAsString()));
			System.out.println(response.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 1;
	}
}
