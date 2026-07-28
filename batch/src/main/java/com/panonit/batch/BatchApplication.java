package com.panonit.batch;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@SpringBootApplication
public class BatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchApplication.class, args);
    }

    @Bean
    Job csvToDbJob(JobRepository repository, Step step) {
        return new JobBuilder("csvToDbJob", repository)
                .start(step)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    Step csvToDbStep(JobRepository repository, FlatFileItemReader<Dog> reader, JdbcBatchItemWriter<Dog> writer) {
        return new StepBuilder("csvToDbStep", repository)
                .<Dog, Dog>chunk(10)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    FlatFileItemReader<Dog> reader(@Value("classpath:/dogs.csv") Resource resource) {
        return new FlatFileItemReaderBuilder<Dog>()
                .name("dogsItemReader")
                .linesToSkip(1)
                .resource(resource)
                .fieldSetMapper(set -> new Dog(
                        set.readInt("id"),
                        set.readString("name"),
                        set.readString("owner"),
                        set.readString("description"))
                )
                .delimited()
                .names("id,name,description,dob,owner,gender,image".split(","))
                .build();
    }

    @Bean
    JdbcBatchItemWriter<Dog> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Dog>()
                .dataSource(dataSource)
                .assertUpdates(true)
                .sql("INSERT INTO dogs (id, name, description, owner) VALUES (?, ?, ?, ?)")
                .itemPreparedStatementSetter((dog, statement) -> {
                    statement.setInt(1, dog.id());
                    statement.setString(2, dog.name());
                    statement.setString(3, dog.description());
                    statement.setString(4, dog.owner());
                })
                .build();
    }

}

record Dog(int id, String name, String owner, String description) {
}

