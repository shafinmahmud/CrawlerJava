/*
 */
package me.shafin.crawlerjava;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;

/**
 *
 * @author SHAFIN
 */
public class HtmlToParagraph {

    public static String htmlToParagraphUsingBoilerPipe(String html) {
        try {
            return ArticleExtractor.INSTANCE.getText(html);
        } catch (BoilerpipeProcessingException ex) {
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
