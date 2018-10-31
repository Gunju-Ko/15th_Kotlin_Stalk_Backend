FROM openjdk:8-jre
COPY  build/libs/stalk-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]