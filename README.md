# file-publisher

How to start the file-publisher application
---

1. Configure the AWS props in the `config.yml` file
1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/file-publisher-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`

TODO:
---

- Execute validations
- Improve error handling
- Test coverage
- Dockerize
