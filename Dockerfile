FROM openjdk:14-alpine
COPY build/libs/latency_misreporting_example-*-all.jar latency_misreporting_example.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "latency_misreporting_example.jar"]