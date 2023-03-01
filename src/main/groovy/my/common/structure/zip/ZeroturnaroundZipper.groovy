package my.common.structure.zip

import com.cloudbees.groovy.cps.NonCPS
import org.zeroturnaround.zip.ZipUtil

@Grab('org.zeroturnaround:zt-zip:1.13')

class ZeroturnaroundZipper implements my.common.structure.zip.Zipper {

    String outputDir;

    @Override
    @NonCPS
    byte[] zip(String inputDir) {
        byte [] result;
        try {
            def fileOutput = new File(inputDir)
            fileOutput.mkdirs()
            result = ZipUtil.packEntry(new File(inputDir), fileOutput)
        }
        catch (Exception e){
            println("PIZDAZIPKIN:    " +  e.getMessage())
        }
        return result
    }
}