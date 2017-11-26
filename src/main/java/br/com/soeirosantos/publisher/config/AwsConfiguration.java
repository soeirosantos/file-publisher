package br.com.soeirosantos.publisher.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AwsConfiguration {

    @Valid
    @NotNull
    private Credentials credentials = new Credentials();

    @Valid
    @NotNull
    private S3 s3 = new S3();

    @Valid
    @NotNull
    private Sns sns = new Sns();

    public S3ClientFactory getS3ClientFactory() {
        return new S3ClientFactory(credentials);
    }

    public SnsClientFactory getSnsClientFactory() {
        return new SnsClientFactory(credentials);
    }

    @Getter
    @Setter
    public static class Credentials {

        @NotEmpty
        private String accessKey;

        @NotEmpty
        private String secretKey;

        public AWSCredentials build() {
            return new BasicAWSCredentials(accessKey, secretKey);
        }
    }

    @Getter
    @Setter
    public static class S3 {

        @NotEmpty
        private String bucketName;
    }

    @Getter
    @Setter
    public static class Sns {

        @NotEmpty
        private String topicName;
    }
}
