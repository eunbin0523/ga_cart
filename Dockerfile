FROM openjdk:18-jdk-alpine AS gogle
ADD Spring-Shopping-Cart.tar .
WORKDIR Spring-Shopping-Cart
RUN chmod +x mvnw
RUN ./mvnw clean install -DskipTests=true

FROM openjdk:18-jdk-alpine
COPY --from=gogle /Spring-Shopping-Cart/target/SpringShoppingCart2-0.0.1-SNAPSHOT.jar /
CMD ["java", "-jar", "-Dspring.profiles.active=mysql", "/SpringShoppingCart2-0.0.1-SNAPSHOT.jar"]
