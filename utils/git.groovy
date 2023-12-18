def cloneAndCheckoutBranch(String gitproject, String branch, String path="/home/jenkins/sources", String server = "git@github.com:kalpgohil8") {
    retry(10) {
        try {
            sh """ 
                #!/bin/bash -v
                set -euo pipefail
                mkdir -p ${path}
                cd ${path}
                if [ ! -e ${gitproject} ]; then
                    mkdir -p ${gitproject}
                    cd ${gitproject}/..
                    git clone ${server}/${gitproject}
                fi
                cd ${gitproject}
                git fetch origin ${branch}
                git checkout -b ${branch} origin/${branch}
            """
        } catch (err) {
            println "Clearing directory, not fully cloned or branch not found, and retrying"
            sh """ 
                #!/bin/bash -v
                rm -rf ${path}/${gitproject}
            """
        }
    }
}


def tmp()
{
    println "Kalp Gohil"
}

return this;