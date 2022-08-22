package com.chunyan.chunyan.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpMethod;


public class RequestUtils {
	public static JSONObject getJSON(String requestURL) {
		HttpURLConnection conn;
		try {
			conn = connection(requestURL, HttpMethod.GET);
			return getJSONResponse(readResponse(conn));
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static HttpURLConnection connection(String requestURL, HttpMethod method) throws IOException {
		URL url = new URL(requestURL);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();

		conn.setRequestMethod(method.name());
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Transfer-Encoding", "chunked");
		conn.setRequestProperty("Connection", "keep-alive");
		conn.setDoOutput(true);

		return conn;
	}

	public static String readResponse(HttpURLConnection conn) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
		while (br.ready()) {
			sb.append(br.readLine());
		}
		return br.toString();
	}

	public static JSONObject getJSONResponse(String response) throws ParseException {
		return (JSONObject)new JSONParser().parse(response);
	}
}
