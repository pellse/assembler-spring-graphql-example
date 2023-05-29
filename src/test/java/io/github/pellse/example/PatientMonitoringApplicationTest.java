package io.github.pellse.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.testcontainers.utility.DockerImageName.parse;

@TestConfiguration
public class PatientMonitoringApplicationTest {

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgreSQLContainer() {
        return new PostgreSQLContainer<>("postgres:15.1-alpine");
    }

    @Bean
    @ServiceConnection
    MongoDBContainer mongoDBContainer() {
        return new MongoDBContainer("mongo:5.0");
    }

    @Bean
    @ServiceConnection
    public KafkaContainer kafka() {
        return new KafkaContainer(parse("confluentinc/cp-kafka:7.4.0"))
                .withKraft()
                .withReuse(true);
    }

    public static void main(String[] args) {

        SpringApplication
                .from(PatientMonitoringApplication::main)
                .with(PatientMonitoringApplicationTest.class)
                .run(args);
    }
}
