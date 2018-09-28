package br.com.willianantunes.services.mappers;

import br.com.willianantunes.domain.Product;
import br.com.willianantunes.services.dtos.ProductCreateDTO;
import br.com.willianantunes.services.dtos.ProductDTO;
import br.com.willianantunes.services.dtos.ProductUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductMapper {

    public Product productDTOToProduct(ProductDTO productDTO) {

        return Optional.ofNullable(productDTO).map(u ->
            Product.builder()
                .id(productDTO.getId())
                .code(productDTO.getCode())
                .name(productDTO.getName())
                .details(productDTO.getDetails())
                .price(productDTO.getPrice())
                .imageLink(productDTO.getImageLink())
                .build()).orElse(null);
    }

    public ProductDTO productToProductDTO(Product product) {

        return Optional.ofNullable(product).map(u ->
            ProductDTO.builder()
                .id(Optional.ofNullable(product.getId()).orElse(null))
                .code(product.getCode())
                .name(product.getName())
                .details(product.getDetails())
                .price(product.getPrice())
                .imageLink(product.getImageLink())
                .build()).orElse(null);
    }

    public List<ProductDTO> productsToProductDTOs(List<Product> products) {

        return products.stream()
            .filter(Objects::nonNull)
            .map(this::productToProductDTO)
            .collect(Collectors.toList());
    }

    public Product productCreateDTOToProduct(ProductCreateDTO productCreateDTO) {

        return Optional.ofNullable(productCreateDTO).map(u ->
            Product.builder()
                .code(productCreateDTO.getCode())
                .name(productCreateDTO.getName())
                .details(productCreateDTO.getDetails())
                .price(productCreateDTO.getPrice())
                .imageLink(productCreateDTO.getImageLink())
                .build()).orElse(null);
    }

    public Product productUpdateDTOToProduct(ProductUpdateDTO productUpdateDTO) {

        return Optional.ofNullable(productUpdateDTO).map(u ->
            Product.builder()
                .id(productUpdateDTO.getId())
                .code(productUpdateDTO.getCode())
                .name(productUpdateDTO.getName())
                .details(productUpdateDTO.getDetails())
                .price(productUpdateDTO.getPrice())
                .imageLink(productUpdateDTO.getImageLink())
                .build()).orElse(null);
    }
}