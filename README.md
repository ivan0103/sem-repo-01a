# CSE2115 - Project

### Running 
`gradle :microservice_name:bootRun`

### Testing
```shell
gradle test
```

To generate a coverage report:
```shell
gradle :microservice_name:jacocoTestCoverageVerification
```


And
```shell
gradle :microservice_name:jacocoTestReport
```
The coverage report is generated in: build/reports/jacoco/test/html and then parsed to get coverage on gitlab.

### Static analysis
```shell
gradle :microservice_name:checkStyleMain
gradle :microservice_name:checkStyleTest
gradle :microservice_name:pmdMain
gradle :microservice_name:pmdTest
```

### Notes
- You should have a local .gitignore file to make sure that any OS-specific and IDE-specific files do not get pushed to the repo (e.g. .idea). These files do not belong in the .gitignore on the repo.
- If you change the name of the repo to something other than template, you should also edit the build.gradle file.
- You can add issue and merge request templates in the .gitlab folder on your repo. 
