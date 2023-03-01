package my.reporter

import com.cloudbees.groovy.cps.NonCPS

interface ReportSender {
    @NonCPS
    void sendReport (String url, String contentType, String data)
}