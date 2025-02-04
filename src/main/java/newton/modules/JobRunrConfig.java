package newton.modules;


import org.jobrunr.configuration.JobRunr;
import org.jobrunr.jobs.mappers.JobMapper;
import org.jobrunr.storage.StorageProvider;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jobrunr.storage.sql.common.SqlStorageProviderFactory;
import org.jobrunr.utils.mapper.jackson.JacksonJsonMapper;
import org.jobrunr.utils.mapper.jsonb.JsonbJsonMapper;

import javax.sql.DataSource;


public class JobRunrConfig {
    public StorageProvider getStorageProvider(DataSource dataSource) {
        return SqlStorageProviderFactory.using(dataSource);
    }

    public void initializeJobRunr() {
        HikariDataSource dataSource = createDataSource();
        StorageProvider storageProvider = SqlStorageProviderFactory.using(dataSource);

        JobRunr.configure()
                .useStorageProvider(storageProvider)
                .useJsonMapper(new JacksonJsonMapper())
                .initialize();
    }

    private HikariDataSource createDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:sqlite:mydatabase.db");
        dataSource.setMaximumPoolSize(10);
        return dataSource;
    }


}
