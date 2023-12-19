def cloneAndCheckoutBranch(String gitproject, String branch, String server = "git@github.com:kalpgohil8") {
    retry(10) {
        try {
            sh """ 
                #!/bin/bash -v
                set -eu
                if [ ! -e ${gitproject} ]; then
                    sudo mkdir -p ${gitproject}
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
                sudo rm -rf ${gitproject}
            """
        }
    }
}


def tmp()
{
    println "Kalp Gohil"
}

return this;