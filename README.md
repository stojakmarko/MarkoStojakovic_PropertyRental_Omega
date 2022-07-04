# Property Rental

***

**Spring Boot app with PostgreSQL database** \
**App has functionality to help people find accommodations**

### How to run?

To have email service in app:

* Create secrets.yml like secrets-example.yml in src/main/resources
* Add username and password


1. **Run as a packaged application**

```
    mvn clean package
    java -jar target/propertyrental-0.0.1-SNAPSHOT.jar 
```

2. **Maven**

```     
    mvn spring-boot:run
```

3. **Docker**

```
     docker-compose up
```

### Docs

* http://localhost:8080/swagger-ui/index.html
