#!/usr/bin/groovy

/** simulates the container(name) { ... } expression in kubernetes-plugin */
def call(String name = "", body) {
    // evaluate the body block, and collect configuration into the object
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config

    echo "invoking container ${name}"
    
    body()
}
