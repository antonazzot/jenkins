package my.plagiarism

class ParamPlagiarism {
    final Script script

    String externalTaskId
    Language language
    File workingDirectory
    String obtainerWorkDir

    def _ = { "${it}".trim() }


    ParamPlagiarism(Script script) {
        this.script = script
        script.env.with {
            this.externalTaskId = _(externalTaskId)
            this.language = Language.valueOf(_(language))
        }
        this.workingDirectory = new File("/tmp/repos/${externalTaskId}")
        this.obtainerWorkDir = "/var/jenkins_home/workspace/obtainer/*/tmp/repos/${externalTaskId}"
        println(externalTaskId)
    }

    ParamPlagiarism(String externalTaskId) {
        this.script = new Script() {
            @Override
            Object run() {
                return null
            }
        }
        this.externalTaskId = externalTaskId
        this.workingDirectory = new File("/Users/Anton_Tsyrkunou/Documents/SomeProject/my-jenkins/my-jenkins/src/main/resources/gitcontent")
    }
}
