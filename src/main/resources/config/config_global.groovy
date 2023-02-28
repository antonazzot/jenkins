package config

import hudson.plugins.filesystem_scm.FSSCM
import hudson.security.FullControlOnceLoggedInAuthorizationStrategy
import hudson.security.HudsonPrivateSecurityRealm
import hudson.security.csrf.DefaultCrumbIssuer
import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.plugin.JenkinsJobManagement
import jenkins.model.Jenkins
import org.jenkinsci.plugins.workflow.libs.GlobalLibraries
import org.jenkinsci.plugins.workflow.libs.LibraryConfiguration
import org.jenkinsci.plugins.workflow.libs.SCMRetriever
import org.slf4j.LoggerFactory

def log = LoggerFactory.getLogger(getClass())

log.info "Configuring global settings"

log.info "Updating 'my' job configuration"
def jobManagement = new JenkinsJobManagement(System.out, [:], new File('.'))
def scriptLoader = new DslScriptLoader(jobManagement)
scriptLoader.runScript(new File('/usr/share/my/checkout.groovy').text)
scriptLoader.runScript(new File('/usr/share/my/plagiarism.groovy').text)
log.info "'my' job has been successfully updated."



log.info "Setting up my-pipeline-library"
def retriever = new SCMRetriever(new FSSCM("/usr/share/my/my-pipeline-library", false, false, null))
def libConfig = new LibraryConfiguration('my-pipeline-library', retriever)
libConfig.defaultVersion = '2.0.0'
libConfig.implicit = true
GlobalLibraries.get().setLibraries(Collections.singletonList(libConfig))



log.info "Setting up security"

log.info "Registering default administrator"
def securityRealm = (HudsonPrivateSecurityRealm) Jenkins.get().getSecurityRealm()
securityRealm.createAccount(System.getenv("JENKINS_USERNAME"), System.getenv("JENKINS_PASSWORD"))

log.info "Setting up authorization strategy"
def authStrategy = new FullControlOnceLoggedInAuthorizationStrategy()
authStrategy.setAllowAnonymousRead(false)
Jenkins.get().setAuthorizationStrategy(authStrategy)

log.info "Configuring crumb issuer"
Jenkins.get().setCrumbIssuer(new DefaultCrumbIssuer(true))

log.info "Configuring number of executors"
Jenkins.get().setNumExecutors(10)

log.info "Finished configuring global settings"