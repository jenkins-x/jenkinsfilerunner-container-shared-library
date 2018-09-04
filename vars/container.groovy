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
            error("Multiple container names are not yet supported in 'container(name) { ... }' blocks! So cannot run command in container '${name}' as we have already used container '${globalContainerName}'")
        }
    }
    
    body()
}
