FROM amazoncorretto:21.0.5
VOLUME /tmp
RUN yum -y install shadow-utils
RUN useradd -ms /bin/bash spring-user
USER spring-user
COPY build/libs/comment-api-1.0.0-SNAPSHOT.jar comment.jar

ENTRYPOINT ["java", "-jar", "/comment.jar"]
