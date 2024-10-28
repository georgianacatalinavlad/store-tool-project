package org.example.storetoolproject.controllers;

import lombok.AllArgsConstructor;
import org.example.storetoolproject.models.entities.Product;
import org.example.storetoolproject.models.requests.ProductRequest;
import org.example.storetoolproject.models.requests.ProductUpdateRequest;
import org.example.storetoolproject.services.StoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "api/store")
public class StoreController {

    private final StoreService storeService;

    @GetMapping(value = "/available-products")
    private List<Product> getAllAvailableProducts() {
        return storeService.getAllAvailableProducts();
    }

    @GetMapping(value = "/unavailable-products")
    private List<Product> getAllUnavailableProducts() {
        return storeService.getAllUnavailableProducts();
    }

    @PutMapping(value = "/save-product")
    private int saveProduct(@RequestBody(required = true) ProductRequest productRequest) {
        return storeService.saveProduct(productRequest);
    }

    @PostMapping(value = "/update-product")
    private Product updatePriceProduct(@RequestParam(required = true) int productId, @RequestBody(required = false) ProductUpdateRequest productUpdateRequest) {
        return storeService.updatePriceProduct(productId, productUpdateRequest);
    }

    @DeleteMapping(value = "/delete-product")
    private void deleteProduct(@RequestParam(required = true) Integer productId) {
        storeService.deleteProduct(productId);
    }

    @GetMapping(value = "/find-product")
    private Product findProduct(@RequestParam(required = true) Integer productId) {
        return storeService.findProduct(productId);
    }

    @GetMapping(value = "/filter-products")
    private List<String> filterProductsByStatus(@RequestParam(required = true) String status) {
        return storeService.filterProducts(status);
    }
}

