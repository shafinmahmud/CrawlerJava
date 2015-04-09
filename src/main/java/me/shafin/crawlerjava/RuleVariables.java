/*
 */
package me.shafin.crawlerjava;

/**
 *
 * @author SHAFIN
 */
public class RuleVariables {
    
    private static String domainName;
    private static String folderPath;
    private static int textParsingType;

    /**
     * @return the domainName
     */
    public static String getDomainName() {
        return domainName;
    }

    /**
     * @param aDomainName the domainName to set
     */
    public static void setDomainName(String aDomainName) {
        domainName = aDomainName;
    }

    /**
     * @return the folderPath
     */
    public static String getFolderPath() {
        return folderPath;
    }

    /**
     * @param aFolderPath the folderPath to set
     */
    public static void setFolderPath(String aFolderPath) {
        folderPath = aFolderPath;
    }

    /**
     * @return the textParsingType
     */
    public static int getTextParsingType() {
        return textParsingType;
    }

    /**
     * @param aTextParsingType the textParsingType to set
     */
    public static void setTextParsingType(int aTextParsingType) {
        textParsingType = aTextParsingType;
    }
    
}
