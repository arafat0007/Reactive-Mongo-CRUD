FROM openjdk:8
EXPOSE 8080
ADD target/Reactive-Mongo-CRUD.jar Reactive-Mongo-CRUD.jar
ENTRYPOINT ["java", "-jar", "/Reactive-Mongo-CRUD.jar"]