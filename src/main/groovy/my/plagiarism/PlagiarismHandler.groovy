package my.plagiarism

import de.jplag.JPlag
@Grab('de.jplag:jplag:4.2.0')
@Grab('de.jplag:python-3:4.2.0')
@Grab('de.jplag:kotlin:4.2.0')
@Grab('de.jplag:swift:4.2.0')
@Grab('de.jplag:java:4.2.0')

import de.jplag.JPlagResult
import de.jplag.options.JPlagOptions
import de.jplag.reporting.reportobject.ReportObjectFactory

class PlagiarismHandler {

    Script script

    ParamPlagiarism parameters

    String obtainerWorkDir;

    LinkedHashMap<Language, de.jplag.Language> languages;

    PlagiarismHandler(Script script) {
        this.script = script
        this.parameters = new ParamPlagiarism(script)
        this.languages = [(Language.JAVA)  : new de.jplag.java.Language(),
                          (Language.KOTLIN): new de.jplag.kotlin.Language(),
                          (Language.PYTHON): new de.jplag.python3.Language(),
                          (Language.SWIFT) : new de.jplag.swift.Language()]
    }

    PlagiarismHandler(ParamPlagiarism param) {
        this.script = param.script
        this.parameters = param
        this.obtainerWorkDir = parameters.obtainerWorkDir
        this.languages = [(Language.JAVA)  : new de.jplag.java.Language(),
                          (Language.KOTLIN): new de.jplag.kotlin.Language(),
                          (Language.PYTHON): new de.jplag.python3.Language(),
                          (Language.SWIFT) : new de.jplag.swift.Language()]
    }

    void check() {
        println("Start check")

        def language = languages.get(parameters.language)

        println("PATH TO SCAN IS : " + obtainerWorkDir)

        def files = [new File(obtainerWorkDir)] as Set<File>
        def oldSubmision = [] as Set<File>

        println("!Files created!")

        def jPlagOptions = new JPlagOptions(language, files, oldSubmision)
                .withBaseCodeSubmissionDirectory(new File(obtainerWorkDir + "/basedir"))
                .withMaximumNumberOfComparisons(-1)
        .withMinimumTokenMatch()
        .withSimilarityThreshold()

        JPlag jplag = new JPlag(jPlagOptions)
        JPlagResult result = jplag.run()
        ReportObjectFactory reportObjectFactory = new ReportObjectFactory();
        reportObjectFactory.createAndSaveReport(result, parameters.workingDirectory.path + "/result");


//        options.setMaximumNumberOfComparisons(-1);
//        options.setBaseCodeSubmissionName("basedir")
//        options.setComparisonMode(ComparisonMode.PARALLEL);
//        options.setVerbosity(Verbosity.LONG);

//        File reportDir = new File(parameters.workingDirectory.path+"/result");
//        Report report = new Report(reportDir, options);
//        report.writeResult(result);
    }

    void checkLocal() {
//        println("Start check")
//
//        def language = LanguageOption.JAVA;
//
//        println("PATH TO SCAN IS : " + parameters.workingDirectory.getPath())
//
//        JPlagOptions options = new JPlagOptions(parameters.workingDirectory.getPath(), language);
//        options.setMaximumNumberOfComparisons(-1);
//        options.setComparisonMode(ComparisonMode.PARALLEL);
//        options.setVerbosity(Verbosity.LONG);
//
//        JPlag jplag = new JPlag(options);
//        JPlagResult result = jplag.run();
//        File reportDir = new File(parameters.workingDirectory.getPath() + "/result");
//        Report report = new Report(reportDir, options);
//        report.writeResult(result);
    }
}
