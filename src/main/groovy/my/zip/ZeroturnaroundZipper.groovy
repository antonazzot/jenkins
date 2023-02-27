package my.zip

import com.cloudbees.groovy.cps.NonCPS

@Grab('org.zeroturnaround:zt-zip:1.13')

class ZeroturnaroundZipper implements Zipper {

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
import org.zeroturnaround.zip.ZipUtil
