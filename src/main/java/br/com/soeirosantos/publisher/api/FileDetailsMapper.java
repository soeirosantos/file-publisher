package br.com.soeirosantos.publisher.api;

import br.com.soeirosantos.publisher.core.entity.FileMetadata;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class FileDetailsMapper {

    private final ModelMapper modelMapper;

    public FileDetailsMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureMapper();
    }

    public FileMetadata toEntity(FileDetails details) {
        return modelMapper.map(details, FileMetadata.class);
    }

    private void configureMapper() {
        final Converter<Date, LocalDateTime> toLocalDateTime =
                ctx -> ctx.getSource() == null ? null:
                        LocalDateTime.ofInstant(ctx.getSource().toInstant(), ZoneId.systemDefault());
        TypeMap<FileDetails, FileMetadata> typeMap = modelMapper.createTypeMap(FileDetails.class, FileMetadata.class);
        typeMap.addMappings(mapper ->
                mapper.using(toLocalDateTime).map(FileDetails::getCreated, FileMetadata::setCreated));
        typeMap.addMappings(mapper ->
                mapper.using(toLocalDateTime).map(FileDetails::getLastModified, FileMetadata::setLastModified));
    }
}
