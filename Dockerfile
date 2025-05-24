FROM tomcat:9.0-jdk8-openjdk

LABEL maintainer="Eduardo"

RUN rm -rf /usr/local/tomcat/webapps/*

COPY target/GerenciadorTarefas.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

# Comando pra iniciar o Tomcat
CMD ["catalina.sh", "run"]
