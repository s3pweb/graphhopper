FROM maven:3.8.6-openjdk-8 AS build

COPY . /home/app

#RUN mkdir /home/app/target && mvn -DskipTests -f /home/app/pom.xml clean package && mv /home/app/web/target/*.jar /home/app/target
RUN mvn -DskipTests -f /home/app/pom.xml clean package

FROM openjdk:8

COPY --from=build /home/app/web/target/graphhopper-*.jar /usr/local/lib/

RUN mv /usr/local/lib/graphhopper-*.jar /usr/local/lib/graphhopper.jar

EXPOSE 8989

WORKDIR /app

ENV OVERRIDE_STREET_NAME_WITH_ID="false"

ENTRYPOINT ["java","-Xmx16g","-Xms16g","-Ddw.graphhopper.datareader.file=/data.osm.pbf","-jar","/usr/local/lib/graphhopper.jar"]
