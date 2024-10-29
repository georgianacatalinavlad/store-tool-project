package org.example.storetoolproject;

import org.example.storetoolproject.enums.CategoryTypes;
import org.example.storetoolproject.models.entities.Product;
import org.example.storetoolproject.models.requests.ProductRequest;

import static org.example.storetoolproject.enums.ProductStatus.IN_STOCK;

public class StoreFactoryTest {

    public static Product getSavedProduct() {
        return Product.builder()
                .id(1)
                .name("Test name")
                .brand("Test brand")
                .category("FOOD")
                .pricePerUnit(432.00)
                .currency("USD")
                .stock(320)
                .createdBy("admin")
                .status(IN_STOCK.getValue())
                .build();
    }

    public static Product getUpdatedProduct() {
        return Product.builder()
                .id(1)
                .name("Test name")
                .brand("Test brand")
                .category("FOOD")
                .currency("USD")
                .pricePerUnit(333.00)
                .stock(3)
                .createdBy("user")
                .build();
    }

    public static ProductRequest getProductRequest() {
        return ProductRequest.builder()
                .name("Test name")
                .brand("Test brand")
                .category(CategoryTypes.FOOD)
                .currency("USD")
                .pricePerUnit(333.00)
                .stock(3)
                .build();
    }
}
