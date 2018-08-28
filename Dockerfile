FROM java:8

WORKDIR /

ADD EventProcessingApp-1.0.0.jar EventProcessingApp-1.0.0.jar

EXPOSE 8280

CMD java -jar EventProcessingApp-1.0.0.jar
EXPOSE 80

