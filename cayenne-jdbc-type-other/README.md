## Cayenne 4.2 JDBC ``Type.OTHER`` support demo.

This example shows support for DB specific types that are not part of the JDBC spec.
Apache Cayenne supports Json and Geo types that are available in many modern RDBMS.

The demo uses ``Wkt`` and ``Json`` types, 
later additionally wrapped into custom value type ``ObjectNode`` provided by the Jackson library.

### Usage

Start PostGIS DB:

```shell script
docker run --name cayenne-postgis -p 5432:5432 -e POSTGRES_PASSWORD=cayenne -e POSTGRES_USER=cayenne -e POSTGRES_DB=cayenne-postgis -d postgis/postgis
```

Build:

```shell script
mvn package
```

Run:

```shell script
java -jar target/cayenne-jdbc-type-other-1.0-SNAPSHOT.jar -j
java -jar target/cayenne-jdbc-type-other-1.0-SNAPSHOT.jar -g
```

See Cayenne project:

```shell script
mvn cayenne-modeler:run
```