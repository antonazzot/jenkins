package my

import com.cloudbees.groovy.cps.NonCPS
import my.structure.FileCreator
import my.structure.RejectionException
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


    List<GitWrapper> getPreparedGitWrappers (){
        println("!!!!START PREPARATION!!!!!")
        fileCreator.makeDir(parameters.workingDirectory)
        repositoryInfos = parser.parse(parameters.gitInfo)
        println("!!!!PREPARATION FINISHED!!!!!")
        return mapToGitWrapper(repositoryInfos)
    }


    @NonCPS
    void run() {
        try {
            fileCreator.makeDir(parameters.workingDirectory)
            repositoryInfos = parser.parse(parameters.gitInfo)
            println(repositoryInfos)
            println("!!!!START REPO!!!!!")
            List<GitWrapper> gitWrapperList = mapToGitWrapper(repositoryInfos)
            println("!git wrapper was created!")
            for (wraper in gitWrapperList) {
                wraper.checkout()
            }
            println("!!!!REPOS WAS OBTAINED!!!!!")
        } catch (Exception e) {
            throw new RejectionException("Cannot checkout repository. Please make sure you have access to it.", e)
        }
    }

    @NonCPS
    List<GitWrapper> mapToGitWrapper(List<RepositoryInfo> repositoryInfos) {

        List<GitWrapper> gitWrapperList = new ArrayList<>();

        for (repo in  repositoryInfos) {
            gitWrapperList.add(new GitWrapper(script, parameters.workingDirectory.path, repo, fileCreator))
        }

        return gitWrapperList;
    }

}
