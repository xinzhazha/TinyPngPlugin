package com.waynell.tinypng

/**
 * Create On 16/12/2016
 * @author Wayne
 */
public class TinyPngExtension {
    String apiKey
    boolean isShowLog
    Iterable<String> whiteList;
    Iterable<String> resourceList;

    public TinyPngExtension() {
        apiKey = ""
        isShowLog = false
        whiteList = []
        resourceList = []
    }

    @Override
    public String toString() {
        return "TinyPngExtension{" +
                "apiKey='" + apiKey + '\'' +
                ", isShowLog=" + isShowLog +
                ", whiteList=" + whiteList +
                ", resourceList=" + resourceList +
                '}';
    }
}
