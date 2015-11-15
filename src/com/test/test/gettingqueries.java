package com.test.test;

//code credit http://stackoverflow.com/questions/3727662/how-can-you-search-google-programmatically-java-api

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import com.google.gson.Gson;

public class gettingqueries {

	public static void main(String[] args) throws IOException {
		int count = 1;
		for (int i1 = 0; i1 < 20; i1 = i1 + 4) {
			String address = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&start="+i1+"&q=";
		String query = "prateek site:ics.uci.edu -filetype:ppt -filetype:pdf  -filetype:zip -filetype:rar -filetype:gz -filetype:csv -filetype:js -filetype:bmp -filetype:gif -filetype:png -filetype:jpe?g -filetype:php";
		String charset = "UTF-8";

		URL url = new URL(address + URLEncoder.encode(query, charset));
		Reader reader = new InputStreamReader(url.openStream(), charset);
		GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);

		int total = results.getResponseData().getResults().size();
		System.out.println("total: "+total);
		
		// Show title and URL of each results
		for(int i=0; i<total; i++){
			System.out.println(count);
			System.out.println("Title: " + results.getResponseData().getResults().get(i).getTitle());
			System.out.println("URL: " + results.getResponseData().getResults().get(i).getUrl() + "\n");
			count++;
		}
		}
	}
}


class GoogleResults{

 private ResponseData responseData;
 public ResponseData getResponseData() { return responseData; }
 public void setResponseData(ResponseData responseData) { this.responseData = responseData; }
 public String toString() { return "ResponseData[" + responseData + "]"; }

 static class ResponseData {
     private List<Result> results;
     public List<Result> getResults() { return results; }
     public void setResults(List<Result> results) { this.results = results; }
     public String toString() { return "Results[" + results + "]"; }
 }

 static class Result {
     private String url;
     private String title;
     public String getUrl() { return url; }
     public String getTitle() { return title; }
     public void setUrl(String url) { this.url = url; }
     public void setTitle(String title) { this.title = title; }
     public String toString() { return "Result[url:" + url +",title:" + title + "]"; }
 }
}