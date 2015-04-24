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
            
            if(!(s.contains("।")||s.contains("?")||s.contains("!"))){
                taggedText = taggedText.concat("<trash>"+s+"</trash>"+endOfLine);
            }else{
                taggedText = taggedText.concat(s+endOfLine);
            }
        }
        return taggedText;
    }
}
