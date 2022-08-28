FROM adoptopenjdk/openjdk11:ubi
ARG JAR_FILE=target/*.jar
ENV IP_KEY = ${IP_KEY}
ENV WEATHER_KEY = ${WEATHER_KEY}
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dip.key=${IP_KEY}", "-Dweather.key=${WEATHER_KEY}", "-jar", "/app.jar"]