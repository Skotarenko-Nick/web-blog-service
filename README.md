# Spring Boot Blog

## About

This is a demo project **Spring Boot Rest Service **. 

It was made using **Spring Boot**, **Spring Security**, **Spring Data JPA**, **Spring Data REST** and **Docker**, **Swagger** and **Lombok**. 
Database is in memory **H2**.

There is a login and registration functionality included.

User has his own blog page, where he can add new blog posts. 
Every authenticated user can comment on posts made by other users.
Home page is paginated list of all posts.
Non-authenticated users can see all blog posts, but cannot add new posts or comment.

## Configuration

### Configuration Files

Folder **src/resources/** contains config files for **blog-demo** Spring Boot application.

* **src/resources/application.properties** - main configuration file. Here it is possible to change admin username/password,
as well as change the port number.

## How to run

There are several ways to run the application. You can run it from the command line with included Maven Wrapper, Maven or Docker. 

Once the app starts, go to the web browser and visit `http://206.189.248.22` it redirects you to `https://206.189.248.22` with "Self Signed Certificate"

Admin username: **admin**

Admin password: **admin**

User username: **user**

User password: **password**

### Maven Wrapper

#### Using the Maven Plugin

Go to the root folder of the application and type:
```bash
$ chmod +x scripts/mvnw
$ scripts/mvnw spring-boot:run
```

#### Using Executable Jar

Or you can build the JAR file with 
```bash
$ scripts/mvnw clean package
``` 

Then you can run the JAR file:
```bash
$ java -jar target/blog-demo-0.0.1-SNAPSHOT.jar
```

### Maven

Open a terminal and run the following commands to ensure that you have valid versions of Java and Maven installed:

```bash
$ java -version
java version "1.8.0_181"
Java(TM) SE Runtime Environment (build 1.8.0_181-b13)
Java HotSpot(TM) 64-Bit Server VM (build 25.181-b13, mixed mode)
```

```bash
$ mvn -v
Apache Maven 3.3.9 (bb52d8502b132ec0a5a3f4c09453c07478323dc5; 2015-11-10T16:41:47+00:00)
Maven home: /usr/local/Cellar/maven/3.3.9/libexec
Java version: 1.8.0_102, vendor: Oracle Corporation
```

#### Using the Maven Plugin

The Spring Boot Maven plugin includes a run goal that can be used to quickly compile and run your application. 
Applications run in an exploded form, as they do in your IDE. 
The following example shows a typical Maven command to run a Spring Boot application:
 
```bash
$ mvn spring-boot:run
``` 

#### Using Executable Jar

To create an executable jar run:

```bash
$ mvn clean package
``` 

To run that application, use the java -jar command, as follows:

```bash
$ java -jar target/blog-demo-0.0.1-SNAPSHOT.jar
```

To exit the application, press **ctrl-c**.

### Docker

It is possible to run **blog-demo** using Docker:

Build Docker image:
```bash
$ mvn clean package
$ docker build -t blog-demo:dev -f docker/Dockerfile .
```

Run Docker container:
```bash
$ sudo docker run -it -p 443:443 -p 80:80 --name blog-demo blog-demo:dev --restart unless-stopped blog-demo
```

##### Helper script

It is possible to run all of the above with helper script:

```bash
$ chmod +x scripts/run_docker.sh
$ scripts/run_docker.sh
```

## Docker 

Folder **docker** contains:

* **docker/blog-demo/Dockerfile** - Docker build file for executing blog-demo Docker image. 
Instructions to build artifacts, copy build artifacts to docker image and then run app on proper port with proper configuration file.

## Util Scripts

* **scripts/run_docker.sh** - util script for running blog-demo Docker container using **docker/Dockerfile**

## Tests

Tests can be run by executing following command from the root of the project:

```bash
$ mvn test
```

## Helper Tools

### HAL REST Browser

Go to the web browser and visit `206.189.248.22`

You will need to be authenticated to be able to see this page.

### H2 Database web interface

Go to the web browser and visit `https://206.189.248.22/h2-console`

In field **JDBC URL** put 
```
jdbc:h2:mem:blog_simple_db
```

### Swagger UI Browser

Go to the web browser and visit `https://206.189.248.22/swagger-ui.html`

You will need to be authenticated to be able to see this page.

In `/src/main/resources/application.properties` file it is possible to change both
web interface url path, as well as the datasource url.