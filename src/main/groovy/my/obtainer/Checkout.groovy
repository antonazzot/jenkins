package my.obtainer

import com.cloudbees.groovy.cps.NonCPS
import my.common.structure.parser.RepositoryInfoParser
import my.common.structure.parser.RepositoryInfoParserImpl

class Checkout {

    public final String workDirPath
    private final RepositoryInfoParser parser = new RepositoryInfoParserImpl()
    Param parameters

    Checkout(Script script) {
//        this.script = script
        this.parameters = new Param(script)
        this.workDirPath = parameters.workingDirectory.path
    }

    Checkout(Param param) {
        this.parameters = param
        this.workDirPath = parameters.workingDirectory.path
    }


    List<GitWrapper> getPreparedGitWrappers() {
        println("!!!!START PREPARATION!!!!!")
        parameters.workingDirectory.mkdirs()
        def repositoryInfo = parser.parse(parameters.gitInfo)
        println("!!!!PREPARATION FINISHED!!!!!")
        return mapToGitWrapper(repositoryInfo)
    }

    GitWrapper createBaseRepo() {
        def repo = parser.parseSingle(parameters.gitBaseInfo)
        return new GitWrapper(parameters.script, repo,  workDirPath + "/basedir")
    }

    @NonCPS
    List<GitWrapper> mapToGitWrapper(List<RepositoryInfo> repositoryInfos) {

        List<GitWrapper> gitWrapperList = new ArrayList<>();

        println("Start wrapping")
        for (repo in repositoryInfos) {
            gitWrapperList.add(new GitWrapper(parameters.script, workDirPath, repo))
        }
        println("Finish wrapping")

        return gitWrapperList;
    }
}
