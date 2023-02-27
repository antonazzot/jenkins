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
    String commit
    String credentialsId
    String dir
    FileCreator fileCreator;

    GitWrapper(Script script, String workDir, RepositoryInfo repositoryInfo, FileCreator fileCreator) {
        println("checkout constract")
        this.script = script
        this.workDir = workDir
        this.owner = repositoryInfo.owner
        this.branch = repositoryInfo.branch
        this.repository = repositoryInfo.url
        this.credentialsId = repositoryInfo.sha
        this.dir = workDir+"/"+owner+"/"+repository
//        this.credentialsId = "psw"
        this.fileCreator = fileCreator
    }

    @NonCPS
    void checkout() {
        println("checkout worker")
//        fileCreator.makeDir(dir)
        resolveCheckout()
//        script.dir(dir) {
//            resolveDeleteDir()
//        }
        println("after checkout worker")
    }

    @NonCPS
    private void resolveDeleteDir() {
        println(" delete dir ")
        script.deleteDir()
    }

    @NonCPS
    private void resolveCheckout() {
        println(" checkout dir ")
//        script.sh('chmod -R 777 ' +dir)
        script.checkout([
                $class                           : 'GitSCM',
                branches                         : [[name: "*/${branch}"]],
                doGenerateSubmoduleConfigurations: false,
                extensions                       : [[$class: 'RelativeTargetDirectory', relativeTargetDir: "*/${dir}"], [$class: 'CloneOption', timeout: 20, noTags: false]],
                submoduleCfg                     : [],
                userRemoteConfigs                : [[credentialsId: credentialsId,
                                                     url          : repository]]
        ])
//        checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: '/Users/Anton_Tsyrkunou/Downloads/temp/checkout']], userRemoteConfigs: [[credentialsId: 'e0850795-67c3-4691-940c-033ab359133c', url: 'https://github.com/antonazzot/k8s.git'], [credentialsId: 'e0850795-67c3-4691-940c-033ab359133c', url: 'https://github.com/antonazzot/WednesdayFrog1.git']]])
//        script.checkout(
//                [
//                        $class: 'GitSCM',
//                        branches: [[name: '*/main']],
//                        extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: '/Users/Anton_Tsyrkunou/Downloads/temp/checkout']],
//                        userRemoteConfigs: [[credentialsId: 'e0850795-67c3-4691-940c-033ab359133c',
//                                             url: 'https://github.com/antonazzot/k8s.git'],
//                                            [credentialsId: 'e0850795-67c3-4691-940c-033ab359133c', url: 'https://github.com/antonazzot/WednesdayFrog1.git']]])
//                ]
//        )

    }
}