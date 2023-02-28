package my.reporter


import org.apache.commons.codec.binary.Base64

class ReportSenderImpl implements my.reporter.ReportSender {


    private Script script

    ReportSenderImpl(Script script) {
        this.script=script
    }

    @Override
    void sendReport(String url, String filename, String contentType, byte[] bytes) {
        def data = Base64.encodeBase64String(bytes)
        println("START SEND")
        script.httpRequest url: url,
                httpMode: 'POST',
                contentType: contentType,
                quiet: false,
                requestBody: data
    }

    void sendReport(String url, String contentType, int amountOfWrappers) {
        println("START SEND")
        script.httpRequest url: url,
                httpMode: 'POST',
                contentType: contentType,
                quiet: false,
                requestBody: amountOfWrappers.toString()
    }
}
