FROM openjdk:17.0.2-jdk
VOLUME /tmp/image
ADD images-1.0-SNAPSHOT.jar image.jar
ENTRYPOINT ["java",  "-jar", "image.jar"]