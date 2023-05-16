package pe.com.ci.migration.migrationservice.config;

import com.azure.spring.data.cosmos.core.query.CosmosPageRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

import org.springframework.data.domain.Page;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import pe.com.ci.migration.migrationservice.component.ClinicalRecordItemProcessor;
import pe.com.ci.migration.migrationservice.component.ClinicalRecordItemReader;
import pe.com.ci.migration.migrationservice.component.FacturaItemWriter;
import pe.com.ci.migration.migrationservice.entity.ClinicalRecord;
import pe.com.ci.migration.migrationservice.entity.Factura;
import pe.com.ci.migration.migrationservice.component.PageRangePartitioner;
import pe.com.ci.migration.migrationservice.enums.StatusExpediente;
import pe.com.ci.migration.migrationservice.repository.ClinicalRecordRepository;
import pe.com.ci.migration.migrationservice.repository.DocumentRepository;
import pe.com.ci.migration.migrationservice.repository.FacturaRepository;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@Slf4j
public class BatchConfig {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private ClinicalRecordRepository clinicalRecordRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Value("${app.custom.batch.page-size}")
    private int pageSize;

    @Value("${app.custom.batch.grid-size}")
    private int gridSize;

    @Value("${app.custom.batch.max-pool-size}")
    private int maxPoolSize;

    @Value("${app.custom.batch.core-pool-size}")
    private int corePoolSize;

    @Value("${app.custom.batch.queue-capacity}")
    private int queueCapacity;

    @Bean
    @StepScope
    public ClinicalRecordItemReader reader(
            @Value("#{stepExecutionContext['startPageNumber']}") int startPageNumber,
            @Value("#{stepExecutionContext['endPageNumber']}") int endPageNumber
    ) {
        return new ClinicalRecordItemReader(clinicalRecordRepository, pageSize, startPageNumber, endPageNumber);
    }

    @Bean
    public ClinicalRecordItemProcessor processor() {
        return new ClinicalRecordItemProcessor(modelMapper, documentRepository);
    }

    @Bean
    public FacturaItemWriter writer() {
        return new FacturaItemWriter(facturaRepository);
    }

    @Bean
    public Step slaveStep() {
        return stepBuilderFactory
                .get("slaveStep")
                .<ClinicalRecord, Factura>chunk(pageSize)
                .reader(reader(0, 0))
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public PageRangePartitioner partitioner() {
        Page<ClinicalRecord> clinicalRecordPage = clinicalRecordRepository
                .findAllByEstado(
                        StatusExpediente.EXPEDIENTE_PENDIENTE.name(),
                        new CosmosPageRequest(0, pageSize, null));

        return new PageRangePartitioner(clinicalRecordPage.getTotalPages());
    }

    @Bean
    public PartitionHandler partitionHandler() {
        TaskExecutorPartitionHandler taskExecutorPartitionHandler = new TaskExecutorPartitionHandler();
        taskExecutorPartitionHandler.setGridSize(gridSize);
        taskExecutorPartitionHandler.setTaskExecutor(taskExecutor());
        taskExecutorPartitionHandler.setStep(slaveStep());
        return taskExecutorPartitionHandler;
    }

    @Bean
    public Step masterStep() {
        return stepBuilderFactory
                .get("masterStep")
                .partitioner(slaveStep().getName(), partitioner())
                .partitionHandler(partitionHandler())
                .build();
    }

    @Bean
    public JobRepository getJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.setTablePrefix("BATCH_");
        factory.setMaxVarCharLength(1000);
        return factory.getObject();
    }

    @Bean
    public Job runJob() throws Exception {
        return jobBuilderFactory
                .get("migracionClinicalRecordJob")
                .repository(getJobRepository())
                .flow(masterStep())
                .end()
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        return threadPoolTaskExecutor;
    }
}