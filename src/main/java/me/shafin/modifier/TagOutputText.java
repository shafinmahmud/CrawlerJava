/*
 */
package me.shafin.modifier;

/**
 *
 * @author SHAFIN
 */
public class TagOutputText {
    
    public static String tagIncompleteSentence(String text){
        String endOfLine = System.getProperty("line.separator");
        String[] textLines = text.split("\n");
        String taggedText = "";
        
        for(String s:textLines){
//            System.out.println((int)s.charAt(0));
//            System.out.println(s.length()+" -- "+s.trim().length());
            if(!(s.contains("।")||s.contains("?")||s.contains("!"))){
                taggedText = taggedText.concat("<t>"+s+"</t>"+endOfLine);
            }else{
                taggedText = taggedText.concat(s+endOfLine);
            }
        }
        return taggedText;
    }
}
