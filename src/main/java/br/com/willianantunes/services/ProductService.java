package br.com.willianantunes.services;

import br.com.willianantunes.domain.Product;
import br.com.willianantunes.repositories.UserRepository;
import br.com.willianantunes.services.dtos.ProductDTO;
import br.com.willianantunes.services.mappers.ProductMapper;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service
public class ProductService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductMapper productMapper;

    public void bindProductToUser(ProductDTO productDTO, String userId) {

        userRepository.findById(new ObjectId(userId))
            .ifPresent(user -> {

                Product product = productMapper.productDTOToProduct(productDTO);
                product.setId(new ObjectId().toString());
                user.getProducts().add(product);

                userRepository.save(user);
            });
    }

    public void deleteProductFromUser(String productId, String userId) {

        userRepository.findById(new ObjectId(userId))
            .ifPresent(user -> {

                user.getProducts().removeIf(product -> product.getId().equals(productId));

                userRepository.save(user);
            });
    }

    public void updateProductFromUser(String productId, ProductDTO productDTO, String userId) {

        userRepository.findById(new ObjectId(userId))
            .ifPresent(user -> {

                List<Product> products = user.getProducts().stream()
                    .map(product -> product.getId().equals(productId) ? productMapper.productDTOToProduct(productDTO, product.getId()) : product)
                    .collect(toList());

                user.setProducts(products);

                userRepository.save(user);
            });
    }

    public List<ProductDTO> getAllProductsFromUser(String userId) {

        List<Product> products = userRepository.findById(new ObjectId(userId)).map(u -> u.getProducts()).orElse(emptyList());
        return productMapper.productsToProductDTOs(products);
    }
}