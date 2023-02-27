package my

import de.jplag.JPlag

@Grab('de.jplag:jplag:3.0.0')

import de.jplag.JPlagResult
import de.jplag.options.JPlagOptions
import de.jplag.options.LanguageOption
import de.jplag.options.Verbosity
import de.jplag.reporting.Report
import de.jplag.strategy.ComparisonMode

class PlagiarismHandler {

    Script script

    Param parameters

    final String prefix = "/var/jenkins_home/workspace/myscript/*"

    PlagiarismHandler(Script script) {
        this.script = script
        this.parameters = new Param(script)
    }

    PlagiarismHandler(Param param) {
        this.script = param.script
        this.parameters = param
    }

    void check() {
        println("Start check")

        def language =  LanguageOption.JAVA;

        println("PATH TO SCAN IS : " + prefix + parameters.workingDirectory.getPath())

        JPlagOptions options = new JPlagOptions(prefix + parameters.workingDirectory.getPath(), language);
        options.setMaximumNumberOfComparisons(-1);
        options.setComparisonMode(ComparisonMode.PARALLEL);
        options.setVerbosity(Verbosity.LONG);

        JPlag jplag = new JPlag(options);
        JPlagResult result = jplag.run();
        File reportDir = new File(parameters.workingDirectory.getPath()+"/result");
        Report report = new Report(reportDir, options);
        report.writeResult(result);
    }

    void checkLocal() {
        println("Start check")

        def language =  LanguageOption.JAVA;

        println("PATH TO SCAN IS : " + parameters.workingDirectory.getPath())

        JPlagOptions options = new JPlagOptions(parameters.workingDirectory.getPath(), language);
        options.setMaximumNumberOfComparisons(-1);
        options.setComparisonMode(ComparisonMode.PARALLEL);
        options.setVerbosity(Verbosity.LONG);

        JPlag jplag = new JPlag(options);
        JPlagResult result = jplag.run();
        File reportDir = new File(parameters.workingDirectory.getPath()+"/result");
        Report report = new Report(reportDir, options);
        report.writeResult(result);
    }
}
