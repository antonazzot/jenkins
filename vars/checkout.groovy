import my.Checkout
import my.GitWrapper
import my.Param
import my.reporter.ReportSenderImpl

def call() {
    node("built-in") {

        def param = new Param(this)
        def checkout = new Checkout(param)
        def sender = new ReportSenderImpl(this)
        List<GitWrapper> wrappers = checkout.getPreparedGitWrappers()
//        wrappers.add(checkout.createBaseRepo())

        // checkout repos
        try {
            wrappers.each {
                wrapper ->
                    stage(wrapper.owner) {
                        wrapper.checkout()
                    }
            }
        }
        catch (Exception e) {
            println("PIZDA: " + e.getMessage())
        }

        stage("Send result") {
            sender.sendReport("http://host.docker.internal:8989/back/antiresult/checkout/${param.externalTaskId}",'TEXT_PLAIN', wrappers.size())
        }
    }
}
