package com.waynell.tinypng

/**
 * Create On 16/12/2016
 * @author Wayne
 */
class TinyPngInfo {
    String path
    String preSize;
    String postSize
    long modifyTime

    TinyPngInfo() {
    }

    TinyPngInfo(String path, String preSize, String postSize, long modifyTime) {
        this.path = path
        this.preSize = preSize
        this.postSize = postSize
        this.modifyTime = modifyTime
    }

}