package my.reporter

class ReportSenderImpl implements ReportSender {


    private Script script

    ReportSenderImpl(Script script) {
        this.script=script
    }

    @Override
    void sendReport(String url, String contentType, String data) {
        println("START SEND--->>>>>")
        script.httpRequest url: url,
                httpMode: 'POST',
                contentType: contentType,
                quiet: false,
                requestBody: data
    }
}
