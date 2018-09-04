#!/usr/bin/groovy

def globalContainerName = ""

/** simulates the container(name) { ... } expression in kubernetes-plugin */
def call(String name = "", body) {
    // evaluate the body block, and collect configuration into the object
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config

    echo "invoking container ${name}"

    if (globalContainerName == "") {
        globalContainerName = name
    } else {
        if (globalContainerName != name) {
            echo "WARNING: multiple container images are not yet supported in 'container(x) { ... }' blocks! So cannot run command in conatiner ${name} so using ${globalContainerName} instead!"
        }
    }
    
    body()
}
