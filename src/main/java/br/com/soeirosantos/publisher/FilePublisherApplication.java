package br.com.soeirosantos.publisher;

import br.com.soeirosantos.publisher.client.StorageService;
import br.com.soeirosantos.publisher.client.impl.S3Client;
import br.com.soeirosantos.publisher.core.entity.FileMetadata;
import br.com.soeirosantos.publisher.core.service.FileMetadataService;
import br.com.soeirosantos.publisher.db.FileMetadataDao;
import br.com.soeirosantos.publisher.resources.FileResource;
import com.amazonaws.services.s3.AmazonS3;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

public class FilePublisherApplication extends Application<FilePublisherConfiguration> {

    public static void main(final String[] args) throws Exception {
        new FilePublisherApplication().run(args);
    }

    private final HibernateBundle<FilePublisherConfiguration> hibernateBundle =
            new HibernateBundle<FilePublisherConfiguration>(FileMetadata.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(FilePublisherConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    @Override
    public String getName() {
        return "filePublisher";
    }

    @Override
    public void initialize(final Bootstrap<FilePublisherConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(final FilePublisherConfiguration configuration,
                    final Environment environment) {

        environment.getObjectMapper()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        final FileMetadataDao fileMetadataDao = new FileMetadataDao(hibernateBundle.getSessionFactory());
        final AmazonS3 s3Client = configuration.getAws().getS3ClientFactory().build();
        final StorageService storageService = new S3Client(configuration.getAws().getS3().getBucketName(), s3Client);
        final FileMetadataService fileMetadataService = new FileMetadataService(fileMetadataDao, storageService);
        environment.jersey().register(MultiPartFeature.class);
        environment.jersey().register(new FileResource(fileMetadataService));
    }

}
