package br.com.soeirosantos.publisher.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileDetails {

    @NotBlank
    @Length(max = 255)
    private final String name;

    @NotBlank
    @Length(max = 500)
    private final String virtualDir;

    @JsonIgnore
    private String originalName;
}
