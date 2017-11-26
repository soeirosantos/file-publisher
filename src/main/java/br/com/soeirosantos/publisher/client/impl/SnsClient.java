package br.com.soeirosantos.publisher.client.impl;

import br.com.soeirosantos.publisher.client.NotificationService;
import br.com.soeirosantos.publisher.core.entity.FileMetadata;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.time.LocalDateTime;

public class SnsClient implements NotificationService {

    private final AmazonSNS amazonSns;
    private final String topicName;
    private final ObjectMapper objectMapper;

    public SnsClient(AmazonSNS amazonSns, String topicName, ObjectMapper objectMapper) {
        this.amazonSns = amazonSns;
        this.topicName = topicName;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @Override
    public String notify(FileMetadata fileMetadata) {
        NewFileEvent event = new NewFileEvent(fileMetadata.getId(), fileMetadata.getName(), fileMetadata.getSize(), LocalDateTime.now());
        PublishRequest publishRequest = new PublishRequest(topicName, objectMapper.writeValueAsString(event));
        PublishResult publishResult = amazonSns.publish(publishRequest);
        return publishResult.getMessageId();
    }
}
