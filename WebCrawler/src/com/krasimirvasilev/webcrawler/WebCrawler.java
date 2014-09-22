package com.krasimirvasilev.webcrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;

/**
 * WebCrawler is a class which has only one public method, "crawl()" and that
 * method will searching for a word or sentence in a submitted web site.
 * 
 * @author Vasilev
 * 
 */
public class WebCrawler {

	private Queue<URL> allLinks;
	private HtmlLinkExtractor extractor;

	public WebCrawler() {
		this.allLinks = new LinkedList<URL>();
		this.extractor = new HtmlLinkExtractor();
	}

	/**
	 * Searching a word or expression (needle) in specific web site
	 * (startLocation).
	 * 
	 * @param startLocaltion
	 *            is a the point from where start the searching
	 * @param needle
	 *            is the word or expression which we search
	 * @return URL (the result)
	 * @throws MalformedURLException
	 */
	public URL crawl(URL startLocaltion, String needle)
			throws MalformedURLException {

		if (checkParams(startLocaltion, needle)) {

			// open the stream and put it into BufferedReader
			BufferedReader bufferedReader;
			StringBuilder result = null;

			try {

				HttpURLConnection connection = (HttpURLConnection) startLocaltion
						.openConnection();

				connection.setRequestMethod("GET");
				int responseCode = connection.getResponseCode();

				if (responseCode == Constants.RESULT_OK) {

					bufferedReader = new BufferedReader(new InputStreamReader(
							connection.getInputStream(), "UTF-8"));

					result = new StringBuilder();

					while (bufferedReader.ready()) {

						String currentLine = bufferedReader.readLine();

						if (currentLine.contains(needle))
							return startLocaltion;

						result.append(currentLine + "\n");
					}

					if (bufferedReader != null) {
						bufferedReader.close();
					}

					LinkedList<URL> extractedLinks = extractor.grabHTMLLinks(
							startLocaltion, result.toString());

					allLinks.addAll(extractedLinks);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			while (allLinks.size() > 0) {

				URL subUrl = allLinks.remove();

				return crawl(subUrl, needle);
			}
		}

		return new URL("");
	}

	/**
	 * Check web address and searching word which are pass like a Params
	 * 
	 * @param location
	 *            is address from where will start the searching
	 * @param needle
	 *            is the word for which we will search
	 * @return true if all checks are OK, otherwise return false
	 */
	private boolean checkParams(URL location, String needle) {

		if (location != null && needle != null) {

			if (location.toString().startsWith("http")) {

				if (!needle.equals("")) {

					return true;
				} else {

					System.out.println("Needle can not be EMPTY!");
				}

			} else {
				System.out.println("Start location address is not correct!");
			}
		} else {

			System.out.println("Parameters can not be NULL!");
		}

		return false;
	}
}
