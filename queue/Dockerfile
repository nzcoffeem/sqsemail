FROM openjdk:8-jre-alpine

ARG ELASTICMQ_VERSION
ENV ELASTICMQ_VERSION ${ELASTICMQ_VERSION:-1.2.1}

RUN apk add --no-cache curl ca-certificates
RUN mkdir -p /opt/elasticmq/log /opt/elasticmq/lib /opt/elasticmq/config
RUN curl -sfLo /opt/elasticmq/lib/elasticmq.jar https://s3-eu-west-1.amazonaws.com/softwaremill-public/elasticmq-server-${ELASTICMQ_VERSION}.jar

COPY ${PWD}/elasticmq.conf /opt/elasticmq/conf/elasticmq.conf

WORKDIR /opt/elasticmq

EXPOSE 9324

ENTRYPOINT [ "/usr/bin/java", "-Dconfig.file=/opt/elasticmq/conf/elasticmq.conf", "-jar", "/opt/elasticmq/lib/elasticmq.jar" ]