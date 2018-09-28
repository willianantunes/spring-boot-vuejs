package br.com.willianantunes.controllers;

import br.com.willianantunes.controllers.util.ResponseUtil;
import br.com.willianantunes.services.ProductService;
import br.com.willianantunes.services.dtos.ProductCreateDTO;
import br.com.willianantunes.services.dtos.ProductDTO;
import br.com.willianantunes.services.dtos.ProductUpdateDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriTemplate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static br.com.willianantunes.controllers.components.UrlBuilder.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(REQUEST_PATH_API)
public class ProductController {

    private final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping(value = REQUEST_PATH_PRODUCT_GET_ALL_FROM_USER, produces = APPLICATION_JSON_UTF8_VALUE)
    public List<ProductDTO> products(@PathVariable String id) {

        log.info("REST request to get all Products from the User's id: {}", id);

        return productService.getAllProductsFromUser(id);
    }

    @GetMapping(value = REQUEST_PATH_PRODUCT_GET_OR_DELETE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ProductDTO> product(@PathVariable String id) {

        log.info("REST request to get Product : {}", id);

        Optional<ProductDTO> product = productService.getProductById(id);

        return ResponseUtil.wrapOrNotFound(product);
    }

    @PostMapping(value = REQUEST_PATH_PRODUCT_POST, consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> createProduct(@Valid @RequestBody ProductCreateDTO productCreateDTO, @PathVariable String id) {

        log.info("REST request to save the following Product to the User's id {}: {}", id, productCreateDTO);

        ProductDTO createdProductDTO = productService.bindProductToUser(productCreateDTO, id);

        return ResponseEntity.created(new UriTemplate(REQUEST_PATH_API + REQUEST_PATH_PRODUCT_GET_OR_DELETE).expand(createdProductDTO.getId())).build();
    }

    @PutMapping(value = REQUEST_PATH_PRODUCT_PUT, consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> updateProduct(@Valid @RequestBody ProductUpdateDTO productUpdateDTO) {

        log.info("REST request to update Product : {}", productUpdateDTO);

        productService.updateProductFromUser(productUpdateDTO);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = REQUEST_PATH_PRODUCT_GET_OR_DELETE)
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {

        log.info("REST request to delete Product : {}", id);

        productService.deleteProductFromUser(id);

        return ResponseEntity.ok().build();
    }
}