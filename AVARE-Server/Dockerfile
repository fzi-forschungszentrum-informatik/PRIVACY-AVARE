FROM java:8
LABEL description="Microservice fuer die Datensynchronisation"
LABEL maintainer="Lukas Struppek"
LABEL contact="lukas.struppek@student.kit.edu"
EXPOSE 8443
ADD /target/AvareSyncServer-1.0.jar /AvareSyncServer-1.0.jar
ENTRYPOINT ["java","-jar","AvareSyncServer-1.0.jar"]