import my.plagiarism.ParamPlagiarism
import my.plagiarism.PlagiarismHandler
import my.reporter.ReportSenderImpl
import my.common.structure.parser.zip.ManualZipper
import org.apache.commons.codec.binary.Base64

def call() {
    node("built-in") {

        def param = new ParamPlagiarism(this)
        def sender = new ReportSenderImpl(this)
        String resultDir
        byte[] result

        stage("Plagiarism checking") {
            new PlagiarismHandler(param).check()
        }

        try {
            stage("Write zip  with MANUALZIPPER") {
                result = new ManualZipper().zipToByteArray(param.workingDirectory.path + "/result.zip")
            }
        }
        catch (Exception e) {
            println("PIZDA it zipper: " + e.getMessage())
        }

        // Send result
        stage("Send result") {
            def data = Base64.encodeBase64String(result)
            sender.sendReport("http://host.docker.internal:8989/back/plagiarismresult/${param.externalTaskId}", 'TEXT_PLAIN', data)
        }
    }
}
