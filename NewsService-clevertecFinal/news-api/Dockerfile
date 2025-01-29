FROM amazoncorretto:21.0.5
VOLUME /tmp
RUN yum -y install shadow-utils
RUN useradd -ms /bin/bash spring-user
USER spring-user
COPY build/libs/news-api-1.0.0-SNAPSHOT.jar news.jar

ENTRYPOINT ["java", "-jar", "/news.jar"]