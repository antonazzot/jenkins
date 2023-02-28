import my.ParamPlagiarism
import my.PlagiarismHandler
import my.reporter.ReportSenderImpl
import my.zip.ManualZipper

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
                resultDir = param.workingDirectory.getPath() + "/result"
                result = new ManualZipper().zip(resultDir);
            }
        }
        catch (Exception e) {
            println("PIZDA it zipper: " + e.getMessage())
        }

        // Send result
        stage("Send result") {
            sender.sendReport("http://host.docker.internal:8989/back/antiresult", resultDir, 'TEXT_PLAIN', result)
        }
    }
}
