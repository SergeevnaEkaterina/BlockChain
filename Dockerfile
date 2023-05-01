FROM maven:3.6.1-jdk-11 as build
WORKDIR /app
COPY . .
RUN mvn verify package
FROM openjdk:11
COPY --from=build /app/target/BlockChainRealization-1.0-SNAPSHOT-jar-with-dependencies.jar /usr/local/lib/BlockChainRealization.jar
COPY src/main/resources/blockchain.properties src/main/resources/blockchain.properties
ENTRYPOINT ["java", "-jar", "/usr/local/lib/BlockChainRealization.jar"]