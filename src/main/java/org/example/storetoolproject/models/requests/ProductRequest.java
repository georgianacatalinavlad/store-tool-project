package org.example.storetoolproject.models.requests;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.example.storetoolproject.enums.CategoryTypes;
import org.example.storetoolproject.enums.ProductStatus;

@Data
@Builder
@AllArgsConstructor
public class ProductRequest {
    @NonNull
    @NotBlank(message = "Name should not be empty")
    private String name;
    @NonNull
    @NotBlank(message = "Price should not be empty")
    private Integer pricePerUnit;
    @NonNull
    @NotBlank(message = "Currency should not be empty")
    private String currency;
    @NonNull
    @NotBlank(message = "Stock should not be empty")
    private Integer stock;
    @NonNull
    @NotBlank(message = "Category should not be empty")
    @Enumerated(EnumType.STRING)
    private CategoryTypes category;
    @NonNull
    @NotBlank(message = "Brand should not be empty")
    private String brand;
    @NonNull
    @NotBlank(message = "Status should not be empty")
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
}
