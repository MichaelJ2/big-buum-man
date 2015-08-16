@ECHO OFF
set MAVEN_OPTS="-Djava.library.path=target/natives"
mvn test
pause
exit
