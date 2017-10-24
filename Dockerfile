FROM java:8

COPY target/scala-2.12/AkkaClusterExample-assembly-1.0.jar /bin/AkkaClusterExample-assembly-1.0.jar
COPY bin/_start.sh /bin/_start.sh

RUN chmod +x /bin/_start.sh
RUN chmod +x /bin/AkkaClusterExample-assembly-1.0.jar

EXPOSE 8080

CMD ["/bin/_start.sh"]