package com.krasimirvasilev.webcrawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HtmlLinkExtractor is a class which parse a html content.
 * 
 * @author Vasilev
 */
public class HtmlLinkExtractor {

	private Pattern patternTag, patternLink;
	private Matcher matcherTag, matcherLink;

	private static final String HTML_A_TAG_PATTERN = "(?i)<a([^>]+)>(.+?)</a>";
	private static final String HTML_A_HREF_TAG_PATTERN = "\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))";

	public HtmlLinkExtractor() {
		patternTag = Pattern.compile(HTML_A_TAG_PATTERN);
		patternLink = Pattern.compile(HTML_A_HREF_TAG_PATTERN);
	}

	/**
	 * Parse html content with Regular Expression
	 * 
	 * @param url
	 *            is a start location URL
	 * @param html
	 *            is String (html content)
	 * @return LinkedList<URL> with all find links in that html
	 * @throws MalformedURLException
	 */
	public LinkedList<URL> grabHTMLLinks(URL url, String html)
			throws MalformedURLException {

		LinkedList<URL> result = new LinkedList<URL>();

		if (url != null && html != null) {

			matcherTag = patternTag.matcher(html);

			while (matcherTag.find()) {

				String href = matcherTag.group(1); // href

				matcherLink = patternLink.matcher(href);

				while (matcherLink.find()) {

					String link = matcherLink.group(1).replace("\"", ""); // link
					URL subUrl;

					if (link.startsWith("http")) {
						subUrl = new URL(link);
					} else {
						subUrl = new URL(url.toString() + "/" + link);
					}

					result.add(subUrl);
				}
			}
		}

		return result;
	}
}
