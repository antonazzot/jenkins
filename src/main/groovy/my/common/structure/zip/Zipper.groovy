package my.common.structure.zip

import com.cloudbees.groovy.cps.NonCPS

interface Zipper {
    @NonCPS
    byte [] zip (String inputDir)
}