package br.com.soeirosantos.publisher;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class FilePublisherApplication extends Application<FilePublisherConfiguration> {

    public static void main(final String[] args) throws Exception {
        new FilePublisherApplication().run(args);
    }

    @Override
    public String getName() {
        return "filePublisher";
    }

    @Override
    public void initialize(final Bootstrap<FilePublisherConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final FilePublisherConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
