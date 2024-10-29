package org.example.storetoolproject.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.storetoolproject.exceptions.ProductNotFoundException;
import org.example.storetoolproject.exceptions.StoreProcessFailedException;
import org.example.storetoolproject.models.entities.Product;
import org.example.storetoolproject.models.requests.ProductRequest;
import org.example.storetoolproject.models.requests.ProductUpdateRequest;
import org.example.storetoolproject.repositories.ProductRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.example.storetoolproject.enums.ProductStatus.*;


@Service
@AllArgsConstructor
@Slf4j
public class StoreService {

    private final ProductRepository productRepository;

    public int saveProduct(ProductRequest productRequest) {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            Product product = Product.builder()
                    .name(productRequest.getName())
                    .category(productRequest.getCategory().getValue())
                    .pricePerUnit(productRequest.getPricePerUnit())
                    .currency(productRequest.getCurrency())
                    .stock(productRequest.getStock())
                    .brand(productRequest.getBrand())
                    .createdBy("user")
                    .createdOn(LocalDateTime.now())
                    .createdBy(username)
                    .status(productRequest.getStock() > 0 ? IN_STOCK.getValue() : NOT_STOCK.getValue())
                    .build();

            log.info("The product with name {} was saved successfuly.", productRequest.getName());

            return productRepository.save(product).getId();

        }catch (Exception e){
            log.error(e.getMessage());
            throw new StoreProcessFailedException("Error while saving product.");
        }
    }

    public List<Product> getAllAvailableProducts() {
        try {
            List<Product> allAvailableProducts = productRepository.findAll()
                    .stream()
                    .filter(product -> !DELETED.getValue().equals(product.getStatus()))
                    .toList();

            if(isNull(allAvailableProducts) || allAvailableProducts.isEmpty()){
                throw new ProductNotFoundException();
            }

            return allAvailableProducts;
        } catch (Exception e) {
            throw new StoreProcessFailedException("Error while getting available products.");
        }
    }

    public List<Product> getAllUnavailableProducts() {
        try {
            List<Product> allUnavailableProducts = productRepository.findAll()
                    .stream()
                    .filter(product -> DELETED.getValue().equals(product.getStatus()))
                    .toList();

            if(isNull(allUnavailableProducts) || allUnavailableProducts.isEmpty()){
                throw new ProductNotFoundException();
            }

            return allUnavailableProducts;

        } catch (Exception e) {
            throw new StoreProcessFailedException("Error while getting available products.");
        }
    }

    public Product updateProduct(int productId, ProductUpdateRequest productUpdateRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            Product oldProduct = productRepository.findProductById(productId)
                            .orElseThrow(() -> new ProductNotFoundException(productId));

            Map<Boolean, Product> productMap
                    = getDifferenceBetweenOldAndNewProduct(oldProduct, productUpdateRequest);

            for (Map.Entry<Boolean, Product> entry : productMap.entrySet()) {
                if (entry.getKey().equals(true)) {
                    entry.getValue().setModifiedOn(LocalDateTime.now());
                    entry.getValue().setModifiedBy(username);
                    entry.getValue().setStatus(entry.getValue().getStock() > 0 ? IN_STOCK.getValue() : NOT_STOCK.getValue());
                }
            }

            productRepository.save(oldProduct);

            log.info("The product with name {} was successfully updated.", oldProduct.getName());

            return oldProduct;
        }catch (Exception e){
            log.error(e.getMessage());
            throw new StoreProcessFailedException("Error while updating product.");
        }
    }

    public Product findProduct(Integer productId) {
        try {
            return productRepository.findProductById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(productId));
        } catch (Exception e) {
            throw new StoreProcessFailedException("Error while finding product.");
        }
    }

    public List<String> filterProducts(String status) {
        try {
            List<String> productFiltered = productRepository.findAll().stream()
                    .filter(product -> status.equals(product.getStatus()))
                    .map(Product::getName)
                    .toList();

            if(isNull(productFiltered) || productFiltered.isEmpty()){
                throw new ProductNotFoundException();
            }

            return productFiltered;
        } catch (Exception e) {
            throw new StoreProcessFailedException("Error while filtering products.");
        }
    }

    public void deleteProduct(Integer productId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            Product product = productRepository.findProductById(productId).orElseThrow(() -> new ProductNotFoundException(productId));

            product.setStatus(DELETED.getValue());
            product.setDeletedOn(LocalDateTime.now());
            product.setDeletedBy(username);

            productRepository.save(product);

            log.info("The product with name {} was successfully deleted.", product.getName());
        } catch (Exception e) {
            throw new StoreProcessFailedException("Error while deleting product.");
        }
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

        productMap.put(isProductUpdated, product);
        return productMap;
    }

}

