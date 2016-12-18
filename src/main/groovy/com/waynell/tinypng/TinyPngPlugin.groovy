/**
 * Created by Wayne Yang
 * Copyright (c) 2015-present, mogujie.
 * All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 *
 */

package com.waynell.tinypng

import org.gradle.api.Plugin
import org.gradle.api.Project

public class TinyPngPlugin implements Plugin<Project> {


    @Override
    void apply(Project project) {

//        def hasApp = project.plugins.withType(AppPlugin)

//        def variants = hasApp ? project.android.applicationVariants : project.android.libraryVariants


//        def android = project.extensions.android
        project.extensions.create("tinyInfo", TinyPngExtension);

        project.afterEvaluate {
            project.task("tinyPng", type: TinyPngTask)
        }
        /*project.afterEvaluate {
            variants.all { variant ->
                def processResourceTask;
                def HashSet abiSet = android.splits.abiFilters
                if (!abiSet.isEmpty()) {
                    abiSet.each { abi ->
                        processResourceTask = project.tasks.findByName("process${abi.capitalize()}${variant.path.capitalize()}Resources")
                        println "findByName process${abi.capitalize()}${variant.path.capitalize()}Resources"
                    }
                }
                else {
                    processResourceTask = project.tasks.findByName("process${variant.path.capitalize()}Resources")
                }
            }
        }*/
    }
}