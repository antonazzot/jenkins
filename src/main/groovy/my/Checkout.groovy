package my

import com.cloudbees.groovy.cps.NonCPS
import my.structure.FileCreator
import my.structure.RepositoryInfo
import my.structure.RepositoryInfoParser
import my.structure.RepositoryInfoParserImpl

class Checkout {

    private final RepositoryInfoParser parser = new RepositoryInfoParserImpl()
    private final FileCreator fileCreator
    List<RepositoryInfo> repositoryInfos;
    Script script
    Param parameters

    Checkout(Script script) {
        this.script = script
        this.parameters = new Param(script)
        this.fileCreator = new FileCreator(script)
    }

    Checkout(Param param) {
        this.script = param.script
        this.parameters = param
        this.fileCreator = new FileCreator(param.script)
    }


    List<GitWrapper> getPreparedGitWrappers() {
        println("!!!!START PREPARATION!!!!!")
        fileCreator.makeDir(parameters.workingDirectory)
        repositoryInfos = parser.parse(parameters.gitInfo)
        println("!!!!PREPARATION FINISHED!!!!!")
        return mapToGitWrapper(repositoryInfos)
    }

    GitWrapper createBaseRepo() {
//        fileCreator.makeDir(parameters.workingDirectory.getPath() + "/basedir")
        def repoInfo = parser.parseSingle(parameters.gitBaseInfo)
        return mapToGitWrapper(repoInfo)
    }

    @NonCPS
    List<GitWrapper> mapToGitWrapper(List<RepositoryInfo> repositoryInfos) {

        List<GitWrapper> gitWrapperList = new ArrayList<>();

        println("Start wrapping")
        for (repo in repositoryInfos) {
            gitWrapperList.add(new GitWrapper(script, parameters.workingDirectory.path, repo, fileCreator))
        }
        println("Finish wrapping")

        return gitWrapperList;
    }

    @NonCPS
    GitWrapper mapToGitWrapper(RepositoryInfo repositoryInfo) {
        return new GitWrapper(script, parameters.workingDirectory.path, repositoryInfo)
    }

}
