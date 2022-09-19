FROM eclipse-temurin
EXPOSE 8080
ADD target/registeruserapi-0.0.1-SNAPSHOT.jar registeruserapi.jar
ENTRYPOINT [ "java", "-jar", "registeruserapi.jar" ]