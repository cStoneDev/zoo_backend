▎ zoobackend

### Backend in Spring Boot 3.3.6 for the zoofrontend project

> Note: Make sure to check the application.properties of your setup, watch for the DB configuration, most importantly the URL (name of your DB).

In the DB folder you'll find two files: a .sql and a .backup to properly set up your DB; choose whichever you prefer.

▎ Running the Project

To run the project, navigate to the project folder and execute:

mvnw spring-boot:run   # for cmd
./mvnw spring-boot:run  # for bash


This will automatically install all the necessary dependencies and everything related to Spring Boot.

To access Swagger, open the following link in your browser:

http://localhost:8080/swagger-ui/index.html


▎ Dependencies

To add dependencies in the pom.xml file, write the necessary XML code for it, and then execute:

mvnw clean install   # for cmd
./mvnw clean install  # for bash


It is important to run this command every time you make changes to the pom.xml.

That's all for now!