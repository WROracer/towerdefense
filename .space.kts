job("Build, Test, Deploy"){
    container("maven:3-openjdk-8-slim"){ 
        env["TD_VERSION"] = Params("td_version")
        shellScript("Build Test Deploy") {
            content = """
                echo Setup ...
                mvn versions:set -DnewVersion=${'$'}TD_VERSION${'$'}JB_SPACE_EXECUTION_NUMBER
                echo Setting new Version: ${'$'}TD_VERSION${'$'}JB_SPACE_EXECUTION_NUMBER
                
                echo Build artifacts...
                mvn versions:set -DnewVersion=${'$'}TD_VERSION${'$'}JB_SPACE_EXECUTION_NUMBER
                mvn package -DskipTests 
                    -DspaceUsername=${'$'}JB_SPACE_CLIENT_ID \
                    -DspacePassword=${'$'}JB_SPACE_CLIENT_TOKEN
                
                echo Publishing Artifacts
                set -e -x -u
                mvn deploy -DskipTests
                    -DspaceUsername=${'$'}JB_SPACE_CLIENT_ID \
                    -DspacePassword=${'$'}JB_SPACE_CLIENT_TOKEN
            """
        }

    }
}