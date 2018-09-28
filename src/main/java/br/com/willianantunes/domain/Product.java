package br.com.willianantunes.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product implements Serializable {

    @NotBlank
    private String id;
    @NotBlank
    @Indexed(unique = true)
    private String code;
    @NotBlank
    private String name;
    private String details;
    @NotNull
    private BigDecimal price;
    @URL
    private String imageLink;
}
