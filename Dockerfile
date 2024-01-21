FROM openjdk:17-ea-jdk-alpine
VOLUME /product-inventory
EXPOSE 9090
ADD ./target/product-inventory-1.0.0.jar product-inventory.jar
ENTRYPOINT ["java","-jar","/product-inventory.jar"]