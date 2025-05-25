#Estágio 1: Build da aplicação
FROM maven:3.8.4-openjdk-8 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

#Copia o código fonte
COPY src ./src

#Compila a aplicação
RUN mvn clean package -DskipTests

#Estágio 2: Runtime
FROM tomcat:9.0-jdk8-openjdk

LABEL maintainer="Eduardo"

RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=builder /app/target/GerenciadorTarefas.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

#Comando pra iniciar o Tomcat
CMD ["catalina.sh", "run"]
