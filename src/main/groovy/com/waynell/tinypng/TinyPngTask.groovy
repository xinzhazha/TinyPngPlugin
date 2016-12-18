package com.waynell.tinypng

import com.tinify.*
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import java.lang.Exception
import java.text.DecimalFormat

/**
 * TingPng Task
 * @author Wayne
 */
public class TinyPngTask extends DefaultTask {

    def android
    def TinyPngExtension configuration

    TinyPngTask() {
        description = 'Tiny Resources'
        group = 'tinypng'
        outputs.upToDateWhen { false }
        android = project.extensions.android
        configuration = project.tinyInfo
    }

    public static String formetFileSize(long fileS) {
        def df = new DecimalFormat("#.00")
        if (fileS == 0L) {
            return "0B"
        }

        if (fileS < 1024) {
            return df.format((double) fileS) + "B"
        } else if (fileS < 1048576) {
            return df.format((double) fileS / 1024) + "KB"
        } else if (fileS < 1073741824) {
            return df.format((double) fileS / 1048576) + "MB"
        } else {
            return df.format((double) fileS / 1073741824) + "GB"
        }
    }

    public static List<TinyPngInfo> compress(File resDir, Iterable<String> whiteList, Iterable<TinyPngInfo> compressedList) {
        def newCompressedList = new ArrayList<TinyPngInfo>()
        label: for (File file : resDir.listFiles()) {
            def filePath = file.path
            def fileName = file.name

            for (String s : whiteList) {
                if (fileName == s) {
                    continue label
                }
            }

            for (TinyPngInfo info : compressedList) {
                if (filePath == info.path && file.lastModified() == info.modifyTime) {
                    continue label
                }
            }

            if (fileName.endsWith(".jpg") || fileName.endsWith(".png")) {
                if (fileName.contains(".9")) {
                    continue
                }

                println("find target pic >>>>>>>>>>>>>" + filePath)

                def fis = new FileInputStream(file)

                try {
                    def beforeSize = fis.available()
                    def beforeSizeStr = formetFileSize(beforeSize)
                    println("beforeSize: $beforeSizeStr")

                    // Use the Tinify API client
                    def tSource = Tinify.fromFile("${resDir}/${fileName}")
                    tSource.toFile("${resDir}/${fileName}")

                    def afterSize = fis.available()
                    def afterSizeStr = formetFileSize(afterSize)
                    println("afterSize: ${afterSizeStr}")

                    newCompressedList.add(new TinyPngInfo(filePath, beforeSizeStr, afterSizeStr, file.lastModified()))
                } catch (AccountException e) {
                    println("AccountException: ${e.getMessage()}")
                    // Verify your API key and account limit.
                } catch (ClientException e) {
                    // Check your source image and request options.
                    println("ClientException: ${e.getMessage()}")
                } catch (ServerException e) {
                    // Temporary issue with the Tinify API.
                    println("ServerException: ${e.getMessage()}")
                } catch (ConnectionException e) {
                    // A network connection error occurred.
                    println("ConnectionException: ${e.getMessage()}")
                } catch (IOException e) {
                    // Something else went wrong, unrelated to the Tinify API.
                    println("IOException: ${e.getMessage()}")
                } catch (Exception e) {
                    println("Exception: ${e.toString()}")
                }
            }
        }
        return newCompressedList
    }

    @TaskAction
    def run() {
        println(configuration.toString())

        if (!(configuration.resourceList ?: false)) {
            println("Not found resources list")
            return
        }
        if (!(configuration.apiKey ?: false)) {
            println("Tiny API Key not set")
            return
        }

        def apiKey = configuration.apiKey
        try {
            Tinify.setKey("${apiKey}")
            Tinify.validate()
        } catch (Exception ignored) {
            println("Tiny Validation of API key failed.")
            return
        }

        def compressedList = []
        def compressedListFile = new File("${project.projectDir}/compressed-resource.json")
        if (!compressedListFile.exists()) {
            compressedListFile.createNewFile()
        }
        else {
            try {
                def list = new JsonSlurper().parse(compressedListFile, "utf-8")
                if(list instanceof ArrayList) {
                    compressedList = list as ArrayList<TinyPngInfo>
                }
                else {
                    println("compressed-resource.json is invalid, ignore")
                }
            } catch (Exception ignored) {
                println("compressed-resource.json is invalid, ignore")
            }
        }

        def newCompressedList = new ArrayList<TinyPngInfo>()
        configuration.resourceList.each {
            def dir = new File(it)
            dir.eachDirMatch(~/drawable[a-z-]*/) { drawDir ->
                List<TinyPngInfo> list = compress(drawDir, configuration.whiteList, compressedList)
                if(list) {
                    newCompressedList.addAll(list)
                }
            }
        }

        if(newCompressedList) {
            compressedList.addAll(newCompressedList)
            def jsonOutput = new JsonOutput()
            def json = jsonOutput.toJson(compressedList)
            compressedListFile.write(jsonOutput.prettyPrint(json), "utf-8")
        }

    }
}