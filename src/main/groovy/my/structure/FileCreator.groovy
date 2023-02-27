package my.structure

import com.cloudbees.groovy.cps.NonCPS

class FileCreator {

    private final Script script

    FileCreator(Script script) {
        this.script = script
    }

    @NonCPS
    String makeDir(File file) {
        try {
            script.fileOperations(folderCreateOperation(
                    fileContent: "",
                    fileName: "${file.getPath()}"
            ))
        }
        catch (Exception e) {
            println("!!!!ERoro")
            makeDirInside(file)
        }
    }

    @NonCPS
    String makeDir(String path) {
        try {
            script.fileOperations(folderCreateOperation(fileContent: "",
                    fileName: "${path}"))
        }
        catch (Exception e) {
            println("!!!EROr" + e.getMessage())
            makeDirInside(path)
        }
    }

    @NonCPS
    void makeDirInside(File file) {
        file.mkdirs()
    }

    @NonCPS
    void makeDirInside(String file) {
        new File(file).mkdirs()
    }
}
