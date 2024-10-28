package org.example.storetoolproject.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.storetoolproject.models.entities.Product;
import org.example.storetoolproject.models.requests.ProductRequest;
import org.example.storetoolproject.models.requests.ProductUpdateRequest;
import org.example.storetoolproject.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;
import static org.example.storetoolproject.enums.ProductStatus.DELETED;


@Service
@AllArgsConstructor
@Slf4j
public class StoreService {

    private final ProductRepository productRepository;

    public int saveProduct(ProductRequest productRequest) {
            Product product = Product.builder()
                    .name(productRequest.getName())
                    .category(productRequest.getCategory().getValue())
                    .pricePerUnit(productRequest.getPricePerUnit())
                    .currency(productRequest.getCurrency())
                    .stock(productRequest.getStock())
                    .brand(productRequest.getBrand())
                    .createdBy("georgiana")
                    .createdOn(LocalDateTime.now())
                    .status(productRequest.getStatus().getValue())
                    .build();
            return productRepository.save(product).getId();
    }

    public List<Product> getAllAvailableProducts() {
        return productRepository.findAll()
                    .stream()
                    .filter(product -> !DELETED.getValue().equals(product.getStatus()))
                    .toList();
    }

    public List<Product> getAllUnavailableProducts() {
            return productRepository.findAll()
                    .stream()
                    .filter(product -> DELETED.getValue().equals(product.getStatus()))
                    .toList();
    }

    public Product updatePriceProduct(int productId, ProductUpdateRequest productUpdateRequest) {
        Product oldProduct =productRepository.findProductById(productId).orElse(null);
        Map<Boolean, Product> productMap
                = getDifferenceBetweenOldAndNewProduct(oldProduct, productUpdateRequest);
        productRepository.save(oldProduct);
        return oldProduct;
    }

    public Product findProduct(Integer productId) {
            return productRepository.findProductById(productId).orElse(null);
    }

    public List<String> filterProducts(String status) {
            return productRepository.findAll().stream()
                    .filter(product -> status.equals(product.getStatus()))
                    .map(Product::getName).toList();
    }

    public void deleteProduct(Integer productId) {
            Product product = productRepository.findProductById(productId).orElse(null);
            product.setStatus(DELETED.getValue());
            productRepository.save(product);
    }

    private Map<Boolean, Product> getDifferenceBetweenOldAndNewProduct(Product product, ProductUpdateRequest productUpdateRequest) {
        Map<Boolean, Product> productMap = new HashMap<>();
        boolean isProductUpdated = false;
        if(nonNull(productUpdateRequest.getPricePerUnit())
                && product.getPricePerUnit() != productUpdateRequest.getPricePerUnit()){
            product.setPricePerUnit(productUpdateRequest.getPricePerUnit());
            isProductUpdated = true;
        }
        if(nonNull(productUpdateRequest.getCurrency())
                && product.getCurrency() != productUpdateRequest.getCurrency()){
            product.setCurrency(productUpdateRequest.getCurrency());
            isProductUpdated = true;
        }
        if(nonNull(productUpdateRequest.getStock())
                && product.getStock() != productUpdateRequest.getStock()){
            product.setStock(productUpdateRequest.getStock());
            isProductUpdated = true;
        }
        if (nonNull(productUpdateRequest.getStatus().getValue())
                && product.getStatus() != productUpdateRequest.getStatus().getValue()){
            product.setStatus(productUpdateRequest.getStatus().getValue());
            isProductUpdated = true;
        }

        productMap.put(isProductUpdated, product);
        return productMap;
    }

}
