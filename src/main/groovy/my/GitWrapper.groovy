/*
 * Copyright © 2021 EPAM Systems, Inc. All Rights Reserved. All information contained herein is, and remains the
 * property of EPAM Systems, Inc. and/or its suppliers and is protected by international intellectual
 * property law. Dissemination of this information or reproduction of this material is strictly forbidden,
 * unless prior written permission is obtained from EPAM Systems, Inc
 */
package my

import com.cloudbees.groovy.cps.NonCPS
import my.structure.FileCreator
import my.structure.RepositoryInfo

class GitWrapper {

    final String prefix = "https://github.com"

    Script script
    String workDir
    String owner
    String repository
    String branch
    String credentialsId
    String dir
    FileCreator fileCreator;

    GitWrapper(Script script, String workDir, RepositoryInfo repositoryInfo, FileCreator fileCreator) {
        this.script = script
        this.workDir = workDir
        this.owner = repositoryInfo.owner
        this.branch = repositoryInfo.branch
        this.repository = repositoryInfo.url
        this.credentialsId = repositoryInfo.sha
        this.dir = workDir+"/"+owner
        this.fileCreator = fileCreator
    }

    GitWrapper(Script script, String workDir, RepositoryInfo repositoryInfo) {
        println("checkout constract")
        this.script = script
        this.workDir = workDir
        this.owner = repositoryInfo.owner
        this.branch = repositoryInfo.branch
        this.repository = repositoryInfo.url
        this.credentialsId = repositoryInfo.sha
        this.dir = workDir+"/basedir"
    }

    @NonCPS
    void checkout() {
//        fileCreator.makeDir(dir)
        resolveCheckout()
        println("after checkout worker")
    }

    @NonCPS
    private void resolveDeleteDir() {
        println(" delete dir ")
        script.deleteDir()
    }

    @NonCPS
    private void resolveCheckout() {
        println(" _________________ ")
        println("Work dir: " + workDir)
        println("Owner: " + owner)
        println("Repository " + repository)
        println("Branch: " + branch)
        println("Rep dir: " + dir)

        script.checkout([
                $class                           : 'GitSCM',
                branches                         : [[name: "*/${branch}"]],
                doGenerateSubmoduleConfigurations: false,
                extensions                       : [[$class: 'RelativeTargetDirectory', relativeTargetDir: "*/${dir}"], [$class: 'CloneOption', timeout: 20, noTags: false]],
                submoduleCfg                     : [],
                userRemoteConfigs                : [[credentialsId: credentialsId,
                                                     url          : repository]]
        ])
    }
}