package com.waynell.tinypng

/**
 * Create On 16/12/2016
 * @author Wayne
 */
public class TinyPngExtension {
    String apiKey
    boolean isShowLog
    ArrayList<String> whiteList;
    ArrayList<String> resourceDir;
    ArrayList<String> resourcePattern;

    public TinyPngExtension() {
        apiKey = ""
        isShowLog = false
        whiteList = []
        resourceDir = []
        resourcePattern = []
    }

    @Override
    public String toString() {
        return "TinyPngExtension{" +
                "apiKey='" + apiKey + '\'' +
                ", isShowLog=" + isShowLog +
                ", whiteList=" + whiteList +
                ", resourceDir=" + resourceDir +
                ", resourcePattern=" + resourcePattern +
                '}';
    }
}
