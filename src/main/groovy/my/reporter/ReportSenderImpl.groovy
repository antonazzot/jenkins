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

//        def json = request.toJson()
//        println json
//        def response = context.script.withCredentials([context.script.string(credentialsId: 'SYSTEM_AUTH_SECRET', variable: 'systemAuthSecret')]) {
//            context.script.httpRequest url: pathToAddResource,
//                    httpMode: 'PATCH',
//                    contentType: 'APPLICATION_JSON',
//                    customHeaders: [[name: 'X-System-Auth', value: context.script.env.systemAuthSecret, maskValue: true]],
//                    requestBody: json
//        }
//        return response.content
    }
}
