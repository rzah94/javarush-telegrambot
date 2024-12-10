FROM eclipse-temurin:21
ARG JAR_FILE=target
ENV BOT_NAME=${BOT_NAME}
ENV BOT_TOKEN=${BOT_TOKEN}
RUN mkdir /opt/app
COPY target/japp.jar /opt/app
CMD ["java", "-jar", "/opt/app/japp.jar"]