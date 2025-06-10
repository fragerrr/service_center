package kz.justdika.service_center.config;

import kz.justdika.service_center.repository.RepositoryScanner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.core.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.mockito.Mockito.mock;

@Configuration
@Import(value = {
        TransactionAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
})
@EntityScan(basePackages = "kz.justdika.service_center.model.entity")
@ComponentScan(value = {
        "kz.justdika.service_center.model.entity",
        "kz.justdika.service_center.service",
        "kz.justdika.service_center.repository"
})
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = RepositoryScanner.class)
public class ServiceCenterTestConfiguration {
    @Bean
    public KafkaTemplate kafkaTemplate() {
        return mock(KafkaTemplate.class);
    }
}