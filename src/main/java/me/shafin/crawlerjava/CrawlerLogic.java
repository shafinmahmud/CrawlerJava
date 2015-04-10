/*
 */
package me.shafin.crawlerjava;

import java.util.Set;
import java.util.regex.Pattern;

import org.apache.http.Header;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 *
 * @author SHAFIN
 */
public class CrawlerLogic extends WebCrawler {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp3|zip|gz))$");
    private static final String DOMAIN_RULE = RuleVariables.getDomainName();

    /**
     * You should implement this function to specify whether the given url
     * should be crawled or not (based on your crawling logic).
     *
     * @param referringPage
     * @return
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        // Ignore the url if it has an extension that matches our defined set of image extensions.    
        // Only accept the url if it is in the "www.ics.uci.edu" domain and protocol is "http".
        return !FILTERS.matcher(href).matches()
                && href.startsWith(DOMAIN_RULE);
    }

    /**
     * This function is called when a page is fetched and ready to be processed
     * by your program.
     */
    @Override
    public void visit(Page page) {
        int docid = page.getWebURL().getDocid();
        String url = page.getWebURL().getURL();
        String domain = page.getWebURL().getDomain();
        String path = page.getWebURL().getPath();
        String subDomain = page.getWebURL().getSubDomain();
        String parentUrl = page.getWebURL().getParentUrl();
        String anchor = page.getWebURL().getAnchor();

        logger.debug("Docid: {}", docid);
        logger.info("URL: {}", url);
        logger.debug("Domain: '{}'", domain);
        logger.debug("Sub-domain: '{}'", subDomain);
        logger.debug("Path: '{}'", path);
        logger.debug("Parent page: {}", parentUrl);
        logger.debug("Anchor text: {}", anchor);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();

            String title = htmlParseData.getTitle();

            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            System.out.println(docid + "  " + title);

            switch (RuleVariables.getTextParsingType()) {
                case 1: {
                    String text = htmlParseData.getText();
                    logger.debug("Text length: {}", text.length());
                    DataWriter.writeDataToFile(RuleVariables.getFolderPath() + title + ".txt", text);
                    break;
                }
                case 2: {
                    String html = htmlParseData.getHtml();
                    logger.debug("Html length: {}", html.length());
                    String paragraph = HtmlToParagraph.htmlToParagraphText(html);
                    paragraph = html;
                    DataWriter.writeDataToFile(RuleVariables.getFolderPath() + title + "html.txt", paragraph);
                    break;
                }
            }
            logger.debug("Number of outgoing links: {}", links.size());
        }

        Header[] responseHeaders = page.getFetchResponseHeaders();
        if (responseHeaders != null) {
            logger.debug("Response headers:");
            for (Header header : responseHeaders) {
                logger.debug("\t{}: {}", header.getName(), header.getValue());
            }
        }

        logger.debug("=============");
    }
}
