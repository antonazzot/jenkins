package my.common.structure.zip

import com.cloudbees.groovy.cps.NonCPS

import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

class ManualZipper implements my.common.structure.zip.Zipper {


    String zipFileName = "RESULT_OUTPUT.zip"
    String inputDir
//Zip files


    @Override
    @NonCPS
    byte[] zip(String inputDir) {
        this.inputDir = inputDir
        def outputStream = new FileOutputStream(inputDir + zipFileName)
        ZipOutputStream zipFile = new ZipOutputStream(outputStream)

        new File(inputDir).eachFile() { file ->
            //check if file
            if (file.isFile()) {
                zipFile.putNextEntry(new ZipEntry(file.name))
                def buffer = new byte[file.size()]
                file.withInputStream {
                    zipFile.write(buffer, 0, it.read(buffer))
                }

                zipFile.closeEntry()
            }
        }
        zipFile.close()

        def inputStream = new FileInputStream(inputDir + zipFileName)
//        def channel = inputStream.getChannel()
//        def outChannel = outputStream.getChannel()
//        channel.transferTo(0, channel.size(), outChannel)


        return inputStream.readAllBytes()
    }

    @NonCPS
    private byte[] readZipFile() {
        //UnZip archive
        byte[] buffer
        def fl = new File(inputDir + zipFileName+"out.zip")
        fl.mkdirs()
        def zip = new ZipFile(fl)
        zip.entries().each {
            if (!it.isDirectory()) {
                def fOut = new File(outputDir + File.separator + it.name)
                //create output dir if not exists
                new File(fOut.parent).mkdirs()

                buffer = zip.getInputStream(it).readAllBytes();
            }
        }
        zip.close()

        return buffer;
    }

}


//        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFileName))
//    ZipEntry zipEntry = zis.getNextEntry()
//    while (zipEntry != null) {
//        File newFile = new File(outputDir+ File.separator, zipEntry.name)
//        if (zipEntry.isDirectory()) {
//            if (!newFile.isDirectory() && !newFile.mkdirs()) {
//                throw new IOException("Failed to create directory " + newFile)
//            }
//        } else {
//            // fix for Windows-created archives
//            File parent = newFile.parentFile
//            if (!parent.isDirectory() && !parent.mkdirs()) {
//                throw new IOException("Failed to create directory " + parent)
//            }
//            // write file content
//            FileOutputStream fos = new FileOutputStream(newFile)
//            int len = 0
//            while ((len = zis.read(buffer)) > 0) {
//                fos.write(buffer, 0, len)
//            }
//            fos.close()
//        }
//        zipEntry = zis.getNextEntry()
//    }
//    zis.closeEntry()
//    zis.close()
