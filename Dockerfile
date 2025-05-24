# Copia o pom.xml primeiro (para cache de dependências)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código fonte
COPY src ./src

# Compila a aplicação
RUN mvn clean package -DskipTests

# Estágio 2: Runtime
FROM tomcat:9.0-jdk8-openjdk

LABEL maintainer="Eduardo"

# Remove aplicações padrão do Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=builder /app/target/GerenciadorTarefas.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]