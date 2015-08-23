@ECHO OFF
set MAVEN_OPTS="-Djava.library.path=target/natives"
cmd /k mvn test
