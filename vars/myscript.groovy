import my.Checkout
import my.GitWrapper
import my.Param
import my.PlagiarismHandler
import my.reporter.ReportSenderImpl
import my.zip.ManualZipper

def call() {
    node("built-in") {

        def param = new Param(this)
        def checkout = new Checkout(param)
        List<GitWrapper> wrappers = checkout.getPreparedGitWrappers()
        def sender = new ReportSenderImpl(this)
        String resultDir
        byte[] result

        // checkout repos
        try {
            wrappers.each {
                wrapper ->
                    stage(wrapper.owner) {
                        println("next stage")
                        wrapper.checkout()
                        println("stage finished")
                    }
            }
        }
        catch (Exception e) {
            println("PIZDA")
        }

        // Plagiarism checking
        stage("Plagiarism checking") {
            new PlagiarismHandler(param).check()
        }

        // ZIP result
//        try {
//            stage("Write zip  with ZeroturnaroundZipper") {
//                resultDir = param.workingDirectory.getPath() + "/result"
//                result = new ZeroturnaroundZipper().zip(resultDir);
//            }
//        }
//        catch (Exception e) {
//            println("PIZDA ith ZeroturnaroundZipper")
//
//            stage("Write zip  with MANUALZIPPER") {
//                resultDir = param.workingDirectory.getPath() + "/result"
//                result = new ManualZipper().zip(resultDir);
//            }
//        }

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
