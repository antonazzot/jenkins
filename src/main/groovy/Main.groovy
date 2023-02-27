import my.Checkout
import my.PlagiarismHandler
import my.structure.Language
import my.Param
import my.zip.ManualZipper

static void main(String[] args) {
//  println "Hello world!"
//  String repo = "ANTONAZZOT#/antonazzot/jmp_web.git#01-start-branch#sha&antobepant#/antobepant/k8s.git#kuber-persistance-voluem#sha1"
//  def checkout = new Checkout(new Param("taskid", repo, Language.JAVA))
//  checkout scmGit(branches: [[name: '*/01-casandra']], extensions: [], userRemoteConfigs: [[credentialsId: 'getthereamazinglyfast', url: 'https://github.com/antonazzot/cassandra']])
//  antonazzot#/antonazzot/jmp_web.git#01-start-branch#sha
//  antonazzot#/antonazzot/kafka-container-test.git#02-kafka-inside-docker-container#sha&antonazzot#/antonazzot/k8s.git#kuber-persistance-voluem#sha1
//  checkout.run()

  def param = new Param("taskid", "repo", Language.JAVA)
  new PlagiarismHandler(param).checkLocal()
  new ManualZipper().zip(param.workingDirectory.getPath())


}