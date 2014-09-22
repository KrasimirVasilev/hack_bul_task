package com.krasimirvasilev.webcrawler;

import java.net.MalformedURLException;
import java.net.URL;

public class MainClass {

	public static void main(String[] args) {

		WebCrawler webCrawler = new WebCrawler();
		URL startLocation = null;

		try {

			startLocation = new URL("http://blog.hackbulgaria.com");
			URL result = webCrawler
					.crawl(startLocation,
							"Като страничен ефект, особено при момчетата, може да бъде бързо-растяща брада.");

			if (result.toString().equals("")) {
				System.out.println(Constants.NOTHING_WAS_FOUND_MESSAGE);
			} else {
				System.out.println(result.toString());
			}

		} catch (MalformedURLException e) {
			System.out.println(Constants.NOTHING_WAS_FOUND_MESSAGE);
		}
	}
}
