package br.com.willianantunes.services.mappers;

import br.com.willianantunes.domain.Product;
import br.com.willianantunes.services.dtos.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductMapper {

    public Product productDTOToProduct(ProductDTO productDTO) {

        return Optional.ofNullable(productDTO).map(u ->
            Product.builder()
                .code(productDTO.getCode())
                .name(productDTO.getName())
                .details(productDTO.getDetails())
                .price(productDTO.getPrice())
                .imageLink(productDTO.getImageLink())
                .build()).orElse(null);
    }

    public Product productDTOToProduct(ProductDTO productDTO, String id) {

        Product product = productDTOToProduct(productDTO);
        product.setId(id);

        return product;
    }

    public ProductDTO productToProductDTO(Product product) {

        return Optional.ofNullable(product).map(u ->
            ProductDTO.builder()
                .code(product.getCode())
                .name(product.getName())
                .details(product.getDetails())
                .price(product.getPrice())
                .imageLink(product.getImageLink())
                .build()).orElse(null);
    }
}