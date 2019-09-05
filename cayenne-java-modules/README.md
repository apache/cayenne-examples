Apache Cayenne example with java modules
=============

Example shows how to use Apache Cayenne in application with java modules.

- Clone:
```bash
git clone https://github.com/apache/cayenne-examples
cd cayenne-java-modules
```

- Run instance of mysql database and change url, username and password in cayenne-project.xml. 

- Build:
```bash
mvn clean install
```

- Run:
```bash
java --module-path target/cayenne-java-modules-1.0-SNAPSHOT.jar:target/lib/cayenne-server-4.1.B2.jar:target/lib/cayenne-di-4.1.B2.jar:target/lib/slf4j-api-1.7.25.jar:target/lib/mysql-connector-java-8.0.17.jar:target/lib/protobuf-java-3.6.1.jar:target/lib/slf4j-simple-1.7.25.jar --module cayenne.java.module/org.apache.cayenne.example.Main
```