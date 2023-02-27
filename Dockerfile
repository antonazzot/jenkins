FROM jenkins/jenkins:2.391-jdk17

USER root
RUN apt-get update
RUN apt-get install apt-transport-https dirmngr wget software-properties-common lsb-release ca-certificates -y
RUN apt-key adv --keyserver keyserver.ubuntu.com --recv-keys D7CC6F019D06AF36

RUN echo "RUN JENKINS PLUGIN CLI"
COPY target/resources/plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt --list --verbose

RUN echo "RUN  COPY INIT.GROOVY"
COPY target/resources/init.groovy /usr/share/jenkins/ref/init.groovy.d/

### Global configuration (clouds, secrets, etc.)
RUN echo "RUN  COPY GLOBAl CONFIG"
COPY target/resources/config/*.groovy /usr/share/my/config/

RUN echo "REPO CaMEL"
COPY src/main/resources/gitcontent /usr/share/my/gitcontent/

### Job configuration
RUN echo "RUN  COPY MY-JOB CONFIG"
COPY target/resources/my-job.groovy /usr/share/my/

### Pipeline library files
RUN echo "RUN  COPY Pipeline CONFIG"
COPY target/my-pipeline-library /usr/share/my/my-pipeline-library/