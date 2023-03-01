package my.obtainer

class Param {
    final Script script

    String externalTaskId
    String gitInfo
    String gitBaseInfo
    File workingDirectory
    String isFinished

    def _ = { "${it}".trim() }


    Param(Script script) {
        this.script = script
        script.env.with {
            this.externalTaskId = _(externalTaskId)
            this.gitInfo = _(gitInfo)
            this.gitBaseInfo = _(gitBaseInfo)
            this.isFinished = _(isFinished)
        }
        this.workingDirectory = new File("/tmp/repos/${externalTaskId}")

        println(workingDirectory.getPath())
        println(externalTaskId)
        println(isFinished)
    }

    Param(String externalTaskId) {
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
