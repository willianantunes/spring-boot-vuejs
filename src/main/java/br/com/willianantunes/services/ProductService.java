package br.com.willianantunes.services;

import br.com.willianantunes.domain.Product;
import br.com.willianantunes.domain.User;
import br.com.willianantunes.repositories.ProductRepository;
import br.com.willianantunes.repositories.UserRepository;
import br.com.willianantunes.services.dtos.ProductCreateDTO;
import br.com.willianantunes.services.dtos.ProductDTO;
import br.com.willianantunes.services.dtos.ProductUpdateDTO;
import br.com.willianantunes.services.mappers.ProductMapper;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service
public class ProductService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    public ProductDTO bindProductToUser(ProductCreateDTO productCreateDTO, String userId) {

        return userRepository.findById(new ObjectId(userId))
            .map(user -> {

                Product product = productMapper.productCreateDTOToProduct(productCreateDTO);
                product.setId(new ObjectId().toString());
                user.getProducts().add(product);
                userRepository.save(user);

                return product;
            }).map(productMapper::productToProductDTO).orElseThrow();
    }

    public void deleteProductFromUser(String productId) {

        userRepository.findUserByProductId(productId)
            .map(User::getId)
            .map(userRepository::findById)
            .map(Optional::get)
            .ifPresent(user -> {

                user.getProducts().removeIf(product -> product.getId().equals(productId));

                userRepository.save(user);
            });
    }

    public void updateProductFromUser(ProductUpdateDTO productUpdateDTO) {

        userRepository.findUserByProductId(productUpdateDTO.getId())
            .map(User::getId)
            .map(userRepository::findById)
            .map(Optional::get)
            .ifPresent(user -> {

                List<Product> products = user.getProducts().stream()
                    .map(product -> product.getId().equals(productUpdateDTO.getId()) ?
                        productMapper.productUpdateDTOToProduct(productUpdateDTO) : product)
                    .collect(toList());

                user.setProducts(products);

                userRepository.save(user);
            });
    }

    public List<ProductDTO> getAllProductsFromUser(String userId) {

        List<Product> products = userRepository.findById(new ObjectId(userId)).map(User::getProducts).orElse(emptyList());

        return productMapper.productsToProductDTOs(products);
    }

    public Optional<ProductDTO> getProductById(String productId) {

        return productRepository.findById(productId).map(productMapper::productToProductDTO);
    }
}