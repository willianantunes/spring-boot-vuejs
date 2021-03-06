package br.com.willianantunes.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private String id;
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    private String details;
    @NotNull
    private BigDecimal price;
    @URL
    private String imageLink;
}