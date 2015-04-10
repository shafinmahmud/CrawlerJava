/*
 */
package me.shafin.crawlerjava;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SHAFIN
 */
public class HtmlToParagraph {

    public static String htmlToParagraphText(String html) {
        String paragraphSubString = "";

        //DataWriter.writeDataToFile("D:\\test.text", html);
        int startIndex = 0;
        List<Integer> tagArray = new ArrayList<>();
        int i = 0;
        while (i < html.length() - 4) {
            if (html.charAt(i) == '<') {
                if (html.charAt(++i) == 'p') {
                    if (html.charAt(++i) == '>') {
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
            temp = temp.replaceAll("<br/>", "\n");
            temp = temp.replaceAll("<strong>", "\n");
            temp = temp.replaceAll("</strong>", "\n");
            paragraphSubString = paragraphSubString.concat(temp + "\n\n");
            //System.out.println("START " + start + " END " + end);
            //System.out.println(temp);
        }
       // System.out.println(paragraphSubString);

        return paragraphSubString;
    }
}
