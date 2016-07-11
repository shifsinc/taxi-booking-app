# Pull base image
FROM tomcat:8-jre8
VOLUME /tmp

#Updating configuration files
RUN rm /usr/local/tomcat/conf/server.xml /usr/local/tomcat/conf/tomcat-users.xml /usr/local/tomcat/conf/context.xml
COPY context.xml /usr/local/tomcat/conf/context.xml
COPY server.xml /usr/local/tomcat/conf/server.xml
COPY tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
COPY setenv.sh /usr/local/tomcat/bin/setenv.sh

#Copying configuration
RUN mkdir /srv/microservices
ADD config /srv/microservices/config

COPY secrets.properties /srv/microservices/secrets.properties

#Copying artifcat
COPY microservices.war /usr/local/tomcat/webapps/microservices.war

#Initializing New Relic APM
RUN cd /usr/local/tomcat
COPY newrelic-java.zip  /usr/local/tomcat/newrelic-java.zip
RUN unzip newrelic-java.zip
COPY newrelic-key.txt /usr/local/tomcat/newrelic-key.txt
RUN export NEWRELICKEY=`cat newrelic-key.txt` && sed -i -e "s/<%= license_key %>/$NEWRELICKEY/g" /usr/local/tomcat/newrelic/newrelic.yml
RUN java -jar /usr/local/tomcat/newrelic/newrelic.jar install

# Maintainer
MAINTAINER "TS Shriram <tsshriram@live.in">
