@ECHO OFF
set MAVEN_OPTS="-Djava.library.path=target/natives"
mvn integration-test > log.txt
