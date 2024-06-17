package batch.config;

import batch.model.Product;
import batch.model.Review;
import batch.service.JsonLReader;
import batch.service.ProductProcessor;
import batch.service.ReviewProcessor;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class BatchStepConfig {

    private final EntityManagerFactory entityManagerFactory;
    private final DataSource dataSource;

    @Autowired
    public BatchStepConfig(EntityManagerFactory entityManagerFactory, DataSource dataSource) {
        this.entityManagerFactory = entityManagerFactory;
        this.dataSource = dataSource;
    }

    // JpaTransactionManager 专门为使用JPA技术栈设计，管理JPA EntityManager 的事务
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }


    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                      ProductProcessor processor) {
        return new StepBuilder("step1", jobRepository)
                .<Product, Product>chunk(10, transactionManager)
                .reader(jsonLProductReader("meta_Gift_Cards.jsonl"))
                .processor(processor)
                .writer(jsonJpaProductWriter())
                .build();
    }

    @Bean
    public Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                      ProductProcessor processor) {
        return new StepBuilder("step2", jobRepository)
                .<Product, Product>chunk(10, transactionManager)
                .reader(jsonLProductReader("meta_Magazine_Subscriptions.jsonl"))
                .processor(processor)
                .writer(jsonJpaProductWriter())
                .build();
    }

    @Bean
    public Step step3(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                      ReviewProcessor processor) {
        return new StepBuilder("step3", jobRepository)
                .<Review, Review>chunk(10, transactionManager)
                .reader(jsonLReviewReader("Gift_Cards.jsonl"))
                .processor(processor)
                .writer(jsonJpaReviewWriter())
                .build();
    }

    @Bean
    public Step step4(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                      ReviewProcessor processor) {
        return new StepBuilder("step4", jobRepository)
                .<Review, Review>chunk(10, transactionManager)
                .reader(jsonLReviewReader("Magazine_Subscriptions.jsonl"))
                .processor(processor)
                .writer(jsonJpaReviewWriter())
                .build();
    }


    public JsonLReader<Product> jsonLProductReader(String path) {
        return new JsonLReader<>(new ClassPathResource(path), Product.class);
    }

    public JsonLReader<Review> jsonLReviewReader(String path) {
        return new JsonLReader<>(new ClassPathResource(path), Review.class);
    }

    @Bean
    public JpaItemWriter<Product> jsonJpaProductWriter() {
        JpaItemWriter<Product> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public JpaItemWriter<Review> jsonJpaReviewWriter() {
        JpaItemWriter<Review> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

}
