package br.com.soeirosantos.publisher.client;

import br.com.soeirosantos.publisher.core.service.NotificationService;
import br.com.soeirosantos.publisher.core.entity.FileMetadata;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
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
    public void notify(FileMetadata fileMetadata) {
        NewFileEvent event = new NewFileEvent(fileMetadata.getId(), fileMetadata.getName(), fileMetadata.getSize(), LocalDateTime.now());
        PublishRequest publishRequest = new PublishRequest(topicName, objectMapper.writeValueAsString(event));
        amazonSns.publish(publishRequest);
    }
}
