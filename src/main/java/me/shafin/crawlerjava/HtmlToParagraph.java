/*
 */
package me.shafin.crawlerjava;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.HTMLHighlighter;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author SHAFIN
 */
public class HtmlToParagraph {

    public static String htmlToHtmlFragmantUsibgBoilerPipe(String html) {
        StringBuilder bf = new StringBuilder();
        try {
            ArticleExtractor extractor = ArticleExtractor.INSTANCE;
            InputSource is = new InputSource(new StringReader(html));
            HTMLHighlighter hh = HTMLHighlighter.newExtractingInstance();

            final TextDocument doc = new BoilerpipeSAXInput(is).getTextDocument();
            extractor.process(doc);

            bf.append("<meta http-equiv=\"Content-Type\" content=\"text-html; charset=utf-8\" />");
            bf.append(hh.process(doc, html));

        } catch (BoilerpipeProcessingException | SAXException ex) {
            Logger.getLogger(HtmlToParagraph.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bf.toString();
    }

    
    
    public static String htmlToParagraphUsingBoilerPipe(String pageUrl) {
        try {
            URL url = new URL(pageUrl);
            
            InputSource is = new InputSource();
            is.setEncoding("UTF-8");
            is.setByteStream(url.openStream());
            
            return ArticleExtractor.INSTANCE.getText(is);

        } catch (BoilerpipeProcessingException | MalformedURLException ex) {
            Logger.getLogger(HtmlToParagraph.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HtmlToParagraph.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    
    
    public static String htmlToParagraphUsingPtag(String html) {
        String paragraphSubString = "";

        int startIndex = 0;
        List<Integer> tagArray = new ArrayList<>();

        int i = 0;
        while (i < html.length() - 4) {
            if (html.charAt(i) == '<') {
                if (html.charAt(++i) == 'p') {
                    boolean startTagTailFound = false;
                    while (!startTagTailFound) {
                        if (html.charAt(++i) == '>') {
                            startTagTailFound = true;
                        }
                    }
                    if (startTagTailFound) {
                        //System.out.println("START "+i);
                        startIndex = i + 1;
                        tagArray.add(startIndex);

                        boolean endFlag = false;

                        while (!endFlag) {
                            if (html.charAt(++i) == '<') {

                                if (html.charAt(++i) == '/') {
                                    if (html.charAt(++i) == 'p') {
                                        if (html.charAt(++i) == '>') {
                                            int endIndex = i - 3;
                                            tagArray.add(endIndex);
                                            endFlag = true;
                                            //System.out.println("END "+i);
                                        }
                                    }
                                }

                            }
                        }

                    }
                }

            } else {
                i++;
            }
        }

        for (i = 0; i <= tagArray.size() - 1; i += 2) {
            int start = tagArray.get(i);
            int end = tagArray.get(i + 1);
            String temp = html.substring(start, end);
            paragraphSubString = paragraphSubString.concat(temp + "\n\n");
        }

        paragraphSubString = paragraphSubString.replaceAll("(?i)<br[^>]*>", "br2n");
        paragraphSubString = Jsoup.parse(paragraphSubString).text();
        paragraphSubString = paragraphSubString.replaceAll("\"+comment+\"", "");
        paragraphSubString = paragraphSubString.replaceAll("br2n", "\n");

        return paragraphSubString;
    }

}
