package my

import my.structure.Language

class Param {
    final Script script

    String externalTaskId
    String gitInfo
    String gitBaseInfo
    Language language
    File workingDirectory
    Boolean isFinished

    def _ = { "${it}".trim() }


    Param(Script script) {
        this.script = script
        script.env.with {
            this.externalTaskId = _(externalTaskId)
            this.gitInfo = _(gitInfo)
            this.gitBaseInfo = _(gitBaseInfo)
            this.language = Language.valueOf(_(language))
            this.isFinished = _(isFinished)
        }
        this.workingDirectory = new File("/tmp/repos/${externalTaskId}")

        println(workingDirectory.getPath())
        println(externalTaskId)
        println(language)
        println(isFinished.booleanValue())
    }

    Param(String externalTaskId, String repos, Language language) {
        this.script = new Script() {
            @Override
            Object run() {
                return null
            }
        }
        this.externalTaskId = externalTaskId
        this.language = language
        this.workingDirectory = new File("/Users/Anton_Tsyrkunou/Documents/SomeProject/my-jenkins/my-jenkins/src/main/resources/gitcontent")
    }
}
