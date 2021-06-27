package it.espr.utils.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringHttpClient implements HttpClient<String> {

	private static final Logger log = LoggerFactory.getLogger(StringHttpClient.class);

	public String get(String url) {
		return this.get(url, 0, true, "html");
	}

	public String get(String url, int timeout, boolean followRedirects, String type) {
		StringBuffer content = new StringBuffer();
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			if (timeout > 0) {
				connection.setConnectTimeout(timeout);
			}
			
			connection.setInstanceFollowRedirects(followRedirects);
			
			if ("json".equals(type)) {
				connection.setRequestProperty("Accept", "application/json");
			}
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;

			while ((line = reader.readLine()) != null) {
				content.append(line);
			}
			reader.close();
		} catch (Exception e) {
			log.error("Problem when reading data from {}", url, e);
		}
		return content.toString();
	}

	public void post(String url, String body) {
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "text/plain;charset=UTF-8");

			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(body);
			writer.close();

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				log.debug("POST request to {} was successful", url);
			} else {
				log.error("POST request to {} wasn't successful, response code {}", url, connection.getResponseCode());
			}
		} catch (Exception e) {
			log.error("Problem when sending POST request to {}", url, e);
		}
	}
}
