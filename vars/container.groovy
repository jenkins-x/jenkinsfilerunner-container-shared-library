#!/usr/bin/groovy

/** simulates the container(name) { ... } expression in kubernetes-plugin */
def call(String name = "", body) {
    // evaluate the body block, and collect configuration into the object
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config

    echo "invoking container ${name}"

    def key = "io.jenkins.jenkinsfile-runner.container"
    def globalContainerName = System.getProperty(key)
    if (!globalContainerName) {
        System.setProperty(key, name)
    } else {
        if (globalContainerName != name) {
            echo "WARNING: multiple container images are not yet supported in 'container(x) { ... }' blocks! So cannot run command in conatiner ${name} so using ${globalContainerName} instead!"
        }
    }
    
    body()
}
