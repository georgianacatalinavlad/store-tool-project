package org.example.storetoolproject.models.requests;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.storetoolproject.enums.ProductStatus;

@Data
@Builder
@AllArgsConstructor
public class ProductUpdateRequest {
    private Double pricePerUnit;
    private String currency;
    private Integer stock;
}