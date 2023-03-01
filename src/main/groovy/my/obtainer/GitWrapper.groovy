/*
 * Copyright © 2021 EPAM Systems, Inc. All Rights Reserved. All information contained herein is, and remains the
 * property of EPAM Systems, Inc. and/or its suppliers and is protected by international intellectual
 * property law. Dissemination of this information or reproduction of this material is strictly forbidden,
 * unless prior written permission is obtained from EPAM Systems, Inc
 */
package my.obtainer

import com.cloudbees.groovy.cps.NonCPS

class GitWrapper {

    Script script
    String owner
    String repository
    String branch
    String credentialsId
    String dir

    GitWrapper(Script script, String workDir, RepositoryInfo repositoryInfo) {
        this.script = script
        this.owner = repositoryInfo.owner
        this.branch = repositoryInfo.branch
        this.repository = repositoryInfo.url
        this.credentialsId = repositoryInfo.sha
        this.dir = workDir+"/"+owner
    }

    GitWrapper(Script script, RepositoryInfo repositoryInfo, String dir) {
        println("checkout constract")
        this.script = script
        this.owner = repositoryInfo.owner
        this.branch = repositoryInfo.branch
        this.repository = repositoryInfo.url
        this.credentialsId = repositoryInfo.sha
        this.dir = dir
    }

    @NonCPS
    void checkout() {
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