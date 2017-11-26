package br.com.soeirosantos.publisher.config;

import com.amazonaws.services.sns.AmazonSNS;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SnsClientFactory {

    private final AwsConfiguration.Credentials credentials;

    public AmazonSNS build() {
        return null; // TODO -
    }
}
