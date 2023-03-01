package my.common.structure.parser.zip

import com.cloudbees.groovy.cps.NonCPS

interface Zipper {
    @NonCPS
    byte [] zip (String inputDir)
}