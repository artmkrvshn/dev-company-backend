#FROM eclipse-temurin:21-alpine
#
#WORKDIR /dev-company
#CMD ["./gradlew", "clean", "bootJar"]
#COPY build/libs/*.jar app.jar
#
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]


FROM eclipse-temurin:21-alpine
WORKDIR /dev-company
#COPY gradle gradle/
COPY . .
RUN chmod +x gradlew
RUN --mount=type=cache,target=/root/.gradle ./gradlew --no-daemon -i clean bootJar
COPY /build/libs/*.jar app.jar
EXPOSE 8080

CMD /bin/sh
ENTRYPOINT ["java", "-jar", "app.jar"]



#COPY target/ docker-message-server-1.0.0.jar message-server-1.0.0.jar
#ENTRYPOINT ["java","-jar","/message-server-1.0.0.jar"]
#ENTRYPOINT ["top", "-b"]

#ADD target/app.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]


#COPY src /home/app/src
#COPY pom.xml /home/app
#RUN mvn -f /home/app/pom.xml clean package
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","/home/app/target/spring_rest_docker.jar"]
#
#
#FROM maven:3.8.5-openjdk-17
#
#WORKDIR /bezkoder-app
#COPY . .
#RUN mvn clean install
#
#CMD mvn spring-boot:run