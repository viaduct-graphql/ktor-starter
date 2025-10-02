# Viaduct Ktor Starter App

## Requirements

- Java JDK 21 is installed
- `JAVA_HOME` environment variable is set correctly or `java` is in the classpath

## Quick Start

Check out the [Getting Started](https://airbnb.io/viaduct/docs/getting_started/) docs.

### Start the Viaduct Ktor Starter App

```bash
./gradlew run
```

The server will start on `http://localhost:8080`.

### Test the GraphQL endpoint

#### curl

With the server running, you can use the following `curl` command to send GraphQL queries:

```bash
curl 'http://localhost:8080/graphql' -H 'content-type: application/json' --data-raw '{"query":"{ greeting }"}'
```

You should see the following output:
```json
{"data":{"greeting":"Hello, World!"}}
```
