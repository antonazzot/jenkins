import my.obtainer.Checkout
import my.obtainer.GitWrapper
import my.obtainer.Param
import my.obtainer.PreparedResult
import my.reporter.ReportSenderImpl

def call() {
    node("built-in") {

        def param = new Param(this)
        def checkout = new Checkout(param)
        def sender = new ReportSenderImpl(this)
        List<GitWrapper> wrappers = checkout.getPreparedGitWrappers()
        wrappers.add(checkout.createBaseRepo())

        // checkout repos
        try {
            stage("Obtain for build__" + param.externalTaskId) {
                wrappers.each {
                    wrapper ->
                        wrapper.checkout()
                }
            }
        }
        catch (Exception e) {
            println("PIZDA: " + e.getMessage())
        }

        stage("Send result") {
            def data = new PreparedResult(param.externalTaskId, wrappers.size(), param.getIsFinished()).toJson()
            sender.sendReport("http://host.docker.internal:8989/back/obtainresult/${param.externalTaskId}", 'APPLICATION_JSON', data)
        }
    }
}
