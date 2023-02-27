package my

import my.structure.Language

class Param {
    final Script script

    String externalTaskId
    String gitInfo
    Language language
    File workingDirectory

    def _ = { "${it}".trim() }


    Param(Script script) {
        this.script = script
        script.env.with {
            this.externalTaskId = _(externalTaskId)
            this.gitInfo = _(gitInfo)
            this.language = Language.valueOf(_(language))
        }
        this.workingDirectory = new File("/tmp/repos/workdir_${script.env.BUILD_NUMBER}")
        println(externalTaskId)
        println(gitInfo)
        println(language)
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
